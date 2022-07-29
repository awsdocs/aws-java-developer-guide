--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Run an Amazon EC2 Instance<a name="run-instance"></a>

Use the following procedure to launch one or more identically configured EC2 instances from the same Amazon Machine Image \(AMI\)\. After you create your EC2 instances, you can check their status\. After your EC2 instances are running, you can connect to them\.

1. Create and initialize a [RunInstancesRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/RunInstancesRequest.html) instance\. Make sure that the AMI, key pair, and security group that you specify exist in the region that you specified when you created the client object\.

   ```
   RunInstancesRequest runInstancesRequest =
      new RunInstancesRequest();
   
   runInstancesRequest.withImageId("ami-a9d09ed1")
                      .withInstanceType(InstanceType.T1Micro)
                      .withMinCount(1)
                      .withMaxCount(1)
                      .withKeyName("my-key-pair")
                      .withSecurityGroups("my-security-group");
   ```  
 [withImageId](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/RunInstancesRequest.html#withImageId-java.lang.String-)   
   + The ID of the AMI\. To learn how to find public AMIs provided by Amazon or create your own, see [Amazon Machine Image \(AMI\)](http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/AMIs.html)\.  
 [withInstanceType](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/RunInstancesRequest.html#withInstanceType-java.lang.String-)   
   + An instance type that is compatible with the specified AMI\. For more information, see [Instance Types](http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/instance-types.html) in the Amazon EC2 User Guide for Linux Instances\.  
 [withMinCount](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/RunInstancesRequest.html#withMinCount-java.lang.Integer-)   
   + The minimum number of EC2 instances to launch\. If this is more instances than Amazon EC2 can launch in the target Availability Zone, Amazon EC2 launches no instances\.  
 [withMaxCount](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/RunInstancesRequest.html#withMaxCount-java.lang.Integer-)   
   + The maximum number of EC2 instances to launch\. If this is more instances than Amazon EC2 can launch in the target Availability Zone, Amazon EC2 launches the largest possible number of instances above `MinCount`\. You can launch between 1 and the maximum number of instances you’re allowed for the instance type\. For more information, see How many instances can I run in Amazon EC2 in the Amazon EC2 General FAQ\.  
 [withKeyName](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/RunInstancesRequest.html#withKeyName-java.lang.String-)   
   + The name of the EC2 key pair\. If you launch an instance without specifying a key pair, you can’t connect to it\. For more information, see [Create a Key Pair](create-key-pair.md)\.  
 [withSecurityGroups](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/RunInstancesRequest.html#withSecurityGroups-java.util.Collection-)   
   + One or more security groups\. For more information, see [Create an Amazon EC2 Security Group](create-security-group.md)\.

1. Launch the instances by passing the request object to the [runInstances](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/AmazonEC2Client.html#runInstances-com.amazonaws.services.ec2.model.RunInstancesRequest-) method\. The method returns a [RunInstancesResult](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/RunInstancesResult.html) object, as follows:

   ```
   RunInstancesResult result = amazonEC2Client.runInstances(
                                 runInstancesRequest);
   ```

After your instance is running, you can connect to it using your key pair\. For more information, see [Connect to Your Linux Instance](http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/AccessingInstances.html)\. in the Amazon EC2 User Guide for Linux Instances\.