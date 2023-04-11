# Create an Amazon EC2 Security Group<a name="create-security-group"></a>

## EC2\-Classic is retiring<a name="retiringEC2Classic"></a>

**Warning**  
We are retiring EC2\-Classic on August 15, 2022\. We recommend that you migrate from EC2\-Classic to a VPC\. For more information, see **Migrate from EC2\-Classic to a VPC** in the [Amazon EC2 User Guide for Linux Instances](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/vpc-migrate.html) or the [Amazon EC2 User Guide for Windows Instances](https://docs.aws.amazon.com/AWSEC2/latest/WindowsGuide/vpc-migrate.html)\. Also see the blog post [EC2\-Classic\-Classic Networking is Retiring – Here's How to Prepare](http://aws.amazon.com/blogs/aws/ec2-classic-is-retiring-heres-how-to-prepare/)\.

Create a *security group*, which acts as a virtual firewall that controls the network traffic for one or more EC2 instances\. By default, Amazon EC2 associates your instances with a security group that allows no inbound traffic\. You can create a security group that allows your EC2 instances to accept certain traffic\. For example, if you need to connect to a Linux instance, you must configure the security group to allow SSH traffic\. You can create a security group using the Amazon EC2 console or the AWS SDK for Java\.

You create a security group for use in either EC2\-Classic or EC2\-VPC\. For more information about EC2\-Classic and EC2\-VPC, see [Supported Platforms](http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/ec2-supported-platforms.html) in the Amazon EC2 User Guide for Linux Instances\.

For more information about creating a security group using the Amazon EC2 console, see [Amazon EC2 Security Groups](http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/using-network-security.html) in the Amazon EC2 User Guide for Linux Instances\.

1. Create and initialize a [CreateSecurityGroupRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/CreateSecurityGroupRequest.html) instance\. Use the [withGroupName](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/CreateSecurityGroupRequest.html#withGroupName-java.lang.String-) method to set the security group name, and the [withDescription](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/CreateSecurityGroupRequest.html#withDescription-java.lang.String-) method to set the security group description, as follows:

   ```
   CreateSecurityGroupRequest csgr = new CreateSecurityGroupRequest();
   csgr.withGroupName("JavaSecurityGroup").withDescription("My security group");
   ```

   The security group name must be unique within the AWS region in which you initialize your Amazon EC2 client\. You must use US\-ASCII characters for the security group name and description\.

1. Pass the request object as a parameter to the [createSecurityGroup](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/AmazonEC2.html#createSecurityGroup-com.amazonaws.services.ec2.model.CreateSecurityGroupRequest-) method\. The method returns a [CreateSecurityGroupResult](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/CreateSecurityGroupResult.html) object, as follows:

   ```
   CreateSecurityGroupResult createSecurityGroupResult =
       amazonEC2Client.createSecurityGroup(csgr);
   ```

   If you attempt to create a security group with the same name as an existing security group, `createSecurityGroup` throws an exception\.

By default, a new security group does not allow any inbound traffic to your Amazon EC2 instance\. To allow inbound traffic, you must explicitly authorize security group ingress\. You can authorize ingress for individual IP addresses, for a range of IP addresses, for a specific protocol, and for TCP/UDP ports\.

1. Create and initialize an [IpPermission](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/IpPermission.html) instance\. Use the [withIpv4Ranges](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/IpPermission.html#withIpv4Ranges-java.util.Collection-) method to set the range of IP addresses to authorize ingress for, and use the [withIpProtocol](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/IpPermission.html#withIpProtocol-java.lang.String-) method to set the IP protocol\. Use the [withFromPort](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/IpPermission.html#withFromPort-java.lang.Integer-) and [withToPort](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/IpPermission.html#withToPort-java.lang.Integer-) methods to specify range of ports to authorize ingress for, as follows:

   ```
   IpPermission ipPermission =
       new IpPermission();
   
   IpRange ipRange1 = new IpRange().withCidrIp("111.111.111.111/32");
   IpRange ipRange2 = new IpRange().withCidrIp("150.150.150.150/32");
   
   ipPermission.withIpv4Ranges(Arrays.asList(new IpRange[] {ipRange1, ipRange2}))
               .withIpProtocol("tcp")
               .withFromPort(22)
               .withToPort(22);
   ```

   All the conditions that you specify in the `IpPermission` object must be met in order for ingress to be allowed\.

   Specify the IP address using CIDR notation\. If you specify the protocol as TCP/UDP, you must provide a source port and a destination port\. You can authorize ports only if you specify TCP or UDP\.

1. Create and initialize an [AuthorizeSecurityGroupIngressRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/AuthorizeSecurityGroupEgressRequest.html) instance\. Use the `withGroupName` method to specify the security group name, and pass the `IpPermission` object you initialized earlier to the [withIpPermissions](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/AuthorizeSecurityGroupEgressRequest.html#withIpPermissions-com.amazonaws.services.ec2.model.IpPermission…​-) method, as follows:

   ```
   AuthorizeSecurityGroupIngressRequest authorizeSecurityGroupIngressRequest =
       new AuthorizeSecurityGroupIngressRequest();
   
   authorizeSecurityGroupIngressRequest.withGroupName("JavaSecurityGroup")
                                       .withIpPermissions(ipPermission);
   ```

1. Pass the request object into the [authorizeSecurityGroupIngress](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/AmazonEC2Client.html#authorizeSecurityGroupIngress-com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressRequest-) method, as follows:

   ```
   amazonEC2Client.authorizeSecurityGroupIngress(authorizeSecurityGroupIngressRequest);
   ```

   If you call `authorizeSecurityGroupIngress` with IP addresses for which ingress is already authorized, the method throws an exception\. Create and initialize a new `IpPermission` object to authorize ingress for different IPs, ports, and protocols before calling `AuthorizeSecurityGroupIngress`\.

Whenever you call the [authorizeSecurityGroupIngress](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/AmazonEC2Client.html#authorizeSecurityGroupIngress-com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressRequest-) or [authorizeSecurityGroupEgress](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/AmazonEC2Client.html#authorizeSecurityGroupEgress-com.amazonaws.services.ec2.model.AuthorizeSecurityGroupEgressRequest-) methods, a rule is added to your security group\.