############
|S3| Objects
############

An |S3| object represents a *file*, or collection of data. Every object must reside within a
:doc:`bucket <buckets>`.

.. include:: ../examples-note.txt

.. contents::
    :local:

.. _upload-object:

Upload an object
================

Use the |s3client| client's :methodname:`putObject` method, supplying it with a bucket name, key
name, and file to upload. *The bucket must exist, or an error will result*.

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/PutObject.java
   :lines: 47-54
   :dedent: 8

.. _list-objects:

List objects
============

To get a list of objects within a bucket, use the |s3client| client's :methodname:`listObjects`
method, supplying it with the name of a bucket.

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/ListObjects.java
   :lines: 46-47
   :dedent: 8

The :methodname:`listObjects` method returns an :java-api:`ObjectListing
<services/s3/model/ObjectListing>` object that provides information about the objects in the bucket.
To list the object names (keys), use the :methodname:`getObjectSummaries` method to get a List of
:java-api:`S3ObjectSummary <services/s3/model/S3ObjectSummary>` objects, each of which represents a
single object in the bucket, then call its :methodname:`getKey` method to retrieve the object's
name.

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/ListObjects.java
   :lines: 48-52
   :dedent: 8


.. _download-object:

Download an object
==================

Use the |s3client| client's :methodname:`getObject` method, passing it the name of a bucket and
object to download. If successful, the method will return an :java-api:`S3Object
<services/s3/model/S3Object>`. *The specified bucket and object key must exist, or an error will
result*.

You can get the object's contents by calling :methodname:`getObjectContent` on the
:classname:`S3Object`. This returns an :java-api:`S3ObjectInputStream
<services/s3/model/S3ObjectInputStream>` that behaves as a standard Java :classname:`InputStream`
object.

The following example downloads an object from S3 and saves its contents to a file (using the same
name as the object's key):

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/GetObject.java
   :lines: 51-75
   :dedent: 8


.. _delete-object:

Delete an object
================

Use the |s3client| client's :methodname:`deleteObject` method, passing it the name of a bucket and
object to delete. *The specified bucket and object key must exist, or an error will result*.

.. literalinclude:: ../../example_code/s3/src/main/java/aws/example/s3/DeleteObject.java
   :lines: 48-55
   :dedent: 8

