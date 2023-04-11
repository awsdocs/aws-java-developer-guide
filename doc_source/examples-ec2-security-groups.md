# Working with Security Groups in Amazon EC2<a name="examples-ec2-security-groups"></a>

## Creating a Security Group<a name="creating-a-security-group"></a>

To create a security group, call the AmazonEC2Client’s `createSecurityGroup` method with a [CreateSecurityGroupRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/CreateSecurityGroupRequest.html) that contains the key’s name\.

 **Imports** 

```
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.CreateSecurityGroupRequest;
import com.amazonaws.services.ec2.model.CreateSecurityGroupResult;
```

 **Code** 

```
final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

CreateSecurityGroupRequest create_request = new
    CreateSecurityGroupRequest()
        .withGroupName(group_name)
        .withDescription(group_desc)
        .withVpcId(vpc_id);

CreateSecurityGroupResult create_response =
    ec2.createSecurityGroup(create_request);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/ec2/src/main/java/aws/example/ec2/CreateSecurityGroup.java)\.

## Configuring a Security Group<a name="configuring-a-security-group"></a>

A security group can control both inbound \(ingress\) and outbound \(egress\) traffic to your Amazon EC2 instances\.

To add ingress rules to your security group, use the AmazonEC2Client’s `authorizeSecurityGroupIngress` method, providing the name of the security group and the access rules \([IpPermission](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/IpPermission.html)\) you want to assign to it within an [AuthorizeSecurityGroupIngressRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/AuthorizeSecurityGroupIngressRequest.html) object\. The following example shows how to add IP permissions to a security group\.

 **Imports** 

```
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.CreateSecurityGroupRequest;
import com.amazonaws.services.ec2.model.CreateSecurityGroupResult;
```

 **Code** 

```
IpRange ip_range = new IpRange()
    .withCidrIp("0.0.0.0/0");

IpPermission ip_perm = new IpPermission()
    .withIpProtocol("tcp")
    .withToPort(80)
    .withFromPort(80)
    .withIpv4Ranges(ip_range);

IpPermission ip_perm2 = new IpPermission()
    .withIpProtocol("tcp")
    .withToPort(22)
    .withFromPort(22)
    .withIpv4Ranges(ip_range);

AuthorizeSecurityGroupIngressRequest auth_request = new
    AuthorizeSecurityGroupIngressRequest()
        .withGroupName(group_name)
        .withIpPermissions(ip_perm, ip_perm2);

AuthorizeSecurityGroupIngressResult auth_response =
    ec2.authorizeSecurityGroupIngress(auth_request);
```

To add an egress rule to the security group, provide similar data in an [AuthorizeSecurityGroupEgressRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/AuthorizeSecurityGroupEgressRequest.html) to the AmazonEC2Client’s `authorizeSecurityGroupEgress` method\.

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/ec2/src/main/java/aws/example/ec2/CreateSecurityGroup.java)\.

## Describing Security Groups<a name="describing-security-groups"></a>

To describe your security groups or get information about them, call the AmazonEC2Client’s `describeSecurityGroups` method\. It returns a [DescribeSecurityGroupsResult](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/DescribeSecurityGroupsResult.html) that you can use to access the list of security groups by calling its `getSecurityGroups` method, which returns a list of [SecurityGroupInfo](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/SecurityGroupInfo.html) objects\.

 **Imports** 

```
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsRequest;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsResult;
```

 **Code** 

```
final String USAGE =
    "To run this example, supply a group id\n" +
    "Ex: DescribeSecurityGroups <group-id>\n";

if (args.length != 1) {
    System.out.println(USAGE);
    System.exit(1);
}

String group_id = args[0];
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/ec2/src/main/java/aws/example/ec2/DescribeSecurityGroups.java)\.

## Deleting a Security Group<a name="deleting-a-security-group"></a>

To delete a security group, call the AmazonEC2Client’s `deleteSecurityGroup` method, passing it a [DeleteSecurityGroupRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/DeleteSecurityGroupRequest.html) that contains the ID of the security group to delete\.

 **Imports** 

```
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DeleteSecurityGroupRequest;
import com.amazonaws.services.ec2.model.DeleteSecurityGroupResult;
```

 **Code** 

```
final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

DeleteSecurityGroupRequest request = new DeleteSecurityGroupRequest()
    .withGroupId(group_id);

DeleteSecurityGroupResult response = ec2.deleteSecurityGroup(request);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/ec2/src/main/java/aws/example/ec2/DeleteSecurityGroup.java)\.

## More Information<a name="more-information"></a>
+  [Amazon EC2 Security Groups](http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/ec2-key-pairs.html) in the Amazon EC2 User Guide for Linux Instances
+  [Authorizing Inbound Traffic for Your Linux Instances](http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/authorizing-access-to-an-instance.html) in the Amazon EC2 User Guide for Linux Instances
+  [CreateSecurityGroup](http://docs.aws.amazon.com/AWSEC2/latest/APIReference/API_CreateSecurityGroup.html) in the Amazon EC2 API Reference
+  [DescribeSecurityGroups](http://docs.aws.amazon.com/AWSEC2/latest/APIReference/API_DescribeSecurityGroups.html) in the Amazon EC2 API Reference
+  [DeleteSecurityGroup](http://docs.aws.amazon.com/AWSEC2/latest/APIReference/API_DeleteSecurityGroup.html) in the Amazon EC2 API Reference
+  [AuthorizeSecurityGroupIngress](http://docs.aws.amazon.com/AWSEC2/latest/APIReference/API_AuthorizeSecurityGroupIngress.html) in the Amazon EC2 API Reference