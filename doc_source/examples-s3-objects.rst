.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

#####################################
Performing Operations on |S3| Objects
#####################################

.. meta::
    :description: How to list, upload, download, copy, rename, move or delete objects in an Amazon
                  S3 bucket using the AWS SDK for Java.
    :keywords: AWS SDK for Java code example


An |S3| object represents a *file* or collection of data. Every object must reside within a
:doc:`bucket <examples-s3-buckets>`.

.. include:: includes/examples-note.txt

.. contents::
    :local:
    :depth: 1


.. _upload-object:

Upload an Object
================

Use the |s3client| client's :methodname:`putObject` method, supplying a bucket name, key
name, and file to upload. *The bucket must exist, or an error will result*.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/PutObject.java
   :lines: 15-18
   :language: java

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/PutObject.java
   :lines: 46-53
   :dedent: 8
   :language: java

See the :sdk-examples-java-s3:`complete example <PutObject.java>` on GitHub.

.. _list-objects:

List Objects
============

To get a list of objects within a bucket, use the |s3client| client's :methodname:`listObjects`
method, supplying the name of a bucket.

The :methodname:`listObjects` method returns an :aws-java-class:`ObjectListing
<services/s3/model/ObjectListing>` object that provides information about the objects in the bucket.
To list the object names (keys), use the :methodname:`getObjectSummaries` method to get a List of
:aws-java-class:`S3ObjectSummary <services/s3/model/S3ObjectSummary>` objects, each of which represents a
single object in the bucket. Then call its :methodname:`getKey` method to retrieve the object's
name.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/ListObjects.java
   :lines: 16-20
   :language: java

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/ListObjects.java
   :lines: 44-50
   :dedent: 8
   :language: java

See the :sdk-examples-java-s3:`complete example <ListObjects.java>` on GitHub.

.. _download-object:

Download an Object
==================

Use the |s3client| client's :methodname:`getObject` method, passing it the name of a bucket and
object to download. If successful, the method returns an :aws-java-class:`S3Object
<services/s3/model/S3Object>`. *The specified bucket and object key must exist, or an error will
result*.

You can get the object's contents by calling :methodname:`getObjectContent` on the
:classname:`S3Object`. This returns an :aws-java-class:`S3ObjectInputStream
<services/s3/model/S3ObjectInputStream>` that behaves as a standard Java :classname:`InputStream`
object.

The following example downloads an object from S3 and saves its contents to a file (using the same
name as the object's key).

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/GetObject.java
   :lines: 15-23
   :language: java

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/GetObject.java
   :lines: 50-71
   :dedent: 8
   :language: java

See the :sdk-examples-java-s3:`complete example <GetObject.java>` on GitHub.

.. _copy-object:

Copy, Move, or Rename Objects
=============================

You can copy an object from one bucket to another by using the |s3client| client's
:methodname:`copyObject` method. It takes the name of the bucket to copy from, the object to copy,
and the destination bucket name.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/CopyObject.java
   :lines: 15-17
   :language: java

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/CopyObject.java
   :lines: 48-64
   :dedent: 8
   :language: java

See the :sdk-examples-java-s3:`complete example <CopyObject.java>` on GitHub.

.. note:: You can use :methodname:`copyObject` with :ref:`deleteObject <delete-object>` to **move**
   or **rename** an object, by first copying the object to a new name (you can use the same bucket
   as both the source and destination) and then deleting the object from its old location.


.. _delete-object:

Delete an Object
================

Use the |s3client| client's :methodname:`deleteObject` method, passing it the name of a bucket and
object to delete. *The specified bucket and object key must exist, or an error will result*.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/DeleteObject.java
   :lines: 15-17
   :language: java

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/DeleteObject.java
   :lines: 47-53
   :dedent: 8
   :language: java

See the :sdk-examples-java-s3:`complete example <DeleteObject.java>` on GitHub.


.. _delete-objects:

Delete Multiple Objects at Once
===============================

Using the |s3client| client's :methodname:`deleteObjects` method, you can delete multiple objects
from the same bucket by passing their names to the :aws-java-class:`DeleteObjectRequest
<services/s3/model/DeleteObjectsRequest>` :methodname:`withKeys` method.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/DeleteObjects.java
   :lines: 15-18
   :language: java

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/DeleteObjects.java
   :lines: 52-60
   :dedent: 8
   :language: java

See the :sdk-examples-java-s3:`complete example <DeleteObjects.java>` on GitHub.
