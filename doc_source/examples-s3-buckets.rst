.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

############################################
Creating, Listing, and Deleting |S3| Buckets
############################################

.. meta::
    :description: How to create, list, or delete a bucket using the AWS SDK for Java.
    :keywords: Amazon S3, AWS SDK for Java, create bucket, list bucket, delete
               bucket, delete versioned bucket

Every object (file) in |S3| must reside within a *bucket*, which represents a collection (container)
of objects. Each bucket is known by a *key* (name), which must be unique. For detailed information
about buckets and their configuration, see :s3-dg:`Working with Amazon S3 Buckets <UsingBucket>` in
the |s3-dg|.

.. include:: common/s3-note-incomplete-upload-policy.txt

.. include:: includes/examples-note.txt

.. _create-bucket:

Create a Bucket
===============

Use the |s3client| client's :methodname:`createBucket` method. The new :aws-java-class:`Bucket
<services/s3/model/Bucket>` is returned. The :methodname:`createBucket` method will raise an
exception if the bucket already exists.

.. tip:: To check whether a bucket already exists before attempting to create one with the same
   name, call the :methodname:`doesBucketExist` method. It will return :code-java:`true` if the
   bucket exists, and :code-java:`false` otherwise.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/CreateBucket.java
   :lines: 16-22
   :language: java

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/CreateBucket.java
   :lines: 46-56
   :dedent: 8
   :language: java

See the :sdk-examples-java-s3:`complete example <CreateBucket.java>` on GitHub.

.. _list-buckets:

List Buckets
============

Use the |s3client| client's :methodname:`listBucket` method. If successful, a list of
:aws-java-class:`Bucket <services/s3/model/Bucket>` is returned.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/ListBuckets.java
   :lines: 16-21
   :language: java

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/ListBuckets.java
   :lines: 32-36
   :dedent: 8
   :language: java

See the :sdk-examples-java-s3:`complete example <ListBuckets.java>` on GitHub.

.. _delete-bucket:

Delete a Bucket
===============

Before you can delete an |S3| bucket, you must ensure that the bucket is empty or an error
will result. If you have a :S3-dg:`versioned bucket <Versioning>`, you must also delete any
versioned objects associated with the bucket.

.. note:: The :sdk-examples-java-s3:`complete example <DeleteBucket.java>` includes each of these
   steps in order, providing a complete solution for deleting an |S3| bucket and its contents.

.. contents::
   :local:

Remove Objects from an Unversioned Bucket Before Deleting It
------------------------------------------------------------

Use the |s3client| client's
:methodname:`listObjects` method to retrieve the list of objects and :methodname:`deleteObject` to
delete each one.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/DeleteBucket.java
   :lines: 16-22
   :language: java

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/DeleteBucket.java
   :lines: 50-66
   :dedent: 12
   :language: java

See the :sdk-examples-java-s3:`complete example <DeleteBucket.java>` on GitHub.


Remove Objects from a Versioned Bucket Before Deleting It
---------------------------------------------------------

If you're using a :S3-dg:`versioned bucket <Versioning>`, you also need to remove any stored
versions of the objects in the bucket before the bucket can be deleted.

Using a pattern similar to the one used when removing objects within a bucket, remove versioned
objects by using the |s3client| client's :methodname:`listVersions` method to list any versioned
objects, and then :methodname:`deleteVersion` to delete each one.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/DeleteBucket.java
   :lines: 16-22
   :language: java

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/DeleteBucket.java
   :lines: 68-86
   :dedent: 12
   :language: java

See the :sdk-examples-java-s3:`complete example <DeleteBucket.java>` on GitHub.

Delete an Empty Bucket
----------------------

Once you remove the objects from a bucket (including any versioned objects), you can delete the
bucket itself by using the |s3client| client's :methodname:`deleteBucket` method.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/DeleteBucket.java
   :lines: 16-22
   :language: java

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/DeleteBucket.java
   :lines: 88-89
   :dedent: 12
   :language: java

See the :sdk-examples-java-s3:`complete example <DeleteBucket.java>` on GitHub.
