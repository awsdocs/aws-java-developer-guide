--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Code Samples included with the SDK<a name="java-dg-samples"></a>

The AWS SDK for Java comes packaged with code samples that demonstrate many of the features of the SDK in buildable, runnable programs\. You can study or modify these to implement your own AWS solutions using the AWS SDK for Java\.

## How to Get the Samples<a name="how-to-get-the-samples"></a>

The AWS SDK for Java code samples are provided in the *samples* directory of the SDK\. If you downloaded and installed the SDK using the information in [Set up the AWS SDK for Java](setup-install.md), you already have the samples on your system\.

You can also view the latest samples on the AWS SDK for Java GitHub repository, in the [src/samples](https://github.com/aws/aws-sdk-java/tree/master/src/samples) directory\.

## Building and Running the Samples Using the Command Line<a name="samples-cmdline"></a>

The samples include [Ant](http://ant.apache.org/) build scripts so that you can easily build and run them from the command line\. Each sample also contains a README file in HTML format that contains information specific to each sample\.

**Note**  
If you’re browsing the sample code on GitHub, click the **Raw** button in the source code display when viewing the sample’s README\.html file\. In raw mode, the HTML will render as intended in your browser\.

### Prerequisites<a name="prerequisitessamples"></a>

Before running any of the AWS SDK for Java samples, you need to set your AWS credentials in the environment or with the AWS CLI, as specified in [Set up AWS Credentials and Region for Development](setup-credentials.md)\. The samples use the default credential provider chain whenever possible\. So by setting your credentials in this way, you can avoid the risky practice of inserting your AWS credentials in files within the source code directory \(where they may inadvertently be checked in and shared publicly\)\.

### Running the Samples<a name="running-the-samples"></a>

1. Change to the directory containing the sample’s code\. For example, if you’re in the root directory of the AWS SDK download and want to run the `AwsConsoleApp` sample, you would type:

   ```
   cd samples/AwsConsoleApp
   ```

1. Build and run the sample with Ant\. The default build target performs both actions, so you can just enter:

   ```
   ant
   ```

The sample prints information to standard output—​for example:

```
===========================================

Welcome to the {AWS} Java SDK!

===========================================
You have access to 4 Availability Zones.

You have 0 {EC2} instance(s) running.

You have 13 Amazon SimpleDB domain(s) containing a total of 62 items.

You have 23 {S3} bucket(s), containing 44 objects with a total size of 154767691 bytes.
```

## Building and Running the Samples Using the Eclipse IDE<a name="building-and-running-the-samples-using-the-eclipse-ide"></a>

If you use the AWS Toolkit for Eclipse, you can also start a new project in Eclipse based on the AWS SDK for Java or add the SDK to an existing Java project\.

### Prerequisites<a name="id1samples"></a>

After installing the AWS Toolkit for Eclipse, we recommend configuring the Toolkit with your security credentials\. You can do this anytime by choosing **Preferences** from the **Window** menu in Eclipse, and then choosing the ** AWS Toolkit** section\.

### Running the Samples<a name="id2"></a>

1. Open Eclipse\.

1. Create a new AWS Java project\. In Eclipse, on the **File** menu, choose **New**, and then click **Project**\. The **New Project** wizard opens\.

1. Expand the ** AWS ** category, then choose ** AWS Java Project**\.

1. Choose **Next**\. The project settings page is displayed\.

1. Enter a name in the **Project Name** box\. The AWS SDK for Java Samples group displays the samples available in the SDK, as described previously\.

1. Select the samples you want to include in your project by selecting each check box\.

1. Enter your AWS credentials\. If you’ve already configured the AWS Toolkit for Eclipse with your credentials, this is automatically filled in\.

1. Choose **Finish**\. The project is created and added to the **Project Explorer**\.

1. Choose the sample `.java` file you want to run\. For example, for the Amazon S3 sample, choose `S3Sample.java`\.

1. Choose **Run** from the **Run** menu\.

1. Right\-click the project in **Project Explorer**, point to **Build Path**, and then choose **Add Libraries**\.

1. Choose ** AWS Java SDK**, choose **Next**, and then follow the remaining on\-screen instructions\.