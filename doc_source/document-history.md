--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Document History<a name="document-history"></a>

This topic describes important changes to the AWS SDK for Java Developer Guide over the course of its history\.

 **This documentation was built on:** 2022\-07\-28

July 28, 2022  
+ Added an alert that EC2\-Classic is retiring on August 15, 2022\.

Mar 22, 2018  
+ Removed managing Tomcat sessions in DynamoDB example as that tool is no longer supported\.

Nov 2, 2017  
+ Added cryptography examples for Amazon S3 encryption client, including new topics: [Use Amazon S3 client\-side encryption](examples-crypto.md) and [Amazon S3 client\-side encryption with AWS KMS managed keys](examples-crypto-kms.md) and [Amazon S3 client\-side encryption with client master keys](examples-crypto-masterkey.md)\.

Apr 14, 2017  
+ Made a number of updates to the [Amazon S3 Examples Using the AWS SDK for Java](examples-s3.md) section, including new topics: [Managing Amazon S3 Access Permissions for Buckets and Objects](examples-s3-access-permissions.md) and [Configuring an Amazon S3 Bucket as a Website](examples-s3-website-configuration.md)\.

Apr 04, 2017  
+ A new topic, [Enabling Metrics for the AWS SDK for Java](generating-sdk-metrics.md) describes how to generate application and SDK performance metrics for the AWS SDK for Java\.

Apr 03, 2017  
+ Added new CloudWatch examples to the [CloudWatch Examples Using the AWS SDK for Java](examples-cloudwatch.md) section: [Getting Metrics from CloudWatch](examples-cloudwatch-get-metrics.md), [Publishing Custom Metric Data](examples-cloudwatch-publish-custom-metrics.md), [Working with CloudWatch Alarms](examples-cloudwatch-create-alarms.md), [Using Alarm Actions in CloudWatch](examples-cloudwatch-use-alarm-actions.md), and [Sending Events to CloudWatch](examples-cloudwatch-send-events.md) 

Mar 27, 2017  
+ Added more Amazon EC2 examples to the [Amazon EC2 Examples Using the AWS SDK for Java](prog-services-ec2.md) section: [Managing Amazon EC2 Instances](examples-ec2-instances.md), [Using Elastic IP Addresses in Amazon EC2](examples-ec2-elastic-ip.md), [Use regions and availability zones](examples-ec2-regions-zones.md), [Working with Amazon EC2 Key Pairs](examples-ec2-key-pairs.md), and [Working with Security Groups in Amazon EC2](examples-ec2-security-groups.md)\.

Mar 21, 2017  
+ Added a new set of IAM examples to the [IAM Examples Using the AWS SDK for Java](examples-iam.md) section: [Managing IAM Access Keys](examples-iam-access-keys.md), [Managing IAM Users](examples-iam-users.md), [Using IAM Account Aliases](examples-iam-account-aliases.md), [Working with IAM Policies](examples-iam-policies.md), and [Working with IAM Server Certificates](examples-iam-server-certificates.md) 

Mar 13, 2017  
+ Added three new topics to the Amazon SQS section: [Enabling Long Polling for Amazon SQS Message Queues](examples-sqs-long-polling.md), [Setting Visibility Timeout in Amazon SQS](examples-sqs-visibility-timeout.md), and [Using Dead Letter Queues in Amazon SQS](examples-sqs-dead-letter-queues.md)\.

Jan 26, 2017  
+ Added a new Amazon S3 topic, [Using TransferManager for Amazon S3 Operations](examples-s3-transfermanager.md), and a new [Best Practices for AWS Development with the AWS SDK for Java](best-practices.md) topic in the [Using the AWS SDK for Java](basics.md) section\.

Jan 16, 2017  
+ Added a new Amazon S3 topic, [Managing Access to Amazon S3 Buckets Using Bucket Policies](examples-s3-bucket-policies.md), and two new Amazon SQS topics, [Working with Amazon SQS Message Queues](examples-sqs-message-queues.md) and [Sending Receiving and Deleting Amazon SQS Messages](examples-sqs-messages.md)\.

Dec 16, 2016  
+ Added new example topics for DynamoDB: [Working with Tables in DynamoDB](examples-dynamodb-tables.md) and [Working with Items in DynamoDB](examples-dynamodb-items.md)\.

Sep 26, 2016  
+ The topics in the **Advanced** section have been moved into [Using the AWS SDK for Java](basics.md), since they really are central to using the SDK\.

Aug 25, 2016  
+ A new topic, [Creating Service Clients](creating-clients.md), has been added to [Using the AWS SDK for Java](basics.md), which demonstrates how to use *client builders* to simplify the creation of AWS service clients\.

  The [AWS SDK for Java Code Examples](prog-services.md) section has been updated with [new examples for S3](examples-s3.md) which are backed by a [repository on GitHub](https://github.com/awsdocs/aws-doc-sdk-examples) that contains the complete example code\.

May 02, 2016  
+ A new topic, [Asynchronous Programming](basics-async.md), has been added to the [Using the AWS SDK for Java](basics.md) section, describing how to work with asynchronous client methods that return `Future` objects or that take an `AsyncHandler`\.

Apr 26, 2016  
+ The *SSL Certificate Requirements* topic has been removed, since it is no longer relevant\. Support for SHA\-1 signed certificates was deprecated in 2015 and the site that housed the test scripts has been removed\.

Mar 14, 2016  
+ Added a new topic to the Amazon SWF section: [Lambda Tasks](swf-lambda-task.md), which describes how to implement a Amazon SWF workflow that calls Lambda functions as tasks as an alternative to using traditional Amazon SWF activities\.

Mar 04, 2016  
+ The [Amazon SWF Examples Using the AWS SDK for Java](prog-services-swf.md) section has been updated with new content:
  +  [Amazon SWF Basics](swf-basics.md)\- Provides basic information about how to include SWF in your projects\.
  +  [Building a Simple Amazon SWF Application](swf-hello.md)\- A new tutorial that provides step\-by\-step guidance for Java developers new to Amazon SWF\.
  +  [Shutting Down Activity and Workflow Workers Gracefully](swf-graceful-shutdown.md)\- Describes how you can gracefully shut down Amazon SWF worker classes using Java’s concurrency classes\.

Feb 23, 2016  
+ The source for the AWS SDK for Java Developer Guide has been moved to [aws\-java\-developer\-guide](https://github.com/awsdocs/aws-java-developer-guide)\.

Dec 28, 2015  
+  [Setting the JVM TTL for DNS Name Lookups](java-dg-jvm-ttl.md) has been moved from **Advanced** into [Using the AWS SDK for Java](basics.md), and has been rewritten for clarity\.

   [Using the SDK with Apache Maven](setup-project-maven.md) has been updated with information about how to include the SDK’s bill of materials \(BOM\) in your project\.

Aug 04, 2015  
+  *SSL Certificate Requirements* is a new topic in the [Getting Started](getting-started.md) section that describes AWS' move to SHA256\-signed certificates for SSL connections, and how to fix early 1\.6 and previous Java environments to use these certificates, which are *required* for AWS access after September 30, 2015\.
**Note**  
Java 1\.7\+ is already capable of working with SHA256\-signed certificates\.

May 14, 2014  
+ The [introduction](welcome.md) and [getting started](getting-started.md) material has been heavily revised to support the new guide structure and now includes guidance about how to [Set up AWS Credentials and Region for Development](setup-credentials.md)\.

  The discussion of [code samples](java-dg-samples.md) has been moved into its own topic in the [Additional Documentation and Resources](welcome.md#additional-resources) section\.

  Information about how to [view the SDK revision history](welcome.md#java-sdk-history) has been moved into the introduction\.

May 9, 2014  
+ The overall structure of the AWS SDK for Java documentation has been simplified, and the [Getting Started](getting-started.md) and [Additional Documentation and Resources](welcome.md#additional-resources) topics have been updated\.

  New topics have been added:
  +  [Working with AWS Credentials](credentials.md)\- discusses the various ways that you can specify credentials for use with the AWS SDK for Java\.
  +  [Using IAM Roles to Grant Access to AWS Resources on Amazon EC2](java-dg-roles.md)\- provides information about how to securely specify credentials for applications running on EC2 instances\.

Sep 9, 2013  
+ This topic, *Document History*, tracks changes to the AWS SDK for Java Developer Guide\. It is intended as a companion to the release notes history\.