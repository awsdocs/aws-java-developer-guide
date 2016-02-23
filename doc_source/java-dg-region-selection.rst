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

.. note:: The |sdk-java| uses |region-api-default| as the default region if you do not specify a
   region in your code. However, the |console| uses |region-console-default| as its default.
   Therefore, when using the |console| in conjunction with your development, :emphasis:`be sure to
   specify the same region in both your code and the console`.

.. contents::
   :depth: 1
   :local:

.. _region-selection-query-service:

Checking for Service Availability in an AWS Region
==================================================

To see if a particular AWS service is available in a region, use the isServiceSupported method on
the region that you'd like to use. For example:

.. code-block:: java

    Region.getRegion(Regions.US_WEST_2).isServiceSupported(ServiceAbbreviations.Dynamodb);

See the :java-api:`Regions <regions/Regions>` class documentation to see which regions can be
specified, and see :java-api:`ServiceAbbrevations <regions/ServiceAbbreviations>` for the list of
services that you can query.


.. _region-selection-choose-region:

Choosing a Region
=================

Beginning with version 1.4 of the |sdk-java|, you can specify a region name and the SDK will
automatically choose an appropriate endpoint for you. If you want to choose the endpoint yourself,
see :ref:`region-selection-choose-endpoint`.

The Region.getRegion method will retrieve a Region object, which you can use to create a new client
that is configured to use that region. For example:

.. code-block:: java

    AmazonEC2 ec2 = Region.getRegion(Regions.US_WEST_2).createClient(
      AmazonEC2Client.class, credentials, clientConfig);

To choose a region for an :emphasis:`existing AWS client`, call the setRegion method on the client
object. For example:

.. code-block:: java

    AmazonEC2 ec2 = new AmazonEC2(myCredentials);
    ec2.setRegion(Region.getRegion(Regions.US_WEST_2));

.. important:: :methodname:`setRegion` is not thread-safe, so you should be careful when changing
   the region for an existing client. To avoid potential thread synchronization issues, create a
   :emphasis:`new` client object for each region that you are using.


.. _region-selection-choose-endpoint:

Choosing a Specific Endpoint
============================

Each AWS client can be configured to use a specific endpoint by calling the setEndpoint method.

For example, to configure the |EC2| client to use the |euwest1-name|, use the following code:

.. code-block:: java

     AmazonEC2 ec2 = new AmazonEC2(myCredentials); ec2.setEndpoint("https://ec2.eu-west-1.amazonaws.com");

Go to |regions-and-endpoints|_ for the current list of regions and their corresponding endpoints for
all AWS services.


Developing Code that Accesses Multiple AWS Regions
==================================================

Regions are logically isolated from each other; cross-region resource use is prohibited. This means
that you can't access *US East* resources when communicating with the *EU West* endpoint, for
example.

If your code accesses multiple AWS regions, instantiate a specific client for each region:

.. code-block:: java

    AmazonEC2 ec2_euro = Region.getRegion(Regions.EU_WEST_1).createClient(
      AmazonEC2Client.class, credentials, clientConfig);

    AmazonEC2 ec2_us = Region.getRegion(Regions.US_EAST_1).createClient(
      AmazonEC2Client.class, credentials, clientConfig);
