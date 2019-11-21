.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

###################################################
|S3| Client-Side Encryption with Client Master Keys
###################################################

.. meta::
   :description: How to use the cryptography configuration settings for the AWS SDK for Java
   :keywords: AWS SDK for Java code examples

The following examples use the
:aws-java-class:`AmazonS3EncryptionClientBuilder <services/s3/AmazonS3EncryptionClientBuilder>` class
to create an |S3| client with client-side encryption enabled. Once enabled,
any objects you upload to |S3| using this client
will be encrypted. Any objects you get from |S3| using this client will automatically
be decrypted.

.. note::
   The following examples demonstrate using the |S3| client-side
   encryption with customer-managed client master keys. To learn how to use encryption
   with |KMS| managed keys, see :doc:`examples-crypto-kms`.

You can choose from three encryption modes when enabling client-side |S3| encryption: encryption-only,
authenticated, and strict authenticated.
The following sections show how to enable each type. To learn which algorithms each mode uses,
see the :aws-java-class:`CryptoMode <services/s3/model/CryptoMode>` definition.

Required Imports
================

Import the following classes for these examples.

**Imports**

.. literalinclude:: s3.java1.s3_encrypt.import.txt
   :language: java

.. _encryption-only:

Encryption-Only Mode
====================

Encryption-only is the default mode, if no :classname:`CryptoMode` is specified. To enable
encryption, you must pass a key to the :aws-java-class:`EncryptionMaterials`
constructor. The example below uses
the :class:`KeyGenerator` Java class generate a symmetric private key.

**Code**

.. literalinclude:: s3.java1.s3_encrypt.encryption_only.txt
  :dedent: 4
  :language: java

To use an asymmetric key or a key pair, simply pass the key pair to the same
:aws-java-class:`EncryptionMaterials` class. The example below uses the
:class:`KeyPairGenerator` class to generate a key pair.

**Code**

.. literalinclude:: s3.java1.s3_encrypt.encryption_only_asymetric_key_build.txt
  :dedent: 8
  :language: java

Call the :methodname:`putObject` method on the |S3| encryption client to upload objects.

**Code**

.. literalinclude:: s3.java1.s3_encrypt.encryption_only_asymetric_key_put_object.txt
  :dedent: 8
  :language: java

You can retrieve the object using the same client. This example calls the
:methodname:`getObjectAsString` method to retrieve the string that was stored.

**Code**

.. literalinclude:: s3.java1.s3_encrypt.encryption_only_asymetric_key_retrieve.txt
  :dedent: 8
  :language: java

See the :sdk-examples-java-s3:`complete example <S3Encrypt.java>` on GitHub.

.. _authenticated-encryption:

Authenticated Encryption Mode
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

.. literalinclude:: s3.java1.s3_encrypt.authenticated_encryption_build.txt
   :dedent: 8
   :language: java

The :classname:`AuthenticatedEncryption` mode can retrieve unencrypted objects and
objects encrypted with :classname:`EncryptionOnly` mode. The following example shows the
|S3| encryption client retrieving an unencrypted object.

**Code**

.. literalinclude:: s3.java1.s3_encrypt.authenticated_encryption.txt
   :dedent: 8
   :language: java

See the :sdk-examples-java-s3:`complete example <S3Encrypt.java#L66-L80>` on GitHub.

.. _strict-authenticated-encryption:

Strict Authenticated Encryption
===============================

To enable this mode, specify the :classname:`StrictAuthenticatedEncryption` value in the
:methodName:`withCryptoConfiguration` method.

.. note:: To use client-side authenticated encryption, you must include the latest
          `Bouncy Castle jar <https://www.bouncycastle.org/latest_releases.html>`_ file
          in the classpath of your application.

**Code**

.. literalinclude:: s3.java1.s3_encrypt.strict_authenticated_encryption_build.txt
   :dedent: 8
   :language: java

In :classname:`StrictAuthenticatedEncryption` mode, the |S3| client throws an
exception when retrieving an object that was not encrypted using an
authenticated mode.

**Code**

.. literalinclude:: s3.java1.s3_encrypt.strict_authenticated_encryption.txt
   :dedent: 8
   :language: java

See the :sdk-examples-java-s3:`complete example <S3Encrypt.java>` on GitHub.
