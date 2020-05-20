.. Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

##################################
Use regions and availability zones
##################################

.. meta::
   :description: How to list EC2 regions and availability zones using the AWS SDK for Java.
   :keywords: AWS SDK for Java, code examples, EC2, list regions, describe regions, list availability
              zones, describe availability zones


Describe regions
================

To list the Regions available to your account, call the |ec2client|'s :methodname:`describeRegions`
method. It returns a :aws-java-class:`DescribeRegionsResult
<services/ec2/model/DescribeRegionsResult>`. Call the returned object's :methodname:`getRegions`
method to get a list of :aws-java-class:`Region <services/ec2/model/Region>` objects that represent
each Region.

**Imports**

.. literalinclude:: ec2.java1.describe_region_and_zones.import.txt
   :language: java

**Code**

.. literalinclude:: ec2.java1.describe_region_and_zones.regions.txt
   :dedent: 8
   :language: java

See the :sdk-examples-java-ec2:`complete example <DescribeRegionsAndZones.java>`.


Describe availability zones
===========================

To list each Availability Zone available to your account, call the |ec2client|'s
:methodname:`describeAvailabilityZones` method. It returns a
:aws-java-class:`DescribeAvailabilityZonesResult
<services/ec2/model/DescribeAvailabilityZonesResult>`. Call its :methodname:`getAvailabilityZones`
method to get a list of :aws-java-class:`AvailabilityZone <services/ec2/model/AvailabilityZone>`
objects that represent each Availability Zone.

**Imports**

.. literalinclude:: ec2.java1.describe_region_and_zones.import.txt
   :language: java

**Code**

.. literalinclude:: ec2.java1.describe_region_and_zones.zones.txt
   :dedent: 8
   :language: java

See the :sdk-examples-java-ec2:`complete example <DescribeRegionsAndZones.java>`.

Describe accounts
=================

To describe your account, call the |ec2client|'s :methodname:`describeAccountAttributes`
method. This method returns a
:aws-java-class:`DescribeAccountAttributesResult <services/ec2/model/DescribeAccountAttributesResult>`
object.
Invoke this objects :methodname:`getAccountAttributes` method to get a list of
:aws-java-class:`AccountAttribute <services/ec2/model/AccountAttribute>` objects. You can iterate
through the list to retrieve an
:aws-java-class:`AccountAttribute <services/ec2/model/AccountAttribute>` object.

You can get your account's attribute values by invoking the
:aws-java-class:`AccountAttribute <services/ec2/model/AccountAttribute>` object's
:methodname:`getAttributeValues` method. This method returns a list of
:aws-java-class:`AccountAttributeValue <services/ec2/model/AccountAttributeValue>` objects. You can
iterate through this second list to display the value of attributes (see the following code
example).

**Imports**

.. literalinclude:: ec2.java1.describe_account.import.txt
   :language: java

**Code**

.. literalinclude:: ec2.java1.describe_account.main.txt
   :dedent: 8
   :language: java

See the :sdk-examples-java-ec2:`complete example <DescribeAccount.java>` on GitHub.




More information
================

* :ec2-ug:`Regions and Availability Zones <using-regions-availability-zones>` in the |ec2-ug|
* :ec2-api:`DescribeRegions` in the |ec2-api|
* :ec2-api:`DescribeAvailabilityZones` in the |ec2-api|
