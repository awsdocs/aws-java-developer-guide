--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Getting Temporary Credentials with AWS STS<a name="prog-services-sts"></a>

You can use AWS Security Token Service \([AWS STS](http://aws.amazon.com/iam/)\) to get temporary, limited\-privilege credentials that can be used to access AWS services\.

There are three steps involved in using AWS STS:

1. Activate a region \(optional\)\.

1. Retrieve temporary security credentials from AWS STS\.

1. Use the credentials to access AWS resources\.

**Note**  
Activating a region is *optional*; by default, temporary security credentials are obtained from the global endpoint *sts\.amazonaws\.com*\. However, to reduce latency and to enable you to build redundancy into your requests by using additional endpoints if an AWS STS request to the first endpoint fails, you can activate regions that are geographically closer to your services or applications that use the credentials\.

## \(Optional\) Activate and use an STS region<a name="optional-activate-and-use-an-sts-region"></a>

To activate a region for use with AWS STS, use the AWS Management Console to select and activate the region\.

1. Sign in as an IAM user with permissions to perform IAM administration tasks `"iam:*"` for the account for which you want to activate AWS STS in a new region\.

1. Open the IAM console and in the navigation pane click **Account Settings**\.

1. Expand the **STS Regions** list, find the region that you want to use, and then click **Activate**\.

After this, you can direct calls to the STS endpoint that is associated with that region\.

**Note**  
For more information about activating STS regions and for a list of the available AWS STS endpoints, see [Activating and Deactivating AWS STS in an AWS Region](http://docs.aws.amazon.com/IAM/latest/UserGuide/id_credentials_temp_enable-regions.html) in the IAM User Guide\.

## Retrieve temporary security credentials from STS<a name="retrieving-an-sts-token"></a>

1. Create an [AWSSecurityTokenServiceClient](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/securitytoken/AWSSecurityTokenServiceClient.html) object:

   ```
   AWSSecurityTokenService sts_client = new AWSSecurityTokenServiceClientBuilder().standard().withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("sts-endpoint.amazonaws.com", "signing-region")).build()
   ```

   When creating the client with no arguments \(`AWSSecurityTokenService sts_client = new AWSSecurityTokenServiceClientBuilder().standard().build();`\), the default credential provider chain is used to retrieve credentials\. You can provide a specific credential provider if you want\. For more information, see [Providing AWS Credentials in the AWS SDK for Java](credentials.md)\.

1. Create a [GetSessionTokenRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/securitytoken/model/GetSessionTokenRequest.html) object, and optionally set the duration in seconds for which the temporary credentials are valid:

**Example**  
\+  

```
GetSessionTokenRequest session_token_request = new GetSessionTokenRequest();
session_token_request.setDurationSeconds(7200); // optional.
```
\+  
The duration of temporary credentials can range from 900 seconds \(15 minutes\) to 129600 seconds \(36 hours\) for IAM users\. If a duration isn’t specified, then 43200 seconds \(12 hours\) is used by default\.  
\+  
For a root AWS account, the valid range of temporary credentials is from 900 to 3600 seconds \(1 hour\), with a default value of 3600 seconds if no duration is specified\.  
\+ IMPORTANT: It is *strongly recommended*, from a security standpoint, that you *use IAM users* instead of the root account for AWS access\. For more information, see IAM Best Practices in the IAM User Guide\. \. Call [getSessionToken](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/securitytoken/AWSSecurityTokenService.html#getSessionToken-com.amazonaws.services.securitytoken.model.GetSessionTokenRequest-) on the STS client to get a session token, using the `GetSessionTokenRequest` object:  
\+  

```
GetSessionTokenResult session_token_result =
    sts_client.getSessionToken(session_token_request);
```

1. Get session credentials using the result of the call to `getSessionToken`:

   ```
   Credentials session_creds = session_token_result.getCredentials();
   ```
The session credentials provide access only for the duration that was specified by the `GetSessionTokenRequest` object\. Once the credentials expire, you will need to call `getSessionToken` again to obtain a new session token for continued access to AWS\.  
<a name="use-the-token-to-access-aws-resources"></a>== Use the temporary credentials to access Amazon resources  
Once you have temporary security credentials, you can use them to initialize an AWS service client to use its resources, using the technique described in [Explicitly Specifying Credentials](credentials.md#credentials-explicit)\.  
For example, to create an S3 client using temporary service credentials:  

```
BasicSessionCredentials sessionCredentials = new BasicSessionCredentials(
   session_creds.getAccessKeyId(),
   session_creds.getSecretAccessKey(),
   session_creds.getSessionToken());

AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                        .withCredentials(new AWSStaticCredentialsProvider(sessionCredentials))
                        .build();
```
You can now use the `AmazonS3` object to make Amazon S3 requests\.  
<a name="for-more-information"></a>== For more information  
For more information about how to use temporary security credentials to access AWS resources, visit the following sections in the IAM User Guide:  
+  [Requesting Temporary Security Credentials](http://docs.aws.amazon.com/IAM/latest/UserGuide/id_credentials_temp_request.html) 
+  [Controlling Permissions for Temporary Security Credentials](http://docs.aws.amazon.com/IAM/latest/UserGuide/id_credentials_temp_control-access.html) 
+  [Using Temporary Security Credentials to Request Access to AWS Resources](http://docs.aws.amazon.com/IAM/latest/UserGuide/id_credentials_temp_use-resources.html) 
+  [Activating and Deactivating AWS STS in an AWS Region](http://docs.aws.amazon.com/IAM/latest/UserGuide/id_credentials_temp_enable-regions.html) 