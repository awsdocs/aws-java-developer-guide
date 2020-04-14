.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

#################################################
Set up AWS Credentials and Region for Development
#################################################

.. meta::
   :description: Set up default AWS credentials and region for development with the AWS SDK for
                 Java.
   :keywords: AWS region, AWS credentials, setup, shared credentials file, shared config file,
              environment variable

To connect to any of the supported services with the |sdk-java|, you must provide AWS credentials.
The AWS SDKs and CLIs use :emphasis:`provider chains` to look for AWS credentials in a number of
different places, including system/user environment variables and local AWS configuration files.

This topic provides basic information about setting up your AWS credentials for local application
development using the |sdk-java|. If you need to set up credentials for use within an EC2 instance
or if you're using the Eclipse IDE for development, refer to the following topics instead:

* When using an EC2 instance, create an IAM role and then give your EC2 instance access to that role
  as shown in :doc:`java-dg-roles`.

* Set up AWS credentials within Eclipse using the |tke|_. See :tke-ug:`Set up AWS Credentials
  <setup-credentials>` in the |tke-ug|_ for more information.

.. _setup-credentials-setting:

Setting AWS Credentials
=======================

Setting your credentials for use by the |sdk-java| can be done in a number of ways, but here are the
recommended approaches:

.. The following file is in the shared content at https://github.com/awsdocs/aws-doc-shared-content

.. include:: common/sdk-shared-credentials.txt

Once you have set your AWS credentials using one of these methods, they will be loaded automatically
by the |sdk-java| by using the default credential provider chain. For further information about
working with AWS credentials in your Java applications, see :doc:`credentials`.


.. _refresh-credentials:
Refreshing IMDS credentials
===========================

The |sdk-java| supports opt-in refreshing IMDS credentials in the background every 1 minute, regardless of the credential expiration time.
This allows you to refresh credentials more frequently and reduces the chance that not reaching IMDS impacts
the perceived AWS availability.

.. code-block:: java
   :linenos:

    1. // Refresh credentials using a background thread, automatically every minute. This will log an error if IMDS is down during
    2. // a refresh, but your service calls will continue using the cached credentials until the credentials are refreshed
    3. // again one minute later.
    4.
    5. InstanceProfileCredentialsProvider credentials =
    6.     InstanceProfileCredentialsProvider.createAsyncRefreshingProvider(true);
    7.
    8. AmazonS3Client.builder()
    9.              .withCredentials(credentials)
    10.              .build();
    11.
    12. // This is new: When you are done with the credentials provider, you must close it to release the background thread.
    13. credentials.close();


.. _setup-credentials-setting-region:

Setting the AWS Region
======================

You should set a default AWS Region that will be used for accessing AWS services with the AWS SDK
for Java. For the best network performance, choose a region that's geographically close
to you (or to your customers). For a list of regions for each service, see |regions-and-endpoints|_
in the |AWS-gr|.

.. note:: If you *don't* select a region, then |region-api-default| will be used by default.

You can use similar techniques to setting credentials to set your default AWS region:

.. The following file is in the shared content at https://github.com/awsdocs/aws-doc-shared-content

.. include:: common/sdk-shared-region.txt

.. toctree::
   :maxdepth: 1
   :titlesonly:

   prog-services-sts