# Sending Events to CloudWatch<a name="examples-cloudwatch-send-events"></a>

 CloudWatch Events delivers a near real\-time stream of system events that describe changes in AWS resources to Amazon EC2 instances, Lambda functions, Kinesis streams, Amazon ECS tasks, Step Functions state machines, Amazon SNS topics, Amazon SQS queues, or built\-in targets\. You can match events and route them to one or more target functions or streams by using simple rules\.

## Add Events<a name="add-events"></a>

To add custom CloudWatch events, call the AmazonCloudWatchEventsClient’s `putEvents` method with a [PutEventsRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/cloudwatchevents/model/PutEventsRequest.html) object that contains one or more [PutEventsRequestEntry](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/cloudwatchevents/model/PutEventsRequestEntry.html) objects that provide details about each event\. You can specify several parameters for the entry such as the source and type of the event, resources associated with the event, and so on\.

**Note**  
You can specify a maximum of 10 events per call to `putEvents`\.

 **Imports** 

```
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEvents;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEventsClientBuilder;
import com.amazonaws.services.cloudwatchevents.model.PutEventsRequest;
import com.amazonaws.services.cloudwatchevents.model.PutEventsRequestEntry;
import com.amazonaws.services.cloudwatchevents.model.PutEventsResult;
```

 **Code** 

```
final AmazonCloudWatchEvents cwe =
    AmazonCloudWatchEventsClientBuilder.defaultClient();

final String EVENT_DETAILS =
    "{ \"key1\": \"value1\", \"key2\": \"value2\" }";

PutEventsRequestEntry request_entry = new PutEventsRequestEntry()
    .withDetail(EVENT_DETAILS)
    .withDetailType("sampleSubmitted")
    .withResources(resource_arn)
    .withSource("aws-sdk-java-cloudwatch-example");

PutEventsRequest request = new PutEventsRequest()
    .withEntries(request_entry);

PutEventsResult response = cwe.putEvents(request);
```

## Add Rules<a name="add-rules"></a>

To create or update a rule, call the AmazonCloudWatchEventsClient’s `putRule` method with a [PutRuleRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/cloudwatchevents/model/PutRuleRequest.html) with the name of the rule and optional parameters such as the [event pattern](https://docs.aws.amazon.com/AmazonCloudWatch/latest/events/CloudWatchEventsandEventPatterns.html), IAM role to associate with the rule, and a [scheduling expression](https://docs.aws.amazon.com/AmazonCloudWatch/latest/events/ScheduledEvents.html) that describes how often the rule is run\.

 **Imports** 

```
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEvents;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEventsClientBuilder;
import com.amazonaws.services.cloudwatchevents.model.PutRuleRequest;
import com.amazonaws.services.cloudwatchevents.model.PutRuleResult;
import com.amazonaws.services.cloudwatchevents.model.RuleState;
```

 **Code** 

```
final AmazonCloudWatchEvents cwe =
    AmazonCloudWatchEventsClientBuilder.defaultClient();

PutRuleRequest request = new PutRuleRequest()
    .withName(rule_name)
    .withRoleArn(role_arn)
    .withScheduleExpression("rate(5 minutes)")
    .withState(RuleState.ENABLED);

PutRuleResult response = cwe.putRule(request);
```

## Add Targets<a name="add-targets"></a>

Targets are the resources that are invoked when a rule is triggered\. Example targets include Amazon EC2 instances, Lambda functions, Kinesis streams, Amazon ECS tasks, Step Functions state machines, and built\-in targets\.

To add a target to a rule, call the AmazonCloudWatchEventsClient’s `putTargets` method with a [PutTargetsRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/cloudwatchevents/model/PutTargetsRequest.html) containing the rule to update and a list of targets to add to the rule\.

 **Imports** 

```
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEvents;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEventsClientBuilder;
import com.amazonaws.services.cloudwatchevents.model.PutTargetsRequest;
import com.amazonaws.services.cloudwatchevents.model.PutTargetsResult;
import com.amazonaws.services.cloudwatchevents.model.Target;
```

 **Code** 

```
final AmazonCloudWatchEvents cwe =
    AmazonCloudWatchEventsClientBuilder.defaultClient();

Target target = new Target()
    .withArn(function_arn)
    .withId(target_id);

PutTargetsRequest request = new PutTargetsRequest()
    .withTargets(target)
    .withRule(rule_name);

PutTargetsResult response = cwe.putTargets(request);
```

## More Information<a name="more-information"></a>
+  [Adding Events with PutEvents](https://docs.aws.amazon.com/AmazonCloudWatch/latest/events/AddEventsPutEvents.html) in the Amazon CloudWatch Events User Guide
+  [Schedule Expressions for Rules](https://docs.aws.amazon.com/AmazonCloudWatch/latest/events/ScheduledEvents.html) in the Amazon CloudWatch Events User Guide
+  [Event Types for CloudWatch Events](https://docs.aws.amazon.com/AmazonCloudWatch/latest/events/EventTypes.html) in the Amazon CloudWatch Events User Guide
+  [Events and Event Patterns](https://docs.aws.amazon.com/AmazonCloudWatch/latest/events/CloudWatchEventsandEventPatterns.html) in the Amazon CloudWatch Events User Guide
+  [PutEvents](http://docs.aws.amazon.com/AmazonCloudWatchEvents/latest/APIReference/API_PutEvents.html) in the Amazon CloudWatch Events API Reference
+  [PutTargets](http://docs.aws.amazon.com/AmazonCloudWatchEvents/latest/APIReference/API_PutTargets.html) in the Amazon CloudWatch Events API Reference
+  [PutRule](http://docs.aws.amazon.com/AmazonCloudWatchEvents/latest/APIReference/API_PutRule.html) in the Amazon CloudWatch Events API Reference