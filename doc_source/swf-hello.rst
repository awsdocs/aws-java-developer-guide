.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

###################################
Building a Simple |SWF| Application
###################################

This topic will introduce you to programming |SWF|_ applications with the |sdk-java|, while
presenting a few important concepts along the way.


About the example
=================

The example project will create a workflow with a single activity that accepts workflow data passed
through the AWS cloud (In the tradition of HelloWorld, it'll be the name of someone to greet) and
then prints a greeting in response.

While this seems very simple on the surface, |SWF| applications consist of a number of
parts working together:

* A **domain**, used as a logical container for your workflow execution data.

* One or more **workflows** which represent code components that define logical order of
  execution of your workflow's activities and child workflows.

* A **workflow worker**, also known as a *decider*, that polls for decision tasks and schedules
  activities or child workflows in response.

* One or more **activities**, each of which represents a unit of work in the workflow.

* An **activity worker** that polls for activity tasks and runs activity methods in response.

* One or more **task lists**, which are queues maintained by |SWF| used to issue requests to the
  workflow and activity workers. Tasks on a task list meant for workflow workers are called
  *decision tasks*. Those meant for activity workers are called *activity tasks*.

* A **workflow starter** that begins your workflow execution.

Behind the scenes, |SWF| orchestrates the operation of these components, coordinating their flow
from the AWS cloud, passing data between them, handling timeouts and heartbeat notifications, and
logging workflow execution history.


Prerequisites
=============

Development environment
-----------------------

The development environment used in this tutorial consists of:

* The |sdk-java|_.
* `Apache Maven <http://maven.apache.org/>`_ (3.3.1).
* JDK 1.7 or later. This tutorial was developed and tested using JDK 1.8.0.
* A good Java text editor (your choice).

.. note:: If you use a different build system than Maven, you can still create a project using the
   appropriate steps for your environment and use the the concepts provided here to follow along.
   More information about configuring and using the |sdk-java| with various build systems is
   provided in :doc:`getting-started`.

   Likewise, but with more effort, the steps shown here can be implemented using any of the AWS SDKs
   with support for |SWF|.

All of the necessary external dependencies are included with the |sdk-java|, so there's nothing
additional to download.

AWS access
----------

To access Amazon Web Services (AWS), you must have an active AWS account. For information about
signing up for AWS and creating an |IAM| user (recommended over using root account credentials), see
:doc:`signup-create-iam-user`.

This tutorial uses the terminal (command-line) to run the example code, and expects that you have
your AWS credentials and configuration accessible to the SDK. The easiest way to do this is to use
the environment variables :envvar:`AWS_ACCESS_KEY_ID` and :envvar:`AWS_SECRET_ACCESS_KEY`. You
should also set the :envvar:`AWS_REGION` to the region you want to use.

For example, on |unixes|, set the variables this way:

.. code-block:: sh

    export AWS_ACCESS_KEY_ID=your_access_key_id
    export AWS_SECRET_ACCESS_KEY=your_secret_access_key
    export AWS_REGION=us-east-1

To set these variables on Windows, use these commands:

.. code-block:: bat

    set AWS_ACCESS_KEY_ID=your_access_key_id
    set AWS_SECRET_ACCESS_KEY=your_secret_access_key
    set AWS_REGION=us-east-1

.. important:: Substitute your own access key, secret access key and region information for the
   example values shown here.

   For more information about configuring your credentials for the SDK, see :doc:`setup-credentials`.


Create a SWF project
====================

#. Start a new project with Maven:

   .. code-block:: sh

       mvn archetype:generate -DartifactId=helloswf \
       -DgroupId=aws.example.helloswf -DinteractiveMode=false

   This will create a new project with a standard maven project structure:

   .. code-block:: none

       helloswf
       ├── pom.xml
       └── src
           ├── main
           │   └── java
           │       └── aws
           │           └── example
           │               └── helloswf
           │                   └── App.java
           └── test
               └── ...

   You can ignore or delete the :file:`test` directory and all it contains, we won't be using it
   for this tutorial.  You can also delete :file:`App.java`, since we'll be replacing it with
   new classes.

#. Edit the project's :file:`pom.xml` file and add the **aws-java-sdk-simpleworkflow** module by
   adding a dependency for it within the :code-xml:`<dependencies>` block.

   .. literalinclude:: example_code/swf/pom.xml
       :language: xml
       :lines: 10-16
       :dedent: 2

#. *Make sure that Maven builds your project with JDK 1.7+ support*. Add the following to your
   project (either before or after the :code-xml:`<dependencies>` block) in :file:`pom.xml`:

   .. literalinclude:: example_code/swf/pom.xml
       :language: xml
       :lines: 17-28
       :dedent: 2

Code the project
================

The example project will consist of four separate applications, which we'll visit one
by one:

* **HelloTypes.java** |mdash| contains the project's domain, activity and workflow type data, shared
  with the other components. It also handles registering these types with SWF.

* **ActivityWorker.java** |mdash| contains the activity worker, which polls for activity tasks and
  runs activities in response.

* **WorkflowWorker.java** |mdash| contains the workflow worker (decider), which polls for decision
  tasks and schedules new activities.

* **WorkflowStarter.java** |mdash| contains the workflow starter, which starts a new workflow
  execution, which will cause SWF to start generating decision and workflow tasks for your workers
  to consume.

.. _swf-hello-common:

Common steps for all source files
---------------------------------

All of the files that you create to house your Java classes will have a few things in common. In the
interest of time, these steps *will be implied every time you add a new file to the project*:

#. Create the file in the in the project's :file:`src/main/java/example/swf/hello/` directory.

#. Add a :code-java:`package` declaration to the beginning of each file to declare its namespace.
   The example project uses:

   .. literalinclude:: swf.java.hello_types.package.txt
       :language: java

#. Add :code-java:`import` declarations for the :aws-java-class:`AmazonSimpleWorkflowClient
   <services/simpleworkflow/AmazonSimpleWorkflowClient>` class and for multiple classes in the
   ``com.amazonaws.services.simpleworkflow.model`` namespace. To simplify things, we'll use:

   .. literalinclude:: swf.java.hello_types.import.txt
       :language: java

.. _swf-hello-hellotypes:

Register a domain, workflow and activity types
----------------------------------------------

We'll begin by creating a new executable class, :file:`HelloTypes.java`. This file will contain shared
data that different parts of your workflow will need to know about, such as the name and version of
your activity and workflow types, the domain name and the task list name.

#. Open your text editor and create the file :file:`HelloTypes.java`, adding a package declaration and
   imports according to the :ref:`common steps <swf-hello-common>`.

#. Declare the :classname:`HelloTypes` class and provide it with values to use for your registered
   activity and workflow types:

   .. literalinclude:: swf.java.hello_types.string_declare.txt
       :language: java

   These values will be used throughout the code.

#. After the String declarations, create an instance of the :aws-java-class:`AmazonSimpleWorkflowClient
   <services/simpleworkflow/AmazonSimpleWorkflowClient>` class. This is the basic interface to the
   |SWF| methods provided by the |sdk-java|.

   .. literalinclude:: swf.java.hello_types.client.txt
       :language: java
       :dedent: 4

#. Add a new function to register a SWF domain. A *domain* is a logical container for a number of
   related SWF activity and workflow types. SWF components can only communicate with each other if
   they exist within the same domain.

   .. literalinclude:: swf.java.hello_types.new_function.txt
       :language: java
       :dedent: 4

   When you register a domain, you provide it with a *name* (any set of 1 |ndash| 256 characters
   excluding ``:``, ``/``, ``|``, control characters or the literal string 'arn') and a *retention
   period*, which is the number of days that |SWF| will keep your workflow's execution history data
   after a workflow execution has completed. The maximum workflow execution retention period is 90
   days. See :aws-java-class:`RegisterDomainRequest <services/simpleworkflow/model/RegisterDomainRequest>`
   for more information.

   If a domain with that name already exists, a :aws-java-class:`DomainAlreadyExistsException
   <services/simpleworkflow/model/DomainAlreadyExistsException>` is raised. Because we're
   unconcerned if the domain has already been created, we can ignore the exception.

   .. tip:: This code demonstrates a common pattern when working with |sdk-java| methods, data for
       the method is supplied by a class in the ``simpleworkflow.model`` namespace, which you
       instantiate and populate using the chainable ``.with*`` methods.

#. Add a function to register a new activity type. An *activity* represents a unit of work in your
   workflow.

   .. literalinclude:: swf.java.hello_types.new_activity_type.txt
       :language: java
       :dedent: 4

   An activity type is identified by a *name* and a *version*, which are used to uniquely identify
   the activity from any others in the domain that it's registered in. Activities also contain a
   number of optional parameters, such as the default task-list used to receive tasks and data from
   SWF and a number of different timeouts that you can use to place constraints upon how long
   different parts of the activity execution can take. See :aws-java-class:`RegisterActivityTypeRequest
   <services/simpleworkflow/model/RegisterActivityTypeRequest>` for more information.

   .. tip:: All timeout values are specified in *seconds*. See :swf-dg:`Amazon SWF Timeout Types
       <swf-timeout-types>` for a full description of how timeouts affect your workflow executions.

   If the activity type that you're trying to register already exists, an
   :aws-java-class:`TypeAlreadyExistsException <services/simpleworkflow/model/TypeAlreadyExistsException>`
   is raised.

#. Add a function to register a new workflow type. A *workflow*, also known as a *decider*
   represents the logic of your workflow's execution.

   .. literalinclude:: swf.java.hello_types.new_workflow_type.txt
       :language: java
       :dedent: 4

   Similar to activity types, workflow types are identified by a *name* and a *version* and also
   have configurable timeouts. See :aws-java-class:`RegisterWorkflowTypeRequest
   <services/simpleworkflow/model/RegisterWorkflowTypeRequest>` for more information.

   If the workflow type that you're trying to register already exists, an
   :aws-java-class:`TypeAlreadyExistsException <services/simpleworkflow/model/TypeAlreadyExistsException>`
   is raised.

#. Finally, make the class executable by providing it a :code-java:`main` method, which will register the
   domain, the activity type, and the workflow type in turn:

   .. literalinclude:: swf.java.hello_types.main.txt
       :language: java
       :dedent: 4

You can :ref:`build <swf-hello-build>` and :ref:`run <swf-hello-run-register>` the application now
to run the registration script, or continue with coding the activity and workflow workers. Once the
domain, workflow and activity have been registered, you won't need to run this again |mdash| these
types persist until you deprecate them yourself.


Implement the activity worker
-----------------------------

An *activity* is the basic unit of work in a workflow. A workflow provides the logic, scheduling
activities to be run (or other actions to be taken) in response to decision tasks. A typical
workflow usually consists of a number of activities that can run synchronously, asynchronously, or a
combination of both.

The *activity worker* is the bit of code that polls for activity tasks that are generated by |SWF|
in response to workflow decisions. When it receives an activity task, it runs the corresponding
activity and returns a success/failure response back to the workflow.

We'll implement a simple activity worker that drives a single activity.

#. Open your text editor and create the file :file:`ActivityWorker.java`, adding a package
   declaration and imports according to the :ref:`common steps <swf-hello-common>`.
   
   .. literalinclude:: swf.java.activity_worker.import.txt
       :language: java

#. Add the :classname:`ActivityWorker` class to the file, and give it a data member to hold a SWF
   client that we'll use to interact with |SWF|:

   .. literalinclude:: swf.java.activity_worker.client.txt
       :language: java

#. Add the method that we'll use as an activity:

   .. literalinclude:: swf.java.activity_worker.sayHello.txt
       :language: java
       :dedent: 4

   The activity simply takes a string, combines it into a greeting and returns the result. Although
   there is little chance that this activity will raise an exception, it's a good idea to design
   activities that can raise an error if something goes wrong.

#. Add a :methodname:`main` method that we'll use as the activity task polling method. We'll start
   it by adding some code to poll the task list for activity tasks:

   .. literalinclude:: swf.java.activity_worker.poll_method.txt
       :language: java
       :dedent: 4

   The activity receives tasks from |SWF| by calling the SWF client's
   :methodname:`pollForActivityTask` method, specifying the domain and task list to use in the
   passed-in :aws-java-class:`PollForActivityTaskRequest
   <services/simpleworkflow/model/PollForActivityTaskRequest>`.

   Once a task is received, we retrieve a unique identifier for it by calling the task's
   :methodname:`getTaskToken` method.

#. Next, write some code to process the tasks that come in. Add the following to your
   :methodname:`main` method, right after the code that polls for the task and retrieves its task
   token.

   .. literalinclude:: swf.java.activity_worker.process_tasks.txt
       :language: java
       :dedent: 8

   If the task token is not :code-java:`null`, then we can start running the activity method
   (:methodname:`sayHello`), providing it with the input data that was sent with the task.

   If the task *succeeded* (no error was generated), then the worker responds to SWF by calling the
   SWF client's :methodname:`respondActivityTaskCompleted` method with a
   :aws-java-class:`RespondActivityTaskCompletedRequest
   <services/simpleworkflow/model/RespondActivityTaskCompletedRequest>` object containing the task
   token and the activity's result data.

   On the other hand, if the task *failed*, then we respond by calling the
   :methodname:`respondActivityTaskFailed` method with a :aws-java-class:`RespondActivityTaskFailedRequest
   <services/simpleworkflow/model/RespondActivityTaskFailedRequest>` object, passing it the task
   token and information about the error.

.. tip:: This activity will not shut down gracefully if killed. Although it is beyond the scope of
   this tutorial, an alternative implementation of this activity worker is provided in the
   accompanying topic, :doc:`swf-graceful-shutdown`.


Implement the workflow worker
-----------------------------

Your workflow logic resides in a piece of code known as a **workflow worker**. The workflow worker
polls for decision tasks that are sent by |SWF| in the domain, and on the default tasklist, that the
workflow type was registered with.

When the workflow worker receives a task, it makes some sort of decision (usually whether to
schedule a new activity or not) and takes an appropriate action (such as scheduling the activity).

#. Open your text editor and create the file :file:`WorkflowWorker.java`, adding a package
   declaration and imports according to the :ref:`common steps <swf-hello-common>`.

#. Add a few additional imports to the file:

   .. literalinclude:: swf.java.workflow_worker.import.txt
       :language: java

#. Declare the :classname:`WorkflowWorker` class, and create an instance of the
   :aws-java-class:`AmazonSimpleWorkflowClient <services/simpleworkflow/AmazonSimpleWorkflowClient>` class
   used to access SWF methods.

   .. literalinclude:: swf.java.workflow_worker.client.txt
       :language: java

#. Add the :methodname:`main` method. The method loops continuously, polling for decision tasks
   using the SWF client's :methodname:`pollForDecisionTask` method. The
   :aws-java-class:`PollForDecisionTaskRequest <services/simpleworkflow/model/PollForDecisionTaskRequest>`
   provides the details.

   .. literalinclude:: swf.java.workflow_worker.main.txt
       :language: java
       :dedent: 4

   Once a task is received, we call its :methodname:`getTaskToken` method, which returns a string
   that can be used to identify the task. If the returned token is not :code-java:`null`, then we process it
   further in the :methodname:`executeDecisionTask` method, passing it the task token and the list
   of :aws-java-class:`HistoryEvent <services/simpleworkflow/model/HistoryEvent>` objects sent with the
   task.

#. Add the :methodname:`executeDecisionTask` method, taking the task token (a :classname:`String`)
   and the :classname:`HistoryEvent` list.

   .. literalinclude:: swf.java.workflow_worker.execute_decision_task_token.txt
       :language: java
       :dedent: 4

   We also set up some data members to keep track of things such as:

   * A list of :aws-java-class:`Decision <services/simpleworkflow/model/Decision>` objects used to report
     the results of processing the task.
   * A String to hold workflow input provided by the "WorkflowExecutionStarted" event
   * a count of the scheduled and open (running) activities to avoid scheduling the same activity
     when it has already been scheduled or is currently running.
   * a boolean to indicate that the activity has completed.
   * A String to hold the activity results, for returning it as our workflow result.

#. Next, add some code to :methodname:`executeDecisionTask` to process the :classname:`HistoryEvent`
   objects that were sent with the task, based on the event type reported by the
   :methodname:`getEventType` method.

   .. literalinclude:: swf.java.workflow_worker.execute_decision_task_history_events.txt
       :language: java
       :dedent: 8

   For the purposes of our workflow, we are most interested in:

   * the "WorkflowExecutionStarted" event, which indicates that the workflow execution has started
     (typically meaning that you should run the first activity in the workflow), and that provides
     the initial input provided to the workflow. In this case, it's the name portion of our
     greeting, so it's saved in a String for use when scheduling the activity to run.

   * the "ActivityTaskCompleted" event, which is sent once the scheduled activity is complete. The
     event data also includes the return value of the completed activity. Since we have only one
     activity, we'll use that value as the result of the entire workflow.

   The other event types can be used if your workflow requires them. See the :aws-java-class:`HistoryEvent
   <services/simpleworkflow/model/HistoryEvent>` class description for information about each event
   type.

   .. note:: Strings in :code-java:`switch` statements were introduced in Java 7. If you're using an
      earlier version of Java, you can make use of the :aws-java-class:`EventType
      <services.simpleworkflow.model.EventType>` class to convert the :code-java:`String` returned
      by :code-java:`history_event.getType()` to an enum value and then back to a
      :code-java:`String` if necessary:

      .. code-block:: java

         EventType et = EventType.fromValue(event.getEventType());

#. After the :code-java:`switch` statement, add more code to respond with an appropriate *decision*
   based on the task that was received.

   .. literalinclude:: swf.java.workflow_worker.task_decision.txt
       :language: java
       :dedent: 8

   * If the activity hasn't been scheduled yet, we respond with a :classname:`ScheduleActivityTask`
     decision, which provides information in a :aws-java-class:`ScheduleActivityTaskDecisionAttributes
     <services/simpleworkflow/model/ScheduleActivityTaskDecisionAttributes>` structure about the
     activity that |SWF| should schedule next, also including any data that |SWF| should send to the
     activity.

   * If the activity was completed, then we consider the entire workflow completed and respond with
     a :classname:`CompletedWorkflowExecution` decision, filling in a
     :aws-java-class:`CompleteWorkflowExecutionDecisionAttributes
     <services/simpleworkflow/model/CompleteWorkflowExecutionDecisionAttributes>` structure to
     provide details about the completed workflow. In this case, we return the result of the
     activity.

   In either case, the decision information is added to the :classname:`Decision` list that was declared at
   the top of the method.

#. Complete the decision task by returning the list of :classname:`Decision` objects collected while
   processing the task. Add this code at the end of the :methodname:`executeDecisionTask` method
   that we've been writing:

   .. literalinclude:: swf.java.workflow_worker.return_decision_objects.txt
       :language: java
       :dedent: 8

   The SWF client's :methodname:`respondDecisionTaskCompleted` method takes the task token that
   identifies the task as well as the list of :classname:`Decision` objects.


Implement the workflow starter
------------------------------

Finally, we'll write some code to start the workflow execution.

#. Open your text editor and create the file :file:`WorkflowStarter.java`, adding a package
   declaration and imports according to the :ref:`common steps <swf-hello-common>`.

#. Add the :classname:`WorkflowStarter` class:

   .. literalinclude:: swf.java.workflow_starter.complete.txt
       :language: java

   The :classname:`WorkflowStarter` class consists of a single method, :methodname:`main`, which
   takes an optional argument passed on the command-line as input data for the workflow.

   The SWF client method, :methodname:`startWorkflowExecution`, takes a
   :aws-java-class:`StartWorkflowExecutionRequest
   <services/simpleworkflow/model/StartWorkflowExecutionRequest>` object as input. Here, in addition
   to specifying the domain and workflow type to run, we provide it with:

   * a human-readable workflow execution name

   * workflow input data (provided on the command-line in our example)

   * a timeout value that represents how long, in seconds, that the entire workflow should take to
     run.

   The :aws-java-class:`Run <services/simpleworkflow/model/Run>` object that
   :methodname:`startWorkflowExecution` returns provides a *run ID*, a value that can be used to
   identify this particular workflow execution in |SWF|'s history of your workflow executions.

   .. note:: The run ID is generated by |SWF|, and is *not* the same as the workflow execution name
      that you pass in when starting the workflow execution.

.. _swf-hello-build:

Build the example
=================

To build the example project with Maven, go to the :file:`helloswf` directory and type:

.. code-block:: sh

   mvn package

The resulting :file:`helloswf-1.0.jar` will be generated in the :file:`target` directory.


Run the example
===============

The example consists of four separate executable classes, which are run independently of each other.

.. note:: If you are using a |unixes| system, you can run all of them, one after another, in a
   single terminal window. If you are running Windows, you should open two additional command-line
   instances and navigate to the :file:`helloswf` directory in each.


.. _swf-hello-set-classpath:

Setting the Java classpath
--------------------------

Although Maven has handled the dependencies for you, to run the example, you'll need to provide the
AWS SDK library and its dependencies on your Java classpath. You can either set the
:envvar:`CLASSPATH` environment variable to the location of your AWS SDK libraries and the
:file:`third-party/lib` directory in the SDK, which includes necessary dependencies:

.. code-block:: sh

   export CLASSPATH='target/helloswf-1.0.jar:/path/to/sdk/lib/*:/path/to/sdk/third-party/lib/*'
   java example.swf.hello.HelloTypes

or use the :command:`java` command's ``-cp`` option to set the classpath while running each
applications.

.. code-block:: sh

   java -cp target/helloswf-1.0.jar:/path/to/sdk/lib/*:/path/to/sdk/third-party/lib/* \
     example.swf.hello.HelloTypes

The style that you use is up to you. If you had no trouble building the code, both then try to run
the examples and get a series of "NoClassDefFound" errors, it is likely because the classpath is set
incorrectly.

.. _swf-hello-run-register:

Register the domain, workflow and activity types
------------------------------------------------

Before running your workers and the workflow starter, you'll need to register the domain and your
workflow and activity types. The code to do this was implemented in :ref:`swf-hello-hellotypes`.

After building, and if you've :ref:`set the CLASSPATH <swf-hello-set-classpath>`, you can run the
registration code by executing the command:

.. literalinclude:: example_code/swf/run_workflow.sh
    :language: sh
    :lines: 3

.. _swf-hello-run-workers:

Start the activity and workflow workers
---------------------------------------

Now that the types have been registered, you can start the activity and workflow workers. These will
continue to run and poll for tasks until they are killed, so you should either run them in separate
terminal windows, or, if you're running on |unixes| you can use the ``&`` operator to cause each of
them to spawn a separate process when run.

.. literalinclude:: example_code/swf/run_workflow.sh
    :language: sh
    :lines: 4-5

If you're running these commands in separate windows, omit the final ``&`` operator from each line.

.. _swf-hello-start-execution:

Start the workflow execution
----------------------------

Now that your activity and workflow workers are polling, you can start the workflow execution. This
process will run until the workflow returns a completed status. You should run it in a new terminal
window (unless you ran your workers as new spawned processes by using the ``&`` operator).

.. literalinclude:: example_code/swf/run_workflow.sh
    :language: sh
    :lines: 6

.. note:: If you want to provide your own input data, which will be passed first to the workflow and
   then to the activity, add it to the command-line.  For example:

   .. literalinclude:: example_code/swf/run_workflow.sh
       :language: sh
       :lines: 9

Once you begin the workflow execution, you should start seeing output delivered by both workers and
by the workflow execution itself. When the workflow finally completes, its output will be printed to
the screen.


Complete source for this example
================================

You can browse the :github:`complete source
<awsdocs/aws-doc-sdk-examples/tree/master/java/example_code/swf>` for this example on Github in the
*aws-java-developer-guide* repository.


For more information
====================

* The workers presented here can result in lost tasks if they are shutdown while a workflow poll is
  still going on. To find out how to shut down workers gracefully, see :doc:`swf-graceful-shutdown`.

* To learn more about |SWF|, visit the |SWF|_ home page or view the |swf-dg|_.

* You can use the |jflow| to write more complex workflows in an elegant Java style using
  annotations. To learn more, see the |jflow-dg|_.

