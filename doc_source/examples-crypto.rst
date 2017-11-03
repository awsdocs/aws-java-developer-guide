.. Copyright 2010-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

#################################
Using |S3| Client-Side Encryption
#################################

.. meta::
   :description: How to use the cryptography configuration settings for the AWS SDK for Java
   :keywords: cryptography, encryption, example code

Encrypting data using the |S3| encryption client is one way you can provide an
additional layer of protection for sensitive information you store in |S3|.
If you are new to cryptography,
see the :KMS-dg:`Cryptography Basics <crypto-intro>` in the |KMS-dg| for a basic overview of
cryptography terms and algorithms.

.. include:: includes/examples-note.txt

.. toctree::
    :titlesonly:
    :maxdepth: 1

    examples-crypto-masterkey
    examples-crypto-kms

For information about cryptography support across all AWS SDKs, see
:AWS-gr:`AWS SDK Support for Amazon S3 Client-Side Encryption <aws_sdk_cryptography>` in the |AWS-gr|.
