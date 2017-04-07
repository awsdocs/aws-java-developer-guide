.. Copyright 2010-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

#######################################
Configuring an |s3| Bucket as a Website
#######################################

.. meta::
   :description: How to set, retrieve, or delete an S3 bucket's website configuration.
   :keywords: AWS for Java SDK code examples, s3, website, website configuration

You can configure an |s3| bucket to behave as a website. To do this, you need to set its website
configuration.

.. include:: includes/examples-note.txt

Set a Bucket's Website Configuration
====================================

To set an |s3| bucket's website configuration, call the |s3client|'s
:methodname:`setWebsiteConfiguration` method with the bucket name to set the configuration for, and
a :aws-java-class:`BucketWebsiteConfiguration <services/s3/model/BucketWebsiteConfiguration>` object
containing the bucket's website configuration.

Setting an index document is *required*; all other parameters are optional.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/SetWebsiteConfiguration.java
   :lines: 15-18

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/SetWebsiteConfiguration.java
   :dedent: 8
   :lines: 31-50

.. note:: Setting a website configuration does not modify the access permissions for your bucket.
   To make your files visible on the web, you will also need to set a *bucket policy* that allows
   public read access to the files in the bucket. For more information, see
   :doc:`examples-s3-bucket-policies`.

See the :sdk-examples-java-s3:`complete example <SetWebsiteConfiguration.java>`.


Get a Bucket's Website Configuration
====================================

To get an |s3| bucket's website configuration, call the |s3client|'s
:methodname:`getWebsiteConfiguration` method with the name of the bucket to retrieve the
configuration for.

The configuration will be returned as a :aws-java-class:`BucketWebsiteConfiguration
<services/s3/model/BucketWebsiteConfiguration>` object. If there is no website configuration for the
bucket, then :code-java:`null` will be returned.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/GetWebsiteConfiguration.java
   :lines: 15-18

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/GetWebsiteConfiguration.java
   :dedent: 8
   :lines: 30-46

See the :sdk-examples-java-s3:`complete example <GetWebsiteConfiguration.java>`.


Delete a Bucket's Website Configuration
=======================================

To delete an |s3| bucket's website configuration, call the |s3client|'s
:methodname:`deleteWebsiteConfiguration` method with the name of the bucket to delete the
configuration from.

**Imports**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/DeleteWebsiteConfiguration.java
   :lines: 15-17

**Code**

.. literalinclude:: example_code/s3/src/main/java/aws/example/s3/DeleteWebsiteConfiguration.java
   :dedent: 8
   :lines: 29-36

See the :sdk-examples-java-s3:`complete example <DeleteWebsiteConfiguration.java>`.


More Information
================

* :s3-api:`PUT Bucket website <RESTBucketPUTwebsite>` in the |s3-api|
* :s3-api:`GET Bucket website <RESTBucketGETwebsite>` in the |s3-api|
* :s3-api:`DELETE Bucket website <RESTBucketDELETEwebsite>` in the |s3-api|

