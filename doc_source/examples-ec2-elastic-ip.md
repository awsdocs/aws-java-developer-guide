# Using Elastic IP Addresses in Amazon EC2<a name="examples-ec2-elastic-ip"></a>

## EC2\-Classic is retiring<a name="retiringEC2Classic"></a>

**Warning**  
We are retiring EC2\-Classic on August 15, 2022\. We recommend that you migrate from EC2\-Classic to a VPC\. For more information, see **Migrate from EC2\-Classic to a VPC** in the [Amazon EC2 User Guide for Linux Instances](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/vpc-migrate.html) or the [Amazon EC2 User Guide for Windows Instances](https://docs.aws.amazon.com/AWSEC2/latest/WindowsGuide/vpc-migrate.html)\. Also see the blog post [EC2\-Classic\-Classic Networking is Retiring – Here's How to Prepare](http://aws.amazon.com/blogs/aws/ec2-classic-is-retiring-heres-how-to-prepare/)\.

## Allocating an Elastic IP Address<a name="allocating-an-elastic-ip-address"></a>

To use an Elastic IP address, you first allocate one to your account, and then associate it with your instance or a network interface\.

To allocate an Elastic IP address, call the AmazonEC2Client’s `allocateAddress` method with an [AllocateAddressRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/AllocateAddressRequest.html) object containing the network type \(classic EC2 or VPC\)\.

The returned [AllocateAddressResult](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/AllocateAddressResult.html) contains an allocation ID that you can use to associate the address with an instance, by passing the allocation ID and instance ID in a [AssociateAddressRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/AssociateAddressRequest.html) to the AmazonEC2Client’s `associateAddress` method\.

 **Imports** 

```
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.AllocateAddressRequest;
import com.amazonaws.services.ec2.model.AllocateAddressResult;
import com.amazonaws.services.ec2.model.AssociateAddressRequest;
import com.amazonaws.services.ec2.model.AssociateAddressResult;
import com.amazonaws.services.ec2.model.DomainType;
```

 **Code** 

```
final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

AllocateAddressRequest allocate_request = new AllocateAddressRequest()
    .withDomain(DomainType.Vpc);

AllocateAddressResult allocate_response =
    ec2.allocateAddress(allocate_request);

String allocation_id = allocate_response.getAllocationId();

AssociateAddressRequest associate_request =
    new AssociateAddressRequest()
        .withInstanceId(instance_id)
        .withAllocationId(allocation_id);

AssociateAddressResult associate_response =
    ec2.associateAddress(associate_request);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/ec2/src/main/java/aws/example/ec2/AllocateAddress.java)\.

## Describing Elastic IP Addresses<a name="describing-elastic-ip-addresses"></a>

To list the Elastic IP addresses assigned to your account, call the AmazonEC2Client’s `describeAddresses` method\. It returns a [DescribeAddressesResult](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/DescribeAddressesResult.html) which you can use to get a list of [Address](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/Address.html) objects that represent the Elastic IP addresses on your account\.

 **Imports** 

```
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.Address;
import com.amazonaws.services.ec2.model.DescribeAddressesResult;
```

 **Code** 

```
final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

DescribeAddressesResult response = ec2.describeAddresses();

for(Address address : response.getAddresses()) {
    System.out.printf(
            "Found address with public IP %s, " +
            "domain %s, " +
            "allocation id %s " +
            "and NIC id %s",
            address.getPublicIp(),
            address.getDomain(),
            address.getAllocationId(),
            address.getNetworkInterfaceId());
}
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/ec2/src/main/java/aws/example/ec2/DescribeAddresses.java)\.

## Releasing an Elastic IP Address<a name="releasing-an-elastic-ip-address"></a>

To release an Elastic IP address, call the AmazonEC2Client’s `releaseAddress` method, passing it a [ReleaseAddressRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/ReleaseAddressRequest.html) containing the allocation ID of the Elastic IP address you want to release\.

 **Imports** 

```
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.ReleaseAddressRequest;
import com.amazonaws.services.ec2.model.ReleaseAddressResult;
```

 **Code** 

```
final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

ReleaseAddressRequest request = new ReleaseAddressRequest()
    .withAllocationId(alloc_id);

ReleaseAddressResult response = ec2.releaseAddress(request);
```

After you release an Elastic IP address, it is released to the AWS IP address pool and might be unavailable to you afterward\. Be sure to update your DNS records and any servers or devices that communicate with the address\. If you attempt to release an Elastic IP address that you already released, you’ll get an *AuthFailure* error if the address is already allocated to another AWS account\.

If you are using *EC2\-Classic* or a *default VPC*, then releasing an Elastic IP address automatically disassociates it from any instance that it’s associated with\. To disassociate an Elastic IP address without releasing it, use the AmazonEC2Client’s `disassociateAddress` method\.

If you are using a non\-default VPC, you *must* use `disassociateAddress` to disassociate the Elastic IP address before you try to release it\. Otherwise, Amazon EC2 returns an error \(*InvalidIPAddress\.InUse*\)\.

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/ec2/src/main/java/aws/example/ec2/ReleaseAddress.java)\.

## More Information<a name="more-information"></a>
+  [Elastic IP Addresses](http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/elastic-ip-addresses-eip.html) in the Amazon EC2 User Guide for Linux Instances
+  [AllocateAddress](http://docs.aws.amazon.com/AWSEC2/latest/APIReference/API_AllocateAddress.html) in the Amazon EC2 API Reference
+  [DescribeAddresses](http://docs.aws.amazon.com/AWSEC2/latest/APIReference/API_DescribeAddresses.html) in the Amazon EC2 API Reference
+  [ReleaseAddress](http://docs.aws.amazon.com/AWSEC2/latest/APIReference/API_ReleaseAddress.html) in the Amazon EC2 API Reference