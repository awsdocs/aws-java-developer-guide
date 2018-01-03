.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

########################################################
Managing |S3| Access Permissions for Buckets and Objects
########################################################

.. meta::
   :description: How to retrieve or set the access control list for an Amazon S3 bucket.
   :keywords: AWS for Java SDK code examples, bucket access permissions

You can use access control lists (ACLs) for |s3| buckets and objects for fine-grained control over
your |s3| resources.

.. include:: includes/examples-note.txt


Get the Access Control List for a Bucket
========================================

To get the current ACL for a bucket, call the |s3client|'s :methodname:`getBucketAcl` method,
passing it the *bucket name* to query. This method returns an :aws-java-class:`AccessControlList
<services/s3/model/AccessControlList>` object. To get each access grant in the list, call its
:methodname:`getGrantsAsList` method, which will return a standard Java list of
:aws-java-class:`Grant <services/s3/model/Grant>` objects.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/GetAcl.java
   :lines: 16-21
   :language: java

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/GetAcl.java
   :lines: 35-46
   :dedent: 8
   :language: java

See the :sdk-examples-java-s3:`complete example <GetAcl.java>` on GitHub.


Set the Access Control List for a Bucket
========================================

To add or modify permissions to an ACL for a bucket, call the |s3client|'s
:methodname:`setBucketAcl` method. It takes an :aws-java-class:`AccessControlList
<services/s3/model/AccessControlList>` object that contains a list of grantees and access levels to
set.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/SetAcl.java
   :lines: 15-21
   :language: java

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/SetAcl.java
   :lines: 35-48
   :dedent: 8
   :language: java

.. note:: You can provide the grantee's unique identifier directly using the
   :aws-java-class:`Grantee <services/s3/model/Grantee>` class, or use the
   :aws-java-class:`EmailAddressGrantee <services/s3/model/EmailAddressGrantee>` class to set the
   grantee by email, as we've done here.

See the :sdk-examples-java-s3:`complete example <SetAcl.java>` on GitHub.


Get the Access Control List for an Object
=========================================

To get the current ACL for an object, call the |s3client|'s :methodname:`getObjectAcl` method,
passing it the *bucket name* and *object name* to query. Like :methodname:`getBucketAcl`, this
method returns an :aws-java-class:`AccessControlList <services/s3/model/AccessControlList>` object
that you can use to examine each :aws-java-class:`Grant <services/s3/model/Grant>`.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/GetAcl.java
   :lines: 16-21
   :language: java

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/GetAcl.java
   :lines: 54-65
   :dedent: 8
   :language: java

See the :sdk-examples-java-s3:`complete example <GetAcl.java>` on GitHub.


Set the Access Control List for an Object
=========================================

To add or modify permissions to an ACL for an object, call the |s3client|'s
:methodname:`setObjectAcl` method. It takes an :aws-java-class:`AccessControlList
<services/s3/model/AccessControlList>` object that contains a list of grantees and access levels to
set.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/SetAcl.java
   :lines: 15-21
   :language: java

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/SetAcl.java
   :lines: 56-69
   :dedent: 4
   :language: java

.. note:: You can provide the grantee's unique identifier directly using the
   :aws-java-class:`Grantee <services/s3/model/Grantee>` class, or use the
   :aws-java-class:`EmailAddressGrantee <services/s3/model/EmailAddressGrantee>` class to set the
   grantee by email, as we've done here.

See the :sdk-examples-java-s3:`complete example <SetAcl.java>` on GitHub.

More Information
================

* :s3-api:`GET Bucket acl <RESTBucketGETacl>` in the |s3-api|
* :s3-api:`PUT Bucket acl <RESTBucketPUTacl>` in the |s3-api|
* :s3-api:`GET Object acl <RESTObjectGETacl>` in the |s3-api|
* :s3-api:`PUT Object acl <RESTObjectPUTacl>` in the |s3-api|
