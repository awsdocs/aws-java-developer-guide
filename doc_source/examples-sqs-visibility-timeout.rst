.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

###################################
Setting Visibility Timeout in |SQS|
###################################

.. meta::
   :description: How to set visibility timeout for Amazon SQS queues with the AWS SDK for Java.
   :keywords: AWS SDK for Java code examples, Amazon SQS, visibility timeout

When a message is received in |SQS|, it remains on the queue until it's deleted in order to ensure
receipt. A message that was received, but not deleted, will be available in subsequent requests
after a given *visibility timeout* to help prevent the message from being received more than once
before it can be processed and deleted.

.. note:: When using :sqs-dg:`standard queues <standard-queues>`, visibility timeout isn't a
   guarantee against receiving a message twice. If you are using a standard queue, be sure that your
   code can handle the case where the same message has been delivered more than once.

.. _sqs-visibility-timeout-receipt:

Setting the Message Visibility Timeout for a Single Message
===========================================================

When you have received a message, you can modify its visibility timeout by passing its receipt
handle in a :aws-java-class:`ChangeMessageVisibilityRequest
<services/sqs/model/ChangeMessageVisibilityRequest>` that you pass to the |sqsclient| class'
:methodname:`changeMessageVisibility` method.

**Imports**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/VisibilityTimeout.java
   :lines: 16-17
   :language: java

**Code**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/VisibilityTimeout.java
   :lines: 31-39
   :dedent: 8
   :language: java

See the :sdk-examples-java-sqs:`complete example <VisibilityTimeout.java>` on GitHub.


Setting the Message Visibility Timeout for Multiple Messages at Once
====================================================================

To set the message visibility timeout for multiple messages at once, create a list of
:aws-java-class:`ChangeMessageVisibilityBatchRequestEntry
<services/sqs/model/ChangeMessageVisibilityBatchRequestEntry>` objects, each containing a unique ID
string and a receipt handle. Then, pass the list to the |sqs| client class'
:methodname:`changeMessageVisibilityBatch` method.

**Imports**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/VisibilityTimeout.java
   :lines: 16-18, 21-22
   :language: java

**Code**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/VisibilityTimeout.java
   :lines: 46-67
   :dedent: 8
   :language: java

See the :sdk-examples-java-sqs:`complete example <VisibilityTimeout.java>` on GitHub.


More Info
=========

* :sqs-dg:`Visibility Timeout <sqs-visibility-timeout>` in the |sqs-dg|
* :sqs-api:`SetQueueAttributes` in the |sqs-api|
* :sqs-api:`GetQueueAttributes` in the |sqs-api|
* :sqs-api:`ReceiveMessage` in the |sqs-api|
* :sqs-api:`ChangeMessageVisibility` in the |sqs-api|
* :sqs-api:`ChangeMessageVisibilityBatch` in the |sqs-api|
