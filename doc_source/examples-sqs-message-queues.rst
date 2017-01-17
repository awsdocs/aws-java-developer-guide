.. Copyright 2010-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.

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
   :keywords: Amazon SQS, queue, queue operations, create queue, list queue, delete queue, get queue
              URL

A *message queue* is the logical container used for sending messages reliably in |sqslong|. There
are two types of queues: *standard*, and *first-in, first-out* (FIFO). To learn more about queues
and the differences between these types, see the |sqs-dg|_.

This topic describes how to create, list, delete, and get a |SQS| queue's URL using the |sdk-java|.


.. _sqs-create-queue:

Creating queues
===============

To create a new queue, use the |sqsclient| client's :methodname:`createQueue` method, providing it
with a :aws-java-class:`CreateQueueRequest <services/sqs/model/CreateQueueRequest>` object that
describes the queue parameters.

**Imports:**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/UsingQueues.java
   :lines: 17-20

**Code:**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/UsingQueues.java
   :lines: 33, 36-46
   :dedent: 8

You can use the simplified form of :methodname:`createQueue` which needs only a queue name to create
a standard queue for you:

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/UsingQueues.java
   :lines: 56
   :dedent: 8

See the :sdk-examples-java-sqs:`complete sample <UsingQueues.java>`.


.. _sqs-list-queues:

Listing queues
==============

To list the |SQS| queues for your account, call the |sqsclient| client's :methodname:`listQueues`
method.

**Imports:**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/UsingQueues.java
   :lines: 17-18, 22

**Code:**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/UsingQueues.java
   :lines: 33, 59-63
   :dedent: 8

The :methodname:`listQueues` overload without any parameters returns *all queues*. You can filter
the results that are returned by passing it a `ListQueuesRequest` object.

**Imports:**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/UsingQueues.java
   :lines: 17-18, 21

**Code:**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/UsingQueues.java
   :lines: 33, 66-71
   :dedent: 8

See the :sdk-examples-java-sqs:`complete sample <UsingQueues.java>`.


.. _sqs-get-queue-url:

Getting the queue's URL
=======================

To get an existing |SQS| queue's URL, call the SQS client's :methodname:`getQueueUrl` method.

**Imports:**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/UsingQueues.java
   :lines: 17-18

**Code:**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/UsingQueues.java
   :lines: 33, 49
   :dedent: 8

See the :sdk-examples-java-sqs:`complete sample <UsingQueues.java>`.


.. _sqs-delete-queue:

Deleting a queue
================

To delete an |SQS| queue, provide its :ref:`URL <sqs-get-queue-url>` to the |sqsclient| client's
:methodname:`deleteQueue` method.

**Imports:**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/UsingQueues.java
   :lines: 17-18

**Code:**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/UsingQueues.java
   :lines: 33, 52
   :dedent: 8

See the :sdk-examples-java-sqs:`complete sample <UsingQueues.java>`.

See Also
========

* :sqs-dg:`How Amazon SQS Queues Work <sqs-how-it-works>` in the |sqs-dg|
* :sqs-api:`CreateQueue` in the |sqs-api|
* :sqs-api:`GetQueueUrl` in the |sqs-api|
* :sqs-api:`ListQueues` in the |sqs-api|
* :sqs-api:`DeleteQueues` in the |sqs-api|

