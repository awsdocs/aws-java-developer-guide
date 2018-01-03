.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

#########################
Getting Metrics from |CW|
#########################

.. meta::
   :description: How to list metrics from Amazon CloudWatch using the AWS SDK for Java.
   :keywords: Amazon Cloudwatch, AWS SDK for Java, metrics, listing, code examples

Listing Metrics
===============

To list |cw| metrics, create a :aws-java-class:`ListMetricsRequest
<services/cloudwatch/model/ListMetricsRequest>` and call the |cwclient|'s :methodname:`listMetrics`
method. You can use the :classname:`ListMetricsRequest` to filter the returned metrics by namespace,
metric name, or dimensions.

.. note:: A list of metrics and dimensions that are posted by AWS services can be found within the
   :cw-ug:`Amazon CloudWatch Metrics and Dimensions Reference <CW_Support_For_AWS>` in the |cw-ug|.

**Imports**

.. literalinclude:: example_code/cloudwatch/src/main/java/aws/example/cloudwatch/ListMetrics.java
   :lines: 16-20
   :language: java

**Code**

.. literalinclude:: example_code/cloudwatch/src/main/java/aws/example/cloudwatch/ListMetrics.java
   :lines: 41-63
   :dedent: 8
   :language: java

The metrics are returned in a :aws-java-class:`ListMetricsResult
<services/cloudwatch/model/ListMetricsResult>` by calling its :methodname:`getMetrics` method. The
results may be *paged*. To retrieve the next batch of results, call :methodname:`setNextToken` on
the original request object with the return value of the :classname:`ListMetricsResult` object's
:methodname:`getNextToken` method, and pass the modified request object back to another call to
:methodname:`listMetrics`.

More Information
================

* :cw-api:`ListMetrics <API_ListMetrics>` in the |cw-api|.
