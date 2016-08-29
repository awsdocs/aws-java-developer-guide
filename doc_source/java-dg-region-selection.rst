.. Copyright 2010-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

####################
AWS Region Selection
####################

Regions enable you to access AWS services that reside physically in a specific geographic area. This
can be useful both for redundancy and to keep your data and applications running close to where you
and your users will access them.

.. contents::
   :depth: 1
   :local:

.. _region-selection-query-service:

Checking for Service Availability in an AWS Region
==================================================

To see if a particular AWS service is available in a region, use the isServiceSupported method on
the region that you'd like to use. For example:

.. code-block:: java

    Region.getRegion(Regions.US_WEST_2)
            .isServiceSupported(AmazonDynamoDB.ENDPOINT_PREFIX);

See the :java-api:`Regions <regions/Regions>` class documentation to see which regions can be
specified, and use the endpoint prefix of the service to query. Each service's endpoint prefix is defined in the service
interface. For example, Amazon DynamoDB's endpoint prefix is defined in :java-api:`AmazonDynamoDB <services/dynamodbv2/AmazonDynamoDB>`.


.. _region-selection-choose-region:

Choosing a Region
=================

Beginning with version 1.4 of the |sdk-java|, you can specify a region name and the SDK will
automatically choose an appropriate endpoint for you. If you want to choose the endpoint yourself,
see :ref:`region-selection-choose-endpoint`.

To explicitly set a region, it is recommended to use the :java-api:`Regions <regions/Regions>` enum
which is a enumeration of all publicly available regions. To create a client with a region from
the enum use the following code.

.. code-block:: java

    AmazonEC2 ec2 = AmazonEC2ClientBuilder.standard()
                        .withRegion(Regions.US_WEST_2)
                        .build();

If the region you are attempting to use is not in the Regions enum, you can also set the region
with just the name of the region. For example:

.. code-block:: java

    AmazonEC2 ec2 = AmazonEC2ClientBuilder.standard()
                        .withRegion("us-west-2")
                        .build();

Note that once a client has been built with the builder it is immutable and the region cannot be
changed. If you are working with multiple AWS Regions for the same service then you should create
multiple clients.

.. _region-selection-choose-endpoint:

Choosing a Specific Endpoint
============================

Each AWS client can be configured to use a specific endpoint by calling the setEndpoint method.

For example, to configure the |EC2| client to use the |euwest1-name|, use the following code:

.. code-block:: java

     AmazonEC2 ec2 = new AmazonEC2(myCredentials);
     ec2.setEndpoint("https://ec2.eu-west-1.amazonaws.com");

Go to |regions-and-endpoints|_ for the current list of regions and their corresponding endpoints for
all AWS services.


Determining Region from Environment
===================================

When running on Amazon EC2 or AWS Lambda, it's often desirable to configure clients with the same
region that your code is running on. This decouples your code from the environment it's running in
and makes it easier to deploy your application to multiple regions for lower latency or redundancy.

To have the SDK automatically detect the region your code is running in, you can use the client builders.
If you don't explicitly set a region via the withRegion methods, then the SDK will consult a default
region provider chain to try and determine the region to use.

The region lookup process is as follows
    #. Any explicit region set via the withRegion or setRegion on the builder itself takes precedence over anything else.
    #. First, the AWS_REGION environment variable is checked. If it's set that region will be used to configure the client. If not we move on.
        * Note that this environment variable is set by the AWS Lambda container
    #. Next the SDK will look at the AWS shared config file (usually located at ~/.aws/config). If the `region` property is present the SDK will use it.
        * The AWS_CONFIG_FILE environment variable can be used to customize the location of the shared config file.
        * The AWS_PROFILE environment variable or the aws.profile system property can be used to customize which profile is loaded by the SDK.
    #. Finally, if the SDK still hasn't found a region to use it will attempt to call the EC2 instance metadata service to determine the region of the current running EC2 instance.
    #. If the SDK still hasn't found a region at this point then client creation will fail with an exception.

A common approach to developing AWS applications is to use the shared config file to set the region for local
development and rely on the default region provider chain to determine the region when running on AWS
infrastructure. This greatly simplifies client creation and keeps your application portable.
