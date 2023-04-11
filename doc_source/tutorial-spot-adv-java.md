# Tutorial: Advanced Amazon EC2 Spot Request Management<a name="tutorial-spot-adv-java"></a>

 Amazon EC2 Spot Instances allow you to bid on unused Amazon EC2 capacity and run those instances for as long as your bid exceeds the current *spot price*\. Amazon EC2 changes the spot price periodically based on supply and demand\. For more information about Spot Instances, see [Spot Instances](http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/using-spot-instances.html) in the Amazon EC2 User Guide for Linux Instances\.

## Prerequisites<a name="tutor-spot-adv-java-prereq"></a>

To use this tutorial you must have the AWS SDK for Java installed, as well as having met its basic installation prerequisites\. See [Set up the AWS SDK for Java](setup-install.md) for more information\.

## Setting up your credentials<a name="tutor-spot-adv-java-credentials"></a>

To begin using this code sample, you need to set up AWS credentials\. See [Set up AWS Credentials and Region for Development](setup-credentials.md) for instructions on how to do that\.

**Note**  
We recommend that you use the credentials of an IAM user to provide these values\. For more information, see [Sign Up for AWS and Create an IAM User](signup-create-iam-user.md)\.

Now that you have configured your settings, you can get started using the code in the example\.

## Setting up a security group<a name="tutor-spot-adv-java-sg"></a>

A security group acts as a firewall that controls the traffic allowed in and out of a group of instances\. By default, an instance is started without any security group, which means that all incoming IP traffic, on any TCP port will be denied\. So, before submitting our Spot Request, we will set up a security group that allows the necessary network traffic\. For the purposes of this tutorial, we will create a new security group called "GettingStarted" that allows Secure Shell \(SSH\) traffic from the IP address where you are running your application from\. To set up a new security group, you need to include or run the following code sample that sets up the security group programmatically\.

After we create an `AmazonEC2` client object, we create a `CreateSecurityGroupRequest` object with the name, "GettingStarted" and a description for the security group\. Then we call the `ec2.createSecurityGroup` API to create the group\.

To enable access to the group, we create an `ipPermission` object with the IP address range set to the CIDR representation of the subnet for the local computer; the "/10" suffix on the IP address indicates the subnet for the specified IP address\. We also configure the `ipPermission` object with the TCP protocol and port 22 \(SSH\)\. The final step is to call `ec2 .authorizeSecurityGroupIngress` with the name of our security group and the `ipPermission` object\.

\(The following code is the same as what we used in the first tutorial\.\)

```
// Create the AmazonEC2Client object so we can call various APIs.
AmazonEC2 ec2 = AmazonEC2ClientBuilder.standard()
                    .withCredentials(credentials)
                    .build();

// Create a new security group.
try {
    CreateSecurityGroupRequest securityGroupRequest =
        new CreateSecurityGroupRequest("GettingStartedGroup",
        "Getting Started Security Group");
    ec2.createSecurityGroup(securityGroupRequest);
} catch (AmazonServiceException ase) {
    // Likely this means that the group is already created, so ignore.
    System.out.println(ase.getMessage());
}

String ipAddr = "0.0.0.0/0";

// Get the IP of the current host, so that we can limit the Security Group
// by default to the ip range associated with your subnet.
try {
    // Get IP Address
    InetAddress addr = InetAddress.getLocalHost();
    ipAddr = addr.getHostAddress()+"/10";
}
catch (UnknownHostException e) {
    // Fail here...
}

// Create a range that you would like to populate.
ArrayList<String> ipRanges = new ArrayList<String>();
ipRanges.add(ipAddr);

// Open up port 22 for TCP traffic to the associated IP from
// above (e.g. ssh traffic).
ArrayList<IpPermission> ipPermissions = new ArrayList<IpPermission> ();
IpPermission ipPermission = new IpPermission();
ipPermission.setIpProtocol("tcp");
ipPermission.setFromPort(new Integer(22));
ipPermission.setToPort(new Integer(22));
ipPermission.setIpRanges(ipRanges);
ipPermissions.add(ipPermission);

try {
    // Authorize the ports to the used.
    AuthorizeSecurityGroupIngressRequest ingressRequest =
        new AuthorizeSecurityGroupIngressRequest(
            "GettingStartedGroup",ipPermissions);
    ec2.authorizeSecurityGroupIngress(ingressRequest);
}
catch (AmazonServiceException ase) {
    // Ignore because this likely means the zone has already
    // been authorized.
    System.out.println(ase.getMessage());
}
```

You can view this entire code sample in the `advanced.CreateSecurityGroupApp.java` code sample\. Note you only need to run this application once to create a new security group\.

**Note**  
You can also create the security group using the AWS Toolkit for Eclipse\. See [Managing Security Groups from AWS Cost Explorer](https://docs.aws.amazon.com/toolkit-for-eclipse/v1/user-guide/tke-sg.html) in the AWS Toolkit for Eclipse User Guide for more information\.

## Detailed Spot Instance request creation options<a name="tutor-spot-adv-req-opts"></a>

As we explained in [Tutorial: Amazon EC2 Spot Instances](tutorial-spot-instances-java.md), you need to build your request with an instance type, an Amazon Machine Image \(AMI\), and maximum bid price\.

Let’s start by creating a `RequestSpotInstanceRequest` object\. The request object requires the number of instances you want and the bid price\. Additionally, we need to set the `LaunchSpecification` for the request, which includes the instance type, AMI ID, and security group you want to use\. After the request is populated, we call the `requestSpotInstances` method on the `AmazonEC2Client` object\. An example of how to request a Spot Instance follows\.

\(The following code is the same as what we used in the first tutorial\.\)

```
// Create the AmazonEC2 client so we can call various APIs.
AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

// Initializes a Spot Instance Request
RequestSpotInstancesRequest requestRequest = new RequestSpotInstancesRequest();

// Request 1 x t1.micro instance with a bid price of $0.03.
requestRequest.setSpotPrice("0.03");
requestRequest.setInstanceCount(Integer.valueOf(1));

// Set up the specifications of the launch. This includes the
// instance type (e.g. t1.micro) and the latest Amazon Linux
// AMI id available. Note, you should always use the latest
// Amazon Linux AMI id or another of your choosing.
LaunchSpecification launchSpecification = new LaunchSpecification();
launchSpecification.setImageId("ami-a9d09ed1");
launchSpecification.setInstanceType(InstanceType.T1Micro);

// Add the security group to the request.
ArrayList<String> securityGroups = new ArrayList<String>();
securityGroups.add("GettingStartedGroup");
launchSpecification.setSecurityGroups(securityGroups);

// Add the launch specification.
requestRequest.setLaunchSpecification(launchSpecification);

// Call the RequestSpotInstance API.
RequestSpotInstancesResult requestResult =
    ec2.requestSpotInstances(requestRequest);
```

## Persistent vs\. one\-time requests<a name="tutor-spot-adv-persist-v-one"></a>

When building a Spot request, you can specify several optional parameters\. The first is whether your request is one\-time only or persistent\. By default, it is a one\-time request\. A one\-time request can be fulfilled only once, and after the requested instances are terminated, the request will be closed\. A persistent request is considered for fulfillment whenever there is no Spot Instance running for the same request\. To specify the type of request, you simply need to set the Type on the Spot request\. This can be done with the following code\.

```
// Retrieves the credentials from an AWSCredentials.properties file.
AWSCredentials credentials = null;
try {
    credentials = new PropertiesCredentials(
        GettingStartedApp.class.getResourceAsStream("AwsCredentials.properties"));
}
catch (IOException e1) {
    System.out.println(
        "Credentials were not properly entered into AwsCredentials.properties.");
    System.out.println(e1.getMessage());
    System.exit(-1);
}

// Create the AmazonEC2 client so we can call various APIs.
AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

// Initializes a Spot Instance Request
RequestSpotInstancesRequest requestRequest =
    new RequestSpotInstancesRequest();

// Request 1 x t1.micro instance with a bid price of $0.03.
requestRequest.setSpotPrice("0.03");
requestRequest.setInstanceCount(Integer.valueOf(1));

// Set the type of the bid to persistent.
requestRequest.setType("persistent");

// Set up the specifications of the launch. This includes the
// instance type (e.g. t1.micro) and the latest Amazon Linux
// AMI id available. Note, you should always use the latest
// Amazon Linux AMI id or another of your choosing.
LaunchSpecification launchSpecification = new LaunchSpecification();
launchSpecification.setImageId("ami-a9d09ed1");
launchSpecification.setInstanceType(InstanceType.T1Micro);

// Add the security group to the request.
ArrayList<String> securityGroups = new ArrayList<String>();
securityGroups.add("GettingStartedGroup");
launchSpecification.setSecurityGroups(securityGroups);

// Add the launch specification.
requestRequest.setLaunchSpecification(launchSpecification);

// Call the RequestSpotInstance API.
RequestSpotInstancesResult requestResult =
    ec2.requestSpotInstances(requestRequest);
```

## Limiting the duration of a request<a name="tutor-spot-adv-validity-period"></a>

You can also optionally specify the length of time that your request will remain valid\. You can specify both a starting and ending time for this period\. By default, a Spot request will be considered for fulfillment from the moment it is created until it is either fulfilled or canceled by you\. However you can constrain the validity period if you need to\. An example of how to specify this period is shown in the following code\.

```
// Create the AmazonEC2 client so we can call various APIs.
AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

// Initializes a Spot Instance Request
RequestSpotInstancesRequest requestRequest = new RequestSpotInstancesRequest();

// Request 1 x t1.micro instance with a bid price of $0.03.
requestRequest.setSpotPrice("0.03");
requestRequest.setInstanceCount(Integer.valueOf(1));

// Set the valid start time to be two minutes from now.
Calendar cal = Calendar.getInstance();
cal.add(Calendar.MINUTE, 2);
requestRequest.setValidFrom(cal.getTime());

// Set the valid end time to be two minutes and two hours from now.
cal.add(Calendar.HOUR, 2);
requestRequest.setValidUntil(cal.getTime());

// Set up the specifications of the launch. This includes
// the instance type (e.g. t1.micro)

// and the latest Amazon Linux AMI id available.
// Note, you should always use the latest Amazon
// Linux AMI id or another of your choosing.
LaunchSpecification launchSpecification = new LaunchSpecification();
launchSpecification.setImageId("ami-a9d09ed1");
launchSpecification.setInstanceType("t1.micro");

// Add the security group to the request.
ArrayList<String> securityGroups = new ArrayList<String>();
securityGroups.add("GettingStartedGroup");
launchSpecification.setSecurityGroups(securityGroups);

// Add the launch specification.
requestRequest.setLaunchSpecification(launchSpecification);

// Call the RequestSpotInstance API.
RequestSpotInstancesResult requestResult = ec2.requestSpotInstances(requestRequest);
```

## Grouping your Amazon EC2 Spot Instance requests<a name="tutor-spot-adv-grouping"></a>

You have the option of grouping your Spot Instance requests in several different ways\. We’ll look at the benefits of using launch groups, Availability Zone groups, and placement groups\.

If you want to ensure your Spot Instances are all launched and terminated together, then you have the option to leverage a launch group\. A launch group is a label that groups a set of bids together\. All instances in a launch group are started and terminated together\. Note, if instances in a launch group have already been fulfilled, there is no guarantee that new instances launched with the same launch group will also be fulfilled\. An example of how to set a Launch Group is shown in the following code example\.

```
// Create the AmazonEC2 client so we can call various APIs.
AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

// Initializes a Spot Instance Request
RequestSpotInstancesRequest requestRequest = new RequestSpotInstancesRequest();

// Request 5 x t1.micro instance with a bid price of $0.03.
requestRequest.setSpotPrice("0.03");
requestRequest.setInstanceCount(Integer.valueOf(5));

// Set the launch group.
requestRequest.setLaunchGroup("ADVANCED-DEMO-LAUNCH-GROUP");

// Set up the specifications of the launch. This includes
// the instance type (e.g. t1.micro) and the latest Amazon Linux
// AMI id available. Note, you should always use the latest
// Amazon Linux AMI id or another of your choosing.
LaunchSpecification launchSpecification = new LaunchSpecification();
launchSpecification.setImageId("ami-a9d09ed1");
launchSpecification.setInstanceType(InstanceType.T1Micro);

// Add the security group to the request.
ArrayList<String> securityGroups = new ArrayList<String>();
securityGroups.add("GettingStartedGroup");
launchSpecification.setSecurityGroups(securityGroups);

// Add the launch specification.
requestRequest.setLaunchSpecification(launchSpecification);

// Call the RequestSpotInstance API.
RequestSpotInstancesResult requestResult =
    ec2.requestSpotInstances(requestRequest);
```

If you want to ensure that all instances within a request are launched in the same Availability Zone, and you don’t care which one, you can leverage Availability Zone groups\. An Availability Zone group is a label that groups a set of instances together in the same Availability Zone\. All instances that share an Availability Zone group and are fulfilled at the same time will start in the same Availability Zone\. An example of how to set an Availability Zone group follows\.

```
// Create the AmazonEC2 client so we can call various APIs.
AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

// Initializes a Spot Instance Request
RequestSpotInstancesRequest requestRequest = new RequestSpotInstancesRequest();

// Request 5 x t1.micro instance with a bid price of $0.03.
requestRequest.setSpotPrice("0.03");
requestRequest.setInstanceCount(Integer.valueOf(5));

// Set the availability zone group.
requestRequest.setAvailabilityZoneGroup("ADVANCED-DEMO-AZ-GROUP");

// Set up the specifications of the launch.  This includes the instance
// type (e.g.  t1.micro) and the latest Amazon Linux AMI id available.
// Note, you should always use the latest Amazon Linux AMI id or another
// of your choosing.
LaunchSpecification launchSpecification = new LaunchSpecification();
launchSpecification.setImageId("ami-a9d09ed1");
launchSpecification.setInstanceType(InstanceType.T1Micro);

// Add the security group to the request.
ArrayList<String> securityGroups = new ArrayList<String>();
securityGroups.add("GettingStartedGroup");
launchSpecification.setSecurityGroups(securityGroups);

// Add the launch specification.
requestRequest.setLaunchSpecification(launchSpecification);

// Call the RequestSpotInstance API.
RequestSpotInstancesResult requestResult =
    ec2.requestSpotInstances(requestRequest);
```

You can specify an Availability Zone that you want for your Spot Instances\. The following code example shows you how to set an Availability Zone\.

```
// Create the AmazonEC2 client so we can call various APIs.
AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

// Initializes a Spot Instance Request
RequestSpotInstancesRequest requestRequest = new RequestSpotInstancesRequest();

// Request 1 x t1.micro instance with a bid price of $0.03.
requestRequest.setSpotPrice("0.03");
requestRequest.setInstanceCount(Integer.valueOf(1));

// Set up the specifications of the launch. This includes the instance
// type (e.g. t1.micro) and the latest Amazon Linux AMI id available.
// Note, you should always use the latest Amazon Linux AMI id or another
// of your choosing.
LaunchSpecification launchSpecification = new LaunchSpecification();
launchSpecification.setImageId("ami-a9d09ed1");
launchSpecification.setInstanceType(InstanceType.T1Micro);

// Add the security group to the request.
ArrayList<String> securityGroups = new ArrayList<String>();
securityGroups.add("GettingStartedGroup");
launchSpecification.setSecurityGroups(securityGroups);

// Set up the availability zone to use. Note we could retrieve the
// availability zones using the ec2.describeAvailabilityZones() API. For
// this demo we will just use us-east-1a.
SpotPlacement placement = new SpotPlacement("us-east-1b");
launchSpecification.setPlacement(placement);

// Add the launch specification.
requestRequest.setLaunchSpecification(launchSpecification);

// Call the RequestSpotInstance API.
RequestSpotInstancesResult requestResult =
    ec2.requestSpotInstances(requestRequest);
```

Lastly, you can specify a *placement group* if you are using High Performance Computing \(HPC\) Spot Instances, such as cluster compute instances or cluster GPU instances\. Placement groups provide you with lower latency and high\-bandwidth connectivity between the instances\. An example of how to set a placement group follows\.

```
// Create the AmazonEC2 client so we can call various APIs.
AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

// Initializes a Spot Instance Request
RequestSpotInstancesRequest requestRequest = new RequestSpotInstancesRequest();

// Request 1 x t1.micro instance with a bid price of $0.03.
requestRequest.setSpotPrice("0.03");
requestRequest.setInstanceCount(Integer.valueOf(1));

// Set up the specifications of the launch. This includes the instance
// type (e.g. t1.micro) and the latest Amazon Linux AMI id available.
// Note, you should always use the latest Amazon Linux AMI id or another
// of your choosing.

LaunchSpecification launchSpecification = new LaunchSpecification();
launchSpecification.setImageId("ami-a9d09ed1");
launchSpecification.setInstanceType(InstanceType.T1Micro);

// Add the security group to the request.
ArrayList<String> securityGroups = new ArrayList<String>();
securityGroups.add("GettingStartedGroup");
launchSpecification.setSecurityGroups(securityGroups);

// Set up the placement group to use with whatever name you desire.
// For this demo we will just use "ADVANCED-DEMO-PLACEMENT-GROUP".
SpotPlacement placement = new SpotPlacement();
placement.setGroupName("ADVANCED-DEMO-PLACEMENT-GROUP");
launchSpecification.setPlacement(placement);

// Add the launch specification.
requestRequest.setLaunchSpecification(launchSpecification);

// Call the RequestSpotInstance API.
RequestSpotInstancesResult requestResult =
    ec2.requestSpotInstances(requestRequest);
```

All of the parameters shown in this section are optional\. It is also important to realize that most of these parameters—​with the exception of whether your bid is one\-time or persistent—​can reduce the likelihood of bid fulfillment\. So, it is important to leverage these options only if you need them\. All of the preceding code examples are combined into one long code sample, which can be found in the `com.amazonaws.codesamples.advanced.InlineGettingStartedCodeSampleApp.java` class\.

## How to persist a root partition after interruption or termination<a name="tutor-spot-adv-persist-root"></a>

One of the easiest ways to manage interruption of your Spot Instances is to ensure that your data is checkpointed to an Amazon Elastic Block Store \(Amazon Amazon EBS\) volume on a regular cadence\. By checkpointing periodically, if there is an interruption you will lose only the data created since the last checkpoint \(assuming no other non\-idempotent actions are performed in between\)\. To make this process easier, you can configure your Spot Request to ensure that your root partition will not be deleted on interruption or termination\. We’ve inserted new code in the following example that shows how to enable this scenario\.

In the added code, we create a `BlockDeviceMapping` object and set its associated Amazon Elastic Block Store \(Amazon EBS\) to an Amazon EBS object that we’ve configured to `not` be deleted if the Spot Instance is terminated\. We then add this `BlockDeviceMapping` to the ArrayList of mappings that we include in the launch specification\.

```
// Retrieves the credentials from an AWSCredentials.properties file.
AWSCredentials credentials = null;
try {
    credentials = new PropertiesCredentials(
        GettingStartedApp.class.getResourceAsStream("AwsCredentials.properties"));
}
catch (IOException e1) {
    System.out.println(
        "Credentials were not properly entered into AwsCredentials.properties.");
    System.out.println(e1.getMessage());
    System.exit(-1);
}

// Create the AmazonEC2 client so we can call various APIs.
AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

// Initializes a Spot Instance Request
RequestSpotInstancesRequest requestRequest = new RequestSpotInstancesRequest();

// Request 1 x t1.micro instance with a bid price of $0.03.
requestRequest.setSpotPrice("0.03");
requestRequest.setInstanceCount(Integer.valueOf(1));

// Set up the specifications of the launch. This includes the instance
// type (e.g. t1.micro) and the latest Amazon Linux AMI id available.
// Note, you should always use the latest Amazon Linux AMI id or another
// of your choosing.
LaunchSpecification launchSpecification = new LaunchSpecification();
launchSpecification.setImageId("ami-a9d09ed1");
launchSpecification.setInstanceType(InstanceType.T1Micro);

// Add the security group to the request.
ArrayList<String> securityGroups = new ArrayList<String>();
securityGroups.add("GettingStartedGroup");
launchSpecification.setSecurityGroups(securityGroups);

// Create the block device mapping to describe the root partition.
BlockDeviceMapping blockDeviceMapping = new BlockDeviceMapping();
blockDeviceMapping.setDeviceName("/dev/sda1");

// Set the delete on termination flag to false.
EbsBlockDevice ebs = new EbsBlockDevice();
ebs.setDeleteOnTermination(Boolean.FALSE);
blockDeviceMapping.setEbs(ebs);

// Add the block device mapping to the block list.
ArrayList<BlockDeviceMapping> blockList = new ArrayList<BlockDeviceMapping>();
blockList.add(blockDeviceMapping);

// Set the block device mapping configuration in the launch specifications.
launchSpecification.setBlockDeviceMappings(blockList);

// Add the launch specification.
requestRequest.setLaunchSpecification(launchSpecification);

// Call the RequestSpotInstance API.
RequestSpotInstancesResult requestResult =
    ec2.requestSpotInstances(requestRequest);
```

Assuming you wanted to re\-attach this volume to your instance on startup, you can also use the block device mapping settings\. Alternatively, if you attached a non\-root partition, you can specify the Amazon Amazon EBS volumes you want to attach to your Spot Instance after it resumes\. You do this simply by specifying a snapshot ID in your `EbsBlockDevice` and alternative device name in your `BlockDeviceMapping` objects\. By leveraging block device mappings, it can be easier to bootstrap your instance\.

Using the root partition to checkpoint your critical data is a great way to manage the potential for interruption of your instances\. For more methods on managing the potential of interruption, please visit the [Managing Interruption](https://www.youtube.com/watch?feature=player_embedded&v=wcPNnUo60pc) video\.

## How to tag your spot requests and instances<a name="tutor-spot-adv-tags"></a>

Adding tags to Amazon EC2 resources can simplify the administration of your cloud infrastructure\. A form of metadata, tags can be used to create user\-friendly names, enhance searchability, and improve coordination between multiple users\. You can also use tags to automate scripts and portions of your processes\. To read more about tagging Amazon EC2 resources, go to [Using Tags](http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/Using_Tags.html) in the Amazon EC2 User Guide for Linux Instances\.

### Tagging requests<a name="tagging-requests"></a>

To add tags to your spot requests, you need to tag them *after* they have been requested\. The return value from `requestSpotInstances()` provides you with a [RequestSpotInstancesResult](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/RequestSpotInstancesResult.html) object that you can use to get the spot request IDs for tagging:

```
// Call the RequestSpotInstance API.
RequestSpotInstancesResult requestResult = ec2.requestSpotInstances(requestRequest);
List<SpotInstanceRequest> requestResponses = requestResult.getSpotInstanceRequests();

// A list of request IDs to tag
ArrayList<String> spotInstanceRequestIds = new ArrayList<String>();

// Add the request ids to the hashset, so we can determine when they hit the
// active state.
for (SpotInstanceRequest requestResponse : requestResponses) {
    System.out.println("Created Spot Request: "+requestResponse.getSpotInstanceRequestId());
    spotInstanceRequestIds.add(requestResponse.getSpotInstanceRequestId());
}
```

Once you have the IDs, you can tag the requests by adding their IDs to a [CreateTagsRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/CreateTagsRequest.html) and calling the Amazon EC2 client’s `createTags()` method:

```
// The list of tags to create
ArrayList<Tag> requestTags = new ArrayList<Tag>();
requestTags.add(new Tag("keyname1","value1"));

// Create the tag request
CreateTagsRequest createTagsRequest_requests = new CreateTagsRequest();
createTagsRequest_requests.setResources(spotInstanceRequestIds);
createTagsRequest_requests.setTags(requestTags);

// Tag the spot request
try {
    ec2.createTags(createTagsRequest_requests);
}
catch (AmazonServiceException e) {
    System.out.println("Error terminating instances");
    System.out.println("Caught Exception: " + e.getMessage());
    System.out.println("Reponse Status Code: " + e.getStatusCode());
    System.out.println("Error Code: " + e.getErrorCode());
    System.out.println("Request ID: " + e.getRequestId());
}
```

### Tagging instances<a name="tagging-instances"></a>

Similarly to spot requests themselves, you can only tag an instance once it has been created, which will happen once the spot request has been met \(it is no longer in the *open* state\)\.

You can check the status of your requests by calling the Amazon EC2 client’s `describeSpotInstanceRequests()` method with a [DescribeSpotInstanceRequestsRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/DescribeSpotInstanceRequestsRequest.html) object\. The returned [DescribeSpotInstanceRequestsResult](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/DescribeSpotInstanceRequestsResult.html) object contains a list of [SpotInstanceRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/SpotInstanceRequest.html) objects that you can use to query the status of your spot requests and obtain their instance IDs once they are no longer in the *open* state\.

Once the spot request is no longer open, you can retrieve its instance ID from the `SpotInstanceRequest` object by calling its `getInstanceId()` method\.

```
boolean anyOpen; // tracks whether any requests are still open

// a list of instances to tag.
ArrayList<String> instanceIds = new ArrayList<String>();

do {
    DescribeSpotInstanceRequestsRequest describeRequest =
        new DescribeSpotInstanceRequestsRequest();
    describeRequest.setSpotInstanceRequestIds(spotInstanceRequestIds);

    anyOpen=false; // assume no requests are still open

    try {
        // Get the requests to monitor
        DescribeSpotInstanceRequestsResult describeResult =
            ec2.describeSpotInstanceRequests(describeRequest);

        List<SpotInstanceRequest> describeResponses =
            describeResult.getSpotInstanceRequests();

        // are any requests open?
        for (SpotInstanceRequest describeResponse : describeResponses) {
                if (describeResponse.getState().equals("open")) {
                    anyOpen = true;
                    break;
                }
                // get the corresponding instance ID of the spot request
                instanceIds.add(describeResponse.getInstanceId());
        }
    }
    catch (AmazonServiceException e) {
        // Don't break the loop due to an exception (it may be a temporary issue)
        anyOpen = true;
    }

    try {
        Thread.sleep(60*1000); // sleep 60s.
    }
    catch (Exception e) {
        // Do nothing if the thread woke up early.
    }
} while (anyOpen);
```

Now you can tag the instances that are returned:

```
// Create a list of tags to create
ArrayList<Tag> instanceTags = new ArrayList<Tag>();
instanceTags.add(new Tag("keyname1","value1"));

// Create the tag request
CreateTagsRequest createTagsRequest_instances = new CreateTagsRequest();
createTagsRequest_instances.setResources(instanceIds);
createTagsRequest_instances.setTags(instanceTags);

// Tag the instance
try {
    ec2.createTags(createTagsRequest_instances);
}
catch (AmazonServiceException e) {
    // Write out any exceptions that may have occurred.
    System.out.println("Error terminating instances");
    System.out.println("Caught Exception: " + e.getMessage());
    System.out.println("Reponse Status Code: " + e.getStatusCode());
    System.out.println("Error Code: " + e.getErrorCode());
    System.out.println("Request ID: " + e.getRequestId());
}
```

## Canceling spot requests and terminating instances<a name="canceling-spot-requests-and-terminating-instances"></a>

### Canceling a spot request<a name="canceling-a-spot-request"></a>

To cancel a Spot Instance request, call `cancelSpotInstanceRequests` on the Amazon EC2 client with a [CancelSpotInstanceRequestsRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/CancelSpotInstanceRequestsRequest.html) object\.

```
try {
    CancelSpotInstanceRequestsRequest cancelRequest = new CancelSpotInstanceRequestsRequest(spotInstanceRequestIds);
    ec2.cancelSpotInstanceRequests(cancelRequest);
} catch (AmazonServiceException e) {
    System.out.println("Error cancelling instances");
    System.out.println("Caught Exception: " + e.getMessage());
    System.out.println("Reponse Status Code: " + e.getStatusCode());
    System.out.println("Error Code: " + e.getErrorCode());
    System.out.println("Request ID: " + e.getRequestId());
}
```

### Terminating Spot Instances<a name="terminating-spot-instances"></a>

You can terminate any Spot Instances that are running by passing their IDs to the Amazon EC2 client’s `terminateInstances()` method\.

```
try {
    TerminateInstancesRequest terminateRequest = new TerminateInstancesRequest(instanceIds);
    ec2.terminateInstances(terminateRequest);
} catch (AmazonServiceException e) {
    System.out.println("Error terminating instances");
    System.out.println("Caught Exception: " + e.getMessage());
    System.out.println("Reponse Status Code: " + e.getStatusCode());
    System.out.println("Error Code: " + e.getErrorCode());
    System.out.println("Request ID: " + e.getRequestId());
}
```

## Bringing it all together<a name="tutor-spot-adv-bring-together"></a>

To bring this all together, we provide a more object\-oriented approach that combines the steps we showed in this tutorial into one easy to use class\. We instantiate a class called `Requests` that performs these actions\. We also create a `GettingStartedApp` class, which has a main method where we perform the high level function calls\.

The complete source code for this example can be viewed or downloaded at [GitHub](https://github.com/aws/aws-sdk-java/tree/master/src/samples/AmazonEC2SpotInstances-Advanced)\.

Congratulations\! You’ve completed the Advanced Request Features tutorial for developing Spot Instance software with the AWS SDK for Java\.