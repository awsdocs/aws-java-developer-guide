.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

######################################
Working with |IAM| Server Certificates
######################################

.. meta::
   :description: How to set, get, and delete a policy for an Amazon S3 bucket.
   :keywords: AWS for Java SDK code examples, bucket policies

To enable HTTPS connections to your website or application on AWS, you need an SSL/TLS *server
certificate*. You can use a server certificate provided by |acmlong| or one that you obtained from
an external provider.

We recommend that you use |acm| to provision, manage, and deploy your server certificates. With
|acm| you can request a certificate, deploy it to your AWS resources, and let |acm| handle
certificate renewals for you. Certificates provided by |acm| are free. For more information about
|acm| , see the |acm-ug|_.


Getting a Server Certificate
============================

You can retrieve a server certificate by calling the |iamclient|'s
:methodname:`getServerCertificate` method, passing it a :aws-java-class:`GetServerCertificateRequest
<services/identitymanagement/model/GetServerCertificateRequest>` with the certificate's name.

**Imports**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/GetServerCertificate.java
   :lines: 15-18
   :language: java

**Code**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/GetServerCertificate.java
   :lines: 38-44
   :dedent: 8
   :language: java

See the :sdk-examples-java-iam:`complete example <GetServerCertificate.java>` on GitHub.


Listing Server Certificates
===========================

To list your server certificates, call the |iamclient|'s :methodname:`listServerCertificates` method
with a :aws-java-class:`ListServerCertificatesRequest
<services/identitymanagement/model/ListServerCertificatesRequest>`. It returns a
:aws-java-class:`ListServerCertificatesResult
<services/identitymanagement/model/ListServerCertificatesResult>`.

Call the returned :classname:`ListServerCertificateResult` object's
:methodname:`getServerCertificateMetadataList` method to get a list of
:aws-java-class:`ServerCertificateMetadata
<services/identitymanagement/model/ServerCertificateMetadata>` objects that you can use to get
information about each certificate.

Results may be truncated; if the :classname:`ListServerCertificateResult` object's
:methodname:`getIsTruncated` method returns :code-java:`true`, call the
:classname:`ListServerCertificatesRequest` object's :methodname:`setMarker` method and use it to
call :methodname:`listServerCertificates` again to get the next batch of results.

**Imports**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/ListServerCertificates.java
   :lines: 16-20
   :language: java

**Code**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/ListServerCertificates.java
   :lines: 28-51
   :dedent: 8
   :language: java

See the :sdk-examples-java-iam:`complete example <ListServerCertificates.java>` on GitHub.


Updating a Server Certificate
=============================

You can update a server certificate's name or path by calling the |iamclient|'s
:methodname:`updateServerCertificate` method. It takes a
:aws-java-class:`UpdateServerCertificateRequest
<services/identitymanagement/model/UpdateServerCertificateRequest>` object set with the server
certificate's current name and either a new name or new path to use.

**Imports**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/UpdateServerCertificate.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/UpdateServerCertificate.java
   :lines: 40-49
   :dedent: 8
   :language: java

See the :sdk-examples-java-iam:`complete example <UpdateServerCertificate.java>` on GitHub.


Deleting a Server Certificate
=============================

To delete a server certificate, call the |iamclient|'s :methodname:`deleteServerCertificate` method
with a :aws-java-class:`DeleteServerCertificateRequest
<services/identitymanagement/model/DeleteServerCertificateRequest>` containing the certificate's
name.

**Imports**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/DeleteServerCertificate.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/DeleteServerCertificate.java
   :lines: 38-46
   :dedent: 8
   :language: java

See the :sdk-examples-java-iam:`complete example <DeleteServerCertificate.java>` on GitHub.


More Information
================

* :iam-ug:`Working with Server Certificates <id_credentials_server-certs>` in the |iam-ug|
* :iam-api:`GetServerCertificate` in the |iam-api|
* :iam-api:`ListServerCertificates` in the |iam-api|
* :iam-api:`UpdateServerCertificate` in the |iam-api|
* :iam-api:`DeleteServerCertificate` in the |iam-api|
* |acm-ug|_
