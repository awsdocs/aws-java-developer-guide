.. Copyright 2010-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

#######################################
Managing |S3| Bucket Access Permissions
#######################################

.. meta::
   :description: How to retrieve or set the access control list for an Amazon S3 bucket.
   :keywords: AWS for Java SDK code examples, bucket access permissions

You can get or set the access control list for an Amazon S3 bucket.

.. include:: includes/examples-note.txt

Get the Current Bucket Access Control List
==========================================

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/GetAcl.java
   :lines: 16-21

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/GetAcl.java
   :lines: 31-47
   :dedent: 4

See the :sdk-examples-java-s3:`complete example <GetAcl.java>`.

Set a Bucket Access Control List
================================

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/SetAcl.java
   :lines: 16-22

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/SetAcl.java
   :lines: 33-51
   :dedent: 4

See the :sdk-examples-java-s3:`complete example <SetAcl.java>`.
