.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

############################
Working with |EC2| Key Pairs
############################

.. meta::
   :description: How to create, list and delete EC2 key pairs using the AWS SDK for Java.
   :keywords: AWS SDK for Java, code examples, EC2 key pairs, create key pair, list key pairs, delete
              key pair


Creating a Key Pair
===================

To create a key pair, call the |ec2client|'s :methodname:`createKeyPair` method with a
:aws-java-class:`CreateKeyPairRequest <services/ec2/model/CreateKeyPairRequest>` that contains the
key's name.

**Imports**

.. literalinclude:: example_code/ec2/src/main/java/aws/example/ec2/CreateKeyPair.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/ec2/src/main/java/aws/example/ec2/CreateKeyPair.java
   :lines: 39-44
   :dedent: 8
   :language: java

See the :sdk-examples-java-ec2:`complete example <CreateKeyPair.java>`.


Describing Key Pairs
====================

To list your key pairs or to get information about them, call the |ec2client|'s
:methodname:`describeKeyPairs` method. It returns a :aws-java-class:`DescribeKeyPairsResult
<services/ec2/model/DescribeKeyPairsResult>` that you can use to access the list of key pairs by
calling its :methodname:`getKeyPairs` method, which returns a list of :aws-java-class:`KeyPairInfo
<services/ec2/model/KeyPairInfo>` objects.

**Imports**

.. literalinclude:: example_code/ec2/src/main/java/aws/example/ec2/DescribeKeyPairs.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/ec2/src/main/java/aws/example/ec2/DescribeKeyPairs.java
   :lines: 28-38
   :dedent: 8
   :language: java

See the :sdk-examples-java-ec2:`complete example <DescribeKeyPairs.java>`.


Deleting a Key Pair
===================

To delete a key pair, call the |ec2client|'s :methodname:`deleteKeyPair` method, passing it a
:aws-java-class:`DeleteKeyPairRequest <services/ec2/model/DeleteKeyPairRequest>` that contains the
name of the key pair to delete.

**Imports**

.. literalinclude:: example_code/ec2/src/main/java/aws/example/ec2/DeleteKeyPair.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/ec2/src/main/java/aws/example/ec2/DeleteKeyPair.java
   :lines: 39-44
   :dedent: 8
   :language: java

See the :sdk-examples-java-ec2:`complete example <DeleteKeyPair.java>`.


More Information
================

* :ec2-ug:`Amazon EC2 Key Pairs <ec2-key-pairs>` in the |ec2-ug|
* :ec2-api:`CreateKeyPair` in the |ec2-api|
* :ec2-api:`DescribeKeyPairs` in the |ec2-api|
* :ec2-api:`DeleteKeyPair` in the |ec2-api|
