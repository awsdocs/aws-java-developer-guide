.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

###################################
Using Elastic IP Addresses in |EC2|
###################################

.. meta::
   :description: How to allocate, use, list, and release Elastic IP addresses for EC2 instances with
                 the AWS SDK for Java.
   :keywords: AWS SDK for Java, code examples, EC2, Elastic IP, allocate address, release address,
              assign address, associate address, list addresses


Allocating an Elastic IP Address
================================

To use an Elastic IP address, you first allocate one to your account, and then associate it with
your instance or a network interface.

To allocate an Elastic IP address, call the |ec2client|'s :methodname:`allocateAddress` method with
an :aws-java-class:`AllocateAddressRequest <services/ec2/model/AllocateAddressRequest>` object
containing the network type (classic EC2 or VPC).

The returned :aws-java-class:`AllocateAddressResult <services/ec2/model/AllocateAddressResult>`
contains an allocation ID that you can use to associate the address with an instance, by passing the
allocation ID and instance ID in a :aws-java-class:`AssociateAddressRequest
<services/ec2/model/AssociateAddressRequest>` to the |ec2client|'s :methodname:`associateAddress`
method.

**Imports**

.. literalinclude:: example_code/ec2/src/main/java/aws/example/ec2/AllocateAddress.java
   :lines: 16-22
   :language: java

**Code**

.. literalinclude:: example_code/ec2/src/main/java/aws/example/ec2/AllocateAddress.java
   :lines: 42-58
   :dedent: 8
   :language: java

See the :sdk-examples-java-ec2:`complete example <AllocateAddress.java>`.


Describing Elastic IP Addresses
===============================

To list the Elastic IP addresses assigned to your account, call the |ec2client|'s
:methodname:`describeAddresses` method. It returns a :aws-java-class:`DescribeAddressesResult
<services/ec2/model/DescribeAddressesResult>` which you can use to get a list of
:aws-java-class:`Address <services/ec2/model/Address>` objects that represent the Elastic IP
addresses on your account.

**Imports**

.. literalinclude:: example_code/ec2/src/main/java/aws/example/ec2/DescribeAddresses.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/ec2/src/main/java/aws/example/ec2/DescribeAddresses.java
   :lines: 28-42
   :dedent: 8
   :language: java

See the :sdk-examples-java-ec2:`complete example <DescribeAddresses.java>`.


Releasing an Elastic IP Address
===============================

To release an Elastic IP address, call the |ec2client|'s :methodname:`releaseAddress` method,
passing it a :aws-java-class:`ReleaseAddressRequest <services/ec2/model/ReleaseAddressRequest>`
containing the allocation ID of the Elastic IP address you want to release.

**Imports**

.. literalinclude:: example_code/ec2/src/main/java/aws/example/ec2/ReleaseAddress.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/ec2/src/main/java/aws/example/ec2/ReleaseAddress.java
   :lines: 39-44
   :dedent: 8
   :language: java

After you release an Elastic IP address, it is released to the AWS IP address pool and might be
unavailable to you afterward. Be sure to update your DNS records and any servers or devices that
communicate with the address. If you attempt to release an Elastic IP address that you already
released, you'll get an *AuthFailure* error if the address is already allocated to another AWS
account.

If you are using *EC2-Classic* or a *default VPC*, then releasing an Elastic IP address
automatically disassociates it from any instance that it's associated with. To disassociate an
Elastic IP address without releasing it, use the |ec2client|'s :methodname:`disassociateAddress`
method.

If you are using a non-default VPC, you *must* use :methodname:`disassociateAddress` to disassociate
the Elastic IP address before you try to release it. Otherwise, |EC2| returns an error
(*InvalidIPAddress.InUse*).

See the :sdk-examples-java-ec2:`complete example <ReleaseAddress.java>`.


More Information
================

* :ec2-ug:`Elastic IP Addresses <elastic-ip-addresses-eip>` in the |ec2-ug|
* :ec2-api:`AllocateAddress` in the |ec2-api|
* :ec2-api:`DescribeAddresses` in the |ec2-api|
* :ec2-api:`ReleaseAddress` in the |ec2-api|
