--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Working with AWS Credentials<a name="credentials"></a>

To make requests to Amazon Web Services, you must supply AWS credentials to the AWS SDK for Java\. You can do this in the following ways:
+ Use the default credential provider chain *\(recommended\)*\.
+ Use a specific credential provider or provider chain \(or create your own\)\.
+ Supply the credentials yourself\. These can be root account credentials, IAM credentials, or temporary credentials retrieved from AWS STS\.

**Important**  
For security, we *strongly recommend* that you *use IAM users* instead of the root account for AWS access\. For more information, see [IAM Best Practices](https://docs.aws.amazon.com/IAM/latest/UserGuide/best-practices.html) in the IAM User Guide\.

## Using the Default Credential Provider Chain<a name="credentials-default"></a>

When you initialize a new service client without supplying any arguments, the AWS SDK for Java attempts to find AWS credentials by using the *default credential provider chain* implemented by the [DefaultAWSCredentialsProviderChain](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/auth/DefaultAWSCredentialsProviderChain.html) class\. The default credential provider chain looks for credentials in this order:

1.  **Environment variables**\-`AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY`\. The AWS SDK for Java uses the [EnvironmentVariableCredentialsProvider](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/auth/EnvironmentVariableCredentialsProvider.html) class to load these credentials\.

1.  **Java system properties**\-`aws.accessKeyId` and `aws.secretKey`\. The AWS SDK for Java uses the [SystemPropertiesCredentialsProvider](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/auth/SystemPropertiesCredentialsProvider.html) to load these credentials\.

1.  **Web Identity Token credentials** from the environment or container\.

1.  **The default credential profiles file**\- typically located at `~/.aws/credentials` \(location can vary per platform\), and shared by many of the AWS SDKs and by the AWS CLI\. The AWS SDK for Java uses the [ProfileCredentialsProvider](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/auth/profile/ProfileCredentialsProvider.html) to load these credentials\.

   You can create a credentials file by using the `aws configure` command provided by the AWS CLI, or you can create it by editing the file with a text editor\. For information about the credentials file format, see [AWS Credentials File Format](#credentials-file-format)\.

1.  **Amazon ECS container credentials**\- loaded from the Amazon ECS if the environment variable `AWS_CONTAINER_CREDENTIALS_RELATIVE_URI` is set\. The AWS SDK for Java uses the [ContainerCredentialsProvider](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/auth/ContainerCredentialsProvider.html) to load these credentials\. You can specify the IP address for this value\.

1.  **Instance profile credentials**\- used on EC2 instances, and delivered through the Amazon EC2 metadata service\. The AWS SDK for Java uses the [InstanceProfileCredentialsProvider](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/auth/InstanceProfileCredentialsProvider.html) to load these credentials\. You can specify the IP address for this value\.
**Note**  
Instance profile credentials are used only if `AWS_CONTAINER_CREDENTIALS_RELATIVE_URI` is not set\. See [EC2ContainerCredentialsProviderWrapper](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/auth/EC2ContainerCredentialsProviderWrapper.html) for more information\.

### Setting Credentials<a name="setting-credentials"></a>

To be able to use AWS credentials, they must be set in *at least one* of the preceding locations\. For information about setting credentials, see the following topics:
+ To specify credentials in the *environment* or in the default *credential profiles file*, see [Set up AWS Credentials and Region for Development](setup-credentials.md)\.
+ To set Java *system properties*, see the [System Properties](http://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html) tutorial on the official *Java Tutorials* website\.
+ To set up and use *instance profile credentials* with your EC2 instances, see [Using IAM Roles to Grant Access to AWS Resources on Amazon EC2](java-dg-roles.md)\.

### Setting an Alternate Credentials Profile<a name="setting-an-alternate-credentials-profile"></a>

The AWS SDK for Java uses the *default* profile by default, but there are ways to customize which profile is sourced from the credentials file\.

You can use the AWS Profile environment variable to change the profile loaded by the SDK\.

For example, on Linux, macOS, or Unix you would run the following command to change the profile to *myProfile*\.

```
export AWS_PROFILE="myProfile"
```

On Windows you would use the following\.

```
set AWS_PROFILE="myProfile"
```

Setting the `AWS_PROFILE` environment variable affects credential loading for all officially supported AWS SDKs and Tools \(including the AWS CLI and the AWS Tools for Windows PowerShell\)\. To change only the profile for a Java application, you can use the system property `aws.profile` instead\.

**Note**  
The environment variable takes precedence over the system property\.

### Setting an Alternate Credentials File Location<a name="setting-an-alternate-credentials-file-location"></a>

The AWS SDK for Java loads AWS credentials automatically from the default credentials file location\. However, you can also specify the location by setting the `AWS_CREDENTIAL_PROFILES_FILE` environment variable with the full path to the credentials file\.

You can use this feature to temporarily change the location where the AWS SDK for Java looks for your credentials file \(for example, by setting this variable with the command line\)\. Or you can set the environment variable in your user or system environment to change it for the user or systemwide\.

#### To override the default credentials file location<a name="w3aab9c15b9c11b7b1"></a>
+ Set the `AWS_CREDENTIAL_PROFILES_FILE` environment variable to the location of your AWS credentials file\.
  + On Linux, macOS, or Unix, use ** `` **:

    ```
    export AWS_CREDENTIAL_PROFILES_FILE=path/to/credentials_file
    ```
  + On Windows, use ** `` **:

    ```
    set AWS_CREDENTIAL_PROFILES_FILE=path/to/credentials_file
    ```

### Credentials File Format<a name="credentials-file-format"></a>

When you use the `aws configure` command to create an AWS credentials file, the command creates a file with the following format\.

```
[default]
aws_access_key_id={YOUR_ACCESS_KEY_ID}
aws_secret_access_key={YOUR_SECRET_ACCESS_KEY}

[profile2]
aws_access_key_id={YOUR_ACCESS_KEY_ID}
aws_secret_access_key={YOUR_SECRET_ACCESS_KEY}
```

The profile name is specified in square brackets \(for example, `[default]`\), followed by the configurable fields in that profile as key\-value pairs\. You can have multiple profiles in your credentials file, which can be added or edited using `aws configure --profile PROFILE_NAME ` to select the profile to configure\.

You can specify additional fields, such as `aws_session_token`, `metadata_service_timeout`, and `metadata_service_num_attempts`\. These are not configurable with the CLI—​you must edit the file by hand if you want to use them\. For more information about the configuration file and its available fields, see [Configuring the AWS Command Line Interface](http://docs.aws.amazon.com/cli/latest/userguide/cli-chap-getting-started.html) in the AWS Command Line Interface User Guide\.

### Loading Credentials<a name="loading-credentials"></a>

After you set credentials, you can load them by using the default credential provider chain\.

To do this, you instantiate an AWS service client without explicitly providing credentials to the builder, as follows\.

```
AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                       .withRegion(Regions.US_WEST_2)
                       .build();
```

## Specifying a Credential Provider or Provider Chain<a name="credentials-specify-provider"></a>

You can specify a credential provider that is different from the *default* credential provider chain by using the client builder\.

You provide an instance of a credentials provider or provider chain to a client builder that takes an [AWSCredentialsProvider](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/auth/AWSCredentialsProvider.html) interface as input\. The following example shows how to use *environment* credentials specifically\.

```
AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                       .withCredentials(new EnvironmentVariableCredentialsProvider())
                       .build();
```

For the full list of AWS SDK for Java\-supplied credential providers and provider chains, see **All Known Implementing Classes** in [AWSCredentialsProvider](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/auth/AWSCredentialsProvider.html)\.

**Note**  
You can use this technique to supply credential providers or provider chains that you create by using your own credential provider that implements the `AWSCredentialsProvider` interface, or by subclassing the [AWSCredentialsProviderChain](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/auth/AWSCredentialsProviderChain.html) class\.

## Explicitly Specifying Credentials<a name="credentials-explicit"></a>

If the default credential chain or a specific or custom provider or provider chain doesn’t work for your code, you can set credentials that you supply explicitly\. If you’ve retrieved temporary credentials using AWS STS, use this method to specify the credentials for AWS access\.

1. Instantiate a class that provides the [AWSCredentials](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/auth/AWSCredentials.html) interface, such as [BasicAWSCredentials](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/auth/BasicAWSCredentials.html), and supply it with the AWS access key and secret key you will use for the connection\.

1. Create an [AWSStaticCredentialsProvider](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/auth/AWSStaticCredentialsProvider.html) with the `AWSCredentials` object\.

1. Configure the client builder with the `AWSStaticCredentialsProvider` and build the client\.

The following is an example\.

```
BasicAWSCredentials awsCreds = new BasicAWSCredentials("access_key_id", "secret_key_id");
AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                        .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                        .build();
```

When using [temporary credentials obtained from STS](prog-services-sts.md), create a [BasicSessionCredentials](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/auth/BasicSessionCredentials.html) object, passing it the STS\-supplied credentials and session token\.

```
BasicSessionCredentials sessionCredentials = new BasicSessionCredentials(
   session_creds.getAccessKeyId(),
   session_creds.getSecretAccessKey(),
   session_creds.getSessionToken());

AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                        .withCredentials(new AWSStaticCredentialsProvider(sessionCredentials))
                        .build();
```

## More Info<a name="more-info"></a>
+  [Sign Up for AWS and Create an IAM User](signup-create-iam-user.md) 
+  [Set up AWS Credentials and Region for Development](setup-credentials.md) 
+  [Using IAM Roles to Grant Access to AWS Resources on Amazon EC2](java-dg-roles.md) 