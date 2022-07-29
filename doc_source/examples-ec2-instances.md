--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Managing Amazon EC2 Instances<a name="examples-ec2-instances"></a>

## Creating an Instance<a name="creating-an-instance"></a>

Create a new Amazon EC2 instance by calling the AmazonEC2Client’s `runInstances` method, providing it with a [RunInstancesRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/RunInstancesRequest.html) containing the [Amazon Machine Image \(AMI\)](http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/AMIs.html) to use and an [instance type](http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/instance-types.html)\.

 **Imports** 

```
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.InstanceType;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.Tag;
```

 **Code** 

```
RunInstancesRequest run_request = new RunInstancesRequest()
    .withImageId(ami_id)
    .withInstanceType(InstanceType.T1Micro)
    .withMaxCount(1)
    .withMinCount(1);

RunInstancesResult run_response = ec2.runInstances(run_request);

String reservation_id = run_response.getReservation().getInstances().get(0).getInstanceId();
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/ec2/src/main/java/aws/example/ec2/CreateInstance.java)\.

## Starting an Instance<a name="starting-an-instance"></a>

To start an Amazon EC2 instance, call the AmazonEC2Client’s `startInstances` method, providing it with a [StartInstancesRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/StartInstancesRequest.html) containing the ID of the instance to start\.

 **Imports** 

```
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
```

 **Code** 

```
final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

StartInstancesRequest request = new StartInstancesRequest()
    .withInstanceIds(instance_id);

ec2.startInstances(request);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/ec2/src/main/java/aws/example/ec2/StartStopInstance.java)\.

## Stopping an Instance<a name="stopping-an-instance"></a>

To stop an Amazon EC2 instance, call the AmazonEC2Client’s `stopInstances` method, providing it with a [StopInstancesRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/StopInstancesRequest.html) containing the ID of the instance to stop\.

 **Imports** 

```
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
```

 **Code** 

```
final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

StopInstancesRequest request = new StopInstancesRequest()
    .withInstanceIds(instance_id);

ec2.stopInstances(request);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/ec2/src/main/java/aws/example/ec2/StartStopInstance.java)\.

## Rebooting an Instance<a name="rebooting-an-instance"></a>

To reboot an Amazon EC2 instance, call the AmazonEC2Client’s `rebootInstances` method, providing it with a [RebootInstancesRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/RebootInstancesRequest.html) containing the ID of the instance to reboot\.

 **Imports** 

```
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.RebootInstancesRequest;
import com.amazonaws.services.ec2.model.RebootInstancesResult;
```

 **Code** 

```
final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

RebootInstancesRequest request = new RebootInstancesRequest()
    .withInstanceIds(instance_id);

RebootInstancesResult response = ec2.rebootInstances(request);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/ec2/src/main/java/aws/example/ec2/RebootInstance.java)\.

## Describing Instances<a name="describing-instances"></a>

To list your instances, create a [DescribeInstancesRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/DescribeInstancesRequest.html) and call the AmazonEC2Client’s `describeInstances` method\. It will return a [DescribeInstancesResult](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/DescribeInstancesResult.html) object that you can use to list the Amazon EC2 instances for your account and region\.

Instances are grouped by *reservation*\. Each reservation corresponds to the call to `startInstances` that launched the instance\. To list your instances, you must first call the `DescribeInstancesResult` class' `getReservations' method, and then call `getInstances` on each returned [Reservation](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/Reservation.html) object\.

 **Imports** 

```
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
```

 **Code** 

```
final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();
boolean done = false;

DescribeInstancesRequest request = new DescribeInstancesRequest();
while(!done) {
    DescribeInstancesResult response = ec2.describeInstances(request);

    for(Reservation reservation : response.getReservations()) {
        for(Instance instance : reservation.getInstances()) {
            System.out.printf(
                "Found instance with id %s, " +
                "AMI %s, " +
                "type %s, " +
                "state %s " +
                "and monitoring state %s",
                instance.getInstanceId(),
                instance.getImageId(),
                instance.getInstanceType(),
                instance.getState().getName(),
                instance.getMonitoring().getState());
        }
    }

    request.setNextToken(response.getNextToken());

    if(response.getNextToken() == null) {
        done = true;
    }
}
```

Results are paged; you can get further results by passing the value returned from the result object’s `getNextToken` method to your original request object’s `setNextToken` method, then using the same request object in your next call to `describeInstances`\.

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/ec2/src/main/java/aws/example/ec2/DescribeInstances.java)\.

## Monitoring an Instance<a name="monitoring-an-instance"></a>

You can monitor various aspects of your Amazon EC2 instances, such as CPU and network utilization, available memory, and disk space remaining\. To learn more about instance monitoring, see [Monitoring Amazon EC2](http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/monitoring_ec2.html) in the Amazon EC2 User Guide for Linux Instances\.

To start monitoring an instance, you must create a [MonitorInstancesRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/MonitorInstancesRequest.html) with the ID of the instance to monitor, and pass it to the AmazonEC2Client’s `monitorInstances` method\.

 **Imports** 

```
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.MonitorInstancesRequest;
```

 **Code** 

```
final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

MonitorInstancesRequest request = new MonitorInstancesRequest()
        .withInstanceIds(instance_id);

ec2.monitorInstances(request);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/ec2/src/main/java/aws/example/ec2/MonitorInstance.java)\.

## Stopping Instance Monitoring<a name="stopping-instance-monitoring"></a>

To stop monitoring an instance, create an [UnmonitorInstancesRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/UnmonitorInstancesRequest.html) with the ID of the instance to stop monitoring, and pass it to the AmazonEC2Client’s `unmonitorInstances` method\.

 **Imports** 

```
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.UnmonitorInstancesRequest;
```

 **Code** 

```
final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

UnmonitorInstancesRequest request = new UnmonitorInstancesRequest()
    .withInstanceIds(instance_id);

ec2.unmonitorInstances(request);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/ec2/src/main/java/aws/example/ec2/MonitorInstance.java)\.

## More Information<a name="more-information"></a>
+  [RunInstances](http://docs.aws.amazon.com/AWSEC2/latest/APIReference/API_RunInstances.html) in the Amazon EC2 API Reference
+  [DescribeInstances](http://docs.aws.amazon.com/AWSEC2/latest/APIReference/API_DescribeInstances.html) in the Amazon EC2 API Reference
+  [StartInstances](http://docs.aws.amazon.com/AWSEC2/latest/APIReference/API_StartInstances.html) in the Amazon EC2 API Reference
+  [StopInstances](http://docs.aws.amazon.com/AWSEC2/latest/APIReference/API_StopInstances.html) in the Amazon EC2 API Reference
+  [RebootInstances](http://docs.aws.amazon.com/AWSEC2/latest/APIReference/API_RebootInstances.html) in the Amazon EC2 API Reference
+  [MonitorInstances](http://docs.aws.amazon.com/AWSEC2/latest/APIReference/API_MonitorInstances.html) in the Amazon EC2 API Reference
+  [UnmonitorInstances](http://docs.aws.amazon.com/AWSEC2/latest/APIReference/API_UnmonitorInstances.html) in the Amazon EC2 API Reference