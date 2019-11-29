.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

#####################################################
Managing Access to |S3| Buckets Using Bucket Policies
#####################################################

.. meta::
   :description: How to set, get, and delete a policy for an Amazon S3 bucket.
   :keywords: AWS for Java SDK code examples, bucket policies


You can set, get, or delete a *bucket policy* to manage access to your |S3| buckets.

.. _set-s3-bucket-policy:

Set a Bucket Policy
===================

You can set the bucket policy for a particular S3 bucket by:

* Calling the |s3client| client's :methodname:`setBucketPolicy` and providing it with a
  :aws-java-class:`SetBucketPolicyRequest <services/s3/model/SetBucketPolicyRequest>`
* Setting the policy directly by using the :methodname:`setBucketPolicy` overload that takes
  a bucket name and policy text (in JSON format)

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/SetBucketPolicy.java
   :lines: 16-18
   :language: java

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/SetBucketPolicy.java
   :dedent: 8
   :lines: 81-86
   :language: java


.. _use-s3-bucket-policy-class:

Use the Policy Class to Generate or Validate a Policy
-----------------------------------------------------

When providing a bucket policy to :methodname:`setBucketPolicy`, you can do the following:

* Specify the policy directly as a string of JSON-formatted text
* Build the policy using the :aws-java-class:`Policy <auth/policy/Policy>` class

By using the :classname:`Policy` class, you don't have to be concerned about correctly formatting
your text string. To get the JSON policy text from the :classname:`Policy` class, use its
:methodname:`toJson` method.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/SetBucketPolicy.java
   :lines: 19-24
   :language: java

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/SetBucketPolicy.java
   :dedent: 8
   :lines: 70-77
   :language: java

The :classname:`Policy` class also provides a :methodname:`fromJson` method that can attempt to
build a policy using a passed-in JSON string. The method validates it to ensure that the text
can be transformed into a valid policy structure, and will fail with an :code-java:`IllegalArgumentException`
if the policy text is invalid.

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/SetBucketPolicy.java
   :dedent: 8
   :lines: 55-63
   :language: java

You can use this technique to prevalidate a policy that you read in from a file or other means.

See the :sdk-examples-java-s3:`complete example <SetBucketPolicy.java>` on GitHub.


.. _get-s3-bucket-policy:

Get a Bucket Policy
===================

To retrieve the policy for an |S3| bucket, call the |s3client| client's
:methodname:`getBucketPolicy` method, passing it the name of the bucket to get the policy from.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/GetBucketPolicy.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/GetBucketPolicy.java
   :dedent: 6
   :lines: 49-56
   :language: java

If the named bucket doesn't exist, if you don't have access to it, or if it has no bucket policy,
an :classname:`AmazonServiceException` is thrown.

See the :sdk-examples-java-s3:`complete example <GetBucketPolicy.java>` on GitHub.


.. _delete-s3-bucket-policy:

Delete a Bucket Policy
======================

To delete a bucket policy, call the |s3client| client's :methodname:`deleteBucketPolicy`,
providing it with the bucket name.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/DeleteBucketPolicy.java
   :lines: 16-18
   :language: java

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/DeleteBucketPolicy.java
   :dedent: 6
   :lines: 48-54
   :language: java

This method succeeds even if the bucket doesn't already have a policy. If you specify a bucket
name that doesn't exist or if you don't have access to the bucket, an :classname:`AmazonServiceException`
is thrown.

See the :sdk-examples-java-s3:`complete example <DeleteBucketPolicy.java>` on GitHub.

More Info
=========

* :s3-dg:`Access Policy Language Overview <access-policy-language-overview>` in the |S3-dg|
* :s3-dg:`Bucket Policy Examples <example-bucket-policies>` in the |S3-dg|
