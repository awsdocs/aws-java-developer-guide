# Using Alarm Actions in CloudWatch<a name="examples-cloudwatch-use-alarm-actions"></a>

Using CloudWatch alarm actions, you can create alarms that perform actions such as automatically stopping, terminating, rebooting, or recovering Amazon EC2 instances\.

**Note**  
Alarm actions can be added to an alarm by using the [PutMetricAlarmRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/cloudwatch/model/PutMetricAlarmRequest.html)'s `setAlarmActions` method when [creating an alarm](examples-cloudwatch-create-alarms.md)\.

## Enable Alarm Actions<a name="enable-alarm-actions"></a>

To enable alarm actions for a CloudWatch alarm, call the AmazonCloudWatchClient’s `enableAlarmActions` with a [EnableAlarmActionsRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/cloudwatch/model/EnableAlarmActionsRequest.html) containing one or more names of alarms whose actions you want to enable\.

 **Imports** 

```
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.EnableAlarmActionsRequest;
import com.amazonaws.services.cloudwatch.model.EnableAlarmActionsResult;
```

 **Code** 

```
final AmazonCloudWatch cw =
    AmazonCloudWatchClientBuilder.defaultClient();

EnableAlarmActionsRequest request = new EnableAlarmActionsRequest()
    .withAlarmNames(alarm);

EnableAlarmActionsResult response = cw.enableAlarmActions(request);
```

## Disable Alarm Actions<a name="disable-alarm-actions"></a>

To disable alarm actions for a CloudWatch alarm, call the AmazonCloudWatchClient’s `disableAlarmActions` with a [DisableAlarmActionsRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/cloudwatch/model/DisableAlarmActionsRequest.html) containing one or more names of alarms whose actions you want to disable\.

 **Imports** 

```
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.DisableAlarmActionsRequest;
import com.amazonaws.services.cloudwatch.model.DisableAlarmActionsResult;
```

 **Code** 

```
final AmazonCloudWatch cw =
    AmazonCloudWatchClientBuilder.defaultClient();

DisableAlarmActionsRequest request = new DisableAlarmActionsRequest()
    .withAlarmNames(alarmName);

DisableAlarmActionsResult response = cw.disableAlarmActions(request);
```

## More Information<a name="more-information"></a>
+  [Create Alarms to Stop, Terminate, Reboot, or Recover an Instance](https://docs.aws.amazon.com/AmazonCloudWatch/latest/monitoring/UsingAlarmActions.html) in the Amazon CloudWatch User Guide
+  [PutMetricAlarm](http://docs.aws.amazon.com/AmazonCloudWatch/latest/APIReference/API_PutMetricAlarm.html) in the Amazon CloudWatch API Reference
+  [EnableAlarmActions](http://docs.aws.amazon.com/AmazonCloudWatch/latest/APIReference/API_EnableAlarmActions.html) in the Amazon CloudWatch API Reference
+  [DisableAlarmActions](http://docs.aws.amazon.com/AmazonCloudWatch/latest/APIReference/API_DisableAlarmActions.html) in the Amazon CloudWatch API Reference