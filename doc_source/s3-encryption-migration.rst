.. Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

################################
|S3| Encryption Client Migration
################################

.. meta::
   :description: How to migrate your applications from v1 to v2 of the AWS S3 client-side encryption
   service client
   :keywords: AWS for Java SDK, migrate, migration, CSE, encryption, key, KMS, S3,
      AmazonS3EncryptionClientV2, AmazonS3EncryptionClient


This topic shows you how to migrate your applications from Version 1 (V1) of the |S3long| (|S3|)
encryption client to Version 2 (V2) and ensure application availability throughout the migration
process.

.. _s3-cse-prereq:

Prerequisites
=============

* |S3| client-side encryption requires Java 8 or later to be installed in your application
  environment.
  The |sdk-java| works with the
  `Oracle Java SE Development Kit <https://www.oracle.com/java/technologies/javase-downloads.html>`_
  and with distributions of Open Java Development Kit (OpenJDK) such as
  `Amazon Corretto <https://aws.amazon.com/corretto/>`_,
  `Red Hat OpenJDK <https://developers.redhat.com/products/openjdk>`_,
  and `AdoptOpenJDK <https://adoptopenjdk.net/>`_.

* You need to take a dependency on BouncyCastle. If you don't already have this,
  go to https://bouncycastle.org/latest_releases.html and download the provider file that corresponds to your JDK.
  Alternatively, you can pick it up from Maven (groupId: org.bouncycastle, artifactId: bcprov-ext-jdk15on)."

.. _s3-cse-overview:

Migration Overview
==================

This migration happens in two phases:

1. **Update existing clients to read new formats.** Update your application to use version 1.11.837
   or later of the AWS SDK for Java and redeploy the application. This enables the |S3| client-side
   encryption service clients in your application to decrypt objects created by V2 service clients.
   If your application uses multiple AWS SDKs, you must update each SDK separately.

2. **Migrate encryption and decryption clients to V2.** Once all of your V1 encryption clients can
   read V2 encryption formats, update the |S3| client-side encryption and decryption clients in your
   application code to use their V2 equivalents.

.. _s3-cse-update-project:

Update Existing Clients to Read New Formats
===========================================

The V2 encryption client uses encryption algorithms that older versions of the AWS SDK for Java do
not support.

The first step in the migration is to update your V1 encryption clients to use version 1.11.837 or
later of the |sdk-java|. (We recommend that you update to the latest release version, which you can
find in the
`Java API Reference version 1.x <https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc>`_.) To do
so, update the dependency in your project configuration. After your project configuration is
updated, rebuild your project and redeploy it.

Once you have completed these steps, your application’s V1 encryption clients will be able to read
objects written by V2 encryption clients.

Update the Dependency in Your Project Configuration
---------------------------------------------------

Modify your project configuration file (for example, pom.xml or build.gradle) to use version
1.11.837 or later of the AWS SDK for Java. Then, rebuild your project and redeploy it.

Completing this step before deploying new application code helps to ensure that encryption
and decryption operations remain consistent across your fleet during the migration process.

Example Using Maven
~~~~~~~~~~~~~~~~~~~

Snippet from a pom.xml file:

.. code-block:: xml

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

Example Using Gradle
~~~~~~~~~~~~~~~~~~~~

Snippet from a build.gradle file:

.. code-block:: json

   dependencies {
     implementation platform('com.amazonaws:aws-java-sdk-bom:1.11.837')
     implementation 'com.amazonaws:aws-java-sdk-s3'
   }

.. _s3-cse-update-code:

Migrate Encryption and Decryption Clients to V2
===============================================

Once your project has been updated with the latest SDK version, you can modify your application code
to use the V2 client. To do so, first update your code to use the new service client builder. Then
provide encryption materials using a method on the builder that has been renamed, and configure your
service client further as needed.

These code snippets demonstrate how to use client-side encryption with the AWS SDK for Java, and
provide comparisons between the V1 and V2 encryption clients.

**V1**

.. code-block:: java

    // minimal configuration in V1; default CryptoMode.EncryptionOnly.
    EncryptionMaterialsProvider encryptionMaterialsProvider = ...
    AmazonS3Encryption encryptionClient = AmazonS3EncryptionClient.encryptionBuilder()
                 .withEncryptionMaterials(encryptionMaterialsProvider)
                 .build();

**V2**

.. code-block:: java

    // minimal configuration in V2; default CryptoMode.StrictAuthenticatedEncryption.
    EncryptionMaterialsProvider encryptionMaterialsProvider = ...
    AmazonS3EncryptionV2 encryptionClient = AmazonS3EncryptionClientV2.encryptionBuilder()
                 .withEncryptionMaterialsProvider(encryptionMaterialsProvider)
                 .withCryptoConfiguration(new CryptoConfigurationV2()
                               // The following setting allows the client to read V1 encrypted objects
                               .withCryptoMode(CryptoMode.AuthenticatedEncryption)
                 )
                 .build();

The above example sets the :code:`cryptoMode` to :code:`AuthenticatedEncryption`. This is a setting
that allows a V2 encryption client to read objects that have been written by a V1 encryption
client. If your client does not need the capability to read objects written by a V1 client, then we
recommend using the default setting of :code:`StrictAuthenticatedEncryption` instead.

Construct a V2 Encryption Client
--------------------------------

The V2 encryption client can be constructed by calling 
*AmazonS3EncryptionClientV2.encryptionBuilder().*

You can replace all of your existing V1 encryption clients with V2 encryption clients. A V2
encryption client will always be able to read any object that has been written by a V1 encryption
client as long as you permit it to do so by configuring the V2 encryption client to use the
:code:`AuthenticatedEncryption` :code:`cryptoMode`.

Creating a new V2 encryption client is very similar to how you create a V1 encryption client.
However, there are a few differences:

*  You will use a :code:`CryptoConfigurationV2` object to configure the client instead of a
   :code:`CryptoConfiguration` object. This parameter is required.
*  The default :code:`cryptoMode` setting for the V2 encryption client is
   :code:`StrictAuthenticatedEncryption`. For the V1 encryption client it is :code:`EncryptionOnly`.
*  The method *withEncryptionMaterials()* on the encryption client builder has been renamed to
   *withEncryptionMaterialsProvider()*. This is merely a cosmetic change that more accurately
   reflects the argument type. You must use the new method when you configure your service client.

.. note:: When decrypting with AES-GCM, read the entire object to the end before you start using the
   decrypted data. This is to verify that the object has not been modified since it was encrypted.


Use Encryption Materials Providers
----------------------------------

You can continue to use the same encryption materials providers and encryption materials objects
you are already using with the V1 encryption client. These classes are responsible for providing the
keys the encryption client uses to secure your data. They can be used interchangeably with both the
V2 and the V1 encryption client.

Configure the V2 Encryption Client
----------------------------------

The V2 encryption client is configured with a :code:`CryptoConfigurationV2` object. This object can be
constructed by calling its default constructor and then modifying its properties as required from
the defaults.

The default values for :code:`CryptoConfigurationV2` are:

*  :code:`cryptoMode` = :code:`CryptoMode.StrictAuthenticatedEncryption`
*  :code:`storageMode` = :code:`CryptoStorageMode.ObjectMetadata`
*  :code:`secureRandom` = instance of :code:`SecureRandom`
*  :code:`rangeGetMode` = :code:`CryptoRangeGetMode.DISABLED`
*  :code:`unsafeUndecryptableObjectPassthrough` = :code:`false`

Note that `EncryptionOnly` is not a supported :code:`cryptoMode` in the V2 encryption client. The V2
encryption client will always encrypt content using authenticated encryption, and protects content
encrypting keys (CEKs) using V2 :code:`KeyWrap` objects.

The following example demonstrates how to specify the crypto configuration in V1, and how to
instantiate a *CryptoConfigurationV2*  object to pass to the V2 encryption client builder.

**V1**

.. code-block:: java

   CryptoConfiguration cryptoConfiguration = new CryptoConfiguration()
           .withCryptoMode(CryptoMode.StrictAuthenticatedEncryption);

**V2**

.. code-block:: java

   CryptoConfigurationV2 cryptoConfiguration = new CryptoConfigurationV2()
           .withCryptoMode(CryptoMode.StrictAuthenticatedEncryption);

.. _additional-examples:

Additional Examples
===================

The following examples demonstrate how to address specific use cases related to a migration from V1
to V2.

Configure a Service Client to Read Objects Created by the V1 Encryption Client
------------------------------------------------------------------------------

To read objects that were previously written using a V1 encryption client, set the
:code:`cryptoMode` to :code:`AuthenticatedEncryption`. The following code snippet demonstrates how
to construct a configuration object with this setting.

.. code-block:: java

   CryptoConfigurationV2 cryptoConfiguration = new CryptoConfigurationV2()
           .withCryptoMode(CryptoMode.AuthenticatedEncryption);

Configure a Service Client to Get Byte Ranges of Objects
--------------------------------------------------------

To be able to :code:`get` a range of bytes from an encrypted S3 object, enable the new configuration
setting :code:`rangeGetMode`. This setting is disabled on the V2 encryption client by default. Note
that even when enabled, a ranged :code:`get` only works on objects that have been encrypted using
algorithms supported by the :code:`cryptoMode` setting of the client. For more information, see
:aws-java-class:`CryptoRangeGetMode <services/s3/model/CryptoRangeGetMode>` in the AWS SDK for Java
API Reference.

If you plan to use the |S3| TransferManager to perform multipart downloads of encrypted |S3| objects
using the V2 encryption client, then you must first enable the :code:`rangeGetMode` setting on the
V2 encryption client.

The following code snippet demonstrates how to configure the V2 client for performing a ranged
:code:`get`.

.. code-block:: java

    // Allows range gets using AES/CTR, for V2 encrypted objects only
    CryptoConfigurationV2 cryptoConfiguration = new CryptoConfigurationV2()
           .withRangeGetMode(CryptoRangeGetMode.ALL);

    // Allows range gets using AES/CTR and AES/CBC, for V1 and V2 objects
    CryptoConfigurationV2 cryptoConfiguration = new CryptoConfigurationV2()
           .withCryptoMode(CryptoMode.AuthenticatedEncryption)
           .withRangeGetMode(CryptoRangeGetMode.ALL);        
