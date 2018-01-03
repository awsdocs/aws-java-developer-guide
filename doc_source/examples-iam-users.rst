.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

####################
Managing |IAM| Users
####################

.. meta::
   :description: How to set, get, and delete a policy for an Amazon S3 bucket.
   :keywords: AWS for Java SDK code examples, bucket policies

Creating a User
===============

Create a new |IAM| user by providing the user name to the |iamclient|'s :methodname:`createUser`
method, either directly or using a :aws-java-class:`CreateUserRequest
<services/identitymanagement/model/CreateUserRequest>` object containing the user name.

**Imports**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/CreateUser.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/CreateUser.java
   :lines: 39-45
   :dedent: 8
   :language: java

See the :sdk-examples-java-iam:`complete example <CreateUser.java>` on GitHub.


Listing Users
=============

To list the |IAM| users for your account, create a new :aws-java-class:`ListUsersRequest
<services/identitymanagement/model/ListUsersRequest>` and pass it to the |iamclient|'s
:methodname:`listUsers` method. You can retrieve the list of users by calling :methodname:`getUsers`
on the returned :aws-java-class:`ListUsersResponse
<services/identitymanagement/model/ListUsersResponse>` object.

The list of users returned by :methodname:`listUsers` is paged. You can check to see there are more
results to retrieve by calling the response object's :methodname:`getIsTruncated` method. If it
returns :code-java:`true`, then call the request object's :methodname:`setMarker()` method, passing
it the return value of the response object's :methodname:`getMarker()` method.

**Imports**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/ListUsers.java
   :lines: 16-20
   :language: java

**Code**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/ListUsers.java
   :lines: 28-46
   :dedent: 8
   :language: java

See the :sdk-examples-java-iam:`complete example <ListUsers.java>` on GitHub.


Updating a User
===============

To update a user, call the |iamclient| object's :methodname:`updateUser` method, which takes a
:aws-java-class:`UpdateUserRequest <services/identitymanagement/model/UpdateUserRequest>` object
that you can use to change the user's *name* or *path*.

**Imports**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/UpdateUser.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/UpdateUser.java
   :lines: 40-47
   :dedent: 8
   :language: java

See the :sdk-examples-java-iam:`complete example <UpdateUser.java>` on GitHub.


Deleting a User
===============

To delete a user, call the |iamclient|'s :methodname:`deleteUser` request with a
:aws-java-class:`UpdateUserRequest <services/identitymanagement/model/UpdateUserRequest>` object set
with the user name to delete.

**Imports**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/DeleteUser.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/iam/src/main/java/aws/example/iam/DeleteUser.java
   :lines: 39-51
   :dedent: 8
   :language: java

See the :sdk-examples-java-iam:`complete example <DeleteUser.java>` on GitHub.

More Information
================

* :iam-ug:`IAM Users <id_users>` in the |iam-ug|
* :iam-ug:`Managing IAM Users <id_users_manage>` in the |iam-ug|
* :iam-api:`CreateUser` in the |iam-api|
* :iam-api:`ListUsers` in the |iam-api|
* :iam-api:`UpdateUser` in the |iam-api|
* :iam-api:`DeleteUser` in the |iam-api|
