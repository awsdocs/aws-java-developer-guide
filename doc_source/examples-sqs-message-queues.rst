.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

#################################
Working with |SQS| Message Queues
#################################

.. meta::
   :description: How to create, list, delete, and get an Amazon SQS queue's URL.
   :keywords: AWS SDK for Java code example, queue operations

A *message queue* is the logical container used for sending messages reliably in |sqs|. There are
two types of queues: *standard* and *first-in, first-out* (FIFO). To learn more about queues and the
differences between these types, see the |sqs-dg|_.

This topic describes how to create, list, delete, and get the URL of an |sqs| queue by using the
|sdk-java|.


.. _sqs-create-queue:

Create a Queue
==============

Use the |sqsclient| client's :methodname:`createQueue` method, providing a
:aws-java-class:`CreateQueueRequest <services/sqs/model/CreateQueueRequest>` object that describes
the queue parameters.

**Imports**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/UsingQueues.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/UsingQueues.java
   :lines: 31, 34-44
   :dedent: 8
   :language: java

You can use the simplified form of :methodname:`createQueue`, which needs only a queue name, to
create a standard queue.

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/UsingQueues.java
   :lines: 54
   :dedent: 8
   :language: java

See the :sdk-examples-java-sqs:`complete example <UsingQueues.java>` on GitHub.


.. _sqs-list-queues:

Listing Queues
==============

To list the |SQS| queues for your account, call the |sqsclient| client's :methodname:`listQueues`
method.

**Imports**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/UsingQueues.java
   :lines: 16-17, 21
   :language: java

**Code**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/UsingQueues.java
   :lines: 31, 57-61
   :dedent: 8
   :language: java

Using the :methodname:`listQueues` overload without any parameters returns *all queues*. You can
filter the returned results by passing it a :code-java:`ListQueuesRequest` object.

**Imports**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/UsingQueues.java
   :lines: 16-17, 20
   :language: java

**Code**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/UsingQueues.java
   :lines: 31, 64-69
   :dedent: 8
   :language: java

See the :sdk-examples-java-sqs:`complete example <UsingQueues.java>` on GitHub.


.. _sqs-get-queue-url:

Get the URL for a Queue
=======================

Call the |sqsclient| client's :methodname:`getQueueUrl` method.

**Imports**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/UsingQueues.java
   :lines: 16-17
   :language: java

**Code**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/UsingQueues.java
   :lines: 31, 47
   :dedent: 8
   :language: java

See the :sdk-examples-java-sqs:`complete example <UsingQueues.java>` on GitHub.


.. _sqs-delete-queue:

Delete a Queue
==============

Provide the queue's :ref:`URL <sqs-get-queue-url>` to the |sqsclient| client's
:methodname:`deleteQueue` method.

**Imports**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/UsingQueues.java
   :lines: 16-17
   :language: java

**Code**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/UsingQueues.java
   :lines: 31, 50
   :dedent: 8
   :language: java

See the :sdk-examples-java-sqs:`complete example <UsingQueues.java>` on GitHub.

More Info
=========

* :sqs-dg:`How Amazon SQS Queues Work <sqs-how-it-works>` in the |sqs-dg|
* :sqs-api:`CreateQueue` in the |sqs-api|
* :sqs-api:`GetQueueUrl` in the |sqs-api|
* :sqs-api:`ListQueues` in the |sqs-api|
* :sqs-api:`DeleteQueues` in the |sqs-api|
