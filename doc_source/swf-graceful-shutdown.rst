.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

######################################################
Shutting Down Activity and Workflow Workers Gracefully
######################################################

The :doc:`swf-hello` topic provided a complete implementation of a simple workflow application
consisting of a registration application, an activity and workflow worker, and a workflow starter.

Worker classes are designed to run continuously, polling for tasks sent by |SWF| in order to run
activities or return decisions. Once a poll request is made, |SWF| records the poller and will
attempt to assign a task to it.

If the workflow worker is terminated during a long poll, |SWF| may still try to send a task to the
terminated worker, resulting in a lost task (until the task times out).

One way to handle this situation is to wait for all long poll requests to return before the worker
terminates.

In this topic, we'll rewrite the activity worker from ``helloswf``, using Java's shutdown hooks to
attempt a graceful shutdown of the activity worker.

Here is the complete code:

.. literalinclude:: swf.java.activity_worker_with_graceful_shutdown.complete.txt
    :language: java

In this version, the polling code that was in the ``main`` function in the original version has been
moved into its own method, ``pollAndExecute``.

The ``main`` function now uses a :javase-ref:`CountDownLatch <java/util/concurrent/CountDownLatch>`
in conjunction with a :javase-ref:`shutdown hook <java/lang/Runtime>` to cause the thread to wait
for up to 60 seconds after its termination is requested before letting the thread shut down.

