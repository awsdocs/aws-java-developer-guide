.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

.. highlight:: java

.. _tutor-spot-adv-java-overview:

################################################
Tutorial: Advanced |EC2| Spot Request Management
################################################

|EC2| Spot Instances allow you to bid on unused |EC2| capacity and run those instances for as long
as your bid exceeds the current *spot price*. |EC2| changes the spot price periodically based on
supply and demand. For more information about Spot Instances, see :ec2-ug:`Spot Instances
<using-spot-instances>` in the |EC2-ug|.


.. _tutor-spot-adv-java-prereq:

Prerequisites
=============

To use this tutorial you must have the |sdk-java| installed, as well as having met its basic
installation prerequisites. See :doc:`setup-install` for more information.


.. _tutor-spot-adv-java-credentials:

Setting up your credentials
===========================

To begin using this code sample, you need to set up AWS credentials.
See :doc:`setup-credentials` for instructions on how to do that.

.. note:: We recommend that you use the credentials of an IAM user to provide these values. For more
   information, see :doc:`signup-create-iam-user`.

Now that you have configured your settings, you can get started using the code in the example.


.. _tutor-spot-adv-java-sg:

Setting up a security group
===========================

A security group acts as a firewall that controls the traffic allowed in and out of a group of
instances. By default, an instance is started without any security group, which means that all
incoming IP traffic, on any TCP port will be denied. So, before submitting our Spot Request, we will
set up a security group that allows the necessary network traffic. For the purposes of this
tutorial, we will create a new security group called "GettingStarted" that allows Secure Shell (SSH)
traffic from the IP address where you are running your application from. To set up a new security
group, you need to include or run the following code sample that sets up the security group
programmatically.

After we create an :code:`AmazonEC2` client object, we create a :code:`CreateSecurityGroupRequest`
object with the name, "GettingStarted" and a description for the security group. Then we call the
:code:`ec2.createSecurityGroup` API to create the group.

To enable access to the group, we create an :code:`ipPermission` object with the IP address range
set to the CIDR representation of the subnet for the local computer; the "/10" suffix on the IP
address indicates the subnet for the specified IP address. We also configure the
:code:`ipPermission` object with the TCP protocol and port 22 (SSH). The final step is to call
:code:`ec2.authorizeSecurityGroupIngress` with the name of our security group and the
:code:`ipPermission` object.

(The following code is the same as what we used in the first tutorial.)

.. code-block:: java

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

You can view this entire code sample in the :code:`advanced.CreateSecurityGroupApp.java` code
sample. Note you only need to run this application once to create a new security group.

.. note:: You can also create the security group using the |tke|. See :tke-ug:`Managing Security
   Groups from AWS Explorer <tke-sg>` in the |tke-ug| for more information.


.. _tutor-spot-adv-req-opts:

Detailed Spot Instance request creation options
===============================================

As we explained in :doc:`tutorial-spot-instances-java`, you need to build your request with an
instance type, an Amazon Machine Image (AMI), and maximum bid price.

Let's start by creating a :code:`RequestSpotInstanceRequest` object. The request object requires the
number of instances you want and the bid price. Additionally, we need to set the
:code:`LaunchSpecification` for the request, which includes the instance type, AMI ID, and security
group you want to use. After the request is populated, we call the :code:`requestSpotInstances`
method on the :code:`AmazonEC2Client` object. An example of how to request a Spot Instance follows.

(The following code is the same as what we used in the first tutorial.)

.. code-block:: java

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


.. _tutor-spot-adv-persist-v-one:

Persistent vs. one-time requests
================================

When building a Spot request, you can specify several optional parameters. The first is whether your
request is one-time only or persistent. By default, it is a one-time request. A one-time request can
be fulfilled only once, and after the requested instances are terminated, the request will be
closed. A persistent request is considered for fulfillment whenever there is no Spot Instance
running for the same request. To specify the type of request, you simply need to set the Type on the
Spot request. This can be done with the following code.

.. code-block:: java

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

.. _tutor-spot-adv-validity-period:

Limiting the duration of a request
==================================

You can also optionally specify the length of time that your request will remain valid. You can
specify both a starting and ending time for this period. By default, a Spot request will be
considered for fulfillment from the moment it is created until it is either fulfilled or canceled by
you. However you can constrain the validity period if you need to. An example of how to specify this
period is shown in the following code.

.. literalinclude:: snippets/tutorial-spot-adv-java-limit-request-duration.java
   :language: java
   :lines: 14-


.. _tutor-spot-adv-grouping:

Grouping your |EC2| Spot Instance requests
==========================================

You have the option of grouping your Spot Instance requests in several different ways. We'll look at
the benefits of using launch groups, Availability Zone groups, and placement groups.

If you want to ensure your Spot Instances are all launched and terminated together, then you have
the option to leverage a launch group. A launch group is a label that groups a set of bids together.
All instances in a launch group are started and terminated together. Note, if instances in a launch
group have already been fulfilled, there is no guarantee that new instances launched with the same
launch group will also be fulfilled. An example of how to set a Launch Group is shown in the
following code example.

.. code-block:: java

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

If you want to ensure that all instances within a request are launched in the same Availability
Zone, and you don't care which one, you can leverage Availability Zone groups. An Availability Zone
group is a label that groups a set of instances together in the same Availability Zone. All
instances that share an Availability Zone group and are fulfilled at the same time will start in the
same Availability Zone. An example of how to set an Availability Zone group follows.

.. code-block:: java

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

You can specify an Availability Zone that you want for your Spot Instances. The following code
example shows you how to set an Availability Zone.

.. code-block:: java

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

Lastly, you can specify a :emphasis:`placement group` if you are using High Performance Computing
(HPC) Spot Instances, such as cluster compute instances or cluster GPU instances. Placement groups
provide you with lower latency and high-bandwidth connectivity between the instances. An example of
how to set a placement group follows.

.. code-block:: java

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

All of the parameters shown in this section are optional. It is also important to realize that most
of these parameters |mdash| with the exception of whether your bid is one-time or persistent |mdash|
can reduce the likelihood of bid fulfillment. So, it is important to leverage these options only if
you need them. All of the preceding code examples are combined into one long code sample, which can
be found in the :code:`com.amazonaws.codesamples.advanced.InlineGettingStartedCodeSampleApp.java`
class.


.. _tutor-spot-adv-persist-root:

How to persist a root partition after interruption or termination
=================================================================

One of the easiest ways to manage interruption of your Spot Instances is to ensure that your data is
checkpointed to an |EBSlong| (|EBS|) volume on a regular cadence. By checkpointing periodically, if
there is an interruption you will lose only the data created since the last checkpoint (assuming no
other non-idempotent actions are performed in between). To make this process easier, you can
configure your Spot Request to ensure that your root partition will not be deleted on interruption
or termination. We've inserted new code in the following example that shows how to enable this
scenario.

In the added code, we create a :code:`BlockDeviceMapping` object and set its associated Elastic
Block Storage (EBS) to an EBS object that we've configured to :code:`not` be deleted if the Spot
Instance is terminated. We then add this :code:`BlockDeviceMapping` to the ArrayList of mappings
that we include in the launch specification.

.. code-block:: java

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

Assuming you wanted to re-attach this volume to your instance on startup, you can also use the block
device mapping settings. Alternatively, if you attached a non-root partition, you can specify the
Amazon EBS volumes you want to attach to your Spot Instance after it resumes. You do this simply by
specifying a snapshot ID in your :code:`EbsBlockDevice` and alternative device name in your
:code:`BlockDeviceMapping` objects. By leveraging block device mappings, it can be easier to
bootstrap your instance.

Using the root partition to checkpoint your critical data is a great way to manage the potential for
interruption of your instances. For more methods on managing the potential of interruption, please
visit the `Managing Interruption
<https://www.youtube.com/watch?feature=player_embedded&v=wcPNnUo60pc>`_ video.


.. _tutor-spot-adv-tags:

How to tag your spot requests and instances
===========================================

Adding tags to EC2 resources can simplify the administration of your cloud infrastructure. A form of
metadata, tags can be used to create user-friendly names, enhance searchability, and improve
coordination between multiple users. You can also use tags to automate scripts and portions of your
processes. To read more about tagging |EC2| resources, go to :ec2-ug:`Using Tags <Using_Tags>` in
the |EC2-ug|.

Tagging requests
----------------

To add tags to your spot requests, you need to tag them *after* they have been requested. The return
value from :methodname:`requestSpotInstances()` provides you with a
:aws-java-class:`RequestSpotInstancesResult <services/ec2/model/RequestSpotInstancesResult>` object that
you can use to get the spot request IDs for tagging:

.. literalinclude:: snippets/ec2/tag-spot-requests.java
   :lines: 18-30
   :language: java

Once you have the IDs, you can tag the requests by adding their IDs to a
:aws-java-class:`CreateTagsRequest <services/ec2/model/CreateTagsRequest>` and calling the EC2 client's
:methodname:`createTags()` method:

.. literalinclude:: snippets/ec2/tag-spot-requests.java
   :lines: 32-51
   :language: java

Tagging instances
-----------------

Similarly to spot requests themselves, you can only tag an instance once it has been created, which
will happen once the spot request has been met (it is no longer in the *open* state).

You can check the status of your requests by calling the EC2 client's
:methodname:`describeSpotInstanceRequests()` method with a
:aws-java-class:`DescribeSpotInstanceRequestsRequest <services/ec2/model/DescribeSpotInstanceRequestsRequest>`
object. The returned :aws-java-class:`DescribeSpotInstanceRequestsResult
<services/ec2/model/DescribeSpotInstanceRequestsResult>` object contains a list of
:aws-java-class:`SpotInstanceRequest <services/ec2/model/SpotInstanceRequest>` objects that you can use to query
the status of your spot requests and obtain their instance IDs once they are no longer in the *open*
state.

Once the spot request is no longer open, you can retrieve its instance ID from the
:classname:`SpotInstanceRequest` object by calling its :methodname:`getInstanceId()` method.

.. literalinclude:: snippets/ec2/tag-spot-instances.java
   :lines: 18-59
   :language: java

Now you can tag the instances that are returned:

.. literalinclude:: snippets/ec2/tag-spot-instances.java
   :lines: 61-81
   :language: java


Canceling spot requests and terminating instances
=================================================

Canceling a spot request
------------------------

To cancel a Spot Instance request, call :methodname:`cancelSpotInstanceRequests` on the EC2 client
with a :aws-java-class:`CancelSpotInstanceRequestsRequest
<services/ec2/model/CancelSpotInstanceRequestsRequest>` object.

.. literalinclude:: snippets/ec2/cancel-terminate-spot-request.java
   :lines: 18-27
   :language: java

Terminating Spot Instances
--------------------------

You can terminate any Spot Instances that are running by passing their IDs to the EC2 client's
:methodname:`terminateInstances()` method.

.. literalinclude:: snippets/ec2/cancel-terminate-spot-request.java
   :lines: 29-38
   :language: java


.. _tutor-spot-adv-bring-together:

Bringing it all together
========================

To bring this all together, we provide a more object-oriented approach that combines the steps we
showed in this tutorial into one easy to use class. We instantiate a class called :code:`Requests`
that performs these actions. We also create a :code:`GettingStartedApp` class, which has a main
method where we perform the high level function calls.

The complete source code for this example can be viewed or downloaded at :github:`GitHub
<aws/aws-sdk-java/tree/master/src/samples/AmazonEC2SpotInstances-Advanced>`.

Congratulations! You've completed the Advanced Request Features tutorial for developing Spot
Instance software with the |sdk-java|.
