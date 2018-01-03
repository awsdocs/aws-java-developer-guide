.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

######################
Sending Events to |CW|
######################

.. meta::
   :description: How to add events, rules and rule targets for Amazon Cloudwatch using the AWS SDK
                 for Java.
   :keywords: cloudwatch events, add rule, add events, add targets, code examples

.. include:: common/desc-cloudwatch-events.txt

Add Events
==========

To add custom |cw| events, call the |cweclient|'s :methodname:`putEvents` method with a
:aws-java-class:`PutEventsRequest <services/cloudwatchevents/model/PutEventsRequest>` object that
contains one or more :aws-java-class:`PutEventsRequestEntry
<services/cloudwatchevents/model/PutEventsRequestEntry>` objects that provide details about each
event. You can specify several parameters for the entry such as the source and type of the event,
resources associated with the event, and so on.

.. note:: You can specify a maximum of 10 events per call to :methodname:`putEvents`.

**Imports**

.. literalinclude:: example_code/cloudwatch/src/main/java/aws/example/cloudwatch/PutEvents.java
   :lines: 16-20
   :language: java

**Code**

.. literalinclude:: example_code/cloudwatch/src/main/java/aws/example/cloudwatch/PutEvents.java
   :lines: 40-55
   :dedent: 8
   :language: java


Add Rules
=========

To create or update a rule, call the |cweclient|'s :methodname:`putRule` method with a
:aws-java-class:`PutRuleRequest <services/cloudwatchevents/model/PutRuleRequest>` with the name of
the rule and optional parameters such as the :cwe-ug:`event pattern
<CloudWatchEventsandEventPatterns>`, IAM role to associate with the rule, and a :cwe-ug:`scheduling
expression <ScheduledEvents>` that describes how often the rule is run.

**Imports**

.. literalinclude:: example_code/cloudwatch/src/main/java/aws/example/cloudwatch/PutRule.java
   :lines: 16-20
   :language: java

**Code**

.. literalinclude:: example_code/cloudwatch/src/main/java/aws/example/cloudwatch/PutRule.java
   :lines: 41-50
   :dedent: 8
   :language: java


Add Targets
===========

Targets are the resources that are invoked when a rule is triggered. Example targets include |ec2|
instances, |lam| functions, |ak| streams, |ecs| tasks, |sfn| state machines, and built-in targets.

To add a target to a rule, call the |cweclient|'s :methodname:`putTargets` method with a
:aws-java-class:`PutTargetsRequest <services/cloudwatchevents/model/PutTargetsRequest>` containing
the rule to update and a list of targets to add to the rule.

**Imports**

.. literalinclude:: example_code/cloudwatch/src/main/java/aws/example/cloudwatch/PutTargets.java
   :lines: 16-20
   :language: java

**Code**

.. literalinclude:: example_code/cloudwatch/src/main/java/aws/example/cloudwatch/PutTargets.java
   :lines: 45-56
   :dedent: 8
   :language: java


More Information
================

* :cwe-ug:`Adding Events with PutEvents <AddEventsPutEvents>` in the |cwe-ug|
* :cwe-ug:`Schedule Expressions for Rules <ScheduledEvents>` in the |cwe-ug|
* :cwe-ug:`Event Types for CloudWatch Events <EventTypes>` in the |cwe-ug|
* :cwe-ug:`Events and Event Patterns <CloudWatchEventsandEventPatterns>` in the |cwe-ug|
* :cwe-api:`PutEvents <API_PutEvents>` in the |cwe-api|
* :cwe-api:`PutTargets <API_PutTargets>` in the |cwe-api|
* :cwe-api:`PutRule <API_PutRule>` in the |cwe-api|
