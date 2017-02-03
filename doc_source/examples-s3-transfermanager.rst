.. Copyright 2010-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

#########################################
Using TransferManager for |S3| Operations
#########################################

.. meta::
    :description: How to use the AWS SDK for Java's TransferManager class to upload, download, and
                  copy files and directories using Amazon S3.
    :keywords: AWS SDK for Java code examples, TransferManager, Amazon S3 transfer operations

You can use the |sdk-java| |xfermgr| class to reliably transfer files from the local environment to |S3|
and
to copy objects from one S3 location to another. :classname:`TransferManager` can get the progress of
a transfer and pause or resume uploads and downloads.

.. include:: common/s3-note-incomplete-upload-policy.txt

.. include:: includes/examples-note.txt


.. _transfermanager-uploading:

Upload Files and Directories
============================

|xfermgr| can upload files, file lists, and directories to any |S3| buckets that you've
:ref:`previously created <create-bucket>`.

.. contents::
   :local:
   :depth: 1


.. _transfermanager-upload-file:

Upload a Single File
--------------------

Call the |xfermgr| :methodname:`upload` method, providing an |S3|
bucket name, a key (object) name, and a standard Java :javase-ref:`File <java/io/File>` object that
represents the file to upload.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrUpload.java
   :lines: 16-18, 20

**Code**

.. uploadFile() method in the example code...

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrUpload.java
   :lines: 93-97, 99, 101-105
   :dedent: 8

The :methodname:`upload` method returns *immediately*, providing an :code-java:`Upload` object to use
to check the transfer state or to wait for it to complete.

.. include:: includes/transfermanager-complete-get-status-note.txt

See the :sdk-examples-java-s3:`complete example <XferMgrUpload.java>`.


.. _transfermanager-upload-file-list:

Upload a List of Files
----------------------

To upload multiple files in one operation, call the |xfermgr| :methodname:`uploadFileList` method,
providing the following:

* An |S3| bucket name
* A *key prefix* to prepend to the names of the created objects (the path within the
  bucket in which to place the objects)
* A :javase-ref:`File <java/io/File>` object that represents the relative directory from which to create
  file paths
* A :javase-ref:`List <java/util/List>` object containing a set of
  :javase-ref:`File <java/io/File>` objects to upload

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrUpload.java
   :lines: 16-17, 19-21

**Code**

.. uploadFileList() method in the example code...

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrUpload.java
   :lines: 60-69, 71, 73-77
   :dedent: 8

.. include:: includes/transfermanager-complete-get-status-note.txt

.. include:: includes/transfermanager-multifileupload-notes.txt

See the :sdk-examples-java-s3:`complete example <XferMgrUpload.java>`.


.. _transfermanager-upload-directory:

Upload a Directory
------------------

You can use |xfermgr|'s :methodname:`uploadDirectory` method to upload an entire directory of files, with
the option to copy files in subdirectories recursively. You provide an |S3| bucket
name, an S3 key prefix, a :javase-ref:`File <java/io/File>` object representing the local directory
to copy, and a :code-java:`boolean` value indicating whether you want to copy subdirectories
recursively (*true* or *false*).

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrUpload.java
   :lines: 16-20

**Code**

.. uploadDir() method in the example code...

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrUpload.java
   :lines: 38-42, 44, 46-50
   :dedent: 8

.. include:: includes/transfermanager-complete-get-status-note.txt

.. include:: includes/transfermanager-multifileupload-notes.txt

See the :sdk-examples-java-s3:`complete example <XferMgrUpload.java>`.


.. _transfermanager-downloading:

Download Files or Directories
=============================

Use the |xfermgr| class to download either a single file (|S3| object) or a directory (an |S3| bucket
name followed by an object prefix) from |S3|.

.. contents::
   :local:
   :depth: 1


.. _transfermanager-download-file:

Download a Single File
----------------------

Use the |xfermgr|'s :methodname:`download` method, providing the
|S3| bucket name containing the object you want to download, the key (object) name, and a
:javase-ref:`File <java/io/File>` object that represents the file to create on your local system.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrDownload.java
   :lines: 16-18, 20

**Code**

.. downloadFile() method in the example code...

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrDownload.java
   :lines: 57-61, 63, 65-69
   :dedent: 8

.. include:: includes/transfermanager-complete-get-status-note.txt

See the :sdk-examples-java-s3:`complete example <XferMgrDownload.java>`.


.. _tranfermanager-download-directory:

Download a Directory
--------------------

To download a set of files that share a common key prefix (analagous to a directory on a
file system) from |S3|, use the |xfermgr| :methodname:`downloadDirectory` method. The method takes the
|S3| bucket name containing the objects you want to download, the object prefix shared by all of the objects,
and a :javase-ref:`File <java/io/File>` object that represents the directory to download the files
into on your local system. If the named directory doesn't exist yet, it will be created.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrDownload.java
   :lines: 16-17, 19-20

**Code**

.. downloadFile() method in the example code...

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrDownload.java
   :lines: 36-40, 42, 44-48
   :dedent: 8

.. include:: includes/transfermanager-complete-get-status-note.txt

See the :sdk-examples-java-s3:`complete example <XferMgrDownload.java>`.


.. _transfermanager-copy-object:

Copy Objects
============

To copy an object from one S3 bucket to another, use the |xfermgr| :methodname:`copy` method.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrCopy.java
   :lines: 16-18

**Code**

.. copyObjectSimple() method in the example code...

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrCopy.java
   :lines: 35-38, 40, 42-46
   :dedent: 8

See the :sdk-examples-java-s3:`complete example <XferMgrCopy.java>`.


.. _transfermanager-wait-for-completion:

Wait for a Transfer to Complete
===============================

If your application (or thread) can block until the transfer completes, you can use the
:aws-java-class:`Transfer <services/s3/transfer/Transfer>` interface's
:methodname:`waitForCompletion` method to block until the transfer is complete or an exception
occurs.

.. the waitForCompletion() function in XferMgrProgress.java

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrProgress.java
   :lines: 34-45
   :dedent: 8

.. Already said that the method blocks til complete

You get progress of transfers if you poll for
events *before* calling :methodname:`waitForCompletion`, implement a polling mechanism on a separate
thread, or receive progress updates asynchronously using a
:aws-java-class:`ProgressListener <event/ProgressListener>`.

See the :sdk-examples-java-s3:`complete example <XferMgrProgress.java>`.


.. _transfermanager-get-status-and-progress:

Get Transfer Status and Progress
================================

Each of the classes returned by the |xfermgr| :methodname:`upload*`, :methodname:`download*`, and
:methodname:`copy` methods returns an instance of one of the following classes, depending on whether
it's a single-file or multiple-file operation.

.. list-table::
   :header-rows: 1

   * - Class
     - Returned by

   * - :aws-java-class:`Copy <services/s3/transfer/Copy>`
     - :methodname:`copy`

   * - :aws-java-class:`Download <services/s3/transfer/Download>`
     - :methodname:`download`

   * - :aws-java-class:`MultipleFileDownload <services/s3/transfer/MultipleFileDownload>`
     - :methodname:`downloadDirectory`

   * - :aws-java-class:`Upload <services/s3/transfer/Upload>`
     - :methodname:`upload`

   * - :aws-java-class:`MultipleFileUpload <services/s3/transfer/MultipleFileUpload>`
     - :methodname:`uploadFileList`, :methodname:`uploadDirectory`

All of these classes implement the :aws-java-class:`Transfer <services/s3/transfer/Transfer>`
interface. :code-java:`Transfer` provides useful methods to get the progress of a transfer, pause
or resume
the transfer, and get the transfer's current or final status.

.. contents::
   :local:
   :depth: 1

.. _transfermanager-get-progress-polling:

Poll the Current Progress of a Transfer
---------------------------------------

This loop prints the progress of a transfer, examines its current progress while running and, when complete,
prints its final state.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrProgress.java
   :lines: 22

**Code**

.. the showTransferProgress() function in XferMgrProgress.java

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrProgress.java
   :lines: 56-61, 64-67, 70
   :dedent: 8

See the :sdk-examples-java-s3:`complete example <XferMgrProgress.java>`.


.. _transfermanager-progress-listener:

Get Transfer Progress with a ProgressListener
---------------------------------------------

You can attach a :aws-java-class:`ProgressListener <event/ProgressListener>` to any transfer by
using the :aws-java-class:`Transfer <services/s3/transfer/Transfer>` interface's
:methodname:`addProgressListener` method.

A :aws-java-class:`ProgressListener <event/ProgressListener>` requires only one method,
:methodname:`progressChanged`, which takes a :aws-java-class:`ProgressEvent <event/ProgressEvent>`
object. You can use the object to get the total bytes of the operation by calling its
:methodname:`getBytes` method, and the number of bytes transferred so far by calling
:methodname:`getBytesTransferred`.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrProgress.java
   :lines: 16-18, 23, 25

**Code**

.. the uploadFileWithListener() function in XferMgrProgress.java

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrProgress.java
   :lines: 146-149, 152-154, 157-159, 162-168
   :dedent: 8

See the :sdk-examples-java-s3:`complete example <XferMgrProgress.java>`.


.. _transfermanager-get-subtransfer-progress:

Get the Progress of Subtransfers
--------------------------------

The :aws-java-class:`MultipleFileUpload <services/s3/transfer/MultipleFileUpload>` class can return
information about its subtransfers by calling its :methodname:`getSubTransfers` method. It returns
an unmodifiable :javase-ref:`Collection <java/util/Collection>` of :aws-java-class:`Upload
<services/s3/transfer/Upload>` objects that provide the individual transfer status and progress of each
subtransfer.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrProgress.java
   :lines: 23-24, 26-27

**Code**

.. the showMultiUploadProgress() function in XferMgrProgress.java

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrProgress.java
   :lines: 82-83
   :dedent: 8

See the :sdk-examples-java-s3:`complete example <XferMgrProgress.java>`.


.. _transfermanager-see-also:

More Info
=========

* :s3-dg:`Object Keys <UsingMetadata>` in the |s3-dg|

