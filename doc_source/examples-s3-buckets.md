# Creating, Listing, and Deleting Amazon S3 Buckets<a name="examples-s3-buckets"></a>

Every object \(file\) in Amazon S3 must reside within a *bucket*, which represents a collection \(container\) of objects\. Each bucket is known by a *key* \(name\), which must be unique\. For detailed information about buckets and their configuration, see [Working with Amazon S3 Buckets](http://docs.aws.amazon.com/AmazonS3/latest/dev/UsingBucket.html) in the Amazon Simple Storage Service User Guide\.

**Note**  
Best Practice  
We recommend that you enable the [AbortIncompleteMultipartUpload](http://docs.aws.amazon.com/AmazonS3/latest/API/RESTBucketPUTlifecycle.html) lifecycle rule on your Amazon S3 buckets\.  
This rule directs Amazon S3 to abort multipart uploads that don’t complete within a specified number of days after being initiated\. When the set time limit is exceeded, Amazon S3 aborts the upload and then deletes the incomplete upload data\.  
For more information, see [Lifecycle Configuration for a Bucket with Versioning](http://docs.aws.amazon.com/AmazonS3/latest/userguide/lifecycle-configuration-bucket-with-versioning.html) in the Amazon S3 User Guide\.

**Note**  
These code examples assume that you understand the material in [Using the AWS SDK for Java](basics.md) and have configured default AWS credentials using the information in [Set up AWS Credentials and Region for Development](setup-credentials.md)\.

## Create a Bucket<a name="create-bucket"></a>

Use the AmazonS3 client’s `createBucket` method\. The new [Bucket](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/model/Bucket.html) is returned\. The `createBucket` method will raise an exception if the bucket already exists\.

**Note**  
To check whether a bucket already exists before attempting to create one with the same name, call the `doesBucketExist` method\. It will return `true` if the bucket exists, and `false` otherwise\.

 **Imports** 

```
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;

import java.util.List;
```

 **Code** 

```
if (s3.doesBucketExistV2(bucket_name)) {
    System.out.format("Bucket %s already exists.\n", bucket_name);
    b = getBucket(bucket_name);
} else {
    try {
        b = s3.createBucket(bucket_name);
    } catch (AmazonS3Exception e) {
        System.err.println(e.getErrorMessage());
    }
}
return b;
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/CreateBucket.java) on GitHub\.

## List Buckets<a name="list-buckets"></a>

Use the AmazonS3 client’s `listBucket` method\. If successful, a list of [Bucket](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/model/Bucket.html) is returned\.

 **Imports** 

```
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;

import java.util.List;
```

 **Code** 

```
List<Bucket> buckets = s3.listBuckets();
System.out.println("Your {S3} buckets are:");
for (Bucket b : buckets) {
    System.out.println("* " + b.getName());
}
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/ListBuckets.java) on GitHub\.

## Delete a Bucket<a name="delete-bucket"></a>

Before you can delete an Amazon S3 bucket, you must ensure that the bucket is empty or an error will result\. If you have a [versioned bucket](http://docs.aws.amazon.com/AmazonS3/latest/dev/Versioning.html), you must also delete any versioned objects associated with the bucket\.

**Note**  
The [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/DeleteBucket.java) includes each of these steps in order, providing a complete solution for deleting an Amazon S3 bucket and its contents\.

**Topics**
+ [Remove Objects from an Unversioned Bucket Before Deleting It](#remove-objects-from-an-unversioned-bucket-before-deleting-it)
+ [Remove Objects from a Versioned Bucket Before Deleting It](#remove-objects-from-a-versioned-bucket-before-deleting-it)
+ [Delete an Empty Bucket](#delete-an-empty-bucket)

### Remove Objects from an Unversioned Bucket Before Deleting It<a name="remove-objects-from-an-unversioned-bucket-before-deleting-it"></a>

Use the AmazonS3 client’s `listObjects` method to retrieve the list of objects and `deleteObject` to delete each one\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;

import java.util.Iterator;
```

 **Code** 

```
System.out.println(" - removing objects from bucket");
ObjectListing object_listing = s3.listObjects(bucket_name);
while (true) {
    for (Iterator<?> iterator =
         object_listing.getObjectSummaries().iterator();
         iterator.hasNext(); ) {
        S3ObjectSummary summary = (S3ObjectSummary) iterator.next();
        s3.deleteObject(bucket_name, summary.getKey());
    }

    // more object_listing to retrieve?
    if (object_listing.isTruncated()) {
        object_listing = s3.listNextBatchOfObjects(object_listing);
    } else {
        break;
    }
}
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/DeleteBucket.java) on GitHub\.

### Remove Objects from a Versioned Bucket Before Deleting It<a name="remove-objects-from-a-versioned-bucket-before-deleting-it"></a>

If you’re using a [versioned bucket](http://docs.aws.amazon.com/AmazonS3/latest/dev/Versioning.html), you also need to remove any stored versions of the objects in the bucket before the bucket can be deleted\.

Using a pattern similar to the one used when removing objects within a bucket, remove versioned objects by using the AmazonS3 client’s `listVersions` method to list any versioned objects, and then `deleteVersion` to delete each one\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;

import java.util.Iterator;
```

 **Code** 

```
System.out.println(" - removing versions from bucket");
VersionListing version_listing = s3.listVersions(
        new ListVersionsRequest().withBucketName(bucket_name));
while (true) {
    for (Iterator<?> iterator =
         version_listing.getVersionSummaries().iterator();
         iterator.hasNext(); ) {
        S3VersionSummary vs = (S3VersionSummary) iterator.next();
        s3.deleteVersion(
                bucket_name, vs.getKey(), vs.getVersionId());
    }

    if (version_listing.isTruncated()) {
        version_listing = s3.listNextBatchOfVersions(
                version_listing);
    } else {
        break;
    }
}
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/DeleteBucket.java) on GitHub\.

### Delete an Empty Bucket<a name="delete-an-empty-bucket"></a>

Once you remove the objects from a bucket \(including any versioned objects\), you can delete the bucket itself by using the AmazonS3 client’s `deleteBucket` method\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;

import java.util.Iterator;
```

 **Code** 

```
System.out.println(" OK, bucket ready to delete!");
s3.deleteBucket(bucket_name);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/DeleteBucket.java) on GitHub\.