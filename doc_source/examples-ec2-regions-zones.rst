.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

####################################
Using Regions and Availability Zones
####################################

.. meta::
   :description: How to list EC2 regions and availability zones using the AWS SDK for Java.
   :keywords: AWS SDK for Java, code examples, EC2, list regions, describe regions, list availability
              zones, describe availability zones


Describing Regions
==================

To list the Regions available to your account, call the |ec2client|'s :methodname:`describeRegions`
method. It returns a :aws-java-class:`DescribeRegionsResult
<services/ec2/model/DescribeRegionsResult>`. Call the returned object's :methodname:`getRegions`
method to get a list of :aws-java-class:`Region <services/ec2/model/Region>` objects that represent
each Region.

**Imports**

.. literalinclude:: example_code/ec2/src/main/java/aws/example/ec2/DescribeRegionsAndZones.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/ec2/src/main/java/aws/example/ec2/DescribeRegionsAndZones.java
   :lines: 30-40
   :dedent: 8
   :language: java

See the :sdk-examples-java-ec2:`complete example <DescribeRegionsAndZones.java>`.


Describing Availability Zones
=============================

To list each Availability Zone available to your account, call the |ec2client|'s
:methodname:`describeAvailabilityZones` method. It returns a
:aws-java-class:`DescribeAvailabilityZonesResult
<services/ec2/model/DescribeAvailabilityZonesResult>`. Call its :methodname:`getAvailabilityZones`
method to get a list of :aws-java-class:`AvailabilityZone <services/ec2/model/AvailabilityZone>`
objects that represent each Availability Zone.

**Imports**

.. literalinclude:: example_code/ec2/src/main/java/aws/example/ec2/DescribeRegionsAndZones.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/ec2/src/main/java/aws/example/ec2/DescribeRegionsAndZones.java
   :lines: 42-53
   :dedent: 8
   :language: java

See the :sdk-examples-java-ec2:`complete example <DescribeRegionsAndZones.java>`.


More Information
================

* :ec2-ug:`Regions and Availability Zones <using-regions-availability-zones>` in the |ec2-ug|
* :ec2-api:`DescribeRegions` in the |ec2-api|
* :ec2-api:`DescribeAvailabilityZones` in the |ec2-api|
