--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Amazon S3 Encryption Client Migration<a name="s3-encryption-migration"></a>

This topic shows you how to migrate your applications from Version 1 \(V1\) of the Amazon Simple Storage Service \(Amazon S3\) encryption client to Version 2 \(V2\) and ensure application availability throughout the migration process\.

## Prerequisites<a name="s3-cse-prereq"></a>

 Amazon S3 client\-side encryption requires the following:
+ Java 8 or later installed in your application environment\. The AWS SDK for Java works with the [Oracle Java SE Development Kit](https://www.oracle.com/java/technologies/javase-downloads.html) and with distributions of Open Java Development Kit \(OpenJDK\) such as [Amazon Corretto](http://aws.amazon.com/corretto/), [Red Hat OpenJDK](https://developers.redhat.com/products/openjdk), and [AdoptOpenJDK](https://adoptopenjdk.net/)\.
+ The [Bouncy Castle Crypto package](https://bouncycastle.org/latest_releases.html)\. You can place the Bouncy Castle \.jar file on the classpath of your application environment, or add a dependency on the artifactId `bcprov-ext-jdk15on` \(with the groupId of `org.bouncycastle`\) to your Maven `pom.xml` file\.

## Migration Overview<a name="s3-cse-overview"></a>

This migration happens in two phases:

1.  **Update existing clients to read new formats\.** Update your application to use version 1\.11\.837 or later of the AWS SDK for Java and redeploy the application\. This enables the Amazon S3 client\-side encryption service clients in your application to decrypt objects created by V2 service clients\. If your application uses multiple AWS SDKs, you must update each SDK separately\.

1.  **Migrate encryption and decryption clients to V2\.** Once all of your V1 encryption clients can read V2 encryption formats, update the Amazon S3 client\-side encryption and decryption clients in your application code to use their V2 equivalents\.

## Update Existing Clients to Read New Formats<a name="s3-cse-update-project"></a>

The V2 encryption client uses encryption algorithms that older versions of the AWS SDK for Java do not support\.

The first step in the migration is to update your V1 encryption clients to use version 1\.11\.837 or later of the AWS SDK for Java\. \(We recommend that you update to the latest release version, which you can find in the [Java API Reference version 1\.x](https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc)\.\) To do so, update the dependency in your project configuration\. After your project configuration is updated, rebuild your project and redeploy it\.

Once you have completed these steps, your application’s V1 encryption clients will be able to read objects written by V2 encryption clients\.

### Update the Dependency in Your Project Configuration<a name="update-the-dependency-in-your-project-configuration"></a>

Modify your project configuration file \(for example, pom\.xml or build\.gradle\) to use version 1\.11\.837 or later of the AWS SDK for Java\. Then, rebuild your project and redeploy it\.

Completing this step before deploying new application code helps to ensure that encryption and decryption operations remain consistent across your fleet during the migration process\.

#### Example Using Maven<a name="example-using-maven"></a>

Snippet from a pom\.xml file:

```
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>com.amazonaws</groupId>
      <artifactId>aws-java-sdk-bom</artifactId>
      <version>1.11.837</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```

#### Example Using Gradle<a name="example-using-gradle"></a>

Snippet from a build\.gradle file:

```
dependencies {
  implementation platform('com.amazonaws:aws-java-sdk-bom:1.11.837')
  implementation 'com.amazonaws:aws-java-sdk-s3'
}
```

## Migrate Encryption and Decryption Clients to V2<a name="s3-cse-update-code"></a>

Once your project has been updated with the latest SDK version, you can modify your application code to use the V2 client\. To do so, first update your code to use the new service client builder\. Then provide encryption materials using a method on the builder that has been renamed, and configure your service client further as needed\.

These code snippets demonstrate how to use client\-side encryption with the AWS SDK for Java, and provide comparisons between the V1 and V2 encryption clients\.

 **V1** 

```
// minimal configuration in V1; default CryptoMode.EncryptionOnly.
EncryptionMaterialsProvider encryptionMaterialsProvider = ...
AmazonS3Encryption encryptionClient = AmazonS3EncryptionClient.encryptionBuilder()
             .withEncryptionMaterials(encryptionMaterialsProvider)
             .build();
```

 **V2** 

```
// minimal configuration in V2; default CryptoMode.StrictAuthenticatedEncryption.
EncryptionMaterialsProvider encryptionMaterialsProvider = ...
AmazonS3EncryptionV2 encryptionClient = AmazonS3EncryptionClientV2.encryptionBuilder()
             .withEncryptionMaterialsProvider(encryptionMaterialsProvider)
             .withCryptoConfiguration(new CryptoConfigurationV2()
                           // The following setting allows the client to read V1 encrypted objects
                           .withCryptoMode(CryptoMode.AuthenticatedEncryption)
             )
             .build();
```

The above example sets the `cryptoMode` to `AuthenticatedEncryption`\. This is a setting that allows a V2 encryption client to read objects that have been written by a V1 encryption client\. If your client does not need the capability to read objects written by a V1 client, then we recommend using the default setting of `StrictAuthenticatedEncryption` instead\.

### Construct a V2 Encryption Client<a name="construct-a-v2-encryption-client"></a>

The V2 encryption client can be constructed by calling *AmazonS3EncryptionClientV2\.encryptionBuilder\(\)\.* 

You can replace all of your existing V1 encryption clients with V2 encryption clients\. A V2 encryption client will always be able to read any object that has been written by a V1 encryption client as long as you permit it to do so by configuring the V2 encryption client to use the `AuthenticatedEncryption``cryptoMode`\.

Creating a new V2 encryption client is very similar to how you create a V1 encryption client\. However, there are a few differences:
+ You will use a `CryptoConfigurationV2` object to configure the client instead of a `CryptoConfiguration` object\. This parameter is required\.
+ The default `cryptoMode` setting for the V2 encryption client is `StrictAuthenticatedEncryption`\. For the V1 encryption client it is `EncryptionOnly`\.
+ The method *withEncryptionMaterials\(\)* on the encryption client builder has been renamed to *withEncryptionMaterialsProvider\(\)*\. This is merely a cosmetic change that more accurately reflects the argument type\. You must use the new method when you configure your service client\.

**Note**  
When decrypting with AES\-GCM, read the entire object to the end before you start using the decrypted data\. This is to verify that the object has not been modified since it was encrypted\.

### Use Encryption Materials Providers<a name="use-encryption-materials-providers"></a>

You can continue to use the same encryption materials providers and encryption materials objects you are already using with the V1 encryption client\. These classes are responsible for providing the keys the encryption client uses to secure your data\. They can be used interchangeably with both the V2 and the V1 encryption client\.

### Configure the V2 Encryption Client<a name="configure-the-v2-encryption-client"></a>

The V2 encryption client is configured with a `CryptoConfigurationV2` object\. This object can be constructed by calling its default constructor and then modifying its properties as required from the defaults\.

The default values for `CryptoConfigurationV2` are:
+  `cryptoMode` = `CryptoMode.StrictAuthenticatedEncryption` 
+  `storageMode` = `CryptoStorageMode.ObjectMetadata` 
+  `secureRandom` = instance of `SecureRandom` 
+  `rangeGetMode` = `CryptoRangeGetMode.DISABLED` 
+  `unsafeUndecryptableObjectPassthrough` = `false` 

Note that *EncryptionOnly* is not a supported `cryptoMode` in the V2 encryption client\. The V2 encryption client will always encrypt content using authenticated encryption, and protects content encrypting keys \(CEKs\) using V2 `KeyWrap` objects\.

The following example demonstrates how to specify the crypto configuration in V1, and how to instantiate a *CryptoConfigurationV2* object to pass to the V2 encryption client builder\.

 **V1** 

```
CryptoConfiguration cryptoConfiguration = new CryptoConfiguration()
        .withCryptoMode(CryptoMode.StrictAuthenticatedEncryption);
```

 **V2** 

```
CryptoConfigurationV2 cryptoConfiguration = new CryptoConfigurationV2()
        .withCryptoMode(CryptoMode.StrictAuthenticatedEncryption);
```

## Additional Examples<a name="additional-examples"></a>

The following examples demonstrate how to address specific use cases related to a migration from V1 to V2\.

### Configure a Service Client to Read Objects Created by the V1 Encryption Client<a name="configure-a-service-client-to-read-objects-created-by-the-v1-encryption-client"></a>

To read objects that were previously written using a V1 encryption client, set the `cryptoMode` to `AuthenticatedEncryption`\. The following code snippet demonstrates how to construct a configuration object with this setting\.

```
CryptoConfigurationV2 cryptoConfiguration = new CryptoConfigurationV2()
        .withCryptoMode(CryptoMode.AuthenticatedEncryption);
```

### Configure a Service Client to Get Byte Ranges of Objects<a name="configure-a-service-client-to-get-byte-ranges-of-objects"></a>

To be able to `get` a range of bytes from an encrypted S3 object, enable the new configuration setting `rangeGetMode`\. This setting is disabled on the V2 encryption client by default\. Note that even when enabled, a ranged `get` only works on objects that have been encrypted using algorithms supported by the `cryptoMode` setting of the client\. For more information, see [CryptoRangeGetMode](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/s3/model/CryptoRangeGetMode.html) in the AWS SDK for Java API Reference\.

If you plan to use the Amazon S3 TransferManager to perform multipart downloads of encrypted Amazon S3 objects using the V2 encryption client, then you must first enable the `rangeGetMode` setting on the V2 encryption client\.

The following code snippet demonstrates how to configure the V2 client for performing a ranged `get`\.

```
// Allows range gets using AES/CTR, for V2 encrypted objects only
CryptoConfigurationV2 cryptoConfiguration = new CryptoConfigurationV2()
       .withRangeGetMode(CryptoRangeGetMode.ALL);

// Allows range gets using AES/CTR and AES/CBC, for V1 and V2 objects
CryptoConfigurationV2 cryptoConfiguration = new CryptoConfigurationV2()
       .withCryptoMode(CryptoMode.AuthenticatedEncryption)
       .withRangeGetMode(CryptoRangeGetMode.ALL);
```