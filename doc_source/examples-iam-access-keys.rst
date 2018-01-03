.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

##########################
Managing |IAM| Access Keys
##########################

.. meta::
   :description: How to manage IAM access keys with the AWS SDK for Java.
   :keywords: AWS SDK for Java, code examples, IAM access keys, creating, listing, updating,
              deleting, getting last access time


Creating an Access Key
======================

To create an |IAM| access key, call the |iamclient| :methodname:`createAccessKey` method with an
:aws-java-class:`CreateAccessKeyRequest <services/identitymanagement/model/CreateAccessKeyRequest>`
object.

:classname:`CreateAccessKeyRequest` has two constructors — one that takes a user name and another
with no parameters. If you use the version that takes no parameters, you must set the user name
using the :methodname:`withUserName` setter method before passing it to the
:methodname:`createAccessKey` method.

**Imports**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/CreateAccessKey.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/CreateAccessKey.java
   :lines: 39-45
   :dedent: 8
   :language: java

See the :sdk-examples-java-iam:`complete example <CreateAccessKey.java>` on GitHub.


Listing Access Keys
===================

To list the access keys for a given user, create a :aws-java-class:`ListAccessKeysRequest
<services/identitymanagement/model/ListAccessKeysRequest>` object that contains the user name to
list keys for, and pass it to the |iamclient|'s :methodname:`listAccessKeys` method.

.. note:: If you do not supply a user name to :methodname:`listAccessKeys`, it will attempt to list
   access keys associated with the AWS account that signed the request.

**Imports**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/ListAccessKeys.java
   :lines: 16-20
   :language: java

**Code**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/ListAccessKeys.java
   :lines: 39-61
   :dedent: 8
   :language: java

The results of :methodname:`listAccessKeys` are paged (with a default maximum of 100 records per
call). You can call :methodname:`getIsTruncated` on the returned
:aws-java-class:`ListAccessKeysResult <services/identitymanagement/model/ListAccessKeysResult>`
object to see if the query returned fewer results then are available. If so, then call
:methodname:`setMarker` on the :classname:`ListAccessKeysRequest` and pass it back to the next
invocation of :methodname:`listAccessKeys`.

See the :sdk-examples-java-iam:`complete example <ListAccessKeys.java>` on GitHub.


Retrieving an Access Key's Last Used Time
=========================================

To get the time an access key was last used, call the |iamclient|'s
:methodname:`getAccessKeyLastUsed` method with the access key's ID (which can be passed in using a
:aws-java-class:`GetAccessKeyLastUsedRequest
<services/identitymanagement/model/GetAccessKeyLastUsedRequest>` object, or directly to the overload
that takes the access key ID directly.

You can then use the returned :aws-java-class:`GetAccessKeyLastUsedResult
<services/identitymanagement/model/GetAccessKeyLastUsedResult>` object to retrieve the key's last
used time.

**Imports**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/AccessKeyLastUsed.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/AccessKeyLastUsed.java
   :lines: 38-47
   :dedent: 8
   :language: java

See the :sdk-examples-java-iam:`complete example <AccessKeyLastUsed.java>` on GitHub.


.. _iam-access-keys-update:

Activating or Deactivating Access Keys
======================================

You can activate or deactivate an access key by creating an :aws-java-class:`UpdateAccessKeyRequest
<services/identitymanagement/model/UpdateAccessKeyRequest>` object, providing the access key ID,
optionally the user name, and the desired :aws-java-class:`Status
<services/identitymanagement/model/StatusType>`, then passing the request object to the
|iamclient|'s :methodname:`updateAccessKey` method.

**Imports**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/UpdateAccessKey.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/UpdateAccessKey.java
   :lines: 41-49
   :dedent: 8
   :language: java

See the :sdk-examples-java-iam:`complete example <UpdateAccessKey.java>` on GitHub.


Deleting an Access Key
======================

To permanently delete an access key, call the |iamclient|'s :methodname:`deleteKey` method,
providing it with a :aws-java-class:`DeleteAccessKeyRequest
<services/identitymanagement/model/DeleteAccessKeyRequest>` containing the access key's ID and
username.

.. note:: Once deleted, a key can no longer be retrieved or used. To temporarily deactivate a key so
   that it can be activated again later, use :ref:`updateAccessKey <iam-access-keys-update>` method
   instead.

**Imports**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/DeleteAccessKey.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/DeleteAccessKey.java
   :lines: 39-46
   :dedent: 8
   :language: java

See the :sdk-examples-java-iam:`complete example <DeleteAccessKey.java>` on GitHub.


More Information
================

* :iam-api:`CreateAccessKey` in the |iam-api|
* :iam-api:`ListAccessKeys` in the |iam-api|
* :iam-api:`GetAccessKeyLastUsed` in the |iam-api|
* :iam-api:`UpdateAccessKey` in the |iam-api|
* :iam-api:`DeleteAccessKey` in the |iam-api|
