# Lambda Tasks<a name="swf-lambda-task"></a>

As an alternative to, or in conjunction with, Amazon SWF activities, you can use [Lambda](http://aws.amazon.com/lambda/) functions to represent units of work in your workflows, and schedule them similarly to activities\.

This topic focuses on how to implement Amazon SWF Lambda tasks using the AWS SDK for Java\. For more information about Lambda tasks in general, see [AWS Lambda Tasks](https://docs.aws.amazon.com/amazonswf/latest/developerguide/lambda-task.html) in the Amazon SWF Developer Guide\.

## Set up a cross\-service IAM role to run your Lambda function<a name="set-up-a-cross-service-iam-role-to-run-your-lambda-function"></a>

Before Amazon SWF can run your Lambda function, you need to set up an IAM role to give Amazon SWF permission to run Lambda functions on your behalf\. For complete information about how to do this, see [AWS Lambda Tasks](https://docs.aws.amazon.com/amazonswf/latest/developerguide/lambda-task.html)\.

You will need the Amazon Resource Name \(ARN\) of this IAM role when you register a workflow that will use Lambda tasks\.

## Create a Lambda function<a name="create-a-lambda-function"></a>

You can write Lambda functions in a number of different languages, including Java\. For complete information about how to author, deploy and use Lambda functions, see the [AWS Lambda Developer Guide](https://docs.aws.amazon.com/lambda/latest/dg/)\.

**Note**  
It doesn’t matter what language you use to write your Lambda function, it can be scheduled and run by *any* Amazon SWF workflow, regardless of the language that your workflow code is written in\. Amazon SWF handles the details of running the function and passing data to and from it\.

Here’s a simple Lambda function that could be used in place of the activity in [Building a Simple Amazon SWF Application](swf-hello.md)\.
+ This version is written in JavaScript, which can be entered directly using the [AWS Management Console](https://console.aws.amazon.com/console/home):

  ```
  exports.handler = function(event, context) {
      context.succeed("Hello, " + event.who + "!");
  };
  ```
+ Here is the same function written in Java, which you could also deploy and run on Lambda:

  ```
  package example.swf.hellolambda;
  
  import com.amazonaws.services.lambda.runtime.Context;
  import com.amazonaws.services.lambda.runtime.RequestHandler;
  import com.amazonaws.util.json.JSONException;
  import com.amazonaws.util.json.JSONObject;
  
  public class SwfHelloLambdaFunction implements RequestHandler<Object, Object> {
      @Override
      public Object handleRequest(Object input, Context context) {
          String who = "{SWF}";
          if (input != null) {
              JSONObject jso = null;
              try {
                  jso = new JSONObject(input.toString());
                  who = jso.getString("who");
              } catch (JSONException e) {
                  e.printStackTrace();
              }
          }
          return ("Hello, " + who + "!");
      }
  }
  ```
**Note**  
To learn more about deploying Java functions to Lambda, see [Creating a Deployment Package \(Java\)](https://docs.aws.amazon.com/lambda/latest/dg/lambda-java-how-to-create-deployment-package.html) in the AWS Lambda Developer Guide\. You will also want to look at the section titled [Programming Model for Authoring Lambda Functions in Java](https://docs.aws.amazon.com/lambda/latest/dg/java-programming-model.html)\.

 Lambda functions take an *event* or *input* object as the first parameter, and a *context* object as the second, which provides information about the request to run the Lambda function\. This particular function expects input to be in JSON, with a `who` field set to the name used to create the greeting\.

## Register a workflow for use with Lambda<a name="register-a-workflow-for-use-with-lam"></a>

For a workflow to schedule a Lambda function, you must provide the name of the IAM role that provides Amazon SWF with permission to invoke Lambda functions\. You can set this during workflow registration by using the `withDefaultLambdaRole` or `setDefaultLambdaRole` methods of [RegisterWorkflowTypeRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/RegisterWorkflowTypeRequest.html)\.

```
System.out.println("** Registering the workflow type '" + WORKFLOW + "-" + WORKFLOW_VERSION
        + "'.");
try {
    swf.registerWorkflowType(new RegisterWorkflowTypeRequest()
        .withDomain(DOMAIN)
        .withName(WORKFLOW)
        .withDefaultLambdaRole(lambda_role_arn)
        .withVersion(WORKFLOW_VERSION)
        .withDefaultChildPolicy(ChildPolicy.TERMINATE)
        .withDefaultTaskList(new TaskList().withName(TASKLIST))
        .withDefaultTaskStartToCloseTimeout("30"));
}
catch (TypeAlreadyExistsException e) {
```

## Schedule a Lambda task<a name="schedule-a-lam-task"></a>

Schedule a Lambda task is similar to scheduling an activity\. You provide a [Decision](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/Decision.html) with a `ScheduleLambdaFunction`[DecisionType](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/DecisionType.html) and with [ScheduleLambdaFunctionDecisionAttributes](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/ScheduleLambdaFunctionDecisionAttributes.html)\.

```
running_functions == 0 && scheduled_functions == 0) {
AWSLambda lam = AWSLambdaClientBuilder.defaultClient();
GetFunctionConfigurationResult function_config =
    lam.getFunctionConfiguration(
            new GetFunctionConfigurationRequest()
                .withFunctionName("HelloFunction"));
String function_arn = function_config.getFunctionArn();

ScheduleLambdaFunctionDecisionAttributes attrs =
    new ScheduleLambdaFunctionDecisionAttributes()
        .withId("HelloFunction (Lambda task example)")
        .withName(function_arn)
        .withInput(workflow_input);

decisions.add(
```

In the `ScheduleLambdaFuntionDecisionAttributes`, you must supply a *name*, which is the ARN of the Lambda function to call, and an *id*, which is the name that Amazon SWF will use to identify the Lambda function in history logs\.

You can also provide optional *input* for the Lambda function and set its *start to close timeout* value, which is the number of seconds that the Lambda function is allowed to run before generating a `LambdaFunctionTimedOut` event\.

**Note**  
This code uses the [AWSLambdaClient](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/lambda/AWSLambdaClient.html) to retrieve the ARN of the Lambda function, given the function name\. You can use this technique to avoid hard\-coding the full ARN \(which includes your AWS account ID\) in your code\.

## Handle Lambda function events in your decider<a name="handle-lam-function-events-in-your-decider"></a>

 Lambda tasks will generate a number of events that you can take action on when polling for decision tasks in your workflow worker, corresponding to the lifecycle of your Lambda task, with [EventType](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/EventType.html) values such as `LambdaFunctionScheduled`, `LambdaFunctionStarted`, and `LambdaFunctionCompleted`\. If the Lambda function fails, or takes longer to run than its set timeout value, you will receive either a `LambdaFunctionFailed` or `LambdaFunctionTimedOut` event type, respectively\.

```
boolean function_completed = false;
String result = null;

System.out.println("Executing the decision task for the history events: [");
for (HistoryEvent event : events) {
    System.out.println("  " + event);
    EventType event_type = EventType.fromValue(event.getEventType());
    switch(event_type) {
    case WorkflowExecutionStarted:
        workflow_input =
            event.getWorkflowExecutionStartedEventAttributes()
                 .getInput();
        break;
    case LambdaFunctionScheduled:
        scheduled_functions++;
        break;
    case ScheduleLambdaFunctionFailed:
        scheduled_functions--;
        break;
    case LambdaFunctionStarted:
        scheduled_functions--;
        running_functions++;
        break;
    case LambdaFunctionCompleted:
        running_functions--;
        function_completed = true;
        result = event.getLambdaFunctionCompletedEventAttributes()
                      .getResult();
        break;
    case LambdaFunctionFailed:
        running_functions--;
        break;
    case LambdaFunctionTimedOut:
        running_functions--;
        break;
```

## Receive output from your Lambda function<a name="receive-output-from-your-lam-function"></a>

When you receive a `LambdaFunctionCompleted`[EventType](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/EventType.html), you can retrieve your 0 function’s return value by first calling `getLambdaFunctionCompletedEventAttributes` on the [HistoryEvent](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/HistoryEvent.html) to get a [LambdaFunctionCompletedEventAttributes](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/LambdaFunctionCompletedEventAttributes.html) object, and then calling its `getResult` method to retrieve the output of the Lambda function:

```
 LambdaFunctionCompleted:
running_functions--;
```

## Complete source for this example<a name="complete-source-for-this-example"></a>

You can browse the *complete source :github:`<awsdocs/aws\-java\-developer\-guide/tree/master/doc\_source/snippets/helloswf\_lambda/>* for this example on Github in the *aws\-java\-developer\-guide* repository\.