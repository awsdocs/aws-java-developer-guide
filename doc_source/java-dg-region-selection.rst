.. Copyright 2010-2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

####################
AWS Region Selection
####################

.. meta::
   :description: How to check service availability and choose an AWS Region and specific endpoints.
   :keywords:

Regions enable you to access AWS services that physically reside in a specific geographic area. This
can be useful both for redundancy and to keep your data and applications running close to where you
and your users will access them.


.. _region-selection-query-service:

Checking for Service Availability in an AWS Region
==================================================

To see if a particular AWS service is available in a region, use the
:methodname:`isServiceSupported` method on the region that you'd like to use.

::

    Region.getRegion(Regions.US_WEST_2)
        .isServiceSupported(AmazonDynamoDB.ENDPOINT_PREFIX);

See the :aws-java-class:`Regions <regions/Regions>` class documentation for the regions you can specify,
and use the endpoint prefix of the service to query. Each service's endpoint prefix is
defined in the service interface. For example, the |DDB| endpoint prefix is defined in
:aws-java-class:`AmazonDynamoDB <services/dynamodbv2/AmazonDynamoDB>`.


.. _region-selection-choose-region:

Choosing a Region
=================

Beginning with version 1.4 of the |sdk-java|, you can specify a region name and the SDK will
automatically choose an appropriate endpoint for you. To choose the endpoint yourself,
see :ref:`region-selection-choose-endpoint`.

To explicitly set a region, we recommend that you use the :aws-java-class:`Regions <regions/Regions>`
enum. This is an enumeration of all publicly available regions. To create a client with a region
from the enum, use the following code.

::

    AmazonEC2 ec2 = AmazonEC2ClientBuilder.standard()
                        .withRegion(Regions.US_WEST_2)
                        .build();

If the region you are attempting to use isn't in the :classname:`Regions` enum, you can set the
region using a *string* that represents the name of the region.

::

    AmazonEC2 ec2 = AmazonEC2ClientBuilder.standard()
                        .withRegion("us-west-2")
                        .build();

.. note:: After you build a client with the builder, it's *immutable* and the region *cannot
   be changed*. If you are working with multiple AWS Regions for the same service, you should
   create multiple clients |mdash| one per region.


.. _region-selection-choose-endpoint:

Choosing a Specific Endpoint
============================

Each AWS client can be configured to use a *specific endpoint* within a region by calling the
:methodname:`withEndpointConfiguration` method when creating the client.

For example, to configure the |S3| client to use the |euwest1-name|, use the following code.

::

     AmazonS3 s3 = AmazonS3ClientBuilder.standard()
          .withEndpointConfiguration(new EndpointConfiguration(
               "https://s3.eu-west-1.amazonaws.com",
               "eu-west-1"))
          .withCredentials(CREDENTIALS_PROVIDER)
          .build();


See |regions-and-endpoints|_ for the current list of regions and their corresponding endpoints for
all AWS services.

Automatically Determine the AWS Region from the Environment
=============================================================

.. important:: This section applies only when using a :doc:`client builder <creating-clients>` to
   access AWS services. AWS clients created by using the client constructor will not automatically
   determine region from the environment and will, instead, use the *default* SDK region
   (|region-sdk-default|).

When running on |EC2| or |LAM|, you might want to configure clients to use the same region
that your code is running on. This decouples your code from the environment it's running in and
makes it easier to deploy your application to multiple regions for lower latency or redundancy.

*You must use client builders to have the SDK automatically detect the region your code is running
in.*

To use the default credential/region provider chain to determine the region from the environment,
use the client builder's :methodname:`defaultClient` method.

::

    AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

This is the same as using :methodname:`standard` followed by :methodname:`build`.

::

    AmazonEC2 ec2 = AmazonEC2ClientBuilder.standard()
                        .build();

If you don't explicitly set a region using the :methodname:`withRegion` methods, the SDK
consults the default region provider chain to try and determine the region to use.


Default Region Provider Chain
-----------------------------

**The following is the region lookup process:**

#. Any explicit region set by using :methodname:`withRegion` or :methodname:`setRegion` on the builder
   itself takes precedence over anything else.

#. The :envvar:`AWS_REGION` environment variable is checked. If it's set, that region is
   used to configure the client.

   .. note:: This environment variable is set by the |LAM| container.

#. The SDK checks the AWS shared configuration file (usually located at :file:`~/.aws/config`). If
   the :paramname:`region` property is present, the SDK uses it.

   * The :envvar:`AWS_CONFIG_FILE` environment variable can be used to customize the location of the
     shared config file.

   * The :envvar:`AWS_PROFILE` environment variable or the :paramname:`aws.profile` system property
     can be used to customize the profile that is loaded by the SDK.

#. The SDK attempts to use the |EC2| instance metadata service to determine the region of the
   currently running |EC2| instance.

#. If the SDK still hasn't found a region by this point, client creation fails with an
   exception.

When developing AWS applications, a common approach is to use the *shared configuration file*
(described in :ref:`credentials-default`) to set the region for local development, and rely on the default region
provider chain to determine the region when running on AWS infrastructure. This greatly simplifies
client creation and keeps your application portable.

