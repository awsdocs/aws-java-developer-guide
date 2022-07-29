--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Managing Access to Amazon S3 Buckets Using Bucket Policies<a name="examples-s3-bucket-policies"></a>

You can set, get, or delete a *bucket policy* to manage access to your Amazon S3 buckets\.

## Set a Bucket Policy<a name="set-s3-bucket-policy"></a>

You can set the bucket policy for a particular S3 bucket by:
+ Calling the AmazonS3 client’s `setBucketPolicy` and providing it with a [SetBucketPolicyRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/model/SetBucketPolicyRequest.html) 
+ Setting the policy directly by using the `setBucketPolicy` overload that takes a bucket name and policy text \(in JSON format\)

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Principal;
```

 **Code** 

```
    s3.setBucketPolicy(bucket_name, policy_text);
} catch (AmazonServiceException e) {
    System.err.println(e.getErrorMessage());
    System.exit(1);
}
```

### Use the Policy Class to Generate or Validate a Policy<a name="use-s3-bucket-policy-class"></a>

When providing a bucket policy to `setBucketPolicy`, you can do the following:
+ Specify the policy directly as a string of JSON\-formatted text
+ Build the policy using the [Policy](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/auth/policy/Policy.html) class

By using the `Policy` class, you don’t have to be concerned about correctly formatting your text string\. To get the JSON policy text from the `Policy` class, use its `toJson` method\.

 **Imports** 

```
import com.amazonaws.auth.policy.Resource;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.auth.policy.actions.S3Actions;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
```

 **Code** 

```
        new Statement(Statement.Effect.Allow)
                .withPrincipals(Principal.AllUsers)
                .withActions(S3Actions.GetObject)
                .withResources(new Resource(
                        "{region-arn}s3:::" + bucket_name + "/*")));
return bucket_policy.toJson();
```

The `Policy` class also provides a `fromJson` method that can attempt to build a policy using a passed\-in JSON string\. The method validates it to ensure that the text can be transformed into a valid policy structure, and will fail with an `IllegalArgumentException` if the policy text is invalid\.

```
Policy bucket_policy = null;
try {
    bucket_policy = Policy.fromJson(file_text.toString());
} catch (IllegalArgumentException e) {
    System.out.format("Invalid policy text in file: \"%s\"",
            policy_file);
    System.out.println(e.getMessage());
}
```

You can use this technique to prevalidate a policy that you read in from a file or other means\.

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/SetBucketPolicy.java) on GitHub\.

## Get a Bucket Policy<a name="get-s3-bucket-policy"></a>

To retrieve the policy for an Amazon S3 bucket, call the AmazonS3 client’s `getBucketPolicy` method, passing it the name of the bucket to get the policy from\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
```

 **Code** 

```
  try {
      BucketPolicy bucket_policy = s3.getBucketPolicy(bucket_name);
      policy_text = bucket_policy.getPolicyText();
  } catch (AmazonServiceException e) {
      System.err.println(e.getErrorMessage());
      System.exit(1);
  }
```

If the named bucket doesn’t exist, if you don’t have access to it, or if it has no bucket policy, an `AmazonServiceException` is thrown\.

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/GetBucketPolicy.java) on GitHub\.

## Delete a Bucket Policy<a name="delete-s3-bucket-policy"></a>

To delete a bucket policy, call the AmazonS3 client’s `deleteBucketPolicy`, providing it with the bucket name\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
```

 **Code** 

```
  try {
      s3.deleteBucketPolicy(bucket_name);
  } catch (AmazonServiceException e) {
      System.err.println(e.getErrorMessage());
      System.exit(1);
  }
```

This method succeeds even if the bucket doesn’t already have a policy\. If you specify a bucket name that doesn’t exist or if you don’t have access to the bucket, an `AmazonServiceException` is thrown\.

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/DeleteBucketPolicy.java) on GitHub\.

## More Info<a name="more-info"></a>
+  [Access Policy Language Overview](http://docs.aws.amazon.com/AmazonS3/latest/dev/access-policy-language-overview.html) in the Amazon Simple Storage Service User Guide
+  [Bucket Policy Examples](http://docs.aws.amazon.com/AmazonS3/latest/dev/example-bucket-policies.html) in the Amazon Simple Storage Service User Guide