.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.


###############################################
Sending, Receiving, and Deleting |SQS| Messages
###############################################

.. meta::
   :description: How to send, receive and delete Amazon SQS messages.
   :keywords: AWS SDK for Java code examples, Amazon SQS, send message, receive message, delete
              message

This topic describes how to send, receive and delete |SQS| messages. Messages are always delivered
using an :doc:`SQS Queue <examples-sqs-message-queues>`.


.. _sqs-message-send:

Send a Message
==============

Add a single message to an |SQS| queue by calling the |sqsclient| client's
:methodname:`sendMessage` method. Provide a :aws-java-class:`SendMessageRequest
<services/sqs/model/SendMessageRequest>` object that contains the queue's :ref:`URL
<sqs-get-queue-url>`, message body, and optional delay value (in seconds).

**Imports**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/SendReceiveMessages.java
   :lines: 16-17, 23
   :language: java

**Code**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/SendReceiveMessages.java
   :lines: 45-49
   :dedent: 8
   :language: java

See the :sdk-examples-java-sqs:`complete example <SendReceiveMessages.java>` on GitHub.

.. _sqs-messages-send-multiple:

Send Multiple Messages at Once
------------------------------

You can send more than one message in a single request. To send multiple messages, use the
|sqsclient| client's :methodname:`sendMessageBatch` method, which takes a
:aws-java-class:`SendMessageBatchRequest <services/sqs/model/SendMessageBatchRequest>` containing
the queue URL and a list of messages (each one a :aws-java-class:`SendMessageBatchRequestEntry
<services/sqs/model/SendMessageBatchRequestEntry>`) to send. You can also set an optional delay
value per message.

**Imports**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/SendReceiveMessages.java
   :lines: 21-22
   :language: java

**Code**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/SendReceiveMessages.java
   :lines: 53-61
   :dedent: 8
   :language: java

See the :sdk-examples-java-sqs:`complete example <SendReceiveMessages.java>` on GitHub.


.. _sqs-messages-receive:

Receive Messages
================

Retrieve any messages that are currently in the queue by calling the |sqsclient| client's
:methodname:`receiveMessage` method, passing it the queue's URL. Messages are returned as a list of
:aws-java-class:`Message <services/sqs/model/Message>` objects.

**Imports**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/SendReceiveMessages.java
   :lines: 17-18, 21
   :language: java

**Code**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/SendReceiveMessages.java
   :lines: 64
   :dedent: 8
   :language: java

.. _sqs-messages-delete:

Delete Messages after Receipt
=============================

After receiving a message and processing its contents, delete the message from the queue by sending
the message's receipt handle and queue URL to the |sqsclient| client's
:methodname:`deleteMessage` method.

**Code**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/SendReceiveMessages.java
   :lines: 67-69
   :dedent: 8
   :language: java

See the :sdk-examples-java-sqs:`complete example <SendReceiveMessages.java>` on GitHub.


More Info
=========

* :sqs-dg:`How Amazon SQS Queues Work <sqs-how-it-works>` in the |sqs-dg|
* :sqs-api:`SendMessage` in the |sqs-api|
* :sqs-api:`SendMessageBatch` in the |sqs-api|
* :sqs-api:`ReceiveMessage` in the |sqs-api|
* :sqs-api:`DeleteMessage` in the |sqs-api|
