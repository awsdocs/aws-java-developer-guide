.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

###############################
Creating Campaigns in |PINlong|
###############################

.. meta::
   :description: How to create a campaign in Amazon pinpoint.
   :keywords: AWS for Java SDK code examples, amazon pinpoint campaign

You can use campaigns to help increase engagement between your app and your users.
You can create a campaign to reach out to a particular segment of your users with tailored
messages or special promotions. This example demonstrates how to create a new
standard campaign that sends a custom push notification to a specified segment.

Create a Campaign
=================

Before creating a new campaign, you must define a :aws-java-class:`Schedule
<services/pinpoint/model/Schedule>` and a :aws-java-class:`Message
<services/pinpoint/model/Message>` and set these values in a
:aws-java-class:`WriteCampaignRequest <services/pinpoint/model/WriteCampaignRequest>` object.

**Imports**

.. literalinclude:: example_code/pinpoint/src/main/java/com/example/pinpoint/CreateCampaign.java
   :lines: 18-27
   :language: java

**Code**

.. literalinclude:: example_code/pinpoint/src/main/java/com/example/pinpoint/CreateCampaign.java
   :lines: 53-69
   :dedent: 8
   :language: java

Then create a new campaign in |PINlong| by providing the :aws-java-class:`WriteCampaignRequest
<services/pinpoint/model/WriteCampaignRequest>` with the campaign configuration to a
:aws-java-class:`CreateCampaignRequest` object. Finally, pass the CreateCampaignRequest object to the
|pinpointclient|'s :methodname:`createCampaign` method.

**Code**

.. literalinclude:: example_code/pinpoint/src/main/java/com/example/pinpoint/CreateCampaign.java
   :lines: 71-74
   :dedent: 8
   :language: java

See the :sdk-examples-java-pinpoint:`complete example <CreateApp.java>` on GitHub.

More Information
================

* :pin-ug:`Amazon Pinpoint Campaigns <campaigns>` in the |pin-ug|
* :pin-dg:`Creating Campaigns <campaigns>` in the |pin-dg|
* :pin-api:`Campaigns <campaigns>` in the |pin-api|
* :pin-api:`Campaign <campaign>` in the |pin-api|
* :pin-api:`Campaign Activities <campaign-activities>` in the |pin-api|
* :pin-api:`Campaign Versions <campaign-versions>` in the |pin-api|
* :pin-api:`Campaign Version <campaign-version>` in the |pin-api|
