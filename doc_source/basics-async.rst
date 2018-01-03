.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

########################
Asynchronous Programming
########################

.. meta::
   :description: How asynchronous programming works in the AWS SDK for Java and best practices for
                 handling exceptions

You can use either *synchronous* or *asynchronous* methods to call operations on AWS services.
Synchronous methods block your thread's execution until the client receives a response from the
service. Asynchronous methods return immediately, giving control back to the calling thread without
waiting for a response.

Because an asynchronous method returns before a response is available, you need a way to get the
response when it's ready. The |sdk-java| provides two ways: *Future objects* and *callback methods*.

.. _basics-async-future:

Java Futures
============

Asynchronous methods in the |sdk-java| return a :javase-ref:`Future <java/util/concurrent/Future>`
object that contains the results of the asynchronous operation *in the future*.

Call the :code-java:`Future` :methodname:`isDone()` method to see if the service has provided a
response object yet. When the response is ready, you can get the response object by calling the
:code-java:`Future` :methodname:`get()` method. You can use this mechanism to periodically poll for
the asynchronous operation's results while your application continues to work on other things.

Here is an example of an asynchronous operation that calls a |LAM| function, receiving a
:code-java:`Future` that can hold an :aws-java-class:`InvokeResult
<services/lambda/model/InvokeResult>` object. The :classname:`InvokeResult` object is retrieved only
after :methodname:`isDone()` is ``true``.

.. literalinclude:: snippets/lambda_invoke_example/src/main/java/example/lambda/InvokeLambdaFunctionAsync.java
   :language: java
   :lines: 18-

.. _basics-async-callback:

Asynchronous Callbacks
======================

In addition to using the Java :code-java:`Future` object to monitor the status of asynchronous
requests, the SDK also enables you to implement a class that uses the :aws-java-class:`AsyncHandler
<handlers/AsyncHandler>` interface. :code-java:`AsyncHandler` provides two methods that are called
depending on how the request completed: :methodname:`onSuccess` and :methodname:`onError`.

The major advantage of the callback interface approach is that it frees you from having to poll the
:code-java:`Future` object to find out when the request has completed. Instead, your code can
immediately start its next activity, and rely on the SDK to call your handler at the right time.

.. literalinclude:: snippets/lambda_invoke_example/src/main/java/example/lambda/InvokeLambdaFunctionCallback.java
   :language: java
   :lines: 18-


.. _basics-async-tips:

Best Practices
==============

Callback Execution
------------------

Your implementation of :classname:`AsyncHandler` is executed inside the thread pool owned by the
asynchronous client. Short, quickly executed code is most appropriate inside your
:classname:`AsyncHandler` implementation. Long-running or blocking code inside your handler methods
can cause contention for the thread pool used by the asynchronous client, and can prevent the client
from executing requests. If you have a long-running task that needs to begin from a callback, have
the callback run its task in a new thread or in a thread pool managed by your application.


Thread Pool Configuration
-------------------------

The asynchronous clients in the |sdk-java| provide a default thread pool that should work for most
applications. You can implement a custom :javase-ref:`ExecutorService
<java/util/concurrent/ExecutorService>` and pass it to |sdk-java| asynchronous clients for more
control over how the thread pools are managed.

For example, you could provide an :classname:`ExecutorService` implementation that uses a custom
:javase-ref:`ThreadFactory <java/util/concurrent/ThreadFactory>` to control how threads in the pool
are named, or to log additional information about thread usage.


|S3| Asynchronous Access
------------------------

The :aws-java-class:`TransferManager <services/s3/transfer/TransferManager>` class in the
SDK offers asynchronous support for working with the |S3|. :classname:`TransferManager` manages
asynchronous uploads and downloads, provides detailed progress reporting on transfers, and supports
callbacks into different events.
