# Configuring an Amazon S3 Bucket as a Website<a name="examples-s3-website-configuration"></a>

You can configure an Amazon S3 bucket to behave as a website\. To do this, you need to set its website configuration\.

**Note**  
These code examples assume that you understand the material in [Using the AWS SDK for Java](basics.md) and have configured default AWS credentials using the information in [Set up AWS Credentials and Region for Development](setup-credentials.md)\.

## Set a Bucket’s Website Configuration<a name="set-a-bucket-s-website-configuration"></a>

To set an Amazon S3 bucket’s website configuration, call the AmazonS3’s `setWebsiteConfiguration` method with the bucket name to set the configuration for, and a [BucketWebsiteConfiguration](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/model/BucketWebsiteConfiguration.html) object containing the bucket’s website configuration\.

Setting an index document is *required*; all other parameters are optional\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.BucketWebsiteConfiguration;
```

 **Code** 

```
    String bucket_name, String index_doc, String error_doc) {
BucketWebsiteConfiguration website_config = null;

if (index_doc == null) {
    website_config = new BucketWebsiteConfiguration();
} else if (error_doc == null) {
    website_config = new BucketWebsiteConfiguration(index_doc);
} else {
    website_config = new BucketWebsiteConfiguration(index_doc, error_doc);
}

final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
try {
    s3.setBucketWebsiteConfiguration(bucket_name, website_config);
} catch (AmazonServiceException e) {
    System.out.format(
            "Failed to set website configuration for bucket '%s'!\n",
            bucket_name);
    System.err.println(e.getErrorMessage());
    System.exit(1);
}
```

**Note**  
Setting a website configuration does not modify the access permissions for your bucket\. To make your files visible on the web, you will also need to set a *bucket policy* that allows public read access to the files in the bucket\. For more information, see [Managing Access to Amazon S3 Buckets Using Bucket Policies](examples-s3-bucket-policies.md)\.

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/SetWebsiteConfiguration.java) on GitHub\.

## Get a Bucket’s Website Configuration<a name="get-a-bucket-s-website-configuration"></a>

To get an Amazon S3 bucket’s website configuration, call the AmazonS3’s `getWebsiteConfiguration` method with the name of the bucket to retrieve the configuration for\.

The configuration will be returned as a [BucketWebsiteConfiguration](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/model/BucketWebsiteConfiguration.html) object\. If there is no website configuration for the bucket, then `null` will be returned\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.BucketWebsiteConfiguration;
```

 **Code** 

```
final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
try {
    BucketWebsiteConfiguration config =
            s3.getBucketWebsiteConfiguration(bucket_name);
    if (config == null) {
        System.out.println("No website configuration found!");
    } else {
        System.out.format("Index document: %s\n",
                config.getIndexDocumentSuffix());
        System.out.format("Error document: %s\n",
                config.getErrorDocument());
    }
} catch (AmazonServiceException e) {
    System.err.println(e.getErrorMessage());
    System.out.println("Failed to get website configuration!");
    System.exit(1);
}
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/GetWebsiteConfiguration.java) on GitHub\.

## Delete a Bucket’s Website Configuration<a name="delete-a-bucket-s-website-configuration"></a>

To delete an Amazon S3 bucket’s website configuration, call the AmazonS3’s `deleteWebsiteConfiguration` method with the name of the bucket to delete the configuration from\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
```

 **Code** 

```
final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
try {
    s3.deleteBucketWebsiteConfiguration(bucket_name);
} catch (AmazonServiceException e) {
    System.err.println(e.getErrorMessage());
    System.out.println("Failed to delete website configuration!");
    System.exit(1);
}
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/DeleteWebsiteConfiguration.java) on GitHub\.

## More Information<a name="more-information"></a>
+  [PUT Bucket website](http://docs.aws.amazon.com/AmazonS3/latest/API/RESTBucketPUTwebsite.html) in the Amazon S3 API Reference
+  [GET Bucket website](http://docs.aws.amazon.com/AmazonS3/latest/API/RESTBucketGETwebsite.html) in the Amazon S3 API Reference
+  [DELETE Bucket website](http://docs.aws.amazon.com/AmazonS3/latest/API/RESTBucketDELETEwebsite.html) in the Amazon S3 API Reference