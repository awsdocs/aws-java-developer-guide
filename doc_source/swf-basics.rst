.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

############
|SWF| Basics
############

These are general patterns for working with |SWF| using the |sdk-java|. It is meant primarily for
reference. For a more complete introductory tutorial, see :doc:`swf-hello`.

Dependencies
============

Basic |SWF| applications will require the following dependencies, which are included with the
|sdk-java|:

* aws-java-sdk-1.11.*.jar
* commons-logging-1.1.*.jar
* httpclient-4.3.*.jar
* httpcore-4.3.*.jar
* jackson-annotations-2.5.*.jar
* jackson-core-2.5.*.jar
* jackson-databind-2.5.*.jar
* joda-time-2.8.*.jar

.. note:: the version numbers of these packages will differ depending on the version of the SDK that
   you have, but the versions that are supplied with the SDK have been tested for compatibility, and
   are the ones you should use.

|jflow| applications require additional setup, *and* additional dependencies. See the |jflow-dg|_ for
more information about using the framework.

Imports
=======

In general, you can use the following imports for code development:

.. literalinclude:: example_code/swf/src/main/java/aws/example/helloswf/HelloTypes.java
    :language: java
    :lines: 18-19

It's a good practice to import only the classes you require, however. You will likely end up
specifying particular classes in the ``com.amazonaws.services.simpleworkflow.model`` workspace:

.. code-block:: java

  import com.amazonaws.services.simpleworkflow.model.PollForActivityTaskRequest;
  import com.amazonaws.services.simpleworkflow.model.RespondActivityTaskCompletedRequest;
  import com.amazonaws.services.simpleworkflow.model.RespondActivityTaskFailedRequest;
  import com.amazonaws.services.simpleworkflow.model.TaskList;

If you are using the |jflow|, you will import classes from the
``com.amazonaws.services.simpleworkflow.flow`` workspace. For example:

.. code-block:: java

  import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
  import com.amazonaws.services.simpleworkflow.flow.ActivityWorker;

.. note:: The |jflow| has additional requirements beyond those of the base |sdk-java|. For more
   information, see the |jflow-dg|_.


Using the SWF client class
==========================

Your basic interface to |SWF| is through either the :aws-java-class:`AmazonSimpleWorkflowClient
<services/simpleworkflow/AmazonSimpleWorkflowClient>` or :aws-java-class:`AmazonSimpleWorkflowAsyncClient
<services/simpleworkflow/AmazonSimpleWorkflowAsyncClient>` classes. The main difference between
these is that the ``*AsyncClient`` class return :javase-ref:`Future<java/util/concurrent/Future>`
objects for concurrent (asynchronous) programming.

.. code-block:: java

    AmazonSimpleWorkflowClient swf = AmazonSimpleWorkflowClientBuilder.defaultClient();



