.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

############################
Creating an App in |PINlong|
############################

.. meta::
   :description: How to create or delete an app in Amazon pinpoint.
   :keywords: AWS for Java SDK code examples, amazon pinpoint

Creating an App
===============

Create a new app in |PINlong| by providing an app name to the :aws-java-class:`CreateAppRequest
<services/pinpoint/model/CreateAppRequest>` object then passing that object to the
|pinpointclient|'s :methodname:`createApp` method.

**Imports**

.. literalinclude:: example_code/pinpoint/src/main/java/com/example/pinpoint/CreateApp.java
   :lines: 17-21
   :language: java

**Code**

.. literalinclude:: example_code/pinpoint/src/main/java/com/example/pinpoint/CreateApp.java
   :lines: 44-49
   :dedent: 8
   :language: java

See the :sdk-examples-java-pinpoint:`complete example <CreateApp.java>` on GitHub.


Deleting an App
===============

To delete a user, call the |pinpointclient|'s :methodname:`deleteApp` request with a
:aws-java-class:`UpdateAppRequest <services/pinpoint/model/UpdateAppRequest>` object set
with the user name to delete.

**Imports**

.. literalinclude:: example_code/pinpoint/src/main/java/com/example/pinpoint/DeleteApp.java
   :lines: 18-19
   :language: java

**Code**

.. literalinclude:: example_code/pinpoint/src/main/java/com/example/pinpoint/DeleteApp.java
   :lines: 42-45
   :dedent: 8
   :language: java

See the :sdk-examples-java-pinpoint:`complete example <DeleteApp.java>` on GitHub.

More Information
================

* See :pinpoint-api:`Apps <apps>` in the |PIN-api|
