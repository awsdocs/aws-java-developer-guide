.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

###########################
Using |IAM| Account Aliases
###########################

.. meta::
   :description: How to set, get, and delete a policy for an Amazon S3 bucket.
   :keywords: AWS for Java SDK code examples, bucket policies

If you want the URL for your sign-in page to contain your company name or other friendly identifier
instead of your AWS account ID, you can create an alias for your AWS account.

.. note:: AWS supports exactly one account alias per account.


Creating an Account Alias
=========================

To create an account alias, call the |iamclient|'s :methodname:`createAccountAlias` method with a
:aws-java-class:`CreateAccountAliasRequest
<services/identitymanagement/model/CreateAccountAliasRequest>` object that contains the alias name.

**Imports**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/CreateAccountAlias.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/CreateAccountAlias.java
   :lines: 38-44
   :dedent: 8
   :language: java

See the :sdk-examples-java-iam:`complete example <CreateAccountAlias.java>` on GitHub.


Listing Account Aliases
=======================

To list your account's alias, if any, call the |iamclient|'s :methodname:`listAccountAliases`
method.

.. note:: The returned :aws-java-class:`ListAccountAliasesResponse
   <services/identitymanagement/model/ListAccountAliasesResponse>` supports the same
   :methodname:`getIsTruncated` and :methodname:`getMarker` methods as other |sdk-java| *list*
   methods, but an AWS account can have only *one* account alias.

**imports**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/ListAccountAliases.java
   :lines: 16-18
   :language: java

**code**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/ListAccountAliases.java
   :lines: 26-33
   :dedent: 8
   :language: java

see the :sdk-examples-java-iam:`complete example <ListAccountAliases.java>` on GitHub.


Deleting an account alias
=========================

To delete your account's alias, call the |iamclient|'s :methodname:`deleteAccountAlias` method. When
deleting an account alias, you must supply its name using a
:aws-java-class:`DeleteAccountAliasRequest
<services/identitymanagement/model/DeleteAccountAliasRequest>` object.

**imports**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/DeleteAccountAlias.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/DeleteAccountAlias.java
   :lines: 38-44
   :dedent: 8
   :language: java

See the :sdk-examples-java-iam:`complete example <DeleteAccountAlias.java>` on GitHub.

More Information
================

* :iam-ug:`Your AWS Account ID and Its Alias <console_account-alias>` in the |iam-ug|
* :iam-api:`CreateAccountAlias` in the |iam-api|
* :iam-api:`ListAccountAliases` in the |iam-api|
* :iam-api:`DeleteAccountAlias` in the |iam-api|
