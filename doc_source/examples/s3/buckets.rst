######################################
Creating, Listing and Deleting Buckets
######################################

.. meta::
    :description: How to create, list, or delete a bucket using the AWS SDK for Java.
    :keywords: Amazon S3, AWS SDK for Java, bucket, create bucket, list bucket, delete bucket,
               versioned bucket

Every object (file) in |S3| must reside within a *bucket*, which represents a collection (container)
of objects. Each bucket is known by a *key* (name), which must be unique. For detailed information
about buckets and their configuration, see :s3-dg:`Working with Amazon S3 Buckets <UsingBucket>` in
the the |s3-dg|.

.. include:: ../examples-note.txt

.. contents::
    :local:
    :depth: 1

.. _create-bucket:

Create a bucket
===============

Use the |s3client| client's :methodname:`createBucket` method. The new :java-api:`Bucket
<services/s3/model/Bucket>` is returned.

**Imports:**

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/CreateBucket.java
   :lines: 15-17

**Code:**

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/CreateBucket.java
   :lines: 43-50
   :dedent: 8

See the :sdk-examples-java:`complete example <s3/CreateBucket.java>`.

.. _list-buckets:

List buckets
============

Use the |s3client| client's :methodname:`listBucket` method. If successful, a List of
:java-api:`Bucket <services/s3/model/Bucket>` objects will be returned.

**Imports:**

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/ListBuckets.java
   :lines: 16-18

**Code:**

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/ListBuckets.java
   :lines: 31-37
   :dedent: 8

See the :sdk-examples-java:`complete example <s3/ListBuckets.java>`.

.. _delete-bucket:

Delete a bucket
===============

Before you can delete an |S3| bucket, you must ensure that the bucket is empty first, or an error
will result. If you have a :S3-dg:`versioned bucket <Versioning>`, then you must also delete any
object versions associated with the bucket, also.

Removing objects from a bucket prior to deletion
------------------------------------------------

To remove objects from a bucket prior to deletion, you can use the |s3client| client's
:methodname:`listObjects` method to retrieve the list of objects and :methodname:`deleteObject` to
delete each one.

**Imports:**

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/DeleteBucket.java
   :lines: 15-17, 19-20, 23

**Code:**

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/DeleteBucket.java
   :lines: 52-70, 93-97
   :dedent: 8


Removing versioned objects from a bucket prior to deletion
----------------------------------------------------------

Using a pattern similar to the one used when removing objects within a bucket, remove versioned
objects by using the |s3client| client's :methodname:`listVersions` method to list any versioned
objects and then :methodname:`deleteVersion` to delete each one.

**Imports:**

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/DeleteBucket.java
   :lines: 15-18, 21-23

**Code:**

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/DeleteBucket.java
   :lines: 52-54, 72-89, 93-97
   :dedent: 8

Deleting an empty bucket
------------------------

Once you've removed the objects from a bucket (including any versioned objects), you can delete the
bucket itself, use the |s3client| client's :methodname:`deleteBucket` method.

**Imports:**

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/DeleteBucket.java
   :lines: 15-17

**Code:**

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/DeleteBucket.java
   :lines: 52-54, 92-97
   :dedent: 8

See the :sdk-examples-java:`complete example <s3/DeleteBucket.java>`.

