.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

######################################################
Best Practices for AWS Development with the |sdk-java|
######################################################

.. meta::
   :description: AWS coding best practices using the AWS SDK for Java.
   :keywords:

The following best practices can help you avoid issues or trouble as you develop AWS applications
with the |sdk-java|. We've organized best practices by service.

.. best-practices-s3:

|s3|
====

.. _s3-avoid-resetexception:

Avoid ResetExceptions
---------------------

When you upload objects to |s3| by using
streams (either through an |s3client| client or |xfermgr|), you might encounter network
connectivity or timeout issues. By default, the |sdk-java| attempts to retry failed transfers by marking
the input stream before
the start of a transfer and then resetting it before retrying.

If the stream doesn't support mark and reset, the SDK throws a :aws-java-class:`ResetException`
when there are transient failures and retries are enabled.

**Best Practice**

We recommend that you use streams that support mark and reset operations.

The most reliable way to avoid a :aws-java-class:`ResetException` is to provide data by using a
:javase-ref:`File <java/io/File>` or :javase-ref:`FileInputStream <java/io/FileInputStream>`, which
the |sdk-java| can handle without being constrained by mark and reset limits.

If the stream isn't a :javase-ref:`FileInputStream <java/io/FileInputStream>` but does support mark and
reset, you can set the mark limit by using the :methodname:`setReadLimit` method of
:aws-java-class:`RequestClientOptions`. Its default value is 128 KB. Setting the read limit value to
*one byte greater than the size of stream* will reliably avoid a :aws-java-class:`ResetException`.

For example, if the maximum expected size of a stream is 100,000 bytes, set the read limit to 100,001
(100,000 + 1) bytes. The mark and reset will always work for 100,000 bytes or less. Be aware that
this might cause some streams to buffer that number of bytes into memory.

