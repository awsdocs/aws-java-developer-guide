--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Managing Amazon S3 Access Permissions for Buckets and Objects<a name="examples-s3-access-permissions"></a>

You can use access control lists \(ACLs\) for Amazon S3 buckets and objects for fine\-grained control over your Amazon S3 resources\.

**Note**  
These code examples assume that you understand the material in [Using the AWS SDK for Java](basics.md) and have configured default AWS credentials using the information in [Set up AWS Credentials and Region for Development](setup-credentials.md)\.

## Get the Access Control List for a Bucket<a name="get-the-access-control-list-for-a-bucket"></a>

To get the current ACL for a bucket, call the AmazonS3’s `getBucketAcl` method, passing it the *bucket name* to query\. This method returns an [AccessControlList](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/model/AccessControlList.html) object\. To get each access grant in the list, call its `getGrantsAsList` method, which will return a standard Java list of [Grant](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/model/Grant.html) objects\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.Grant;
```

 **Code** 

```
final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
try {
    AccessControlList acl = s3.getBucketAcl(bucket_name);
    List<Grant> grants = acl.getGrantsAsList();
    for (Grant grant : grants) {
        System.out.format("  %s: %s\n", grant.getGrantee().getIdentifier(),
                grant.getPermission().toString());
    }
} catch (AmazonServiceException e) {
    System.err.println(e.getErrorMessage());
    System.exit(1);
}
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/GetAcl.java) on GitHub\.

## Set the Access Control List for a Bucket<a name="set-the-access-control-list-for-a-bucket"></a>

To add or modify permissions to an ACL for a bucket, call the AmazonS3’s `setBucketAcl` method\. It takes an [AccessControlList](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/model/AccessControlList.html) object that contains a list of grantees and access levels to set\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.EmailAddressGrantee;
```

 **Code** 

```
final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
try {
    // get the current ACL
    AccessControlList acl = s3.getBucketAcl(bucket_name);
    // set access for the grantee
    EmailAddressGrantee grantee = new EmailAddressGrantee(email);
    Permission permission = Permission.valueOf(access);
    acl.grantPermission(grantee, permission);
    s3.setBucketAcl(bucket_name, acl);
} catch (AmazonServiceException e) {
    System.err.println(e.getErrorMessage());
    System.exit(1);
}
```

**Note**  
You can provide the grantee’s unique identifier directly using the [Grantee](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/model/Grantee.html) class, or use the [EmailAddressGrantee](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/model/EmailAddressGrantee.html) class to set the grantee by email, as we’ve done here\.

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/SetAcl.java) on GitHub\.

## Get the Access Control List for an Object<a name="get-the-access-control-list-for-an-object"></a>

To get the current ACL for an object, call the AmazonS3’s `getObjectAcl` method, passing it the *bucket name* and *object name* to query\. Like `getBucketAcl`, this method returns an [AccessControlList](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/model/AccessControlList.html) object that you can use to examine each [Grant](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/model/Grant.html)\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.Grant;
```

 **Code** 

```
try {
    AccessControlList acl = s3.getObjectAcl(bucket_name, object_key);
    List<Grant> grants = acl.getGrantsAsList();
    for (Grant grant : grants) {
        System.out.format("  %s: %s\n", grant.getGrantee().getIdentifier(),
                grant.getPermission().toString());
    }
} catch (AmazonServiceException e) {
    System.err.println(e.getErrorMessage());
    System.exit(1);
}
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/GetAcl.java) on GitHub\.

## Set the Access Control List for an Object<a name="set-the-access-control-list-for-an-object"></a>

To add or modify permissions to an ACL for an object, call the AmazonS3’s `setObjectAcl` method\. It takes an [AccessControlList](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/model/AccessControlList.html) object that contains a list of grantees and access levels to set\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.EmailAddressGrantee;
```

 **Code** 

```
    try {
        // get the current ACL
        AccessControlList acl = s3.getObjectAcl(bucket_name, object_key);
        // set access for the grantee
        EmailAddressGrantee grantee = new EmailAddressGrantee(email);
        Permission permission = Permission.valueOf(access);
        acl.grantPermission(grantee, permission);
        s3.setObjectAcl(bucket_name, object_key, acl);
    } catch (AmazonServiceException e) {
        System.err.println(e.getErrorMessage());
        System.exit(1);
    }
}
```

**Note**  
You can provide the grantee’s unique identifier directly using the [Grantee](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/model/Grantee.html) class, or use the [EmailAddressGrantee](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/model/EmailAddressGrantee.html) class to set the grantee by email, as we’ve done here\.

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/SetAcl.java) on GitHub\.

## More Information<a name="more-information"></a>
+  [GET Bucket acl](http://docs.aws.amazon.com/AmazonS3/latest/API/RESTBucketGETacl.html) in the Amazon S3 API Reference
+  [PUT Bucket acl](http://docs.aws.amazon.com/AmazonS3/latest/API/RESTBucketPUTacl.html) in the Amazon S3 API Reference
+  [GET Object acl](http://docs.aws.amazon.com/AmazonS3/latest/API/RESTObjectGETacl.html) in the Amazon S3 API Reference
+  [PUT Object acl](http://docs.aws.amazon.com/AmazonS3/latest/API/RESTObjectPUTacl.html) in the Amazon S3 API Reference