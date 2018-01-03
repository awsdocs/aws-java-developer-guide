.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

#####################################
Working with Security Groups in |EC2|
#####################################

.. meta::
   :description: How to create, configure and delete EC2 security groups with the AWS SDK for Java.
   :keywords: AWS SDK for Java, code examples, EC2 security groups, create a security group, ingress
              rules, egress rules, IP permissions, EC2 access


Creating a Security Group
=========================

To create a security group, call the |ec2client|'s :methodname:`createSecurityGroup` method with a
:aws-java-class:`CreateSecurityGroupRequest <services/ec2/model/CreateSecurityGroupRequest>` that
contains the key's name.

**Imports**

.. literalinclude:: example_code/ec2/src/main/java/aws/example/ec2/CreateSecurityGroup.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/ec2/src/main/java/aws/example/ec2/CreateSecurityGroup.java
   :lines: 45-54
   :dedent: 8
   :language: java

See the :sdk-examples-java-ec2:`complete example <CreateSecurityGroup.java>`.


Configuring a Security Group
============================

A security group can control both inbound (ingress) and outbound (egress) traffic to your |EC2|
instances.

To add ingress rules to your security group, use the |ec2client|'s
:methodname:`authorizeSecurityGroupIngress` method, providing the name of the security group and the
access rules (:aws-java-class:`IpPermission <services/ec2/model/IpPermission>`) you want to assign
to it within an :aws-java-class:`AuthorizeSecurityGroupIngressRequest
<services/ec2/model/AuthorizeSecurityGroupIngressRequest>` object. The following example shows how
to add IP permissions to a security group.

**Imports**

.. literalinclude:: example_code/ec2/src/main/java/aws/example/ec2/CreateSecurityGroup.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/ec2/src/main/java/aws/example/ec2/CreateSecurityGroup.java
   :lines: 60-81
   :dedent: 8
   :language: java

To add an egress rule to the security group, provide similar data in an
:aws-java-class:`AuthorizeSecurityGroupEgressRequest
<services/ec2/model/AuthorizeSecurityGroupEgressRequest>` to the |ec2client|'s
:methodname:`authorizeSecurityGroupEgress` method.

See the :sdk-examples-java-ec2:`complete example <CreateSecurityGroup.java>`.


Describing Security Groups
==========================

To describe your security groups or get information about them, call the |ec2client|'s
:methodname:`describeSecurityGroups` method. It returns a
:aws-java-class:`DescribeSecurityGroupsResult <services/ec2/model/DescribeSecurityGroupsResult>`
that you can use to access the list of security groups by calling its
:methodname:`getSecurityGroups` method, which returns a list of :aws-java-class:`SecurityGroupInfo
<services/ec2/model/SecurityGroupInfo>` objects.

**Imports**

.. literalinclude:: example_code/ec2/src/main/java/aws/example/ec2/DescribeSecurityGroups.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/ec2/src/main/java/aws/example/ec2/DescribeSecurityGroups.java
   :lines: 28-38
   :dedent: 8
   :language: java

See the :sdk-examples-java-ec2:`complete example <DescribeSecurityGroups.java>`.


Deleting a Security Group
=========================

To delete a security group, call the |ec2client|'s :methodname:`deleteSecurityGroup` method, passing
it a :aws-java-class:`DeleteSecurityGroupRequest <services/ec2/model/DeleteSecurityGroupRequest>`
that contains the ID of the security group to delete.

**Imports**

.. literalinclude:: example_code/ec2/src/main/java/aws/example/ec2/DeleteSecurityGroup.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/ec2/src/main/java/aws/example/ec2/DeleteSecurityGroup.java
   :lines: 39-44
   :dedent: 8
   :language: java

See the :sdk-examples-java-ec2:`complete example <DeleteSecurityGroup.java>`.


More Information
================

* :ec2-ug:`Amazon EC2 Security Groups <ec2-key-pairs>` in the |ec2-ug|
* :ec2-ug:`Authorizing Inbound Traffic for Your Linux Instances <authorizing-access-to-an-instance>` in the |ec2-ug|
* :ec2-api:`CreateSecurityGroup` in the |ec2-api|
* :ec2-api:`DescribeSecurityGroups` in the |ec2-api|
* :ec2-api:`DeleteSecurityGroup` in the |ec2-api|
* :ec2-api:`AuthorizeSecurityGroupIngress` in the |ec2-api|
