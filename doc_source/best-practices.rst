.. Copyright 2010-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

######################################################
Best Practices for AWS Development with the |sdk-java|
######################################################

Avoid trouble when developing your AWS applications with the |sdk-java|. Best practices are divided
by service.

.. best-practices-s3:

|s3|
====

.. _s3-avoid-resetexception:

Avoid ResetExceptions
---------------------

You may encounter network connectivity or timeout issues when uploading objects to |s3| using
streams (either through an |s3client| client or |xfermgr|). By default, the |sdk-java| will attempt
to retry failed transfers by marking the input stream before the start of a transfer and then
resetting it before retrying.

If the stream does not support mark and reset, the SDK will throw a :aws-java-class:`ResetException`
when there are transient failures and retries are enabled.

**Best Practice**

We recommend that you use streams that support mark and reset operations.

The most reliable way to avoid :aws-java-class:`ResetException` is to provide data using a
:javase-ref:`File <java/io/File>` or :javase-ref:`FileInputStream <java/io/FileInputStream>` which
can be handled by the |sdk-java| without being constrained by mark and reset limits.

If the stream is not a :javase-ref:`FileInputStream <java/io/FileInputStream>` but supports mark and
reset, you can set the mark limit using the :methodname:`setReadLimit` method of
:aws-java-class:`RequestClientOptions`. Its default value is 128KB. Setting the read limit value to
*one byte greater than the size of stream* will reliably avoid a :aws-java-class:`ResetException`.

For example, if the maximum expected size of stream is 100,000 bytes, set the read limit to 100,001
(100,000 + 1) bytes. The mark and reset will always work for 100,000 bytes or less. Be aware that
this may cause some streams to buffer that number of bytes into memory.

