--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Using TransferManager for Amazon S3 Operations<a name="examples-s3-transfermanager"></a>

You can use the AWS SDK for Java TransferManager class to reliably transfer files from the local environment to Amazon S3 and to copy objects from one S3 location to another\. `TransferManager` can get the progress of a transfer and pause or resume uploads and downloads\.

**Note**  
Best Practice  
We recommend that you enable the [AbortIncompleteMultipartUpload](http://docs.aws.amazon.com/AmazonS3/latest/API/RESTBucketPUTlifecycle.html) lifecycle rule on your Amazon S3 buckets\.  
This rule directs Amazon S3 to abort multipart uploads that don’t complete within a specified number of days after being initiated\. When the set time limit is exceeded, Amazon S3 aborts the upload and then deletes the incomplete upload data\.  
For more information, see [Lifecycle Configuration for a Bucket with Versioning](http://docs.aws.amazon.com/AmazonS3/latest/userguide/lifecycle-configuration-bucket-with-versioning.html) in the Amazon S3 User Guide\.

**Note**  
These code examples assume that you understand the material in [Using the AWS SDK for Java](basics.md) and have configured default AWS credentials using the information in [Set up AWS Credentials and Region for Development](setup-credentials.md)\.

## Upload Files and Directories<a name="transfermanager-uploading"></a>

TransferManager can upload files, file lists, and directories to any Amazon S3 buckets that you’ve [previously created](examples-s3-buckets.md#create-bucket)\.

**Topics**
+ [Upload a Single File](#transfermanager-upload-file)
+ [Upload a List of Files](#transfermanager-upload-file-list)
+ [Upload a Directory](#transfermanager-upload-directory)

### Upload a Single File<a name="transfermanager-upload-file"></a>

Call TransferManager’s `upload` method, providing an Amazon S3 bucket name, a key \(object\) name, and a standard Java [File](https://docs.oracle.com/javase/8/docs/api/index.html?java/io/File.html) object that represents the file to upload\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
```

 **Code** 

```
File f = new File(file_path);
TransferManager xfer_mgr = TransferManagerBuilder.standard().build();
try {
    Upload xfer = xfer_mgr.upload(bucket_name, key_name, f);
    // loop with Transfer.isDone()
    XferMgrProgress.showTransferProgress(xfer);
    //  or block with Transfer.waitForCompletion()
    XferMgrProgress.waitForCompletion(xfer);
} catch (AmazonServiceException e) {
    System.err.println(e.getErrorMessage());
    System.exit(1);
}
xfer_mgr.shutdownNow();
```

The `upload` method returns *immediately*, providing an `Upload` object to use to check the transfer state or to wait for it to complete\.

See [Wait for a Transfer to Complete](#transfermanager-wait-for-completion) for information about using `waitForCompletion` to successfully complete a transfer before calling TransferManager’s `shutdownNow` method\. While waiting for the transfer to complete, you can poll or listen for updates about its status and progress\. See [Get Transfer Status and Progress](#transfermanager-get-status-and-progress) for more information\.

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/XferMgrUpload.java) on GitHub\.

### Upload a List of Files<a name="transfermanager-upload-file-list"></a>

To upload multiple files in one operation, call the TransferManager`uploadFileList` method, providing the following:
+ An Amazon S3 bucket name
+ A *key prefix* to prepend to the names of the created objects \(the path within the bucket in which to place the objects\)
+ A [File](https://docs.oracle.com/javase/8/docs/api/index.html?java/io/File.html) object that represents the relative directory from which to create file paths
+ A [List](https://docs.oracle.com/javase/8/docs/api/index.html?java/util/List.html) object containing a set of [File](https://docs.oracle.com/javase/8/docs/api/index.html?java/io/File.html) objects to upload

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
```

 **Code** 

```
ArrayList<File> files = new ArrayList<File>();
for (String path : file_paths) {
    files.add(new File(path));
}

TransferManager xfer_mgr = TransferManagerBuilder.standard().build();
try {
    MultipleFileUpload xfer = xfer_mgr.uploadFileList(bucket_name,
            key_prefix, new File("."), files);
    // loop with Transfer.isDone()
    XferMgrProgress.showTransferProgress(xfer);
    // or block with Transfer.waitForCompletion()
    XferMgrProgress.waitForCompletion(xfer);
} catch (AmazonServiceException e) {
    System.err.println(e.getErrorMessage());
    System.exit(1);
}
xfer_mgr.shutdownNow();
```

See [Wait for a Transfer to Complete](#transfermanager-wait-for-completion) for information about using `waitForCompletion` to successfully complete a transfer before calling TransferManager’s `shutdownNow` method\. While waiting for the transfer to complete, you can poll or listen for updates about its status and progress\. See [Get Transfer Status and Progress](#transfermanager-get-status-and-progress) for more information\.

The [MultipleFileUpload](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/transfer/MultipleFileUpload.html) object returned by `uploadFileList` can be used to query the transfer state or progress\. See [Poll the Current Progress of a Transfer](#transfermanager-get-progress-polling) and [Get Transfer Progress with a ProgressListener](#transfermanager-progress-listener) for more information\.

You can also use `MultipleFileUpload`'s `getSubTransfers` method to get the individual `Upload` objects for each file being transferred\. For more information, see [Get the Progress of Subtransfers](#transfermanager-get-subtransfer-progress)\.

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/XferMgrUpload.java) on GitHub\.

### Upload a Directory<a name="transfermanager-upload-directory"></a>

You can use TransferManager’s `uploadDirectory` method to upload an entire directory of files, with the option to copy files in subdirectories recursively\. You provide an Amazon S3 bucket name, an S3 key prefix, a [File](https://docs.oracle.com/javase/8/docs/api/index.html?java/io/File.html) object representing the local directory to copy, and a `boolean` value indicating whether you want to copy subdirectories recursively \(*true* or *false*\)\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
```

 **Code** 

```
TransferManager xfer_mgr = TransferManagerBuilder.standard().build();
try {
    MultipleFileUpload xfer = xfer_mgr.uploadDirectory(bucket_name,
            key_prefix, new File(dir_path), recursive);
    // loop with Transfer.isDone()
    XferMgrProgress.showTransferProgress(xfer);
    // or block with Transfer.waitForCompletion()
    XferMgrProgress.waitForCompletion(xfer);
} catch (AmazonServiceException e) {
    System.err.println(e.getErrorMessage());
    System.exit(1);
}
xfer_mgr.shutdownNow();
```

See [Wait for a Transfer to Complete](#transfermanager-wait-for-completion) for information about using `waitForCompletion` to successfully complete a transfer before calling TransferManager’s `shutdownNow` method\. While waiting for the transfer to complete, you can poll or listen for updates about its status and progress\. See [Get Transfer Status and Progress](#transfermanager-get-status-and-progress) for more information\.

The [MultipleFileUpload](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/transfer/MultipleFileUpload.html) object returned by `uploadFileList` can be used to query the transfer state or progress\. See [Poll the Current Progress of a Transfer](#transfermanager-get-progress-polling) and [Get Transfer Progress with a ProgressListener](#transfermanager-progress-listener) for more information\.

You can also use `MultipleFileUpload`'s `getSubTransfers` method to get the individual `Upload` objects for each file being transferred\. For more information, see [Get the Progress of Subtransfers](#transfermanager-get-subtransfer-progress)\.

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/XferMgrUpload.java) on GitHub\.

## Download Files or Directories<a name="transfermanager-downloading"></a>

Use the TransferManager class to download either a single file \(Amazon S3 object\) or a directory \(an Amazon S3 bucket name followed by an object prefix\) from Amazon S3\.

**Topics**
+ [Download a Single File](#transfermanager-download-file)
+ [Download a Directory](#tranfermanager-download-directory)

### Download a Single File<a name="transfermanager-download-file"></a>

Use the TransferManager’s `download` method, providing the Amazon S3 bucket name containing the object you want to download, the key \(object\) name, and a [File](https://docs.oracle.com/javase/8/docs/api/index.html?java/io/File.html) object that represents the file to create on your local system\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.MultipleFileDownload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;

import java.io.File;
```

 **Code** 

```
File f = new File(file_path);
TransferManager xfer_mgr = TransferManagerBuilder.standard().build();
try {
    Download xfer = xfer_mgr.download(bucket_name, key_name, f);
    // loop with Transfer.isDone()
    XferMgrProgress.showTransferProgress(xfer);
    // or block with Transfer.waitForCompletion()
    XferMgrProgress.waitForCompletion(xfer);
} catch (AmazonServiceException e) {
    System.err.println(e.getErrorMessage());
    System.exit(1);
}
xfer_mgr.shutdownNow();
```

See [Wait for a Transfer to Complete](#transfermanager-wait-for-completion) for information about using `waitForCompletion` to successfully complete a transfer before calling TransferManager’s `shutdownNow` method\. While waiting for the transfer to complete, you can poll or listen for updates about its status and progress\. See [Get Transfer Status and Progress](#transfermanager-get-status-and-progress) for more information\.

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/XferMgrDownload.java) on GitHub\.

### Download a Directory<a name="tranfermanager-download-directory"></a>

To download a set of files that share a common key prefix \(analogous to a directory on a file system\) from Amazon S3, use the TransferManager`downloadDirectory` method\. The method takes the Amazon S3 bucket name containing the objects you want to download, the object prefix shared by all of the objects, and a [File](https://docs.oracle.com/javase/8/docs/api/index.html?java/io/File.html) object that represents the directory to download the files into on your local system\. If the named directory doesn’t exist yet, it will be created\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.MultipleFileDownload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;

import java.io.File;
```

 **Code** 

```
TransferManager xfer_mgr = TransferManagerBuilder.standard().build();

try {
    MultipleFileDownload xfer = xfer_mgr.downloadDirectory(
            bucket_name, key_prefix, new File(dir_path));
    // loop with Transfer.isDone()
    XferMgrProgress.showTransferProgress(xfer);
    // or block with Transfer.waitForCompletion()
    XferMgrProgress.waitForCompletion(xfer);
} catch (AmazonServiceException e) {
    System.err.println(e.getErrorMessage());
    System.exit(1);
}
xfer_mgr.shutdownNow();
```

See [Wait for a Transfer to Complete](#transfermanager-wait-for-completion) for information about using `waitForCompletion` to successfully complete a transfer before calling TransferManager’s `shutdownNow` method\. While waiting for the transfer to complete, you can poll or listen for updates about its status and progress\. See [Get Transfer Status and Progress](#transfermanager-get-status-and-progress) for more information\.

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/XferMgrDownload.java) on GitHub\.

## Copy Objects<a name="transfermanager-copy-object"></a>

To copy an object from one S3 bucket to another, use the TransferManager`copy` method\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.transfer.Copy;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
```

 **Code** 

```
System.out.println("Copying s3 object: " + from_key);
System.out.println("      from bucket: " + from_bucket);
System.out.println("     to s3 object: " + to_key);
System.out.println("        in bucket: " + to_bucket);

TransferManager xfer_mgr = TransferManagerBuilder.standard().build();
try {
    Copy xfer = xfer_mgr.copy(from_bucket, from_key, to_bucket, to_key);
    // loop with Transfer.isDone()
    XferMgrProgress.showTransferProgress(xfer);
    // or block with Transfer.waitForCompletion()
    XferMgrProgress.waitForCompletion(xfer);
} catch (AmazonServiceException e) {
    System.err.println(e.getErrorMessage());
    System.exit(1);
}
xfer_mgr.shutdownNow();
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/XferMgrCopy.java) on GitHub\.

## Wait for a Transfer to Complete<a name="transfermanager-wait-for-completion"></a>

If your application \(or thread\) can block until the transfer completes, you can use the [Transfer](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/transfer/Transfer.html) interface’s `waitForCompletion` method to block until the transfer is complete or an exception occurs\.

```
try {
    xfer.waitForCompletion();
} catch (AmazonServiceException e) {
    System.err.println("Amazon service error: " + e.getMessage());
    System.exit(1);
} catch (AmazonClientException e) {
    System.err.println("Amazon client error: " + e.getMessage());
    System.exit(1);
} catch (InterruptedException e) {
    System.err.println("Transfer interrupted: " + e.getMessage());
    System.exit(1);
}
```

You get progress of transfers if you poll for events *before* calling `waitForCompletion`, implement a polling mechanism on a separate thread, or receive progress updates asynchronously using a [ProgressListener](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/event/ProgressListener.html)\.

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/XferMgrProgress.java) on GitHub\.

## Get Transfer Status and Progress<a name="transfermanager-get-status-and-progress"></a>

Each of the classes returned by the TransferManager`upload*`, `download*`, and `copy` methods returns an instance of one of the following classes, depending on whether it’s a single\-file or multiple\-file operation\.


**​**  

| Class | Returned by | 
| --- | --- | 
|   [Copy](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/transfer/Copy.html)   |   `copy`   | 
|   [Download](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/transfer/Download.html)   |   `download`   | 
|   [MultipleFileDownload](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/transfer/MultipleFileDownload.html)   |   `downloadDirectory`   | 
|   [Upload](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/transfer/Upload.html)   |   `upload`   | 
|   [MultipleFileUpload](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/transfer/MultipleFileUpload.html)   |   `uploadFileList`, `uploadDirectory`   | 

All of these classes implement the [Transfer](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/transfer/Transfer.html) interface\. `Transfer` provides useful methods to get the progress of a transfer, pause or resume the transfer, and get the transfer’s current or final status\.

**Topics**
+ [Poll the Current Progress of a Transfer](#transfermanager-get-progress-polling)
+ [Get Transfer Progress with a ProgressListener](#transfermanager-progress-listener)
+ [Get the Progress of Subtransfers](#transfermanager-get-subtransfer-progress)

### Poll the Current Progress of a Transfer<a name="transfermanager-get-progress-polling"></a>

This loop prints the progress of a transfer, examines its current progress while running and, when complete, prints its final state\.

 **Imports** 

```
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.transfer.*;
import com.amazonaws.services.s3.transfer.Transfer.TransferState;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
```

 **Code** 

```
// print the transfer's human-readable description
System.out.println(xfer.getDescription());
// print an empty progress bar...
printProgressBar(0.0);
// update the progress bar while the xfer is ongoing.
do {
    try {
        Thread.sleep(100);
    } catch (InterruptedException e) {
        return;
    }
    // Note: so_far and total aren't used, they're just for
    // documentation purposes.
    TransferProgress progress = xfer.getProgress();
    long so_far = progress.getBytesTransferred();
    long total = progress.getTotalBytesToTransfer();
    double pct = progress.getPercentTransferred();
    eraseProgressBar();
    printProgressBar(pct);
} while (xfer.isDone() == false);
// print the final state of the transfer.
TransferState xfer_state = xfer.getState();
System.out.println(": " + xfer_state);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/XferMgrProgress.java) on GitHub\.

### Get Transfer Progress with a ProgressListener<a name="transfermanager-progress-listener"></a>

You can attach a [ProgressListener](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/event/ProgressListener.html) to any transfer by using the [Transfer](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/transfer/Transfer.html) interface’s `addProgressListener` method\.

A [ProgressListener](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/event/ProgressListener.html) requires only one method, `progressChanged`, which takes a [ProgressEvent](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/event/ProgressEvent.html) object\. You can use the object to get the total bytes of the operation by calling its `getBytes` method, and the number of bytes transferred so far by calling `getBytesTransferred`\.

 **Imports** 

```
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.transfer.*;
import com.amazonaws.services.s3.transfer.Transfer.TransferState;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
```

 **Code** 

```
File f = new File(file_path);
TransferManager xfer_mgr = TransferManagerBuilder.standard().build();
try {
    Upload u = xfer_mgr.upload(bucket_name, key_name, f);
    // print an empty progress bar...
    printProgressBar(0.0);
    u.addProgressListener(new ProgressListener() {
        public void progressChanged(ProgressEvent e) {
            double pct = e.getBytesTransferred() * 100.0 / e.getBytes();
            eraseProgressBar();
            printProgressBar(pct);
        }
    });
    // block with Transfer.waitForCompletion()
    XferMgrProgress.waitForCompletion(u);
    // print the final state of the transfer.
    TransferState xfer_state = u.getState();
    System.out.println(": " + xfer_state);
} catch (AmazonServiceException e) {
    System.err.println(e.getErrorMessage());
    System.exit(1);
}
xfer_mgr.shutdownNow();
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/XferMgrProgress.java) on GitHub\.

### Get the Progress of Subtransfers<a name="transfermanager-get-subtransfer-progress"></a>

The [MultipleFileUpload](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/transfer/MultipleFileUpload.html) class can return information about its subtransfers by calling its `getSubTransfers` method\. It returns an unmodifiable [Collection](https://docs.oracle.com/javase/8/docs/api/index.html?java/util/Collection.html) of [Upload](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/transfer/Upload.html) objects that provide the individual transfer status and progress of each sub\-transfer\.

 **Imports** 

```
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.transfer.*;
import com.amazonaws.services.s3.transfer.Transfer.TransferState;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
```

 **Code** 

```
Collection<? extends Upload> sub_xfers = new ArrayList<Upload>();
sub_xfers = multi_upload.getSubTransfers();

do {
    System.out.println("\nSubtransfer progress:\n");
    for (Upload u : sub_xfers) {
        System.out.println("  " + u.getDescription());
        if (u.isDone()) {
            TransferState xfer_state = u.getState();
            System.out.println("  " + xfer_state);
        } else {
            TransferProgress progress = u.getProgress();
            double pct = progress.getPercentTransferred();
            printProgressBar(pct);
            System.out.println();
        }
    }

    // wait a bit before the next update.
    try {
        Thread.sleep(200);
    } catch (InterruptedException e) {
        return;
    }
} while (multi_upload.isDone() == false);
// print the final state of the transfer.
TransferState xfer_state = multi_upload.getState();
System.out.println("\nMultipleFileUpload " + xfer_state);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/XferMgrProgress.java) on GitHub\.

## More Info<a name="transfermanager-see-also"></a>
+  [Object Keys](http://docs.aws.amazon.com/AmazonS3/latest/dev/UsingMetadata.html) in the Amazon Simple Storage Service User Guide