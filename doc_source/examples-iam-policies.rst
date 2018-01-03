.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

###########################
Working with |IAM| Policies
###########################

.. meta::
   :description: How to set, get, and delete a policy for an Amazon S3 bucket.
   :keywords: AWS for Java SDK code examples, bucket policies

Creating a Policy
=================

To create a new policy, provide the policy's name and a JSON-formatted policy document in a
:aws-java-class:`CreatePolicyRequest <services/identitymanagement/model/CreatePolicyRequest>` to the
|iamclient|'s :methodname:`createPolicy` method.

**Imports**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/CreatePolicy.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/CreatePolicy.java
   :lines: 62-69
   :dedent: 8
   :language: java

|iam| policy documents are JSON strings with a :iam-ug:`well-documented syntax
<reference_policies_grammar>`. Here is an example that provides access to make particular requests
to |ddb|.

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/CreatePolicy.java
   :lines: 26-47
   :dedent: 4
   :language: java

See the :sdk-examples-java-iam:`complete example <CreatePolicy.java>` on GitHub.


Getting a Policy
================

To retrieve an existing policy, call the |iamclient|'s :methodname:`getPolicy` method, providing the
policy's ARN within a :aws-java-class:`GetPolicyRequest
<services/identitymanagement/model/GetPolicyRequest>` object.

**Imports**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/GetPolicy.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/GetPolicy.java
   :lines: 39-45
   :dedent: 8
   :language: java

See the :sdk-examples-java-iam:`complete example <GetPolicy.java>` on GitHub.


Attaching a Role Policy
=======================

You can attach a policy to an |IAM| :iam-ug:`role <id_roles>` by calling the |iamclient|'s
:methodname:`attachRolePolicy` method, providing it with the role name and policy ARN in an
:aws-java-class:`AttachRolePolicyRequest
<services/identitymanagement/model/AttachRolePolicyRequest>`.

**Imports**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/AttachRolePolicy.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/AttachRolePolicy.java
   :lines: 43-45, 76-81
   :dedent: 8
   :language: java

See the :sdk-examples-java-iam:`complete example <AttachRolePolicy.java>` on GitHub.


Listing Attached Role Policies
==============================

List attached policies on a role by calling the |iamclient|'s :methodname:`listAttachedRolePolicies`
method. It takes a :aws-java-class:`ListAttachedRolePoliciesRequest
<services/identitymanagement/model/ListAttachedRolePoliciesRequest>` object that contains the role
name to list the policies for.

Call :methodname:`getAttachedPolicies` on the returned
:aws-java-class:`ListAttachedRolePoliciesResult
<services/identitymanagement/model/ListAttachedRolePoliciesResult>` object to get the list of
attached policies. Results may be truncated; if the :classname:`ListAttachedRolePoliciesResult`
object's :methodname:`getIsTruncated` method returns :code-java:`true`, call the
:classname:`ListAttachedRolePoliciesRequest` object's :methodname:`setMarker` method and use it to
call :methodname:`listAttachedRolePolicies` again to get the next batch of results.

**Imports**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/AttachRolePolicy.java
   :lines: 16-17, 20-24
   :language: java

**Code**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/AttachRolePolicy.java
   :lines: 43-68
   :dedent: 8
   :language: java

See the :sdk-examples-java-iam:`complete example <AttachRolePolicy.java>` on GitHub.


Detaching a Role Policy
=======================

To detach a policy from a role, call the |iamclient|'s :methodname:`detachRolePolicy` method,
providing it with the role name and policy ARN in a :aws-java-class:`DetachRolePolicyRequest
<services/identitymanagement/model/DetachRolePolicyRequest>`.

**Imports**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/DetachRolePolicy.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/DetachRolePolicy.java
   :lines: 39-46
   :dedent: 8
   :language: java

See the :sdk-examples-java-iam:`complete example <DetachRolePolicy.java>` on GitHub.


More Information
================

* :iam-ug:`Overview of IAM Policies <access_policies>` in the |iam-ug|.
* :iam-ug:`AWS IAM Policy Reference <reference_policies>` in the |iam-ug|.
* :iam-api:`CreatePolicy` in the |iam-api|
* :iam-api:`GetPolicy` in the |iam-api|
* :iam-api:`AttachRolePolicy` in the |iam-api|
* :iam-api:`ListAttachedRolePolicies` in the |iam-api|
* :iam-api:`DetachRolePolicy` in the |iam-api|
