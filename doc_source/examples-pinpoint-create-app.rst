.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

#######################################
Creating and Deleting Apps in |PINlong|
#######################################

.. meta::
   :description: How to create or delete an app in Amazon pinpoint.
   :keywords: AWS for Java SDK code examples, amazon pinpoint

An app is an |PINlong| project in which you define the audience for a distinct
application, and you engage this audience with tailored messages. The examples on
this page demonstrate how to create a new app or delete an existing one.

Create an App
=============

Create a new app in |PINlong| by providing an app name to the :aws-java-class:`CreateAppRequest
<services/pinpoint/model/CreateAppRequest>` object, and then passing that object to the
|pinpointclient|'s :methodname:`createApp` method.

**Imports**

.. literalinclude:: example_code/pinpoint/src/main/java/com/example/pinpoint/CreateApp.java
   :lines: 17-21
   :language: java

**Code**

.. literalinclude:: example_code/pinpoint/src/main/java/com/example/pinpoint/CreateApp.java
   :lines: 44-49
   :dedent: 2
   :language: java

See the :sdk-examples-java-pinpoint:`complete example <CreateApp.java>` on GitHub.


Delete an App
=============

To delete an app, call the |pinpointclient|'s :methodname:`deleteApp` request with a
:aws-java-class:`DeleteAppRequest <services/pinpoint/model/DeleteAppRequest>` object that's
set with the app name to delete.

**Imports**

.. literalinclude:: example_code/pinpoint/src/main/java/com/example/pinpoint/DeleteApp.java
   :lines: 18-19
   :language: java

**Code**

.. literalinclude:: example_code/pinpoint/src/main/java/com/example/pinpoint/DeleteApp.java
   :lines: 42-45
   :dedent: 2
   :language: java

See the :sdk-examples-java-pinpoint:`complete example <DeleteApp.java>` on GitHub.

More Information
================

* :pin-api:`Apps <apps>` in the |PIN-api|
* :pin-api:`App <app>` in the |PIN-api|
