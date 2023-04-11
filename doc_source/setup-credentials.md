# Set up AWS temporary credentials and AWS Region for development<a name="setup-credentials"></a>

To connect to any of the supported services with the AWS SDK for Java, you must provide AWS temporary credentials\. The AWS SDKs and CLIs use *provider chains* to look for AWS temporary credentials in a number of different places, including system/user environment variables and local AWS configuration files\.

This topic provides basic information about setting up your AWS temporary credentials for local application development using the AWS SDK for Java\. If you need to set up credentials for use within an EC2 instance or if you’re using the Eclipse IDE for development, refer to the following topics instead:
+ When using an EC2 instance, create an IAM role and then give your EC2 instance access to that role as shown in [Using IAM Roles to Grant Access to AWS Resources on Amazon EC2](java-dg-roles.md)\.
+ Set up AWS credentials within Eclipse using the [AWS Toolkit for Eclipse](http://aws.amazon.com/eclipse/)\. See [Set up AWS Credentials](https://docs.aws.amazon.com/toolkit-for-eclipse/v1/user-guide/setup-credentials.html) in the [AWS Toolkit for Eclipse User Guide](https://docs.aws.amazon.com/toolkit-for-eclipse/v1/user-guide/) for more information\.

## Configure temporary credentials<a name="setup-credentials-setting"></a>

You can configure temporary credentials for the AWS SDK for Java in a number of ways, but here are the recommended approaches:
+ Set tempoaray credentials in the AWS credentials profile file on your local system, located at:
  +  `~/.aws/credentials` on Linux, macOS, or Unix
  +  `C:\Users\USERNAME\.aws\credentials` on Windows

  See the [Set up temporary credentials for the SDK](signup-create-iam-user.md#setup-temp-creds) in this guide for instructions on how to get your temporary credentials\.
+ Set the `AWS_ACCESS_KEY_ID`,`AWS_SECRET_ACCESS_KEY`, and `AWS_SESSION_TOKEN` environment variables\.

  To set these variables on Linux, macOS, or Unix, use :

  ```
  export AWS_ACCESS_KEY_ID=your_access_key_id
  export AWS_SECRET_ACCESS_KEY=your_secret_access_key
  export AWS_SESSION_TOKEN=your_session_token
  ```

  To set these variables on Windows, use:

  ```
  set AWS_ACCESS_KEY_ID=your_access_key_id
  set AWS_SECRET_ACCESS_KEY=your_secret_access_key
  set AWS_SESSION_TOKEN=your_session_token
  ```
+ For an EC2 instance, specify an IAM role and then give your EC2 instance access to that role\. See [IAM Roles for Amazon EC2](http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/iam-roles-for-amazon-ec2.html) in the Amazon EC2 User Guide for Linux Instances for a detailed discussion about how this works\.

Once you have set your AWS temporary credentials using one of these methods, they will be loaded automatically by the AWS SDK for Java by using the default credential provider chain\. For further information about working with AWS credentials in your Java applications, see [Working with AWS Credentials](credentials.md)\.

## Refreshing IMDS credentials<a name="refresh-credentials"></a>

The AWS SDK for Java supports opt\-in refreshing IMDS credentials in the background every 1 minute, regardless of the credential expiration time\. This allows you to refresh credentials more frequently and reduces the chance that not reaching IMDS impacts the perceived AWS availability\.

```
 1. // Refresh credentials using a background thread, automatically every minute. This will log an error if IMDS is down during
 2. // a refresh, but your service calls will continue using the cached credentials until the credentials are refreshed
 3. // again one minute later.
 4.
 5. InstanceProfileCredentialsProvider credentials =
 6.     InstanceProfileCredentialsProvider.createAsyncRefreshingProvider(true);
 7.
 8. AmazonS3Client.builder()
 9.              .withCredentials(credentials)
 10.              .build();
 11.
 12. // This is new: When you are done with the credentials provider, you must close it to release the background thread.
 13. credentials.close();
```

## Set the AWS Region<a name="setup-credentials-setting-region"></a>

You should set a default AWS Region that will be used for accessing AWS services with the AWS SDK for Java\. For the best network performance, choose a region that’s geographically close to you \(or to your customers\)\. For a list of regions for each service, see [Regions and Endpoints](https://docs.aws.amazon.com/general/latest/gr/rande.html) in the Amazon Web Services General Reference\.

**Note**  
If you *don’t* select a region, then us\-east\-1 will be used by default\.

You can use similar techniques to setting credentials to set your default AWS region:
+ Set the AWS Region in the AWS config file on your local system, located at:
  + \~/\.aws/config on Linux, macOS, or Unix
  + C:\\Users\\USERNAME\\\.aws\\config on Windows

  This file should contain lines in the following format:

  \+

  ```
  [default]
  region = your_aws_region
  ```

  \+

  Substitute your desired AWS Region \(for example, "us\-east\-1"\) for *your\_aws\_region*\.
+ Set the `AWS_REGION` environment variable\.

  On Linux, macOS, or Unix, use ** `` **:

  ```
  export AWS_REGION=your_aws_region
  ```

  On Windows, use ** `` **:

  ```
  set AWS_REGION=your_aws_region
  ```

  Where *your\_aws\_region* is the desired AWS Region name\.