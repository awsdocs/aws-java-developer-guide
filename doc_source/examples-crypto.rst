.. Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

###############################
Use |S3| client-side encryption
###############################

.. meta::
   :description: How to use the cryptography configuration settings for the AWS SDK for Java
   :keywords: AWS SDK for Java code examples

Encrypting data using the |S3| encryption client is one way you can provide an
additional layer of protection for sensitive information you store in |S3|.
The examples in this section demonstrate how to create and configure the |S3|
encryption client for your application.

If you are new to cryptography, see the :KMS-dg:`Cryptography Basics <crypto-intro>` in the |KMS-dg|
for a basic overview of cryptography terms and algorithms. For information about cryptography
support across all AWS SDKs, see
:AWS-gr:`AWS SDK Support for Amazon S3 Client-Side Encryption <aws_sdk_cryptography>` in the
|AWS-gr|.

.. include:: includes/examples-note.txt

If you are using version 1.11.836 or earlier of the AWS SDK for Java, see
:doc:`s3-encryption-migration` for information on migrating your applications to later versions.
If you cannot migrate, see 
`this complete example <https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/S3Encrypt.java>`_
on GitHub.

Otherwise, if you are using version 1.11.837 or later of the AWS SDK for Java, explore the example
topics listed below to use |S3| client-side encryption.


.. toctree::
    :titlesonly:
    :maxdepth: 1

    examples-crypto-masterkey
    examples-crypto-kms


