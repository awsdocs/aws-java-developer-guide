#####################
Operations on Objects
#####################

.. meta::
    :description: How to list, upload, download, copy, rename, move or delete objects in an Amazon
                  S3 bucket using the AWS SDK for Java.
    :keywords: AWS Java SDK, AWS SDK for Java, Amazon S3, copy, delete, download, get, list, move,
               objects, put, rename, upload

An |S3| object represents a *file*, or collection of data. Every object must reside within a
:doc:`bucket <buckets>`.

.. include:: ../examples-note.txt

.. contents::
    :local:
    :depth: 1


.. _upload-object:

Upload an object
================

Use the |s3client| client's :methodname:`putObject` method, supplying it with a bucket name, key
name, and file to upload. *The bucket must exist, or an error will result*.

**Imports:**

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/PutObject.java
   :lines: 15-17

**Code:**

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/PutObject.java
   :lines: 46-52
   :dedent: 8

See the :sdk-examples-java-s3:`complete example <PutObject.java>`.

.. _list-objects:

List objects
============

To get a list of objects within a bucket, use the |s3client| client's :methodname:`listObjects`
method, supplying it with the name of a bucket.

The :methodname:`listObjects` method returns an :aws-java-class:`ObjectListing
<services/s3/model/ObjectListing>` object that provides information about the objects in the bucket.
To list the object names (keys), use the :methodname:`getObjectSummaries` method to get a List of
:aws-java-class:`S3ObjectSummary <services/s3/model/S3ObjectSummary>` objects, each of which represents a
single object in the bucket, then call its :methodname:`getKey` method to retrieve the object's
name.

**Imports:**

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/ListObjects.java
   :lines: 16-20

**Code:**

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/ListObjects.java
   :lines: 45-50
   :dedent: 8

See the :sdk-examples-java-s3:`complete example <ListObjects.java>`.

.. _download-object:

Download an object
==================

Use the |s3client| client's :methodname:`getObject` method, passing it the name of a bucket and
object to download. If successful, the method will return an :aws-java-class:`S3Object
<services/s3/model/S3Object>`. *The specified bucket and object key must exist, or an error will
result*.

You can get the object's contents by calling :methodname:`getObjectContent` on the
:classname:`S3Object`. This returns an :aws-java-class:`S3ObjectInputStream
<services/s3/model/S3ObjectInputStream>` that behaves as a standard Java :classname:`InputStream`
object.

The following example downloads an object from S3 and saves its contents to a file (using the same
name as the object's key):

**Imports:**

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/GetObject.java
   :lines: 15-23

**Code:**

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/GetObject.java
   :lines: 50-71
   :dedent: 8

See the :sdk-examples-java-s3:`complete example <GetObject.java>`.

.. _copy-object:

Copying, moving or renaming objects
===================================

You can copy an object from one bucket to another by using the |s3client| client's
:methodname:`copyObject` method. It takes the name of the bucket to copy from, the object to copy,
and the destination bucket and name.

**Imports:**

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/CopyObject.java
   :lines: 15-17

**Code:**

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/CopyObject.java
   :lines: 46-52
   :dedent: 8

See the :sdk-examples-java-s3:`complete example <CopyObject.java>`.

.. note:: You can use :methodname:`copyObject` with :ref:`deleteObject <delete-object>` to **move**
   or **rename** an object, by first copying the object to a new name (you can use the same bucket
   as both the source and destination) and then deleting the object from its old location.


.. _delete-object:

Delete an object
================

Use the |s3client| client's :methodname:`deleteObject` method, passing it the name of a bucket and
object to delete. *The specified bucket and object key must exist, or an error will result*.

**Imports:**

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/DeleteObject.java
   :lines: 15-17

**Code:**

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/DeleteObject.java
   :lines: 47-53
   :dedent: 8

See the :sdk-examples-java-s3:`complete example <DeleteObject.java>`.


.. _delete-objects:

Deleting multiple objects at once
=================================

Using the |s3client| client's :methodname:`deleteObjects` method, you can delete multiple objects
from the same bucket by passing their names to the :aws-java-class:`DeleteObjectRequest
<services/s3/model/DeleteObjectsRequest>` :methodname:`withKeys` method.

**Imports:**

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/DeleteObjects.java
   :lines: 15-18

**Code:**

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/DeleteObjects.java
   :lines: 52-60
   :dedent: 8

See the :sdk-examples-java-s3:`complete example <DeleteObjects.java>`.

