--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Use Amazon S3 client\-side encryption<a name="examples-crypto"></a>

Encrypting data using the Amazon S3 encryption client is one way you can provide an additional layer of protection for sensitive information you store in Amazon S3\. The examples in this section demonstrate how to create and configure the Amazon S3 encryption client for your application\.

If you are new to cryptography, see the [Cryptography Basics](https://docs.aws.amazon.com/kms/latest/developerguide/crypto-intro.html) in the AWS KMS Developer Guide for a basic overview of cryptography terms and algorithms\. For information about cryptography support across all AWS SDKs, see [AWS SDK Support for Amazon S3 Client\-Side Encryption](http://docs.aws.amazon.com/general/latest/gr/aws_sdk_cryptography.html) in the Amazon Web Services General Reference\.

**Note**  
These code examples assume that you understand the material in [Using the AWS SDK for Java](basics.md) and have configured default AWS credentials using the information in [Set up AWS Credentials and Region for Development](setup-credentials.md)\.

If you are using version 1\.11\.836 or earlier of the AWS SDK for Java, see [Amazon S3 Encryption Client Migration](s3-encryption-migration.md) for information on migrating your applications to later versions\. If you cannot migrate, see [this complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/S3Encrypt.java) on GitHub\.

Otherwise, if you are using version 1\.11\.837 or later of the AWS SDK for Java, explore the example topics listed below to use Amazon S3 client\-side encryption\.

**Topics**
+ [Amazon S3 client\-side encryption with client master keys](examples-crypto-masterkey.md)
+ [Amazon S3 client\-side encryption with AWS KMS managed keys](examples-crypto-kms.md)