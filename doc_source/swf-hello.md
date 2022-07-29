--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Building a Simple Amazon SWF Application<a name="swf-hello"></a>

This topic will introduce you to programming [Amazon SWF](http://aws.amazon.com/swf/) applications with the AWS SDK for Java, while presenting a few important concepts along the way\.

## About the example<a name="about-the-example"></a>

The example project will create a workflow with a single activity that accepts workflow data passed through the AWS cloud \(In the tradition of HelloWorld, it’ll be the name of someone to greet\) and then prints a greeting in response\.

While this seems very simple on the surface, Amazon SWF applications consist of a number of parts working together:
+ A **domain**, used as a logical container for your workflow execution data\.
+ One or more **workflows** which represent code components that define logical order of execution of your workflow’s activities and child workflows\.
+ A **workflow worker**, also known as a *decider*, that polls for decision tasks and schedules activities or child workflows in response\.
+ One or more **activities**, each of which represents a unit of work in the workflow\.
+ An **activity worker** that polls for activity tasks and runs activity methods in response\.
+ One or more **task lists**, which are queues maintained by Amazon SWF used to issue requests to the workflow and activity workers\. Tasks on a task list meant for workflow workers are called *decision tasks*\. Those meant for activity workers are called *activity tasks*\.
+ A **workflow starter** that begins your workflow execution\.

Behind the scenes, Amazon SWF orchestrates the operation of these components, coordinating their flow from the AWS cloud, passing data between them, handling timeouts and heartbeat notifications, and logging workflow execution history\.

## Prerequisites<a name="prerequisitesswf"></a>

### Development environment<a name="development-environment"></a>

The development environment used in this tutorial consists of:
+ The [AWS SDK for Java](http://aws.amazon.com/sdk-for-java/)\.
+  [Apache Maven](http://maven.apache.org/) \(3\.3\.1\)\.
+ JDK 1\.7 or later\. This tutorial was developed and tested using JDK 1\.8\.0\.
+ A good Java text editor \(your choice\)\.

**Note**  
If you use a different build system than Maven, you can still create a project using the appropriate steps for your environment and use the the concepts provided here to follow along\. More information about configuring and using the AWS SDK for Java with various build systems is provided in [Getting Started](getting-started.md)\.  
Likewise, but with more effort, the steps shown here can be implemented using any of the AWS SDKs with support for Amazon SWF\.

All of the necessary external dependencies are included with the AWS SDK for Java, so there’s nothing additional to download\.

### Access<a name="aws-access"></a>

To access Amazon Web Services \(AWS\), you must have an active AWS account\. For information about signing up for AWS and creating an IAM user \(recommended over using root account credentials\), see [Sign Up for AWS and Create an IAM User](signup-create-iam-user.md)\.

This tutorial uses the terminal \(command\-line\) to run the example code, and expects that you have your AWS credentials and configuration accessible to the SDK\. The easiest way to do this is to use the environment variables `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY`\. You should also set the `AWS_REGION` to the region you want to use\.

For example, on Linux, macOS, or Unix, set the variables this way:

```
export AWS_ACCESS_KEY_ID=your_access_key_id
export AWS_SECRET_ACCESS_KEY=your_secret_access_key
export AWS_REGION=us-east-1
```

To set these variables on Windows, use these commands:

```
set AWS_ACCESS_KEY_ID=your_access_key_id
set AWS_SECRET_ACCESS_KEY=your_secret_access_key
set AWS_REGION=us-east-1
```

**Important**  
Substitute your own access key, secret access key and region information for the example values shown here\.  
For more information about configuring your credentials for the SDK, see [Set up AWS Credentials and Region for Development](setup-credentials.md)\.

## Create a SWF project<a name="create-a-swf-project"></a>

1. Start a new project with Maven:

   ```
   mvn archetype:generate -DartifactId=helloswf \
   -DgroupId=aws.example.helloswf -DinteractiveMode=false
   ```

   This will create a new project with a standard maven project structure:

   ```
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
   ```

   You can ignore or delete the `test` directory and all it contains, we won’t be using it for this tutorial\. You can also delete `App.java`, since we’ll be replacing it with new classes\.

1. Edit the project’s `pom.xml` file and add the **aws\-java\-sdk\-simpleworkflow** module by adding a dependency for it within the `<dependencies>` block\.

   ```
   <dependencies>
     <dependency>
       <groupId>com.amazonaws</groupId>
       <artifactId>aws-java-sdk-simpleworkflow</artifactId>
       <version>1.11.1000</version>
     </dependency>
   </dependencies>
   ```

1.  *Make sure that Maven builds your project with JDK 1\.7\+ support*\. Add the following to your project \(either before or after the `<dependencies>` block\) in `pom.xml`:

   ```
   <build>
     <plugins>
       <plugin>
         <artifactId>maven-compiler-plugin</artifactId>
         <version>3.6.1</version>
         <configuration>
             <source>1.8</source>
             <target>1.8</target>
         </configuration>
       </plugin>
     </plugins>
   </build>
   ```

## Code the project<a name="code-the-project"></a>

The example project will consist of four separate applications, which we’ll visit one by one:
+  **HelloTypes\.java**\-\-contains the project’s domain, activity and workflow type data, shared with the other components\. It also handles registering these types with SWF\.
+  **ActivityWorker\.java**\-\-contains the activity worker, which polls for activity tasks and runs activities in response\.
+  **WorkflowWorker\.java**\-\-contains the workflow worker \(decider\), which polls for decision tasks and schedules new activities\.
+  **WorkflowStarter\.java**\-\-contains the workflow starter, which starts a new workflow execution, which will cause SWF to start generating decision and workflow tasks for your workers to consume\.

### Common steps for all source files<a name="swf-hello-common"></a>

All of the files that you create to house your Java classes will have a few things in common\. In the interest of time, these steps *will be implied every time you add a new file to the project*:

1. Create the file in the in the project’s `src/main/java/example/swf/hello/` directory\.

1. Add a `package` declaration to the beginning of each file to declare its namespace\. The example project uses:

   ```
   package aws.example.helloswf;
   ```

1. Add `import` declarations for the [AmazonSimpleWorkflowClient](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/AmazonSimpleWorkflowClient.html) class and for multiple classes in the `com.amazonaws.services.simpleworkflow.model` namespace\. To simplify things, we’ll use:

   ```
   import com.amazonaws.regions.Regions;
   import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
   import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClientBuilder;
   import com.amazonaws.services.simpleworkflow.model.*;
   ```

### Register a domain, workflow and activity types<a name="swf-hello-hellotypes"></a>

We’ll begin by creating a new executable class, `HelloTypes.java`\. This file will contain shared data that different parts of your workflow will need to know about, such as the name and version of your activity and workflow types, the domain name and the task list name\.

1. Open your text editor and create the file `HelloTypes.java`, adding a package declaration and imports according to the [common steps](#swf-hello-common)\.

1. Declare the `HelloTypes` class and provide it with values to use for your registered activity and workflow types:

   ```
       public static final String DOMAIN = "HelloDomain";
       public static final String TASKLIST = "HelloTasklist";
       public static final String WORKFLOW = "HelloWorkflow";
       public static final String WORKFLOW_VERSION = "1.0";
       public static final String ACTIVITY = "HelloActivity";
       public static final String ACTIVITY_VERSION = "1.0";
   ```

   These values will be used throughout the code\.

1. After the String declarations, create an instance of the [AmazonSimpleWorkflowClient](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/AmazonSimpleWorkflowClient.html) class\. This is the basic interface to the Amazon SWF methods provided by the AWS SDK for Java\.

   ```
   private static final AmazonSimpleWorkflow swf =
       AmazonSimpleWorkflowClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
   ```

1. Add a new function to register a SWF domain\. A *domain* is a logical container for a number of related SWF activity and workflow types\. SWF components can only communicate with each other if they exist within the same domain\.

   ```
       try {
           System.out.println("** Registering the domain '" + DOMAIN + "'.");
           swf.registerDomain(new RegisterDomainRequest()
               .withName(DOMAIN)
               .withWorkflowExecutionRetentionPeriodInDays("1"));
       } catch (DomainAlreadyExistsException e) {
           System.out.println("** Domain already exists!");
       }
   ```

   When you register a domain, you provide it with a *name* \(any set of 1 \- 256 characters excluding `:`, `/`, `|`, control characters or the literal string '`arn'\) and a *retention period*, which is the number of days that Amazon SWF will keep your workflow’s execution history data after a workflow execution has completed\. The maximum workflow execution retention period is 90 days\. See [RegisterDomainRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/RegisterDomainRequest.html) for more information\.

   If a domain with that name already exists, a [DomainAlreadyExistsException](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/DomainAlreadyExistsException.html) is raised\. Because we’re unconcerned if the domain has already been created, we can ignore the exception\.
**Note**  
This code demonstrates a common pattern when working with AWS SDK for Java methods, data for the method is supplied by a class in the `simpleworkflow.model` namespace, which you instantiate and populate using the chainable `0with*` methods\.

1. Add a function to register a new activity type\. An *activity* represents a unit of work in your workflow\.

   ```
       try {
           System.out.println("** Registering the activity type '" + ACTIVITY +
               "-" + ACTIVITY_VERSION + "'.");
           swf.registerActivityType(new RegisterActivityTypeRequest()
               .withDomain(DOMAIN)
               .withName(ACTIVITY)
               .withVersion(ACTIVITY_VERSION)
               .withDefaultTaskList(new TaskList().withName(TASKLIST))
               .withDefaultTaskScheduleToStartTimeout("30")
               .withDefaultTaskStartToCloseTimeout("600")
               .withDefaultTaskScheduleToCloseTimeout("630")
               .withDefaultTaskHeartbeatTimeout("10"));
       } catch (TypeAlreadyExistsException e) {
           System.out.println("** Activity type already exists!");
       }
   ```

   An activity type is identified by a *name* and a *version*, which are used to uniquely identify the activity from any others in the domain that it’s registered in\. Activities also contain a number of optional parameters, such as the default task\-list used to receive tasks and data from SWF and a number of different timeouts that you can use to place constraints upon how long different parts of the activity execution can take\. See [RegisterActivityTypeRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/RegisterActivityTypeRequest.html) for more information\.
**Note**  
All timeout values are specified in *seconds*\. See [Amazon SWF Timeout Types](https://docs.aws.amazon.com/amazonswf/latest/developerguide/swf-timeout-types.html) for a full description of how timeouts affect your workflow executions\.

If the activity type that you’re trying to register already exists, an [TypeAlreadyExistsException](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/TypeAlreadyExistsException.html) is raised\. \. Add a function to register a new workflow type\. A *workflow*, also known as a *decider* represents the logic of your workflow’s execution\.

\+

```
    try {
        System.out.println("** Registering the workflow type '" + WORKFLOW +
            "-" + WORKFLOW_VERSION + "'.");
        swf.registerWorkflowType(new RegisterWorkflowTypeRequest()
            .withDomain(DOMAIN)
            .withName(WORKFLOW)
            .withVersion(WORKFLOW_VERSION)
            .withDefaultChildPolicy(ChildPolicy.TERMINATE)
            .withDefaultTaskList(new TaskList().withName(TASKLIST))
            .withDefaultTaskStartToCloseTimeout("30"));
    } catch (TypeAlreadyExistsException e) {
        System.out.println("** Workflow type already exists!");
    }
```

\+

Similar to activity types, workflow types are identified by a *name* and a *version* and also have configurable timeouts\. See [RegisterWorkflowTypeRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/RegisterWorkflowTypeRequest.html) for more information\.

\+

If the workflow type that you’re trying to register already exists, an [TypeAlreadyExistsException](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/TypeAlreadyExistsException.html) is raised\. \. Finally, make the class executable by providing it a `main` method, which will register the domain, the activity type, and the workflow type in turn:

\+

```
    registerDomain();
    registerWorkflowType();
    registerActivityType();
```

You can [build](#swf-hello-build) and [run](#swf-hello-run-register) the application now to run the registration script, or continue with coding the activity and workflow workers\. Once the domain, workflow and activity have been registered, you won’t need to run this again—​these types persist until you deprecate them yourself\.

### Implement the activity worker<a name="implement-the-activity-worker"></a>

An *activity* is the basic unit of work in a workflow\. A workflow provides the logic, scheduling activities to be run \(or other actions to be taken\) in response to decision tasks\. A typical workflow usually consists of a number of activities that can run synchronously, asynchronously, or a combination of both\.

The *activity worker* is the bit of code that polls for activity tasks that are generated by Amazon SWF in response to workflow decisions\. When it receives an activity task, it runs the corresponding activity and returns a success/failure response back to the workflow\.

We’ll implement a simple activity worker that drives a single activity\.

1. Open your text editor and create the file `ActivityWorker.java`, adding a package declaration and imports according to the [common steps](#swf-hello-common)\.

   ```
   import com.amazonaws.regions.Regions;
   import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
   import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClientBuilder;
   import com.amazonaws.services.simpleworkflow.model.*;
   ```

1. Add the `ActivityWorker` class to the file, and give it a data member to hold a SWF client that we’ll use to interact with Amazon SWF:

   ```
       private static final AmazonSimpleWorkflow swf =
               AmazonSimpleWorkflowClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
   ```

1. Add the method that we’ll use as an activity:

   ```
   private static String sayHello(String input) throws Throwable {
       return "Hello, " + input + "!";
   }
   ```

   The activity simply takes a string, combines it into a greeting and returns the result\. Although there is little chance that this activity will raise an exception, it’s a good idea to design activities that can raise an error if something goes wrong\.

1. Add a `main` method that we’ll use as the activity task polling method\. We’ll start it by adding some code to poll the task list for activity tasks:

   ```
           System.out.println("Polling for an activity task from the tasklist '"
                   + HelloTypes.TASKLIST + "' in the domain '" +
                   HelloTypes.DOMAIN + "'.");
   
           ActivityTask task = swf.pollForActivityTask(
               new PollForActivityTaskRequest()
                   .withDomain(HelloTypes.DOMAIN)
                   .withTaskList(
                       new TaskList().withName(HelloTypes.TASKLIST)));
   
           String task_token = task.getTaskToken();
   ```

   The activity receives tasks from Amazon SWF by calling the SWF client’s `pollForActivityTask` method, specifying the domain and task list to use in the passed\-in [PollForActivityTaskRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/PollForActivityTaskRequest.html)\.

   Once a task is received, we retrieve a unique identifier for it by calling the task’s `getTaskToken` method\.

1. Next, write some code to process the tasks that come in\. Add the following to your `main` method, right after the code that polls for the task and retrieves its task token\.

   ```
       if (task_token != null) {
           String result = null;
           Throwable error = null;
   
           try {
               System.out.println("Executing the activity task with input '" +
                       task.getInput() + "'.");
               result = sayHello(task.getInput());
           } catch (Throwable th) {
               error = th;
           }
   
           if (error == null) {
               System.out.println("The activity task succeeded with result '"
                       + result + "'.");
               swf.respondActivityTaskCompleted(
                   new RespondActivityTaskCompletedRequest()
                       .withTaskToken(task_token)
                       .withResult(result));
           } else {
               System.out.println("The activity task failed with the error '"
                       + error.getClass().getSimpleName() + "'.");
               swf.respondActivityTaskFailed(
                   new RespondActivityTaskFailedRequest()
                       .withTaskToken(task_token)
                       .withReason(error.getClass().getSimpleName())
                       .withDetails(error.getMessage()));
           }
       }
   ```

   If the task token is not `null`, then we can start running the activity method \(`sayHello`\), providing it with the input data that was sent with the task\.

   If the task *succeeded* \(no error was generated\), then the worker responds to SWF by calling the SWF client’s `respondActivityTaskCompleted` method with a [RespondActivityTaskCompletedRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/RespondActivityTaskCompletedRequest.html) object containing the task token and the activity’s result data\.

   On the other hand, if the task *failed*, then we respond by calling the `respondActivityTaskFailed` method with a [RespondActivityTaskFailedRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/RespondActivityTaskFailedRequest.html) object, passing it the task token and information about the error\.

**Note**  
This activity will not shut down gracefully if killed\. Although it is beyond the scope of this tutorial, an alternative implementation of this activity worker is provided in the accompanying topic, [Shutting Down Activity and Workflow Workers Gracefully](swf-graceful-shutdown.md)\.

### Implement the workflow worker<a name="implement-the-workflow-worker"></a>

Your workflow logic resides in a piece of code known as a **workflow worker**\. The workflow worker polls for decision tasks that are sent by Amazon SWF in the domain, and on the default tasklist, that the workflow type was registered with\.

When the workflow worker receives a task, it makes some sort of decision \(usually whether to schedule a new activity or not\) and takes an appropriate action \(such as scheduling the activity\)\.

1. Open your text editor and create the file `WorkflowWorker.java`, adding a package declaration and imports according to the [common steps](#swf-hello-common)\.

1. Add a few additional imports to the file:

   ```
   import com.amazonaws.regions.Regions;
   import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
   import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClientBuilder;
   import com.amazonaws.services.simpleworkflow.model.*;
   import java.util.ArrayList;
   import java.util.List;
   import java.util.UUID;
   ```

1. Declare the `WorkflowWorker` class, and create an instance of the [AmazonSimpleWorkflowClient](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/AmazonSimpleWorkflowClient.html) class used to access SWF methods\.

   ```
       private static final AmazonSimpleWorkflow swf =
               AmazonSimpleWorkflowClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
   ```

1. Add the `main` method\. The method loops continuously, polling for decision tasks using the SWF client’s `pollForDecisionTask` method\. The [PollForDecisionTaskRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/PollForDecisionTaskRequest.html) provides the details\.

   ```
       PollForDecisionTaskRequest task_request =
           new PollForDecisionTaskRequest()
               .withDomain(HelloTypes.DOMAIN)
               .withTaskList(new TaskList().withName(HelloTypes.TASKLIST));
   
       while (true) {
           System.out.println(
                   "Polling for a decision task from the tasklist '" +
                   HelloTypes.TASKLIST + "' in the domain '" +
                   HelloTypes.DOMAIN + "'.");
   
           DecisionTask task = swf.pollForDecisionTask(task_request);
   
           String taskToken = task.getTaskToken();
           if (taskToken != null) {
               try {
                   executeDecisionTask(taskToken, task.getEvents());
               } catch (Throwable th) {
                   th.printStackTrace();
               }
           }
       }
   ```

   Once a task is received, we call its `getTaskToken` method, which returns a string that can be used to identify the task\. If the returned token is not `null`, then we process it further in the `executeDecisionTask` method, passing it the task token and the list of [HistoryEvent](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/HistoryEvent.html) objects sent with the task\.

1. Add the `executeDecisionTask` method, taking the task token \(a `String`\) and the `HistoryEvent` list\.

   ```
       List<Decision> decisions = new ArrayList<Decision>();
       String workflow_input = null;
       int scheduled_activities = 0;
       int open_activities = 0;
       boolean activity_completed = false;
       String result = null;
   ```

   We also set up some data members to keep track of things such as:
   + A list of [Decision](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/Decision.html) objects used to report the results of processing the task\.
   + A String to hold workflow input provided by the "WorkflowExecutionStarted" event
   + a count of the scheduled and open \(running\) activities to avoid scheduling the same activity when it has already been scheduled or is currently running\.
   + a boolean to indicate that the activity has completed\.
   + A String to hold the activity results, for returning it as our workflow result\.

1. Next, add some code to `executeDecisionTask` to process the `HistoryEvent` objects that were sent with the task, based on the event type reported by the `getEventType` method\.

   ```
   System.out.println("Executing the decision task for the history events: [");
   for (HistoryEvent event : events) {
       System.out.println("  " + event);
       switch(event.getEventType()) {
           case "WorkflowExecutionStarted":
               workflow_input =
                   event.getWorkflowExecutionStartedEventAttributes()
                        .getInput();
               break;
           case "ActivityTaskScheduled":
               scheduled_activities++;
               break;
           case "ScheduleActivityTaskFailed":
               scheduled_activities--;
               break;
           case "ActivityTaskStarted":
               scheduled_activities--;
               open_activities++;
               break;
           case "ActivityTaskCompleted":
               open_activities--;
               activity_completed = true;
               result = event.getActivityTaskCompletedEventAttributes()
                             .getResult();
               break;
           case "ActivityTaskFailed":
               open_activities--;
               break;
           case "ActivityTaskTimedOut":
               open_activities--;
               break;
       }
   }
   System.out.println("]");
   ```

   For the purposes of our workflow, we are most interested in:
   + the "WorkflowExecutionStarted" event, which indicates that the workflow execution has started \(typically meaning that you should run the first activity in the workflow\), and that provides the initial input provided to the workflow\. In this case, it’s the name portion of our greeting, so it’s saved in a String for use when scheduling the activity to run\.
   + the "ActivityTaskCompleted" event, which is sent once the scheduled activity is complete\. The event data also includes the return value of the completed activity\. Since we have only one activity, we’ll use that value as the result of the entire workflow\.

   The other event types can be used if your workflow requires them\. See the [HistoryEvent](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/HistoryEvent.html) class description for information about each event type\.

   \+ NOTE: Strings in `switch` statements were introduced in Java 7\. If you’re using an earlier version of Java, you can make use of the [EventType](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services.simpleworkflow.model.EventType.html) class to convert the `String` returned by `history_event.getType()` to an enum value and then back to a `String` if necessary:

```
EventType et = EventType.fromValue(event.getEventType());
```

1. After the `switch` statement, add more code to respond with an appropriate *decision* based on the task that was received\.

   ```
   if (activity_completed) {
       decisions.add(
           new Decision()
               .withDecisionType(DecisionType.CompleteWorkflowExecution)
               .withCompleteWorkflowExecutionDecisionAttributes(
                   new CompleteWorkflowExecutionDecisionAttributes()
                       .withResult(result)));
   } else {
       if (open_activities == 0 && scheduled_activities == 0) {
   
           ScheduleActivityTaskDecisionAttributes attrs =
               new ScheduleActivityTaskDecisionAttributes()
                   .withActivityType(new ActivityType()
                       .withName(HelloTypes.ACTIVITY)
                       .withVersion(HelloTypes.ACTIVITY_VERSION))
                   .withActivityId(UUID.randomUUID().toString())
                   .withInput(workflow_input);
   
           decisions.add(
                   new Decision()
                       .withDecisionType(DecisionType.ScheduleActivityTask)
                       .withScheduleActivityTaskDecisionAttributes(attrs));
       } else {
           // an instance of HelloActivity is already scheduled or running. Do nothing, another
           // task will be scheduled once the activity completes, fails or times out
       }
   }
   
   System.out.println("Exiting the decision task with the decisions " + decisions);
   ```
   + If the activity hasn’t been scheduled yet, we respond with a `ScheduleActivityTask` decision, which provides information in a [ScheduleActivityTaskDecisionAttributes](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/ScheduleActivityTaskDecisionAttributes.html) structure about the activity that Amazon SWF should schedule next, also including any data that Amazon SWF should send to the activity\.
   + If the activity was completed, then we consider the entire workflow completed and respond with a `CompletedWorkflowExecution` decision, filling in a [CompleteWorkflowExecutionDecisionAttributes](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/CompleteWorkflowExecutionDecisionAttributes.html) structure to provide details about the completed workflow\. In this case, we return the result of the activity\.

   In either case, the decision information is added to the `Decision` list that was declared at the top of the method\.

1. Complete the decision task by returning the list of `Decision` objects collected while processing the task\. Add this code at the end of the `executeDecisionTask` method that we’ve been writing:

   ```
   swf.respondDecisionTaskCompleted(
       new RespondDecisionTaskCompletedRequest()
           .withTaskToken(taskToken)
           .withDecisions(decisions));
   ```

   The SWF client’s `respondDecisionTaskCompleted` method takes the task token that identifies the task as well as the list of `Decision` objects\.

### Implement the workflow starter<a name="implement-the-workflow-starter"></a>

Finally, we’ll write some code to start the workflow execution\.

1. Open your text editor and create the file `WorkflowStarter.java`, adding a package declaration and imports according to the [common steps](#swf-hello-common)\.

1. Add the `WorkflowStarter` class:

   ```
   package aws.example.helloswf;
   
   
   import com.amazonaws.regions.Regions;
   import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
   import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClientBuilder;
   import com.amazonaws.services.simpleworkflow.model.*;
   
   public class WorkflowStarter {
       private static final AmazonSimpleWorkflow swf =
               AmazonSimpleWorkflowClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
       public static final String WORKFLOW_EXECUTION = "HelloWorldWorkflowExecution";
   
       public static void main(String[] args) {
           String workflow_input = "{SWF}";
           if (args.length > 0) {
               workflow_input = args[0];
           }
   
           System.out.println("Starting the workflow execution '" + WORKFLOW_EXECUTION +
                   "' with input '" + workflow_input + "'.");
   
           WorkflowType wf_type = new WorkflowType()
               .withName(HelloTypes.WORKFLOW)
               .withVersion(HelloTypes.WORKFLOW_VERSION);
   
           Run run = swf.startWorkflowExecution(new StartWorkflowExecutionRequest()
               .withDomain(HelloTypes.DOMAIN)
               .withWorkflowType(wf_type)
               .withWorkflowId(WORKFLOW_EXECUTION)
               .withInput(workflow_input)
               .withExecutionStartToCloseTimeout("90"));
   
           System.out.println("Workflow execution started with the run id '" +
                   run.getRunId() + "'.");
       }
   }
   ```

   The `WorkflowStarter` class consists of a single method, `main`, which takes an optional argument passed on the command\-line as input data for the workflow\.

   The SWF client method, `startWorkflowExecution`, takes a [StartWorkflowExecutionRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/StartWorkflowExecutionRequest.html) object as input\. Here, in addition to specifying the domain and workflow type to run, we provide it with:
   + a human\-readable workflow execution name
   + workflow input data \(provided on the command\-line in our example\)
   + a timeout value that represents how long, in seconds, that the entire workflow should take to run\.

   The [Run](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/Run.html) object that `startWorkflowExecution` returns provides a *run ID*, a value that can be used to identify this particular workflow execution in Amazon SWF's history of your workflow executions\.

   \+ NOTE: The run ID is generated by Amazon SWF, and is *not* the same as the workflow execution name that you pass in when starting the workflow execution\.

## Build the example<a name="swf-hello-build"></a>

To build the example project with Maven, go to the `helloswf` directory and type:

```
mvn package
```

The resulting `helloswf-1.0.jar` will be generated in the `target` directory\.

## Run the example<a name="run-the-example"></a>

The example consists of four separate executable classes, which are run independently of each other\.

**Note**  
If you are using a Linux, macOS, or Unix system, you can run all of them, one after another, in a single terminal window\. If you are running Windows, you should open two additional command\-line instances and navigate to the `helloswf` directory in each\.

### Setting the Java classpath<a name="swf-hello-set-classpath"></a>

Although Maven has handled the dependencies for you, to run the example, you’ll need to provide the AWS SDK library and its dependencies on your Java classpath\. You can either set the `CLASSPATH` environment variable to the location of your AWS SDK libraries and the `third-party/lib` directory in the SDK, which includes necessary dependencies:

```
export CLASSPATH='target/helloswf-1.0.jar:/path/to/sdk/lib/*:/path/to/sdk/third-party/lib/*'
java example.swf.hello.HelloTypes
```

or use the ** ` java ` ** command’s `-cp` option to set the classpath while running each applications\.

```
java -cp target/helloswf-1.0.jar:/path/to/sdk/lib/*:/path/to/sdk/third-party/lib/* \
  example.swf.hello.HelloTypes
```

The style that you use is up to you\. If you had no trouble building the code, both then try to run the examples and get a series of "NoClassDefFound" errors, it is likely because the classpath is set incorrectly\.

### Register the domain, workflow and activity types<a name="swf-hello-run-register"></a>

Before running your workers and the workflow starter, you’ll need to register the domain and your workflow and activity types\. The code to do this was implemented in [Register a domain workflow and activity types](#swf-hello-hellotypes)\.

After building, and if you’ve [set the CLASSPATH](#swf-hello-set-classpath), you can run the registration code by executing the command:

```
    echo 'Supply the name of one of the example classes as an argument.'
```

### Start the activity and workflow workers<a name="swf-hello-run-workers"></a>

Now that the types have been registered, you can start the activity and workflow workers\. These will continue to run and poll for tasks until they are killed, so you should either run them in separate terminal windows, or, if you’re running on Linux, macOS, or Unix you can use the `&` operator to cause each of them to spawn a separate process when run\.

```
    echo 'If there are arguments to the class, put them in quotes after the class name.'
    exit 1
```

If you’re running these commands in separate windows, omit the final `&` operator from each line\.

### Start the workflow execution<a name="swf-hello-start-execution"></a>

Now that your activity and workflow workers are polling, you can start the workflow execution\. This process will run until the workflow returns a completed status\. You should run it in a new terminal window \(unless you ran your workers as new spawned processes by using the `&` operator\)\.

```
fi
```

**Note**  
If you want to provide your own input data, which will be passed first to the workflow and then to the activity, add it to the command\-line\. For example:  

```
echo "## Running $className..."
```

Once you begin the workflow execution, you should start seeing output delivered by both workers and by the workflow execution itself\. When the workflow finally completes, its output will be printed to the screen\.

## Complete source for this example<a name="complete-source-for-this-example"></a>

You can browse the [complete source](https://github.com/awsdocs/aws-doc-sdk-examples/tree/master/java/example_code/swf) for this example on Github in the *aws\-java\-developer\-guide* repository\.

## For more information<a name="for-more-information"></a>
+ The workers presented here can result in lost tasks if they are shutdown while a workflow poll is still going on\. To find out how to shut down workers gracefully, see [Shutting Down Activity and Workflow Workers Gracefully](swf-graceful-shutdown.md)\.
+ To learn more about Amazon SWF, visit the [Amazon SWF](http://aws.amazon.com/swf/) home page or view the [Amazon SWF Developer Guide](https://docs.aws.amazon.com/amazonswf/latest/developerguide/)\.
+ You can use the AWS Flow Framework for Java to write more complex workflows in an elegant Java style using annotations\. To learn more, see the [AWS Flow Framework for Java Developer Guide](https://docs.aws.amazon.com/amazonswf/latest/awsflowguide/)\.