# Basic setup to work with AWS services<a name="signup-create-iam-user"></a>

## Overview<a name="signup-create-iam-user-overview"></a>

To successfully develop applications that access AWS services using the AWS SDK for Java, the following conditions are required:
+ You must be able to [sign in to the AWS access portal](#setup-awsaccount) available in the AWS IAM Identity Center \(successor to AWS Single Sign\-On\)\.
+ The [permissions of the IAM role](https://docs.aws.amazon.com/singlesignon/latest/userguide/permissionsetsconcept.html) configured for the SDK must allow access to the AWS services that your application requires\. The permissions associated with the **PowerUserAccess** AWS managed policy are sufficient for most development needs\.
+ A development environment with the following elements:
  + [Shared configuration files](https://docs.aws.amazon.com/sdkref/latest/guide/file-format.html) that are set up in the following way:
    + The `config` file contains a default profile that specifies an AWS Region\.
    + The `credentials` file contains temporary credentials as part of a default profile\.
  + A suitable [installation of Java](#java-dg-java-env)\.
  + A [build automation tool](setup-build-tools.md) such as [Maven](https://maven.apache.org/download.cgi) or [Gradle](https://gradle.org/install/)\.
  + A text editor to work with code\.
  + \(Optional, but recommended\) An IDE \(integrated development environment\) such as [IntelliJ IDEA](https://www.jetbrains.com/idea/download/#section=windows), [Eclipse](https://www.eclipse.org/ide/), or [NetBeans](https://netbeans.org/downloads/)\.

    When you use an IDE, you can also integrate AWS Toolkits to more easily work with AWS services\. The [AWS Toolkit for IntelliJ](https://docs.aws.amazon.com/toolkit-for-jetbrains/latest/userguide/welcome.html) and [AWS Toolkit for Eclipse](https://docs.aws.amazon.com/toolkit-for-eclipse/v1/user-guide/welcome.html) are two toolkits that you can use for Java development\.

**Important**  
The instructions in this setup section assume that you or organization uses IAM Identity Center\. If your organization uses an external identity provider that works independently of IAM Identity Center, find out how you can get temporary credentials for the SDK for Java to use\. Follow [these instructions](#setup-temp-creds) to add temporary credentials to the `~/.aws/credentials` file\.  
If your identity provider adds temporary credentials automatically to the `~/.aws/credentials` file, make sure that the profile name is `[default]` so that you do not need to provide a profile name to the SDK or AWS CLI\.

## Sign\-in ability to the AWS access portal<a name="setup-awsaccount"></a>

The AWS access portal is the web location where you manually sign in to the IAM Identity Center\. The format of the URL is `d-xxxxxxxxxx.awsapps.com/start`or `your_subdomain.awsapps.com/start`\. 

If you are not familiar with the AWS access portal, follow the guidance for account access in [Step 1 of the IAM Identity Center authentication topic](https://docs.aws.amazon.com/sdkref/latest/guide/access-sso.html#idcGettingStarted) in the AWS SDKs and Tools Reference Guide\. Do not follow the Step 2 because the AWS SDK for Java 1\.x does not support automatic token refresh and automatic retrieval of temporary credentials for the SDK that Step 2 describes\. 

## Set up shared configuration files<a name="setup-shared-config-files"></a>

The shared configuration files reside on your development workstation and contain basic settings used by all AWS SDKs and the AWS Command Line Interface \(CLI\)\. The shared configuration files can contain [a number of settings](https://docs.aws.amazon.com/sdkref/latest/guide/settings-reference.html), but these instructions set up the basic elements that are required to work with the SDK\.

### Set up the shared `config` file<a name="setup-shared-config-files-conf"></a>

The following example shows content of a shared `config` file\.

```
[default]
region=us-east-1
output=json
```

For development purposes, use the AWS Region [nearest](https://aws.amazon.com/about-aws/global-infrastructure/regions_az/) to where you plan to run your code\. For a [listing of region codes](https://docs.aws.amazon.com/general/latest/gr/rande.html#region-names-codes) to use in the `config` file see the Amazon Web Services General Reference guide\. The `json` setting for the output format is one of [several possible values](https://docs.aws.amazon.com/cli/latest/userguide/cli-usage-output-format.html)\.

Follow the guidance [in this section](https://docs.aws.amazon.com/sdkref/latest/guide/file-location.html) to create the `config` file\.

### Set up temporary credentials for the SDK<a name="setup-temp-creds"></a>

After you have access to an AWS account and IAM role through the AWS access portal, configure your development environment with temporary credentials for the SDK to access\.

**Steps to set up a local `credentials` file with temporary credentials**

1. [Create a shared `credentials` file](https://docs.aws.amazon.com/sdkref/latest/guide/file-location.html)\.

1. In the `credentials` file, paste the following placeholder text until you paste in working temporary credentials\.

   ```
   [default]
   aws_access_key_id=<value from AWS access portal>
   aws_secret_access_key=<value from AWS access portal>
   aws_session_token=<value from AWS access portal>
   ```

1. Save the file\. The file `~/.aws/credentials` should now exist on your local development system\. This file contains the [\[default\] profile](https://docs.aws.amazon.com/sdkref/latest/guide/file-format.html#file-format-profile) that the SDK for Java uses if a specific named profile is not specified\. 

1. [Sign in to the AWS access portal](https://docs.aws.amazon.com/singlesignon/latest/userguide/howtosignin.html)\.

1. Follow [these instructions](https://docs.aws.amazon.com/singlesignon/latest/userguide/howtogetcredentials.html) to copy IAM role credentials from the AWS access portal
**Note**  
Although the title of the page reads 'Getting IAM role credentials for CLI access', the instructions apply to the SDK for Java as well as the to AWS CLI\. Note that you are not required to have the AWS CLI installed in order to get temporary credentials\.

    

   1. For step 2 in the instructions, choose the AWS account and IAM role name that grants access for your development needs\. This role typically has a name such as **PowerUser** or **Developer**\.

   1. For step 4, select the option that reads **Manually add a profile to your AWS credentials file \(Short\-term credentials\)** and copy the contents

1. Paste the copied credentials into your local `credentials` file and remove any profile name that was pasted\. Your file should resemble the following:

   ```
   [default]
   aws_access_key_id=AKIAIOSFODNN7EXAMPLE
   aws_secret_access_key=wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY
   aws_session_token=IQoJb3JpZ2luX2IQoJb3JpZ2luX2IQoJb3JpZ2luX2IQoJb3JpZ2luX2IQoJb3JpZVERYLONGSTRINGEXAMPLE
   ```

1. Save the `credentials` file

The SDK for Java will access these temporary credentials when it create a service client and use them for each request\. The settings for the IAM role chosen in step 5a determine [how long the temporary credentials are valid](https://docs.aws.amazon.com/singlesignon/latest/userguide/howtosessionduration.html)\. The maximum duration is twelve hours\.

After the temporary credentials expire, repeat steps 4 through 7\.

## Install a Java Development Environment<a name="java-dg-java-env"></a>

The AWS SDK for Java requires J2SE Development Kit *6\.0 or later*\. You can download the latest Java software from [http://www\.oracle\.com/technetwork/java/javase/downloads/](http://www.oracle.com/technetwork/java/javase/downloads/)\.

**Important**  
Java version 1\.6 \(JS2E 6\.0\) did not have built\-in support for SHA256\-signed SSL certificates, which are required for all HTTPS connections with AWS after September 30, 2015\.  
Java versions 1\.7 or newer are packaged with updated certificates and are unaffected by this issue\.

### Choosing a JVM<a name="choosing-a-jvm"></a>

For the best performance of your server\-based applications with the AWS SDK for Java, we recommend that you use the *64\-bit version* of the Java Virtual Machine \(JVM\)\. This JVM runs only in server mode, even if you specify the `-Client` option at run time\.

Using the 32\-bit version of the JVM with the `-Server` option at run time should provide comparable performance to the 64\-bit JVM\.