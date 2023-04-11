# Ways to get the AWS SDK for Java<a name="setup-install"></a>

## Prerequisites<a name="prerequisitesinstall"></a>

To use the AWS SDK for Java, you must have:
+ You must be able to [sign in to the AWS access portal](signup-create-iam-user.md#setup-awsaccount) available in the AWS IAM Identity Center \(successor to AWS Single Sign\-On\)\.
+ A suitable [installation of Java](signup-create-iam-user.md#java-dg-java-env)\.
+ Temporary credentials set up in your local shared `credentials` file\.

See the [Basic setup to work with AWS services](signup-create-iam-user.md) topic for instructions on how to get set up to use the SDK for Java\.

## Use a build tool to manage dependencies for the SDK for Java<a name="include-sdk"></a>

We recommend using Apache Maven or Gradle with your project to access required dependencies of the SDK for Java\.[ This section](setup-build-tools.md) describes how to use those tools\.

## Download and extract the SDK \(not recommended\)<a name="download-and-extract-sdk"></a>

We recommend that you use a build tool to access the SDK for your project, You can, however, download a prebuilt jar of latest version of the SDK \.

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

## Build previous versions of the SDK from source \(not recommended\)<a name="install-prev-sdk"></a>

Only the latest version of the complete SDK is provided in pre\-built form as a downloadable jar\. However, you can build a previous version of the SDK using Apache Maven \(open source\)\. Maven will download all necessary dependencies, build and install the SDK in one step\. Visit [http://maven\.apache\.org/](http://maven.apache.org/) for installation instructions and more information\.

1. Go to the SDK’s GitHub page at: [AWS SDK for Java \(GitHub\)](https://github.com/aws/aws-sdk-java)\.

1. Choose the tag corresponding to the version number of the SDK that you want\. For example, `1.6.10`\.

1. Click the **Download ZIP** button to download the version of the SDK you selected\.

1. Unzip the file to a directory on your development system\. On many systems, you can use your graphical file manager to do this, or use the `unzip` utility in a terminal window\.

1. In a terminal window, navigate to the directory where you unzipped the SDK source\.

1. Build and install the SDK with the following command \([Maven](https://maven.apache.org/) required\):

   ```
   mvn clean install -Dgpg.skip=true
   ```

   The resulting `.jar` file is built into the `target` directory\.

1. \(Optional\) Build the API Reference documentation using the following command:

   ```
   mvn javadoc:javadoc
   ```

   The documentation is built into the `target/site/apidocs/` directory\.