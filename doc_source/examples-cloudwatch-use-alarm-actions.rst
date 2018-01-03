.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

###########################
Using Alarm Actions in |CW|
###########################

.. meta::
   :description: How to enable or disable alarm actions for Amazon Cloudwatch with the AWS SDK for
                 Java.
   :keywords: cloudwatch alarms, enable alarms, disable alarms, code examples

Using |cw| alarm actions, you can create alarms that perform actions such as automatically stopping,
terminating, rebooting, or recovering |ec2| instances.

.. note:: Alarm actions can be added to an alarm by using the :aws-java-class:`PutMetricAlarmRequest
   <services/cloudwatch/model/PutMetricAlarmRequest>`'s :methodname:`setAlarmActions` method when
   :doc:`creating an alarm <examples-cloudwatch-create-alarms>`.


Enable Alarm Actions
====================

To enable alarm actions for a |cw| alarm, call the |cwclient|'s :methodname:`enableAlarmActions`
with a :aws-java-class:`EnableAlarmActionsRequest
<services/cloudwatch/model/EnableAlarmActionsRequest>` containing one or more names of alarms whose
actions you want to enable.

**Imports**

.. literalinclude:: example_code/cloudwatch/src/main/java/aws/example/cloudwatch/EnableAlarmActions.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/cloudwatch/src/main/java/aws/example/cloudwatch/EnableAlarmActions.java
   :lines: 39-45
   :dedent: 8
   :language: java


Disable Alarm Actions
=====================

To disable alarm actions for a |cw| alarm, call the |cwclient|'s :methodname:`disableAlarmActions`
with a :aws-java-class:`DisableAlarmActionsRequest
<services/cloudwatch/model/DisableAlarmActionsRequest>` containing one or more names of alarms whose
actions you want to disable.

**Imports**

.. literalinclude:: example_code/cloudwatch/src/main/java/aws/example/cloudwatch/DisableAlarmActions.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/cloudwatch/src/main/java/aws/example/cloudwatch/DisableAlarmActions.java
   :lines: 39-45
   :dedent: 8
   :language: java

More Information
================

* :cw-ug:`Create Alarms to Stop, Terminate, Reboot, or Recover an Instance <UsingAlarmActions>` in
  the |cw-ug|
* :cw-api:`PutMetricAlarm <API_PutMetricAlarm>` in the |cw-api|
* :cw-api:`EnableAlarmActions <API_EnableAlarmActions>` in the |cw-api|
* :cw-api:`DisableAlarmActions <API_DisableAlarmActions>` in the |cw-api|
