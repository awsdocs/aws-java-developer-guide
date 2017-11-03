.. Copyright 2010-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

##########################################################
|S3| Client-Side Encryption with Client Master Keys
##########################################################

.. meta::
   :description: How to use the cryptography configuration settings for the AWS SDK for Java
   :keywords: cryptography, encryption, example code

The following examples use the
:aws-java-class:`AmazonS3EncryptionClientBuilder <services/s3/AmazonS3EncryptionClientBuilder>`
to create an |S3| client with client-side encryption enabled. Once configured,
any object you upload to |S3| using this client
will be encrypted. Any objects you get from |S3| using this client will automatically
be decrypted.

.. note::
   The examples here demonstrate using the |S3| client-side
   encryption with customer managed client master keys. To learn how to use encryption
   with |KMS| managed keys, see the :doc:`examples-crypto-kms` topic.

You can choose from three encryption modes when enabling client-side |S3| encryption.
The sections below show how to enable each type. To learn which algorithms each mode uses,
see :aws-java-class:`CryptoMode <services/s3/model/CryptoMode>` definition.

Required Imports
================

Import the following libraries for the examples on this page.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/S3Encrypt.java
   :lines: 16-24,26-31
   :language: java

.. _encryption-only:

Encryption only Mode
====================

This is the default mode, if no :classname:`CryptoMode` is not specified. To enable
encryption, you must pass a key to the :aws-java-class:`EncryptionMaterials`
constructor. The example below uses
the Java class :class:`KeyGenerator` to generate a symmetric private key.

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/S3Encrypt.java
  :lines: 198-204
  :dedent: 8
  :language: java

To use an asymmetric key, or a key pair, simply pass the key pair to the same class
:aws-java-class:`EncryptionMaterials`. The example below uses the
:class:`KeyPairGenerator` class to generate a key pair.

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/S3Encrypt.java
  :lines: 217-223
  :dedent: 8
  :language: java

You can retrieve the object with the same client.

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/S3Encrypt.java
  :lines: 229
  :dedent: 8
  :language: java

See the :sdk-examples-java-s3:`complete example <S3Encrypt.java>`.

.. _authenticated-encryption:

Authenticated Encryption Mode
=============================

When :classname:`AuthenticatedEncryption` mode is used, an improved key wrapping algorithm is
applied during encryption. When decrypting in this mode, the algorithm is able to verify the integrity
of the decrypted object and throw an exception if the check fails.
To get more details about how authenticated encryption works, see the
:blog:`Amazon S3 Client-Side Authenticated Encryption <developer/amazon-s3-client-side-authenticated-encryption>`
blog post.

.. note:: To use client-side authenticated encryption, you must include the latest
          `Bouncy Castle jar <https://www.bouncycastle.org/latest_releases.html>`_
          in the classpath of your application.

To enable this mode, specify the :classname:`AuthenticatedEncryption` value in
:method:`withCryptoConfiguration` method.

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/S3Encrypt.java
   :lines: 67-72
   :dedent: 8
   :language: java

The :classname:`AuthenticatedEncryption` mode can retrieve unencrypted objects as well as
objects encrypted with :classname:`EncryptionOnly` mode. This example shows the
|S3| encryption client retrieving an unencrypted object.

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/S3Encrypt.java
   :lines: 66-79
   :dedent: 8
   :language: java

See the :sdk-examples-java-s3:`complete example <S3Encrypt.java>`.

.. _strict-authenticated-encryption:

Strict Authenticated Encryption
===============================

To enable this mode, specify the :classname:`StrictAuthenticatedEncryption` value in
:method:`withCryptoConfiguration` method.

.. note:: To use client-side authenticated encryption, you must include the latest
          `Bouncy Castle jar <https://www.bouncycastle.org/latest_releases.html>`_
          in the classpath of your application.
          
**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/S3Encrypt.java
   :lines: 132-137
   :dedent: 8
   :language: java

In :classname:`StrictAuthenticatedEncryption`, the |S3| client will throw an
exception when retrieving an object that was not encrypted using an
authenticated mode.

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/S3Encrypt.java
   :lines: 131-149
   :dedent: 8
   :language: java

See the :sdk-examples-java-s3:`complete example <S3Encrypt.java>`.
