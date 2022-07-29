--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Developer Guide \- AWS SDK for Java 1\.x<a name="welcome"></a>

The [AWS SDK for Java](http://aws.amazon.com/sdk-for-java/) provides a Java API for AWS services\. Using the SDK, you can easily build Java applications that work with Amazon S3, Amazon EC2, DynamoDB, and more\. We regularly add support for new services to the AWS SDK for Java\. For a list of the supported services and their API versions that are included with each release of the SDK, view the [release notes](https://github.com/aws/aws-sdk-java#release-notes) for the version that you’re working with\.

## Version 2 of the SDK released<a name="new-version-released-aws-sdk-for-java-2-x"></a>

Take a look at the new AWS SDK for Java 2\.x at [https://github\.com/aws/aws\-sdk\-java\-v2/](https://github.com/aws/aws-sdk-java-v2/)\. It includes much awaited features, such as a way to plug in an HTTP implementation\. To get started, see the [AWS SDK for Java 2\.x Developer Guide](https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide)\.

## Additional Documentation and Resources<a name="additional-resources"></a>

In addition to this guide, the following are valuable online resources for AWS SDK for Java developers:
+  [AWS SDK for Java API Reference](https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/) 
+  [Java developer blog](http://aws.amazon.com/blogs/developer/category/java) 
+  [Java developer forums](http://forums.aws.amazon.com/forum.jspa?forumID=70) 
+ GitHub:
  +  [Documentation source](https://github.com/awsdocs/aws-java-developer-guide) 
  +  [Documentation issues](https://github.com/awsdocs/aws-java-developer-guide/issues) 
  +  [SDK source](https://github.com/aws/aws-sdk-java) 
  +  [SDK issues](https://github.com/aws/aws-sdk-java/issues) 
  +  [SDK samples](https://github.com/aws/aws-sdk-java/tree/master/src/samples) 
  +  [Gitter channel](https://gitter.im/aws/aws-sdk-java) 
+ The [AWS Code Sample Catalog](http://docs.aws.amazon.com/code-samples/latest/catalog/) 
+  [@awsforjava \(Twitter\)](https://twitter.com/awsforjava) 
+  [release notes](https://github.com/aws/aws-sdk-java#release-notes) 

## Eclipse IDE Support<a name="eclipse-support"></a>

If you develop code using the Eclipse IDE, you can use the [AWS Toolkit for Eclipse](http://aws.amazon.com/eclipse/) to add the AWS SDK for Java to an existing Eclipse project or to create a new AWS SDK for Java project\. The toolkit also supports creating and uploading Lambda functions, launching and monitoring Amazon EC2 instances, managing IAM users and security groups, a AWS CloudFormation template editor, and more\.

See the [AWS Toolkit for Eclipse User Guide](https://docs.aws.amazon.com/toolkit-for-eclipse/v1/user-guide/) for full documentation\.

## Developing Applications for Android<a name="android-support"></a>

If you’re an Android developer, Amazon Web Services publishes an SDK made specifically for Android development: the [AWS Mobile SDK for Android](http://aws.amazon.com/mobile/sdk/)\. See the [AWS Mobile SDK for Android Developer Guide](http://docs.aws.amazon.com/mobile/sdkforandroid/developerguide/) for full documentation\.

## Viewing the SDK’s Revision History<a name="java-sdk-history"></a>

To view the release history of the AWS SDK for Java, including changes and supported services per SDK version, see the SDK’s [release notes](https://github.com/aws/aws-sdk-java#release-notes)\.

## Building Java Reference Documentation for Earlier SDK versions<a name="build-old-reference-docs"></a>

The [AWS SDK for Java API Reference](https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/) represents the most recent build of version 1\.x of the SDK\. If you’re using an earlier build of the 1\.x version, you might want to access the SDK reference documentation that matches the version you’re using\.

The easiest way to build the documentation is using Apache’s [Maven](https://maven.apache.org/) build tool\. *Download and install Maven first if you don’t already have it on your system*, then use the following instructions to build the reference documentation\.

1. Locate and select the SDK version that you’re using on the [releases](https://github.com/aws/aws-sdk-java/releases) page of the SDK repository on GitHub\.

1. Choose either the `zip` \(most platforms, including Windows\) or `tar.gz` \(Linux, macOS, or Unix\) link to download the SDK to your computer\.

1. Unpack the archive to a local directory\.

1. On the command line, navigate to the directory where you unpacked the archive, and type the following\.

   ```
   mvn javadoc:javadoc
   ```

1. After building is complete, you’ll find the generated HTML documentation in the `aws-java-sdk/target/site/apidocs/` directory\.