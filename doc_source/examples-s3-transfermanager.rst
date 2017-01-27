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
    :keywords: Amazon S3, AWS SDK for Java, AWS Java SDK, TransferManager, upload, download, copy,
               pause, resume, progress.

The |sdk-java|'s |xfermgr| class can reliably transfer files from the local environment to S3 and
copy objects from one S3 location to another. :classname:`TransferManager` can get the progress of a
transfer and the ability to pause/resume uploads and downloads.

.. include:: common/s3-note-incomplete-upload-policy.txt

.. include:: includes/examples-note.txt


.. _transfermanager-uploading:

Uploading files and directories
===============================

|xfermgr| can upload files, file lists, and directories to any |S3| buckets that you've
:ref:`previously created <create-bucket>`.

.. contents::
   :local:
   :depth: 1


.. _transfermanager-upload-file:

Uploading a single file
-----------------------

To upload a single file, call |xfermgr|'s :methodname:`upload` method, providing it with an |S3|
bucket name, a key (object) name, and a standard Java :javase-ref:`File <java/io/File>` object that
represents the file to upload.

**Imports:**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrUpload.java
   :lines: 16-18, 20

**Code:**

.. uploadFile() method in the example code...

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrUpload.java
   :lines: 93-97, 99, 101-105
   :dedent: 8

The :methodname:`upload` method returns *immediately*, providing you with an Upload object that you
can use to check the state of the transfer or to wait for it to complete.

.. include:: includes/transfermanager-complete-get-status-note.txt

See the :sdk-examples-java-s3:`complete example <XferMgrUpload.java>`.


.. _transfermanager-upload-file-list:

Uploading a list of files
-------------------------

To upload multiple files in one operation, call |xfermgr|'s :methodname:`uploadFileList` method,
providing it with:

* an |S3| bucket name.
* a *key prefix* that will be prepended to the names of the objects created (the path within the
  bucket in which to place the objects).
* a :javase-ref:`File <java/io/File>` object that represents the relative directory from which file
  paths will be created.
* a :javase-ref:`List <java/util/List>` containing a set of :javase-ref:`File <java/io/File>`
  objects to upload.

**Imports:**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrUpload.java
   :lines: 16-17, 19-21

**Code:**

.. uploadFileList() method in the example code...

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrUpload.java
   :lines: 60-69, 71, 73-77
   :dedent: 8

.. include:: includes/transfermanager-complete-get-status-note.txt

.. include:: includes/transfermanager-multifileupload-notes.txt

See the :sdk-examples-java-s3:`complete example <XferMgrUpload.java>`.


.. _transfermanager-upload-directory:

Uploading a directory
---------------------

You can upload an entire directory of files, with the option to copy files in subdirectories
recursively, by using |xfermgr|'s :methodname:`uploadDirectory` method. You provide an |S3| bucket
name, an S3 key prefix, a :javase-ref:`File <java/io/File>` object representing the local directory
to copy, and a :code-java:`boolean` value indicating whether you want to copy subdirectories
recursively (*true*), or not (*false*).

**Imports:**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrUpload.java
   :lines: 16-20

**Code:**

.. uploadDir() method in the example code...

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrUpload.java
   :lines: 38-42, 44, 46-50
   :dedent: 8

.. include:: includes/transfermanager-complete-get-status-note.txt

.. include:: includes/transfermanager-multifileupload-notes.txt

See the :sdk-examples-java-s3:`complete example <XferMgrUpload.java>`.


.. _transfermanager-downloading:

Downloading files
=================

You can download either a single file (|S3| object) or a directory (an |S3| bucket name followed by
an object prefix) from |s3| using the |xfermgr| class.

.. contents::
   :local:
   :depth: 1


.. _transfermanager-download-file:

Downloading a file
------------------

To download a single file, use the |xfermgr|'s :methodname:`download` method, providing it with the
|S3| bucket name containing the object you want to download, the key (object) name, and a
:javase-ref:`File <java/io/File>` object that represents the file to create on your local system.

**Imports:**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrDownload.java
   :lines: 16-18, 20

**Code:**

.. downloadFile() method in the example code...

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrDownload.java
   :lines: 57-61, 63, 65-69
   :dedent: 8

.. include:: includes/transfermanager-complete-get-status-note.txt

See the :sdk-examples-java-s3:`complete example <XferMgrDownload.java>`.


.. _tranfermanager-download-directory:

Downloading a directory
-----------------------

To download a set of files from |S3| that share a common key prefix (analagous to a directory on a
file system), use the |xfermgr|'s :methodname:`downloadDirectory` method. It takes the |S3| bucket
name containing the objects you want to download, the object prefix shared by all of the objects,
and a :javase-ref:`File <java/io/File>` object that represents the directory to download the files
into on your local system. If the named directory doesn't exist yet, it will be created.

**Imports:**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrDownload.java
   :lines: 16-17, 19-20

**Code:**

.. downloadFile() method in the example code...

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrDownload.java
   :lines: 36-40, 42, 44-48
   :dedent: 8

.. include:: includes/transfermanager-complete-get-status-note.txt

See the :sdk-examples-java-s3:`complete example <XferMgrDownload.java>`.


.. _transfermanager-copy-object:

Copying objects
===============

To copy an object from one S3 bucket to another, use |xfermgr|'s :methodname:`copy` method:

**Imports:**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrCopy.java
   :lines: 16-18

**Code:**

.. copyObjectSimple() method in the example code...

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrCopy.java
   :lines: 35-38, 40, 42-46
   :dedent: 8

See the :sdk-examples-java-s3:`complete example <XferMgrCopy.java>`.


.. _transfermanager-wait-for-completion:

Waiting for the completion of a transfer
========================================

If your application (or thread) can block until the transfer is completed, you can use the
:aws-java-class:`Transfer <services/s3/transfer/Transfer>` interface's
:methodname:`waitForCompletion` method to block until the transfer is complete or an exception
occurs.

.. the waitForCompletion() function in XferMgrProgress.java

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrProgress.java
   :lines: 34-45
   :dedent: 8

This method blocks until the transfer is complete. You get progress of transfers if you poll for
events *before* calling :methodname:`waitForCompletion`, implement a polling mechanism on a separate
thread, or receive progress updates asynchronously using a :aws-java-class:`ProgressListener
<event/ProgressListener>`.

See the :sdk-examples-java-s3:`complete example <XferMgrProgress.java>`.


.. _transfermanager-get-status-and-progress:

Getting transfer status and progress
====================================

Each of the classes returned by |xfermgr|'s :methodname:`upload*`, :methodname:`download*` and
:methodname:`copy` methods return an instance of one of the following classes, depending on whether
it's a single-file or multiple-file operation:

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
interface, which provides useful methods for getting the progress of a transfer, to pause or resume
it, and to get its current or final status.

.. contents::
   :local:
   :depth: 1

.. _transfermanager-get-progress-polling:

Polling the current progress of a transfer
------------------------------------------

This loop prints the progress of a transfer, examines its current progress while running, and then
prints it final state when complete:

**Imports:**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrProgress.java
   :lines: 22

**Code:**

.. the showTransferProgress() function in XferMgrProgress.java

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrProgress.java
   :lines: 56-61, 64-67, 70
   :dedent: 8

See the :sdk-examples-java-s3:`complete example <XferMgrProgress.java>`.


.. _transfermanager-progress-listener:

Getting transfer progress with a ProgressListener
-------------------------------------------------

You can attach a :aws-java-class:`ProgressListener <event/ProgressListener>` to any transfer by
using the :aws-java-class:`Transfer <services/s3/transfer/Transfer>` interface's
:methodname:`addProgressListener` method.

A :aws-java-class:`ProgressListener <event/ProgressListener>` requires only one method,
:methodname:`progressChanged`, which takes a :aws-java-class:`ProgressEvent <event/ProgressEvent>`
object. You can use the object to get the total bytes of the operation by calling its
:methodname:`getBytes` method, and the number of bytes transferred so far by calling
:methodname:`getBytesTransferred`.

**Imports:**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrProgress.java
   :lines: 16-18, 23, 25

**Code:**

.. the uploadFileWithListener() function in XferMgrProgress.java

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrProgress.java
   :lines: 146-149, 152-154, 157-159, 162-168
   :dedent: 8

See the :sdk-examples-java-s3:`complete example <XferMgrProgress.java>`.


.. _transfermanager-get-subtransfer-progress:

Getting the progress of subtransfers
------------------------------------

The :aws-java-class:`MultipleFileUpload <services/s3/transfer/MultipleFileUpload>` class can return
information about its sub-transfers by calling its :methodname:`getSubTransfers` method. It returns
an unmodifiable :javase-ref:`Collection <java/util/Collection>` of :aws-java-class:`Upload
<services/s3/transfer/Upload>` objects providing the individual transfer status and progress of each
sub-transfer.

**Imports:**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrProgress.java
   :lines: 23-24, 26-27

**Code:**

.. the showMultiUploadProgress() function in XferMgrProgress.java

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrProgress.java
   :lines: 82-83
   :dedent: 8

See the :sdk-examples-java-s3:`complete example <XferMgrProgress.java>`.


.. _transfermanager-see-also:

See Also
========

* :s3-dg:`Object Keys <UsingMetadata>` in the |s3-dg|.

