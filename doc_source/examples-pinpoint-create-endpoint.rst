.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

###############################
Creating Endpoints in |PINlong|
###############################

.. meta::
   :description: How to update an app endpoint in Amazon pinpoint.
   :keywords: AWS for Java SDK code examples, amazon pinpoint endpoint

An endpoint uniquely identifies a user device to which you can send
push notifications with |PINlong|. If your app is enabled with |PINlong|
support, your app automatically registers an endpoint with |PINlong|
when a new user opens your app. The following example demonstrates how to
add a new endpoint programmatically.

Create an Endpoint
==================

Create a new endpoint in |PINlong| by providing the endpoint data in an
:aws-java-class:`EndpointRequest <services/pinpoint/model/EndpointRequest>` object.

**Imports**

.. literalinclude:: example_code/pinpoint/src/main/java/com/example/pinpoint/CreateEndpoint.java
   :lines: 18-28
   :language: java

**Code**

.. literalinclude:: example_code/pinpoint/src/main/java/com/example/pinpoint/CreateEndpoint.java
   :lines: 92-136
   :dedent: 8
   :language: java

Then create an :aws-java-class:`UpdateEndpointRequest <services/pinpoint/model/UpdateEndpointRequest>`
object with that EndpointRequest object. Finally, pass the UpdateEndpointRequest object to the
|pinpointclient|'s :methodname:`updateEndpoint` method.

**Code**

.. literalinclude:: example_code/pinpoint/src/main/java/com/example/pinpoint/CreateEndpoint.java
  :lines: 73-79
  :dedent: 8
  :language: java

See the :sdk-examples-java-pinpoint:`complete example <CreateEndpoint.java>` on GitHub.


More Information
================

* :pin-dg:`Adding Endpoint <endpoints>` in the |pin-dg|
* :pin-api:`Endpoint <endpoint>` in the |pin-api|
