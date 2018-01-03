.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

########################
Working with |CW| Alarms
########################

.. meta::
   :description: How to create, list, and delete alarms in Amazon CloudWatch using the AWS SDK for
                 Java
   :keywords: create alarm, delete alarm, list alarms, metric alarms, example code

Create an Alarm
===============

To create an alarm based on a |cw| metric, call the |cwclient|'s :methodname:`putMetricAlarm` method
with a :aws-java-class:`PutMetricAlarmRequest <services/cloudwatch/model/PutMetricAlarmRequest>`
filled with the alarm conditions.

**Imports**

.. literalinclude:: example_code/cloudwatch/src/main/java/aws/example/cloudwatch/PutMetricAlarm.java
   :lines: 16-23
   :language: java

**Code**

.. literalinclude:: example_code/cloudwatch/src/main/java/aws/example/cloudwatch/PutMetricAlarm.java
   :lines: 43-66
   :dedent: 8
   :language: java


List Alarms
===========

To list the |cw| alarms that you have created, call the |cwclient|'s :methodname:`describeAlarms`
method with a :aws-java-class:`DescribeAlarmsRequest
<services/cloudwatch/model/DescribeAlarmsRequest>` that you can use to set options for the result.

**Imports**

.. literalinclude:: example_code/cloudwatch/src/main/java/aws/example/cloudwatch/DescribeAlarms.java
   :lines: 16-20
   :language: java

**Code**

.. literalinclude:: example_code/cloudwatch/src/main/java/aws/example/cloudwatch/DescribeAlarms.java
   :lines: 29-48
   :dedent: 8
   :language: java

The list of alarms can be obtained by calling :methodname:`getMetricAlarms` on the
:aws-java-class:`DescribeAlarmsResult <services/cloudwatch/model/DescribeAlarmsResult>` that is
returned by :methodname:`describeAlarms`.

The results may be *paged*. To retrieve the next batch of results, call :methodname:`setNextToken`
on the original request object with the return value of the :classname:`DescribeAlarmsResult`
object's :methodname:`getNextToken` method, and pass the modified request object back to another
call to :methodname:`describeAlarms`.

.. tip:: You can also retrieve alarms for a specific metric by using the |cwclient|'s
   :methodname:`describeAlarmsForMetric` method. Its use is similar to :methodname:`describeAlarms`.


Delete Alarms
=============

To delete |cw| alarms, call the |cwclient|'s :methodname:`deleteAlarms` method with a
:aws-java-class:`DeleteAlarmsRequest <services/cloudwatch/model/DeleteAlarmsRequest>` containing one
or more names of alarms that you want to delete.

**Imports**

.. literalinclude:: example_code/cloudwatch/src/main/java/aws/example/cloudwatch/DeleteAlarm.java
   :lines: 16-19
   :language: java

**Code**

.. literalinclude:: example_code/cloudwatch/src/main/java/aws/example/cloudwatch/DeleteAlarm.java
   :lines: 38-44
   :dedent: 8
   :language: java


More Information
================

* :cw-ug:`Creating Amazon CloudWatch Alarms <AlarmThatSendsEmail>` in the |cw-ug|
* :cw-api:`PutMetricAlarm <API_PutMetricAlarm>` in the |cw-api|
* :cw-api:`DescribeAlarms <API_DescribeAlarms>` in the |cw-api|
* :cw-api:`DeleteAlarms <API_DeleteAlarms>` in the |cw-api|
