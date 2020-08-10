.. Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

###################################################
|S3| client-side encryption with client master keys
###################################################

.. meta::
   :description: How to use the cryptography configuration settings for the AWS SDK for Java
   :keywords: AWS SDK for Java code examples

The following examples use the
:aws-java-class:`AmazonS3EncryptionClientV2Builder <services/s3/AmazonS3EncryptionClientV2Builder>` class
to create an |S3| client with client-side encryption enabled. Once enabled,
any objects you upload to |S3| using this client
will be encrypted. Any objects you get from |S3| using this client will automatically
be decrypted.

.. note::
   The following examples demonstrate using the |S3| client-side
   encryption with customer-managed client master keys. To learn how to use encryption
   with |KMS| managed keys, see :doc:`examples-crypto-kms`.

You can choose from two encryption modes when enabling client-side |S3| encryption: strict
authenticated or authenticated.
The following sections show how to enable each type. To learn which algorithms each mode uses,
see the :aws-java-class:`CryptoMode <services/s3/model/CryptoMode>` definition.

Required imports
================

Import the following classes for these examples.

**Imports**

.. code-block:: java

   import com.amazonaws.ClientConfiguration;
   import com.amazonaws.regions.Regions;
   import com.amazonaws.services.s3.AmazonS3EncryptionClientV2Builder;
   import com.amazonaws.services.s3.AmazonS3EncryptionV2;
   import com.amazonaws.services.s3.model.CryptoConfigurationV2;
   import com.amazonaws.services.s3.model.CryptoMode;
   import com.amazonaws.services.s3.model.EncryptionMaterials;
   import com.amazonaws.services.s3.model.StaticEncryptionMaterialsProvider;

.. _strict-authenticated-encryption:

Strict authenticated encryption
===============================

Strict authenticated encryption is the default mode if no :classname:`CryptoMode` is specified.

To explicitly enable this mode, specify the :classname:`StrictAuthenticatedEncryption` value in the
:methodName:`withCryptoConfiguration` method.

.. note:: To use client-side authenticated encryption, you must include the latest
          `Bouncy Castle jar <https://www.bouncycastle.org/latest_releases.html>`_ file
          in the classpath of your application.

**Code**

.. code-block:: java

   AmazonS3EncryptionV2 s3Encryption = AmazonS3EncryptionClientV2Builder.standard()
            .withRegion(Regions.US_WEST_2)
            .withCryptoConfiguration(new CryptoConfigurationV2().withCryptoMode((CryptoMode.StrictAuthenticatedEncryption)))
            .withEncryptionMaterialsProvider(new StaticEncryptionMaterialsProvider(new EncryptionMaterials(secretKey)))
            .build();

   s3Encryption.putObject(bucket_name, ENCRYPTED_KEY2, "This is the 2nd content to encrypt");


Authenticated encryption mode
=============================

When you use :classname:`AuthenticatedEncryption` mode, an improved key wrapping algorithm is
applied during encryption. When decrypting in this mode, the algorithm can verify the integrity
of the decrypted object and throw an exception if the check fails.
For more details about how authenticated encryption works, see the
:blog:`Amazon S3 Client-Side Authenticated Encryption <developer/amazon-s3-client-side-authenticated-encryption>`
blog post.

.. note:: To use client-side authenticated encryption, you must include the latest
          `Bouncy Castle jar <https://www.bouncycastle.org/latest_releases.html>`_ file
          in the classpath of your application.

To enable this mode, specify the :classname:`AuthenticatedEncryption` value in the
:methodName:`withCryptoConfiguration` method.

**Code**

.. code-block:: java

   AmazonS3EncryptionV2 s3EncryptionClientV2 = AmazonS3EncryptionClientV2Builder.standard()
            .withRegion(Regions.DEFAULT_REGION)
            .withClientConfiguration(new ClientConfiguration())
            .withCryptoConfiguration(new CryptoConfigurationV2().withCryptoMode(CryptoMode.AuthenticatedEncryption))
            .withEncryptionMaterialsProvider(new StaticEncryptionMaterialsProvider(new EncryptionMaterials(secretKey)))
            .build();

   s3EncryptionClientV2.putObject(bucket_name, ENCRYPTED_KEY1, "This is the 1st content to encrypt");

