.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

.. include:: _includes/swf_includes.txt

############
Lambda Tasks
############

As an alternative to, or in conjunction with, |SWF| activities, you can use |LAM|_ functions to
represent units of work in your workflows, and schedule them similarly to activities.

This topic focuses on how to implement |SWF| Lambda tasks using the |sdk-java|. For more information
about Lambda tasks in general, see :swf-dg:`AWS Lambda Tasks <lambda-task>` in the |SWF-dg|.


Set up a cross-service IAM role to run your Lambda function
===========================================================

Before |SWF| can run your |LAM| function, you need to set up an |IAM| role to give |SWF| permission
to run |LAM| functions on your behalf. For complete information about how to do this, see
:swf-dg:`AWS Lambda Tasks <lambda-task>`.

You will need the |ARNlong| (ARN) of this IAM role when you register a workflow that will use |LAM|
tasks.


Create a Lambda function
========================

You can write |LAM| functions in a number of different languages, including Java. For complete
information about how to author, deploy and use |LAM| functions, see the |LAM-dg|_.

.. note:: It doesn't matter what language you use to write your |LAM| function, it can be scheduled
   and run by *any* |SWF| workflow, regardless of the language that your workflow code is written
   in. |SWF| handles the details of running the function and passing data to and from it.

Here's a simple |LAM| function that could be used in place of the activity in :doc:`swf-hello`.

* This version is written in JavaScript, which can be entered directly using the |console|_:

  .. code-block:: javascript

      exports.handler = function(event, context) {
          context.succeed("Hello, " + event.who + "!");
      };

* Here is the same function written in Java, which you could also deploy and run on |LAM|:

  .. literalinclude:: snippets/helloswf_lambda/SwfHelloLambdaFunction.java
      :language: java
      :lines: 15-

  .. tip:: To learn more about deploying Java functions to |LAM|, see :lam-dg:`Creating a Deployment
      Package (Java) <lambda-java-how-to-create-deployment-package>` in the |LAM-dg|. You will also
      want to look at the section titled :lam-dg:`Programming Model for Authoring Lambda Functions
      in Java <java-programming-model>`.

|LAM| functions take an *event* or *input* object as the first parameter, and a *context* object as
the second, which provides information about the request to run the |LAM| function. This particular
function expects input to be in JSON, with a ``who`` field set to the name used to create the
greeting.


Register a workflow for use with |LAM|
======================================

For a workflow to schedule a |LAM| function, you must provide the name of the IAM role that provides
|SWF| with permission to invoke |LAM| functions. You can set this during workflow registration by
using the ``withDefaultLambdaRole`` or ``setDefaultLambdaRole`` methods of
:aws-java-class:`RegisterWorkflowTypeRequest <services/simpleworkflow/model/RegisterWorkflowTypeRequest>`.

.. literalinclude:: snippets/helloswf_lambda/src/main/java/example/swf/hellolambda/HelloTypes.java
   :language: java
   :lines: 53-65
   :dedent: 8


Schedule a |LAM| task
=====================

Schedule a |LAM| task is similar to scheduling an activity. You provide a :aws-java-class:`Decision
<services/simpleworkflow/model/Decision>` with a ``ScheduleLambdaFunction`` :aws-java-class:`DecisionType
<services/simpleworkflow/model/DecisionType>` and with
:aws-java-class:`ScheduleLambdaFunctionDecisionAttributes
<services/simpleworkflow/model/ScheduleLambdaFunctionDecisionAttributes>`.

.. literalinclude:: snippets/helloswf_lambda/src/main/java/example/swf/hellolambda/WorkflowWorker.java
   :language: java
   :lines: 118-134
   :dedent: 16

In the ``ScheduleLambdaFuntionDecisionAttributes``, you must supply a *name*, which is the ARN of
the |LAM| function to call, and an *id*, which is the name that |SWF| will use to identify the |LAM|
function in history logs.

You can also provide optional *input* for the |LAM| function and set its *start to close timeout*
value, which is the number of seconds that the |LAM| function is allowed to run before generating a
``LambdaFunctionTimedOut`` event.

.. tip:: This code uses the :aws-java-class:`AWSLambdaClient <services/lambda/AWSLambdaClient>` to
   retrieve the ARN of the |LAM| function, given the function name. You can use this technique to
   avoid hard-coding the full ARN (which includes your AWS account ID) in your code.


Handle |LAM| function events in your decider
============================================

|LAM| tasks will generate a number of events that you can take action on when polling for decision
tasks in your workflow worker, corresponding to the lifecycle of your |LAM| task, with
:aws-java-class:`EventType <services/simpleworkflow/model/EventType>` values such as
``LambdaFunctionScheduled``, ``LambdaFunctionStarted``, and ``LambdaFunctionCompleted``. If the
|LAM| function fails, or takes longer to run than its set timeout value, you will receive either a
``LambdaFunctionFailed`` or ``LambdaFunctionTimedOut`` event type, respectively.

.. literalinclude:: snippets/helloswf_lambda/src/main/java/example/swf/hellolambda/WorkflowWorker.java
   :language: java
   :lines: 72-106
   :dedent: 8


Receive output from your |LAM| function
=======================================

When you receive a ``LambdaFunctionCompleted`` :aws-java-class:`EventType
<services/simpleworkflow/model/EventType>`, you can retrieve your |LAM| function's return value by
first calling :methodname:`getLambdaFunctionCompletedEventAttributes` on the :aws-java-class:`HistoryEvent
<services/simpleworkflow/model/HistoryEvent>` to get a :aws-java-class:`LambdaFunctionCompletedEventAttributes
<services/simpleworkflow/model/LambdaFunctionCompletedEventAttributes>` object, and then calling its
:methodname:`getResult` method to retrieve the output of the |LAM| function:

.. literalinclude:: snippets/helloswf_lambda/src/main/java/example/swf/hellolambda/WorkflowWorker.java
   :language: java
   :lines: 95-96
   :dedent: 16


Complete source for this example
================================

You can browse the `complete source
:github:`<awsdocs/aws-java-developer-guide/tree/master/doc_source/snippets/helloswf_lambda/>` for
this example on Github in the *aws-java-developer-guide* repository.

