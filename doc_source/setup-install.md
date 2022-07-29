--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Set up the AWS SDK for Java<a name="setup-install"></a>

Describes how to use the AWS SDK for Java in your project\.

## Prerequisites<a name="prerequisitesinstall"></a>

To use the AWS SDK for Java, you must have:
+ a suitable [Java Development Environment](#java-dg-java-env)\.
+ An AWS account and access keys\. For instructions, see [Sign Up for AWS and Create an IAM User](signup-create-iam-user.md)\.
+  AWS credentials \(access keys\) set in your environment or using the shared \(by the AWS CLI and other SDKs\) credentials file\. For more information, see [Set up AWS Credentials and Region for Development](setup-credentials.md)\.

## Including the SDK in your project<a name="include-sdk"></a>

To include the SDK your project, use one of the following methods depending on your build system or IDE:
+  **Apache Maven**\- If you use [Apache Maven](https://maven.apache.org/), you can specify the entire SDK \(or specific SDK components\) as dependencies in your project\. See [Using the SDK with Apache Maven](setup-project-maven.md) for details about how to set up the SDK when using Maven\.
+  **Gradle**\- If you use [Gradle](https://gradle.com/), you can import the Maven Bill of Materials \(BOM\) in your Gradle project to automatically manage SDK dependencies\. See [Using the SDK with Gradle](setup-project-gradle.md) for more infomation\.
+  **Eclipse IDE**\- If you use the Eclipse IDE, you may want to install and use the [AWS Toolkit for Eclipse](http://aws.amazon.com/eclipse/), which will automatically download, install and update the Java SDK for you\. For more information and setup instructions, see the [AWS Toolkit for Eclipse User Guide](https://docs.aws.amazon.com/toolkit-for-eclipse/v1/user-guide/)\.

If you are using one the above methods \(for example, you are using Maven\), then you do not need to download and install the AWS JAR files \(you can skip the following section\)\. If you intend to build your projects using a different IDE, with Apache Ant or by any other means, then download and extract the SDK as shown in the next section\.

## Downloading and extracting the SDK<a name="download-and-extract-sdk"></a>

We recommend that you use the most recent pre\-built version of the SDK for new projects, which provides you with the latest support for all AWS services\.

**Note**  
For information about how to download and build previous versions of the SDK, see [Installing previous versions of the SDK](#install-prev-sdk)\.

1. Download the SDK from [https://sdk\-for\-java\.amazonwebservices\.com/latest/aws\-java\-sdk\.zip](https://sdk-for-java.amazonwebservices.com/latest/aws-java-sdk.zip)\.

1. After downloading the SDK, extract the contents into a local directory\.

The SDK contains the following directories:
+  `documentation`\- contains the API documentation \(also available on the web: [AWS SDK for Java API Reference](https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/)\)\.
+  `lib`\- contains the SDK `.jar` files\.
+  `samples`\- contains working sample code that demonstrates how to use the SDK\.
+  `third-party/lib`\- contains third\-party libraries that are used by the SDK, such as Apache commons logging, AspectJ and the Spring framework\.

To use the SDK, add the full path to the `lib` and `third-party` directories to the dependencies in your build file, and add them to your java `CLASSPATH` to run your code\.

## Installing previous versions of the SDK<a name="install-prev-sdk"></a>

Only the latest version of the SDK is provided in pre\-built form\. However, you can build a previous version of the SDK using Apache Maven \(open source\)\. Maven will download all necessary dependencies, build and install the SDK in one step\. Visit [http://maven\.apache\.org/](http://maven.apache.org/) for installation instructions and more information\.

1. Go to the SDK’s GitHub page at: [AWS SDK for Java \(GitHub\)](https://github.com/aws/aws-sdk-java)\.

1. Choose the tag corresponding to the version number of the SDK that you want\. For example, `1.6.10`\.

1. Click the **Download ZIP** button to download the version of the SDK you selected\.

1. Unzip the file to a directory on your development system\. On many systems, you can use your graphical file manager to do this, or use the `unzip` utility in a terminal window\.

1. In a terminal window, navigate to the directory where you unzipped the SDK source\.

1. Build and install the SDK with the following command \([Maven](https://maven.apache.org/) required\):

   ```
   mvn clean install
   ```

   The resulting `.jar` file is built into the `target` directory\.

1. \(Optional\) Build the API Reference documentation using the following command:

   ```
   mvn javadoc:javadoc
   ```

   The documentation is built into the `target/site/apidocs/` directory\.

## Installing a Java Development Environment<a name="java-dg-java-env"></a>

The AWS SDK for Java requires J2SE Development Kit *6\.0 or later*\. You can download the latest Java software from [http://www\.oracle\.com/technetwork/java/javase/downloads/](http://www.oracle.com/technetwork/java/javase/downloads/)\.

**Important**  
Java version 1\.6 \(JS2E 6\.0\) did not have built\-in support for SHA256\-signed SSL certificates, which are required for all HTTPS connections with AWS after September 30, 2015\.  
Java versions 1\.7 or newer are packaged with updated certificates and are unaffected by this issue\.

### Choosing a JVM<a name="choosing-a-jvm"></a>

For the best performance of your server\-based applications with the AWS SDK for Java, we recommend that you use the *64\-bit version* of the Java Virtual Machine \(JVM\)\. This JVM runs only in server mode, even if you specify the `-Client` option at run time\.

Using the 32\-bit version of the JVM with the `-Server` option at run time should provide comparable performance to the 64\-bit JVM\.