.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

##############################################
Enabling Long Polling for |SQS| Message Queues
##############################################

.. meta::
   :description: How to enable long polling for Amazon SQS message queues.
   :keywords: AWS SDK for Java code examples, SQS, long polling, queue management

|SQS| uses *short polling* by default, querying only a subset of the servers—based on a weighted
random distribution—to determine whether any messages are available for inclusion in the response.

Long polling helps reduce your cost of using |SQS| by reducing the number of empty responses when
there are no messages available to return in reply to a ReceiveMessage request sent to an |SQS|
queue and eliminating false empty responses.

.. note:: You can set a long polling frequency from *1–20 seconds*.

.. _sqs-long-polling-create-queue:

Enabling Long Polling when Creating a Queue
===========================================

To enable long polling when creating an |SQS| queue, set the ``ReceiveMessageWaitTimeSeconds``
attribute on the :aws-java-class:`CreateQueueRequest <services/sqs/model/CreateQueueRequest>` object
before calling the |sqsclient| class' :methodname:`createQueue` method.

**Imports**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/LongPolling.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/LongPolling.java
   :lines: 40-53
   :dedent: 8
   :language: java

See the :sdk-examples-java-sqs:`complete example <LongPolling.java>` on GitHub.


.. _sqs-long-polling-existing-queue:

Enabling Long Polling on an Existing Queue
==========================================

In addition to enabling long polling when creating a queue, you can also enable it on an existing
queue by setting ``ReceiveMessageWaitTimeSeconds`` on the :aws-java-class:`SetQueueAttributesRequest
<services/sqs/model/SetQueueAttributesRequest>` before calling the |sqsclient| class'
:methodname:`setQueueAttributes` method.

**Imports**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/LongPolling.java
   :lines: 20
   :language: java

**Code**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/LongPolling.java
   :lines: 56-59
   :dedent: 8
   :language: java

See the :sdk-examples-java-sqs:`complete example <LongPolling.java>` on GitHub.


.. _sqs-long-polling-receive-message:

Enabling Long Polling on Message Receipt
========================================

You can enable long polling when receiving a message by setting the wait time in seconds on the
:aws-java-class:`ReceiveMessageRequest <services/sqs/model/ReceiveMessageRequest>` that
you supply to the |sqsclient| class' :methodname:`receiveMessage` method.

.. note:: You should make sure that the AWS client's request timeout is larger than the maximum long
   poll time (20s) so that your :methodname:`receiveMessage` requests don't time out while waiting
   for the next poll event!

**Imports**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/LongPolling.java
   :lines: 21
   :language: java

**Code**

.. literalinclude:: example_code/sqs/src/main/java/aws/example/sqs/LongPolling.java
   :lines: 62-65
   :dedent: 8
   :language: java

See the :sdk-examples-java-sqs:`complete example <LongPolling.java>` on GitHub.

More Info
=========

* :sqs-dg:`Amazon SQS Long Polling <sqs-long-polling>` in the |sqs-dg|
* :sqs-api:`CreateQueue` in the |sqs-api|
* :sqs-api:`ReceiveMessage` in the |sqs-api|
* :sqs-api:`SetQueueAttributes` in the |sqs-api|
