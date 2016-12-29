.. Copyright 2010-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

############################
Setting |S3| Bucket Policies
############################

You can set a *bucket policy* to manage access to your |S3| buckets.

.. _set-s3-bucket-policy:

Set a Bucket Policy
===================

To set the bucket policy for a particular S3 bucket, call the |s3client| client's
:methodname:`setBucketPolicy`, providing it with either a :aws-java-class:`SetBucketPolicyRequest
<services/s3/model/SetBucketPolicyRequest>`, or you can set the policy directly by using the
:methodname:`setBucketPolicy` overload that takes a bucket name and policy text (in JSON format)

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/SetBucketPolicy.java
   :lines: 16-18

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/SetBucketPolicy.java
   :dedent: 6
   :lines: 113-119


.. _use-s3-bucket-policy-class:

Using the Policy class to generate or validate a policy
-------------------------------------------------------

When providing a bucket policy to :methodname:`setBucketPolicy`, you can:

* specify the policy *directly as a string of JSON-formatted text*.
* *build the policy* using the :aws-java-class:`Policy <auth/policy/Policy>` class.

Using the :classname:`Policy` class will relieve you of the need to correctly format your text
string. For example:

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/SetBucketPolicy.java
   :lines: 19-24

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/SetBucketPolicy.java
   :dedent: 6
   :lines: 68-73

To get the JSON policy text from the Policy class, use its :methodname:`toJson` method:

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/SetBucketPolicy.java
   :dedent: 6
   :lines: 74

The :classname:`Policy` class also provides a :methodname:`fromJson` method that can will attempt to
build a policy using a passed-in JSON string. The method will validate it to make sure that the text
can be transformed into a valid policy structure, and will fail with an IllegalArgumentException if
the policy text is invalid.

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/SetBucketPolicy.java
   :dedent: 6
   :lines: 56-62

You can use this technique to pre-validate a policy that you read in from a file or other means.


.. _get-s3-bucket-policy:

Get a Bucket Policy
===================

To retrieve the policy for an |S3| bucket, call the S3 |s3client| client's
:methodname:`getBucketPolicy` method, passing it the name of the bucket to get the policy from.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/GetBucketPolicy.java
   :lines: 16-19

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/GetBucketPolicy.java
   :dedent: 6
   :lines: 49-56

If the named bucket doesn't exist, if you don't have access to it, or if the bucket has no policy,
an :classname:`AmazonServiceException` will result.

.. _delete-s3-bucket-policy:

Delete a Bucket Policy
======================

To delete a bucket policy, call the S3 |s3client| client's :methodname:`deleteBucketPolicy`,
providing it with the bucket name.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/DeleteBucketPolicy.java
   :lines: 16-18

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/DeleteBucketPolicy.java
   :dedent: 6
   :lines: 48-54

This method will succeed even if the bucket doesn't already have a policy. If you specify a bucket
name that doesn't exist or if you don't have access to it, an :classname:`AmazonServiceException`
will result.

See Also
========

* :s3-dg:`Access Policy Language Overview <access-policy-language-overview>` in the |S3-dg|.
* :s3-dg:`Bucket Policy Examples <example-bucket-policies>` in the |S3-dg|.

