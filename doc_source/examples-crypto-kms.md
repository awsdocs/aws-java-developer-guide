# Amazon S3 client\-side encryption with AWS KMS managed keys<a name="examples-crypto-kms"></a>

The following examples use the [AmazonS3EncryptionClientV2Builder](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/AmazonS3EncryptionClientV2Builder.html) class to create an Amazon S3 client with client\-side encryption enabled\. Once configured, any objects you upload to Amazon S3 using this client will be encrypted\. Any objects you get from Amazon S3 using this client are automatically decrypted\.

**Note**  
The following examples demonstrate how to use the Amazon S3 client\-side encryption with AWS KMS managed keys\. To learn how to use encryption with your own keys, see [Amazon S3 client\-side encryption with client master keys](examples-crypto-masterkey.md)\.

You can choose from two encryption modes when enabling client\-side Amazon S3 encryption: strict authenticated or authenticated\. The following sections show how to enable each type\. To learn which algorithms each mode uses, see the [CryptoMode](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/model/CryptoMode.html) definition\.

## Required imports<a name="required-imports"></a>

Import the following classes for these examples\.

 **Imports** 

```
import com.amazonaws.ClientConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.GenerateDataKeyRequest;
import com.amazonaws.services.kms.model.GenerateDataKeyResult;
import com.amazonaws.services.s3.AmazonS3EncryptionClientV2Builder;
import com.amazonaws.services.s3.AmazonS3EncryptionV2;
import com.amazonaws.services.s3.model.CryptoConfigurationV2;
import com.amazonaws.services.s3.model.CryptoMode;
import com.amazonaws.services.s3.model.EncryptionMaterials;
import com.amazonaws.services.s3.model.KMSEncryptionMaterialsProvider;
```

## Strict authenticated encryption<a name="strict-authenticated-encryption-kms"></a>

Strict authenticated encryption is the default mode if no `CryptoMode` is specified\.

To explicitly enable this mode, specify the `StrictAuthenticatedEncryption` value in the `withCryptoConfiguration` method\.

**Note**  
To use client\-side authenticated encryption, you must include the latest [Bouncy Castle jar](https://www.bouncycastle.org/latest_releases.html) file in the classpath of your application\.

 **Code** 

```
AmazonS3EncryptionV2 s3Encryption = AmazonS3EncryptionClientV2Builder.standard()
         .withRegion(Regions.US_WEST_2)
         .withCryptoConfiguration(new CryptoConfigurationV2().withCryptoMode((CryptoMode.StrictAuthenticatedEncryption)))
         .withEncryptionMaterialsProvider(new KMSEncryptionMaterialsProvider(keyId))
         .build();

s3Encryption.putObject(bucket_name, ENCRYPTED_KEY3, "This is the 3rd content to encrypt with a key created in the {console}");
System.out.println(s3Encryption.getObjectAsString(bucket_name, ENCRYPTED_KEY3));
```

Call the `putObject` method on the Amazon S3 encryption client to upload objects\.

 **Code** 

```
s3Encryption.putObject(bucket_name, ENCRYPTED_KEY3, "This is the 3rd content to encrypt with a key created in the {console}");
```

You can retrieve the object using the same client\. This example calls the `getObjectAsString` method to retrieve the string that was stored\.

 **Code** 

```
System.out.println(s3Encryption.getObjectAsString(bucket_name, ENCRYPTED_KEY3));
```

## Authenticated encryption mode<a name="authenticated-encryption-kms"></a>

When you use `AuthenticatedEncryption` mode, an improved key wrapping algorithm is applied during encryption\. When decrypting in this mode, the algorithm can verify the integrity of the decrypted object and throw an exception if the check fails\. For more details about how authenticated encryption works, see the [Amazon S3 Client\-Side Authenticated Encryption](http://aws.amazon.com/blogs/developer/amazon-s3-client-side-authenticated-encryption) blog post\.

**Note**  
To use client\-side authenticated encryption, you must include the latest [Bouncy Castle jar](https://www.bouncycastle.org/latest_releases.html) file in the classpath of your application\.

To enable this mode, specify the `AuthenticatedEncryption` value in the `withCryptoConfiguration` method\.

 **Code** 

```
AmazonS3EncryptionV2 s3Encryption = AmazonS3EncryptionClientV2Builder.standard()
         .withRegion(Regions.US_WEST_2)
         .withCryptoConfiguration(new CryptoConfigurationV2().withCryptoMode((CryptoMode.AuthenticatedEncryption)))
         .withEncryptionMaterialsProvider(new KMSEncryptionMaterialsProvider(keyId))
         .build();
```

## Configuring the AWS KMS client<a name="configure-kms"></a>

The Amazon S3 encryption client creates a AWS KMS client by default, unless one is explicitly specified\.

To set the region for this automatically\-created AWS KMS client, set the `awsKmsRegion`\.

 **Code** 

```
Region kmsRegion = Region.getRegion(Regions.AP_NORTHEAST_1);

AmazonS3EncryptionV2 s3Encryption = AmazonS3EncryptionClientV2Builder.standard()
        .withRegion(Regions.US_WEST_2)
        .withCryptoConfiguration(new CryptoConfigurationV2().withAwsKmsRegion(kmsRegion))
        .withEncryptionMaterialsProvider(new KMSEncryptionMaterialsProvider(keyId))
        .build();
```

Alternatively, you can use your own AWS KMS client to initialize the encryption client\.

 **Code** 

```
AWSKMS kmsClient = AWSKMSClientBuilder.standard()
        .withRegion(Regions.US_WEST_2);
        .build();

AmazonS3EncryptionV2 s3Encryption = AmazonS3EncryptionClientV2Builder.standard()
        .withRegion(Regions.US_WEST_2)
        .withKmsClient(kmsClient)
        .withCryptoConfiguration(new CryptoConfigurationV2().withCryptoMode((CryptoMode.AuthenticatedEncryption)))
        .withEncryptionMaterialsProvider(new KMSEncryptionMaterialsProvider(keyId))
        .build();
```