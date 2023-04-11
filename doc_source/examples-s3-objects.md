# Performing Operations on Amazon S3 Objects<a name="examples-s3-objects"></a>

An Amazon S3 object represents a *file* or collection of data\. Every object must reside within a [bucket](examples-s3-buckets.md)\.

**Note**  
These code examples assume that you understand the material in [Using the AWS SDK for Java](basics.md) and have configured default AWS credentials using the information in [Set up AWS Credentials and Region for Development](setup-credentials.md)\.

**Topics**
+ [Upload an Object](#upload-object)
+ [List Objects](#list-objects)
+ [Download an Object](#download-object)
+ [Copy, Move, or Rename Objects](#copy-object)
+ [Delete an Object](#delete-object)
+ [Delete Multiple Objects at Once](#delete-objects)

## Upload an Object<a name="upload-object"></a>

Use the AmazonS3 client’s `putObject` method, supplying a bucket name, key name, and file to upload\. *The bucket must exist, or an error will result*\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
```

 **Code** 

```
System.out.format("Uploading %s to S3 bucket %s...\n", file_path, bucket_name);
final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
try {
    s3.putObject(bucket_name, key_name, new File(file_path));
} catch (AmazonServiceException e) {
    System.err.println(e.getErrorMessage());
    System.exit(1);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/PutObject.java) on GitHub\.

## List Objects<a name="list-objects"></a>

To get a list of objects within a bucket, use the AmazonS3 client’s `listObjects` method, supplying the name of a bucket\.

The `listObjects` method returns an [ObjectListing](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/model/ObjectListing.html) object that provides information about the objects in the bucket\. To list the object names \(keys\), use the `getObjectSummaries` method to get a List of [S3ObjectSummary](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/model/S3ObjectSummary.html) objects, each of which represents a single object in the bucket\. Then call its `getKey` method to retrieve the object’s name\.

 **Imports** 

```
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
```

 **Code** 

```
System.out.format("Objects in S3 bucket %s:\n", bucket_name);
final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
ListObjectsV2Result result = s3.listObjectsV2(bucket_name);
List<S3ObjectSummary> objects = result.getObjectSummaries();
for (S3ObjectSummary os : objects) {
    System.out.println("* " + os.getKey());
}
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/ListObjects.java) on GitHub\.

## Download an Object<a name="download-object"></a>

Use the AmazonS3 client’s `getObject` method, passing it the name of a bucket and object to download\. If successful, the method returns an [S3Object](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/model/S3Object.html)\. *The specified bucket and object key must exist, or an error will result*\.

You can get the object’s contents by calling `getObjectContent` on the `S3Object`\. This returns an [S3ObjectInputStream](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/model/S3ObjectInputStream.html) that behaves as a standard Java `InputStream` object\.

The following example downloads an object from S3 and saves its contents to a file \(using the same name as the object’s key\)\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import java.io.File;
```

 **Code** 

```
System.out.format("Downloading %s from S3 bucket %s...\n", key_name, bucket_name);
final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
try {
    S3Object o = s3.getObject(bucket_name, key_name);
    S3ObjectInputStream s3is = o.getObjectContent();
    FileOutputStream fos = new FileOutputStream(new File(key_name));
    byte[] read_buf = new byte[1024];
    int read_len = 0;
    while ((read_len = s3is.read(read_buf)) > 0) {
        fos.write(read_buf, 0, read_len);
    }
    s3is.close();
    fos.close();
} catch (AmazonServiceException e) {
    System.err.println(e.getErrorMessage());
    System.exit(1);
} catch (FileNotFoundException e) {
    System.err.println(e.getMessage());
    System.exit(1);
} catch (IOException e) {
    System.err.println(e.getMessage());
    System.exit(1);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/GetObject.java) on GitHub\.

## Copy, Move, or Rename Objects<a name="copy-object"></a>

You can copy an object from one bucket to another by using the AmazonS3 client’s `copyObject` method\. It takes the name of the bucket to copy from, the object to copy, and the destination bucket name\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
```

 **Code** 

```
    s3.copyObject(from_bucket, object_key, to_bucket, object_key);
} catch (AmazonServiceException e) {
    System.err.println(e.getErrorMessage());
    System.exit(1);
}
System.out.println("Done!");
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/CopyObject.java) on GitHub\.

**Note**  
You can use `copyObject` with [deleteObject](#delete-object) to **move** or **rename** an object, by first copying the object to a new name \(you can use the same bucket as both the source and destination\) and then deleting the object from its old location\.

## Delete an Object<a name="delete-object"></a>

Use the AmazonS3 client’s `deleteObject` method, passing it the name of a bucket and object to delete\. *The specified bucket and object key must exist, or an error will result*\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
```

 **Code** 

```
final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
try {
    s3.deleteObject(bucket_name, object_key);
} catch (AmazonServiceException e) {
    System.err.println(e.getErrorMessage());
    System.exit(1);
}
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/DeleteObject.java) on GitHub\.

## Delete Multiple Objects at Once<a name="delete-objects"></a>

Using the AmazonS3 client’s `deleteObjects` method, you can delete multiple objects from the same bucket by passing their names to the link:sdk\-for\-java/v1/reference/com/amazonaws/services/s3/model/DeleteObjectsRequest\.html`` method\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
```

 **Code** 

```
final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
try {
    DeleteObjectsRequest dor = new DeleteObjectsRequest(bucket_name)
            .withKeys(object_keys);
    s3.deleteObjects(dor);
} catch (AmazonServiceException e) {
    System.err.println(e.getErrorMessage());
    System.exit(1);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/DeleteObjects.java) on GitHub\.