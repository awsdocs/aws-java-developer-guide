.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

##############################
Create an |EC2| Security Group
##############################

Create a :emphasis:`security group`, which acts as a virtual firewall that controls the network
traffic for one or more EC2 instances. By default, |EC2| associates your instances with a security
group that allows no inbound traffic. You can create a security group that allows your EC2 instances
to accept certain traffic. For example, if you need to connect to a Linux instance, you must
configure the security group to allow SSH traffic. You can create a security group using the |EC2|
console or the |sdk-java|.

You create a security group for use in either EC2-Classic or EC2-VPC. For more information about
EC2-Classic and EC2-VPC, see :ec2-ug:`Supported Platforms <ec2-supported-platforms>` in the
|EC2-ug|.

For more information about creating a security group using the |EC2| console, see :ec2-ug:`Amazon
EC2 Security Groups <using-network-security>` in the |EC2-ug|.

.. topic:: To create a security group

    #. Create and initialize a :aws-java-class:`CreateSecurityGroupRequest
       <services/ec2/model/CreateSecurityGroupRequest>` instance. Use the
       :aws-java-ref:`withGroupName
       <services/ec2/model/CreateSecurityGroupRequest.html#withGroupName-java.lang.String->` method
       to set the security group name, and the :aws-java-ref:`withDescription
       <services/ec2/model/CreateSecurityGroupRequest.html#withDescription-java.lang.String->`
       method to set the security group description, as follows:

       .. code-block:: java

          CreateSecurityGroupRequest csgr = new CreateSecurityGroupRequest();
          csgr.withGroupName("JavaSecurityGroup").withDescription("My security group");

       The security group name must be unique within the AWS region in which you initialize your
       |EC2| client. You must use US-ASCII characters for the security group name and description.

    #. Pass the request object as a parameter to the :aws-java-ref:`createSecurityGroup
       <services/ec2/AmazonEC2.html#createSecurityGroup-com.amazonaws.services.ec2.model.CreateSecurityGroupRequest->`
       method. The method returns a :aws-java-class:`CreateSecurityGroupResult
       <services/ec2/model/CreateSecurityGroupResult>` object, as follows:

       .. code-block:: java

          CreateSecurityGroupResult createSecurityGroupResult =
              amazonEC2Client.createSecurityGroup(csgr);

       If you attempt to create a security group with the same name as an existing security group,
       :code:`createSecurityGroup` throws an exception.

By default, a new security group does not allow any inbound traffic to your |EC2| instance. To allow
inbound traffic, you must explicitly authorize security group ingress. You can authorize ingress for
individual IP addresses, for a range of IP addresses, for a specific protocol, and for TCP/UDP
ports.

.. topic:: To authorize security group ingress

    #. Create and initialize an :aws-java-class:`IpPermission <services/ec2/model/IpPermission>`
       instance.  Use the :aws-java-ref:`withIpv4Ranges
       <services/ec2/model/IpPermission.html#withIpv4Ranges-java.util.Collection->` method to set the
       range of IP addresses to authorize ingress for, and use the :aws-java-ref:`withIpProtocol
       <services/ec2/model/IpPermission.html#withIpProtocol-java.lang.String->` method to set the IP
       protocol. Use the :aws-java-ref:`withFromPort
       <services/ec2/model/IpPermission.html#withFromPort-java.lang.Integer->` and
       :aws-java-ref:`withToPort
       <services/ec2/model/IpPermission.html#withToPort-java.lang.Integer->` methods to specify
       range of ports to authorize ingress for, as follows:

       .. code-block:: java

          IpPermission ipPermission =
              new IpPermission();

          IpRange ipRange1 = new IpRange().withCidrIp("111.111.111.111/32");
          IpRange ipRange2 = new IpRange().withCidrIp("150.150.150.150/32");

          ipPermission.withIpv4Ranges(Arrays.asList(new IpRange[] {ipRange1, ipRange2}))
                      .withIpProtocol("tcp")
                      .withFromPort(22)
                      .withToPort(22);

       All the conditions that you specify in the :code:`IpPermission` object must be met in order
       for ingress to be allowed.

       Specify the IP address using CIDR notation. If you specify the protocol as TCP/UDP, you must
       provide a source port and a destination port. You can authorize ports only if you specify TCP
       or UDP.

    #. Create and initialize an :aws-java-class:`AuthorizeSecurityGroupIngressRequest
       <services/ec2/model/AuthorizeSecurityGroupEgressRequest>` instance. Use the
       :code:`withGroupName` method to specify the security group name, and pass the
       :code:`IpPermission` object you initialized earlier to the :aws-java-ref:`withIpPermissions
       <services/ec2/model/AuthorizeSecurityGroupEgressRequest.html#withIpPermissions-com.amazonaws.services.ec2.model.IpPermission...->`
       method, as follows:

       .. code-block:: java

          AuthorizeSecurityGroupIngressRequest authorizeSecurityGroupIngressRequest =
              new AuthorizeSecurityGroupIngressRequest();

          authorizeSecurityGroupIngressRequest.withGroupName("JavaSecurityGroup")
                                              .withIpPermissions(ipPermission);

    #. Pass the request object into the :aws-java-ref:`authorizeSecurityGroupIngress
       <services/ec2/AmazonEC2Client.html#authorizeSecurityGroupIngress-com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressRequest->`
       method, as follows:

       .. code-block:: java

          amazonEC2Client.authorizeSecurityGroupIngress(authorizeSecurityGroupIngressRequest);

       If you call :code:`authorizeSecurityGroupIngress` with IP addresses for which ingress is
       already authorized, the method throws an exception. Create and initialize a new
       :code:`IpPermission` object to authorize ingress for different IPs, ports, and protocols
       before calling :code:`AuthorizeSecurityGroupIngress`.

Whenever you call the :aws-java-ref:`authorizeSecurityGroupIngress
<services/ec2/AmazonEC2Client.html#authorizeSecurityGroupIngress-com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressRequest->`
or :aws-java-ref:`authorizeSecurityGroupEgress
<services/ec2/AmazonEC2Client.html#authorizeSecurityGroupEgress-com.amazonaws.services.ec2.model.AuthorizeSecurityGroupEgressRequest->`
methods, a rule is added to your security group.

