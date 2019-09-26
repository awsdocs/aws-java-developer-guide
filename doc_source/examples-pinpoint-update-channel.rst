.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

##############################
Updating Channels in |PINlong|
##############################

.. meta::
   :description: How to update an app channel in Amazon pinpoint.
   :keywords: AWS for Java SDK code examples, amazon pinpoint channel

A channel defines the types of platforms to which you can deliver messages.
This example shows how to use the APNs channel to send a message.

Update a Channel
================

Enable a channel in |PINlong| by providing an app ID and a request object of the channel type
you want to update. This example updates the APNs channel, which requires the
:aws-java-class:`APNSChannelRequest <services/pinpoint/model/APNSChannelRequest>` object.
Set these in the :aws-java-class:`UpdateApnsChannelRequest <services/pinpoint/model/UpdateApnsChannelRequest>`
and pass that object to the
|pinpointclient|'s :methodname:`updateApnsChannel` method.

**Imports**

.. literalinclude:: example_code/pinpoint/src/main/java/com/example/pinpoint/UpdateChannel.java
   :lines: 18-25
   :language: java

**Code**

.. literalinclude:: example_code/pinpoint/src/main/java/com/example/pinpoint/UpdateChannel.java
   :lines: 66-72
   :dedent: 2
   :language: java

See the :sdk-examples-java-pinpoint:`complete example <UpdateChannel.java>` on GitHub.


More Information
================

* :pin-ug:`Amazon Pinpoint Channels <channels>` in the |pin-ug|
* :pin-api:`ADM Channel <adm-channel>` in the |pin-api|
* :pin-api:`APNs Channel <apns-channel>` in the |pin-api|
* :pin-api:`APNs Sandbox Channel <apns-sandbox-channel>` in the |pin-api|
* :pin-api:`APNs VoIP Channel <apns-voip-channel>` in the |pin-api|
* :pin-api:`APNs VoIP Sandbox Channel <apns-voip-sandbox-channel>` in the |pin-api|
* :pin-api:`Baidu Channel <baidu-channel>` in the |pin-api|
* :pin-api:`Email Channel <email-channel>` in the |pin-api|
* :pin-api:`GCM Channel <gcm-channel>` in the |pin-api|
* :pin-api:`SMS Channel <sms-channel>` in the |pin-api|
