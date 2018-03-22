.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

################
Document History
################

This topic describes important changes to the |sdk-java-dg| over the course of its history.

**This documentation was built on:** |today|

Mar 22, 2018
  Removed managing Tomcat sessions in |DDB| example as that tool is no longer supported.

Nov 2, 2017
  Added cryptography examples for |S3| encryption client, including new topics:
  :doc:`examples-crypto` and :doc:`examples-crypto-kms` and :doc:`examples-crypto-masterkey`.

Apr 14, 2017
   Made a number of updates to the :doc:`examples-s3` section, including new topics:
   :doc:`examples-s3-access-permissions` and :doc:`examples-s3-website-configuration`.

Apr 04, 2017
   A new topic, :doc:`generating-sdk-metrics` describes how to generate application and SDK
   performance metrics for the |sdk-java|.

Apr 03, 2017
   Added new |CW| examples to the :doc:`examples-cloudwatch` section:
   :doc:`examples-cloudwatch-get-metrics`, :doc:`examples-cloudwatch-publish-custom-metrics`,
   :doc:`examples-cloudwatch-create-alarms`, :doc:`examples-cloudwatch-use-alarm-actions`, and
   :doc:`examples-cloudwatch-send-events`

Mar 27, 2017
   Added more |EC2| examples to the :doc:`prog-services-ec2` section: :doc:`examples-ec2-instances`,
   :doc:`examples-ec2-elastic-ip`, :doc:`examples-ec2-regions-zones`, :doc:`examples-ec2-key-pairs`,
   and :doc:`examples-ec2-security-groups`.

Mar 21, 2017
   Added a new set of |IAM| examples to the :doc:`examples-iam` section:
   :doc:`examples-iam-access-keys`, :doc:`examples-iam-users`, :doc:`examples-iam-account-aliases`,
   :doc:`examples-iam-policies`, and :doc:`examples-iam-server-certificates`

Mar 13, 2017
   Added three new topics to the |SQS| section: :doc:`examples-sqs-long-polling`,
   :doc:`examples-sqs-visibility-timeout`, and :doc:`examples-sqs-dead-letter-queues`.

Jan 26, 2017
   Added a new |S3| topic, :doc:`examples-s3-transfermanager`, and a new :doc:`best-practices` topic
   in the :doc:`basics` section.

Jan 16, 2017
   Added a new |S3| topic, :doc:`examples-s3-bucket-policies`, and two new |SQS| topics,
   :doc:`examples-sqs-message-queues` and :doc:`examples-sqs-messages`.

Dec 16, 2016
   Added new example topics for |DDB|: :doc:`examples-dynamodb-tables` and
   :doc:`examples-dynamodb-items`.

Sep 26, 2016
   The topics in the **Advanced** section have been moved into :doc:`basics`, since they really are
   central to using the SDK.

Aug 25, 2016
   A new topic, :doc:`creating-clients`, has been added to :doc:`basics`, which demonstrates how to
   use *client builders* to simplify the creation of AWS service clients.

   The :doc:`prog-services` section has been updated with :doc:`new examples for S3 <examples-s3>`
   which are backed by a `repository on GitHub <sdk-doc-examples_>`_ that contains the complete
   example code.

May 02, 2016
   A new topic, :doc:`basics-async`, has been added to the :doc:`basics` section, describing how to
   work with asynchronous client methods that return :classname:`Future` objects or that take an
   :classname:`AsyncHandler`.

Apr 26, 2016
   The *SSL Certificate Requirements* topic has been removed, since it is no longer relevant.
   Support for SHA-1 signed certificates was deprecated in 2015 and the site that housed the test
   scripts has been removed.

Mar 14, 2016
   Added a new topic to the |SWF| section: :doc:`swf-lambda-task`, which describes how to implement
   a |SWF| workflow that calls |LAM| functions as tasks as an alternative to using traditional |SWF|
   activities.

Mar 04, 2016
   The :doc:`prog-services-swf` section has been updated with new content:

   * :doc:`swf-basics` |ndash| Provides basic information about how to include SWF in your projects.

   * :doc:`swf-hello` |ndash| A new tutorial that provides step-by-step guidance for Java developers
     new to |SWF|.

   * :doc:`swf-graceful-shutdown` |ndash| Describes how you can gracefully shut down |SWF| worker
     classes using Java's concurrency classes.

Feb 23, 2016
   The source for the |sdk-java-dg| has been moved to :github:`aws-java-developer-guide
   <awsdocs/aws-java-developer-guide>`.

Dec 28, 2015
   :doc:`java-dg-jvm-ttl` has been moved from **Advanced** into :doc:`basics`, and has been
   rewritten for clarity.

   :doc:`setup-project-maven` has been updated with information about how to include the SDK's bill
   of materials (BOM) in your project.

Aug 04, 2015
   *SSL Certificate Requirements* is a new topic in the :doc:`getting-started` section that
   describes AWS' move to SHA256-signed certificates for SSL connections, and how to fix early 1.6
   and previous Java environments to use these certificates, which are :emphasis:`required` for AWS
   access after September 30, 2015.

   .. note:: Java 1.7+ is already capable of working with SHA256-signed certificates.

May 14, 2014
   The :doc:`introduction <welcome>` and :doc:`getting started <getting-started>` material has been
   heavily revised to support the new guide structure and now includes guidance about how to
   :doc:`setup-credentials`.

   The discussion of :doc:`code samples <java-dg-samples>` has been moved into its own topic in the
   :ref:`additional-resources` section.

   Information about how to :ref:`view the SDK revision history <java-sdk-history>` has been moved
   into the introduction.

May 9, 2014
   The overall structure of the |sdk-java| documentation has been simplified, and the
   :doc:`getting-started` and :ref:`additional-resources` topics have been updated.

   New topics have been added:

   * :doc:`credentials` |ndash| discusses the various ways that you can specify credentials for use
     with the |sdk-java|.

   * :doc:`java-dg-roles` |ndash| provides information about how to securely specify credentials for
     applications running on EC2 instances.

Sep 9, 2013
   This topic, *Document History*, tracks changes to the |sdk-java-dg|. It is intended as a
   companion to the release notes history.
