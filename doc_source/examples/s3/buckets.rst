######################################
Creating, Listing and Deleting Buckets
######################################

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


.. _delete-bucket:

Delete a bucket
===============

Use the |s3client| client's :methodname:`deleteBucket` method. *The bucket must be empty, or an
error will result*.

**Imports:**

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/DeleteBucket.java
   :lines: 15-17

**Code:**

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/DeleteBucket.java
   :lines: 45-52
   :dedent: 8

