--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Amazon S3 client\-side encryption with client master keys<a name="examples-crypto-masterkey"></a>

The following examples use the [AmazonS3EncryptionClientV2Builder](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/AmazonS3EncryptionClientV2Builder.html) class to create an Amazon S3 client with client\-side encryption enabled\. Once enabled, any objects you upload to Amazon S3 using this client will be encrypted\. Any objects you get from Amazon S3 using this client will automatically be decrypted\.

**Note**  
The following examples demonstrate using the Amazon S3 client\-side encryption with customer\-managed client master keys\. To learn how to use encryption with AWS KMS managed keys, see [Amazon S3 client\-side encryption with AWS KMS managed keys](examples-crypto-kms.md)\.

You can choose from two encryption modes when enabling client\-side Amazon S3 encryption: strict authenticated or authenticated\. The following sections show how to enable each type\. To learn which algorithms each mode uses, see the [CryptoMode](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/model/CryptoMode.html) definition\.

## Required imports<a name="required-imports"></a>

Import the following classes for these examples\.

 **Imports** 

```
import com.amazonaws.ClientConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3EncryptionClientV2Builder;
import com.amazonaws.services.s3.AmazonS3EncryptionV2;
import com.amazonaws.services.s3.model.CryptoConfigurationV2;
import com.amazonaws.services.s3.model.CryptoMode;
import com.amazonaws.services.s3.model.EncryptionMaterials;
import com.amazonaws.services.s3.model.StaticEncryptionMaterialsProvider;
```

## Strict authenticated encryption<a name="strict-authenticated-encryption"></a>

Strict authenticated encryption is the default mode if no `CryptoMode` is specified\.

To explicitly enable this mode, specify the `StrictAuthenticatedEncryption` value in the `withCryptoConfiguration` method\.

**Note**  
To use client\-side authenticated encryption, you must include the latest [Bouncy Castle jar](https://www.bouncycastle.org/latest_releases.html) file in the classpath of your application\.

 **Code** 

```
AmazonS3EncryptionV2 s3Encryption = AmazonS3EncryptionClientV2Builder.standard()
         .withRegion(Regions.US_WEST_2)
         .withCryptoConfiguration(new CryptoConfigurationV2().withCryptoMode((CryptoMode.StrictAuthenticatedEncryption)))
         .withEncryptionMaterialsProvider(new StaticEncryptionMaterialsProvider(new EncryptionMaterials(secretKey)))
         .build();

s3Encryption.putObject(bucket_name, ENCRYPTED_KEY2, "This is the 2nd content to encrypt");
```

## Authenticated encryption mode<a name="authenticated-encryption-mode"></a>

When you use `AuthenticatedEncryption` mode, an improved key wrapping algorithm is applied during encryption\. When decrypting in this mode, the algorithm can verify the integrity of the decrypted object and throw an exception if the check fails\. For more details about how authenticated encryption works, see the [Amazon S3 Client\-Side Authenticated Encryption](http://aws.amazon.com/blogs/developer/amazon-s3-client-side-authenticated-encryption) blog post\.

**Note**  
To use client\-side authenticated encryption, you must include the latest [Bouncy Castle jar](https://www.bouncycastle.org/latest_releases.html) file in the classpath of your application\.

To enable this mode, specify the `AuthenticatedEncryption` value in the `withCryptoConfiguration` method\.

 **Code** 

```
AmazonS3EncryptionV2 s3EncryptionClientV2 = AmazonS3EncryptionClientV2Builder.standard()
         .withRegion(Regions.DEFAULT_REGION)
         .withClientConfiguration(new ClientConfiguration())
         .withCryptoConfiguration(new CryptoConfigurationV2().withCryptoMode(CryptoMode.AuthenticatedEncryption))
         .withEncryptionMaterialsProvider(new StaticEncryptionMaterialsProvider(new EncryptionMaterials(secretKey)))
         .build();

s3EncryptionClientV2.putObject(bucket_name, ENCRYPTED_KEY1, "This is the 1st content to encrypt");
```