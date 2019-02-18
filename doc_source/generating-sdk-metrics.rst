.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

.. |CSMlong| replace:: AWS SDK Metrics for Enterprise Support
.. |CSM| replace:: SDK Metrics


###################################
Enabling Metrics for the |sdk-java|
###################################

The |sdk-java| can generate metrics for visualization and monitoring with |cw|_ that measure:

* your application’s performance when accessing AWS
* the performance of your JVMs when used with AWS
* runtime environment details such as heap memory, number of threads, and opened file descriptors

.. note:: The |CSMlong| is another option for gathering metrics about your application.
   |CSM| is an |AWS| service that publishes data to |CWlong| and enables you to share metric data with AWS Support
   for easier troubleshooting. See :doc:`sdk-metrics` to learn how to enable the |CSM|
   service for your application.

How to Enable |sdk-java| Metric Generation
==========================================

|sdk-java| metrics are *disabled by default*. To enable it for your local development environment, include
a system property that points to your AWS security credential file when starting up the JVM. For
example::

   -Dcom.amazonaws.sdk.enableDefaultMetrics=credentialFile=/path/aws.properties

You need to specify the path to your credential file so that the SDK can upload the gathered
datapoints to |cw| for later analysis.

.. note:: If you are accessing AWS from an |ec2| instance using the |ec2| instance metadata service,
   you don’t need to specify a credential file. In this case, you need only specify::

      -Dcom.amazonaws.sdk.enableDefaultMetrics

All metrics captured by the SDK for Java are under the namespace **AWSSDK/Java**, and are uploaded
to the |cw| default region (*us-east-1*). To change the region, specify it by using the
``cloudwatchRegion`` attribute in the system property. For example, to set the |cw| region to
*us-west-2*, use::

   -Dcom.amazonaws.sdk.enableDefaultMetrics=credentialFile=/path/aws.properties,cloudwatchRegion=us-west-2

Once you enable the feature, every time there is a service request to AWS from the |sdk-java|,
metric data points will be generated, queued for statistical summary, and uploaded asynchronously to
|cw| about once every minute. Once metrics have been uploaded, you can visualize them using the
|console|_ and set alarms on potential problems such as memory leakage, file descriptor leakage, and
so on.

Available Metric Types
======================

The default set of metrics is divided into three major categories:

AWS Request Metrics
   Covers areas such as the latency of the HTTP request/response, number of requests, exceptions,
   and retries.

   .. image:: images/RequestMetric-131111.png

AWS Service Metrics
   Include AWS service-specific data, such as the throughput and byte count for S3 uploads and
   downloads.

   .. image:: images/ServiceMetric-131111.png

Machine Metrics
   Cover the runtime environment, including heap memory, number of threads, and open file
   descriptors.

   .. image:: images/MachineMetric-131111.png

   If you want to exclude Machine Metrics, add ``excludeMachineMetrics`` to the system property::

      -Dcom.amazonaws.sdk.enableDefaultMetrics=credentialFile=/path/aws.properties,excludeMachineMetrics

More Information
================

* See the :aws-java-class:`amazonaws/metrics package summary <metrics/package-summary>` for a full
  list of the predefined core metric types.

* Learn about working with |cw| using the |sdk-java| in :doc:`examples-cloudwatch`.

* Learn more about performance tuning in
  :blog:`Tuning the AWS SDK for Java to Improve Resiliency <developer/tuning-the-aws-sdk-for-java-to-improve-resiliency>`
  blog post.
