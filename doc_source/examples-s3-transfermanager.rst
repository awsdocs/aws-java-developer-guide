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

.. |xfermgr| replace:: :aws-java-class:`TransferManager <services/s3/transfer/TransferManager>`

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

See the :sdk-examples-java-s3:`complete example <XferMgrUpload.java>`.

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
   :lines: 91-99
   :dedent: 8

The :methodname:`upload` method returns *immediately*, providing you with an Upload object that you
can use to check the state of the transfer or to wait for it to complete.

See :ref:`transfermanager-completing` for information about getting the state or progress of a
transfer, and closing the transfer successfully.


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
   :lines: 59-67, 69-74
   :dedent: 8

The :aws-java-class:`MultipleFileUpload <services/s3/transfer/MultipleFileUpload>` object returned
by :methodname:`uploadFileList` can be used to query the transfer state or progress. See
:ref:`transfermanager-get-progress` for more information.

You can also use :classname:`MultipleFileUpload`'s :methodname:`getSubTransfers` method to get the
individual :classname:`Upload` objects for each file being transferred.


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
   :lines: 38-41, 43-48
   :dedent: 8

The :aws-java-class:`MultipleFileUpload <services/s3/transfer/MultipleFileUpload>` object returned
by :methodname:`uploadDirectory` can be used to query the transfer state or progress. See
:ref:`transfermanager-get-progress` for more information.

You can also use :classname:`MultipleFileUpload`'s :methodname:`getSubTransfers` method to get the
individual :classname:`Upload` objects for each file being transferred.


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
   :lines: 55-58, 61-65
   :dedent: 8


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
   :lines: 58-61, 64-68
   :dedent: 8


.. _transfermanager-copy-object:

Copying objects
===============

To copy an object from one S3 bucket to another, use |xfermgr|'s :methodname:`copy` method:

**Imports:**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrCopy.java
   :lines: 15-16

**Code:**

.. copyObjectSimple() method in the example code...

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrCopy.java
   :lines: 33-40
   :dedent: 8


.. _transfermanager-completing:

Completing transfers with TransferManager
=========================================

Each of the classes returned by |xfermgr|'s *upload* and *download* methods return an instance of
one of the following classes, depending on whether it's a single-file or multiple-file operation:

* :aws-java-class:`Download <services/s3/transfer/Download>`
* :aws-java-class:`MultipleFileDownload <services/s3/transfer/MultipleFileDownload>`
* :aws-java-class:`Upload <services/s3/transfer/Upload>`
* :aws-java-class:`MultipleFileUpload <services/s3/transfer/MultipleFileUpload>`

All of these classes implement the :aws-java-class:`Transfer <services/s3/transfer/Transfer>`
interface, which provides useful methods for getting the progress of a transfer, to pause or resume
it, and to get its current or final status.

.. contents::
   :local:
   :depth: 1


.. _transfermanager-wait-for-completion:

Waiting for the completion of a transfer
----------------------------------------

If your application (or thread) can block until the transfer is completed, you can use the
:aws-java-class:`Transfer <services/s3/transfer/Transfer>` interface's
:methodname:`waitForCompletion` method to block until the transfer is complete or an exception
occurs.

.. the waitForCompletion() function in XferMgrProgress.java

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrProgress.java
   :lines: 26-37
   :dedent: 8

This method blocks until the transfer is complete. You get progress of transfers if you poll for
events *before* calling :methodname:`waitForCompletion`, implement a polling mechanism on a
separate thread, or receive progress updates asynchronously using a ProgressListener.


.. _transfermanager-get-progress-polling:

Polling the current progress of a transfer
------------------------------------------

This loop prints the progress of a transfer, examines its current progress while running, and then
prints it final state when complete:

**Imports:**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrProgress.java
   :lines: 17-19

**Code:**

.. the showTransferProgress() function in XferMgrProgress.java

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/XferMgrProgress.java
   :lines: 44, 48-54, 57-60, 63, 65
   :dedent: 8

.. TODO

   .. _transfermanager-progress-listener:

   Getting transfer progress with ProgressListener
   -----------------------------------------------

   .. TODO

   .. _transfermanager-get-subtransfer-progress:

   Getting the progress of subtransfers
   ------------------------------------

   The classes :aws-java-class:`MultipleFileDownload <services/s3/transfer/MultipleFileDownload>` and
   :aws-java-class:`MultipleFileUpload <services/s3/transfer/MultipleFileUpload>` each represent a
   transfer operation that has multiple sub-transfers.

   To get information about the subtransfers for a multiple-file upload or download, use the

   .. TODO


.. _transfermanager-see-also:

See Also
========

* :s3-dg:`Object Keys <UsingMetadata>` in the |s3-dg|.

