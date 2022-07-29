--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Working with CloudWatch Alarms<a name="examples-cloudwatch-create-alarms"></a>

## Create an Alarm<a name="create-an-alarm"></a>

To create an alarm based on a CloudWatch metric, call the AmazonCloudWatchClient’s `putMetricAlarm` method with a [PutMetricAlarmRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/cloudwatch/model/PutMetricAlarmRequest.html) filled with the alarm conditions\.

 **Imports** 

```
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.ComparisonOperator;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.PutMetricAlarmRequest;
import com.amazonaws.services.cloudwatch.model.PutMetricAlarmResult;
import com.amazonaws.services.cloudwatch.model.StandardUnit;
import com.amazonaws.services.cloudwatch.model.Statistic;
```

 **Code** 

```
final AmazonCloudWatch cw =
    AmazonCloudWatchClientBuilder.defaultClient();

Dimension dimension = new Dimension()
    .withName("InstanceId")
    .withValue(instanceId);

PutMetricAlarmRequest request = new PutMetricAlarmRequest()
    .withAlarmName(alarmName)
    .withComparisonOperator(
        ComparisonOperator.GreaterThanThreshold)
    .withEvaluationPeriods(1)
    .withMetricName("CPUUtilization")
    .withNamespace("{AWS}/EC2")
    .withPeriod(60)
    .withStatistic(Statistic.Average)
    .withThreshold(70.0)
    .withActionsEnabled(false)
    .withAlarmDescription(
        "Alarm when server CPU utilization exceeds 70%")
    .withUnit(StandardUnit.Seconds)
    .withDimensions(dimension);

PutMetricAlarmResult response = cw.putMetricAlarm(request);
```

## List Alarms<a name="list-alarms"></a>

To list the CloudWatch alarms that you have created, call the AmazonCloudWatchClient’s `describeAlarms` method with a [DescribeAlarmsRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/cloudwatch/model/DescribeAlarmsRequest.html) that you can use to set options for the result\.

 **Imports** 

```
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.DescribeAlarmsRequest;
import com.amazonaws.services.cloudwatch.model.DescribeAlarmsResult;
import com.amazonaws.services.cloudwatch.model.MetricAlarm;
```

 **Code** 

```
final AmazonCloudWatch cw =
    AmazonCloudWatchClientBuilder.defaultClient();

boolean done = false;
DescribeAlarmsRequest request = new DescribeAlarmsRequest();

while(!done) {

    DescribeAlarmsResult response = cw.describeAlarms(request);

    for(MetricAlarm alarm : response.getMetricAlarms()) {
        System.out.printf("Retrieved alarm %s", alarm.getAlarmName());
    }

    request.setNextToken(response.getNextToken());

    if(response.getNextToken() == null) {
        done = true;
    }
}
```

The list of alarms can be obtained by calling `getMetricAlarms` on the [DescribeAlarmsResult](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/cloudwatch/model/DescribeAlarmsResult.html) that is returned by `describeAlarms`\.

The results may be *paged*\. To retrieve the next batch of results, call `setNextToken` on the original request object with the return value of the `DescribeAlarmsResult` object’s `getNextToken` method, and pass the modified request object back to another call to `describeAlarms`\.

**Note**  
You can also retrieve alarms for a specific metric by using the AmazonCloudWatchClient’s `describeAlarmsForMetric` method\. Its use is similar to `describeAlarms`\.

## Delete Alarms<a name="delete-alarms"></a>

To delete CloudWatch alarms, call the AmazonCloudWatchClient’s `deleteAlarms` method with a [DeleteAlarmsRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/cloudwatch/model/DeleteAlarmsRequest.html) containing one or more names of alarms that you want to delete\.

 **Imports** 

```
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.DeleteAlarmsRequest;
import com.amazonaws.services.cloudwatch.model.DeleteAlarmsResult;
```

 **Code** 

```
final AmazonCloudWatch cw =
    AmazonCloudWatchClientBuilder.defaultClient();

DeleteAlarmsRequest request = new DeleteAlarmsRequest()
    .withAlarmNames(alarm_name);

DeleteAlarmsResult response = cw.deleteAlarms(request);
```

## More Information<a name="more-information"></a>
+  [Creating Amazon CloudWatch Alarms](https://docs.aws.amazon.com/AmazonCloudWatch/latest/monitoring/AlarmThatSendsEmail.html) in the Amazon CloudWatch User Guide
+  [PutMetricAlarm](http://docs.aws.amazon.com/AmazonCloudWatch/latest/APIReference/API_PutMetricAlarm.html) in the Amazon CloudWatch API Reference
+  [DescribeAlarms](http://docs.aws.amazon.com/AmazonCloudWatch/latest/APIReference/API_DescribeAlarms.html) in the Amazon CloudWatch API Reference
+  [DeleteAlarms](http://docs.aws.amazon.com/AmazonCloudWatch/latest/APIReference/API_DeleteAlarms.html) in the Amazon CloudWatch API Reference