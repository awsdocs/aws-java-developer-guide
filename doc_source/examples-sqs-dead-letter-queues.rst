.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

#################################
Using Dead Letter Queues in |SQS|
#################################

.. meta::
   :description: How to enable long polling for Amazon SQS message queues.
   :keywords: AWS SDK for Java code examples, SQS, long polling, queue management

|SQS| provides support for *dead letter queues*. A dead letter queue is a queue that other (source)
queues can target for messages that can't be processed successfully. You can set aside and isolate
these messages in the dead letter queue to determine why their processing did not succeed.

.. _sqs-dead-letter-queue-create-dl-queue:

Creating a Dead Letter Queue
============================

A dead letter queue is created the same way as a regular queue, but it has the following
restrictions:

* A dead letter queue must be the same type of queue (FIFO or standard) as the source queue.
* A dead letter queue must be created using the same AWS account and region as the source queue.

Here we create two identical |SQS| queues, one of which will serve as the dead letter queue:

**Imports**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/DeadLetterQueues.java
   :lines: 16-18
   :language: java

**Code**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/DeadLetterQueues.java
   :lines: 36-54
   :dedent: 8
   :language: java

See the :sdk-examples-java-sqs:`complete example <DeadLetterQueues.java>` on GitHub.


.. _sqs-dead-letter-queue-set-redrive-policy:

Designating a Dead Letter Queue for a Source Queue
==================================================

To designate a dead letter queue, you must first create a *redrive policy*, and then set the policy
in the queue's attributes. A redrive policy is specified in JSON, and specifies the ARN of the dead
letter queue and the maximum number of times the message can be received and not processed before
it's sent to the dead letter queue.

To set the redrive policy for your source queue, call the |sqsclient| class'
:methodname:`setQueueAttributes` method with a :aws-java-class:`SetQueueAttributesRequest
<services/sqs/model/SetQueueAttributesRequest>` object for which you've set the ``RedrivePolicy``
attribute with your JSON redrive policy.

**Imports**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/DeadLetterQueues.java
   :lines: 19-21
   :language: java

**Code**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/DeadLetterQueues.java
   :lines: 57-76
   :dedent: 8
   :language: java

See the :sdk-examples-java-sqs:`complete example <DeadLetterQueues.java>` on GitHub.


More Info
=========

* :sqs-dg:`Using Amazon SQS Dead Letter Queues <sqs-dead-letter-queues>` in the |sqs-dg|
* :sqs-api:`SetQueueAttributes` in the |sqs-api|
