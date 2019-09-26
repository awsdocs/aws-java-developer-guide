.. Copyright 2010-2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

.. |language| replace:: Java
.. |sdk| replace:: |sdk-java|

####################
Enabling |SDKMlong|
####################

|SDKMlong| (|SDKM|\) enables Enterprise customers to collect metrics from |AWS| SDKs on their hosts and clients shared with
|AWS| Enterprise Support. |SDKM| provides information that helps speed up detection and diagnosis of issues occurring in connections
to AWS services for AWS Enterprise Support customers.

As telemetry is collected on each host, it is relayed via UDP to 127.0.0.1 (aka localhost), where the |CW| agent aggregates the data and sends it
to the |SDKM| service. Therefore, to receive metrics, the |CW| agent is required to be added to your instance.

The following steps to set up |SDKM| pertain to an |EC2| instance running Amazon Linux for a client application that is using the |sdk|.
|SDKM| is also available for your production environments if you enable it while configuring the |sdk|.

To utilize |SDKM|, run the latest version of the |CW| agent. Learn how to
:CW-dg:`Configure the CloudWatch Agent for SDK Metrics<Configure-CloudWatch-Agent-SDK-Metrics>` in the |CW-dg|.

To set up |SDKM| with the |sdk|, follow these instructions:

#. Create an application with an |sdk| client to use an AWS service.
#. Host your project on an |EC2| instance or in your local environment.
#. Install and use the latest 1.x version of the |sdk|.
#. Install and configure an |CW| agent on an EC2 instance or in your local environment.
#. Authorize |SDKM| to collect and send metrics.
#. :ref:`csm-enable-agent`.

For more information, see the following:

* :ref:`csm-update-agent`
* :ref:`csm-disable-agent`


.. _csm-enable-agent:

Enable |SDKM| for the |sdk|
===========================

By default, |SDKM| is turned off, and the port is set to 31000. The following are the default parameters.

.. code-block:: ini

    //default values
     [
         'enabled' => false,
         'port' => 31000,
     ]

Enabling |SDKM| is independent of configuring your credentials to use an AWS service.

You can enable |SDKM| using one of 4 options.

* :ref:`csm-enable-agent-code`
* :ref:`csm-enable-agent-environ`
* :ref:`csm-enable-agent-java-prop`
* :ref:`csm-enable-agent-shared-config`

.. _csm-enable-agent-code:

Option 1: Set |SDKM| in Code
----------------------------

The |language| implementation allows you to set |SDKM| configurations within code when building
a service client.
The values set in code override any configurations set in the other options described below.

.. code-block:: java

   CsmConfiguration csmConfig = new CsmConfiguration(true, MY_PORT, MY_CLIENT_ID);
   AmazonDynamoDB dynamodb = AmazonDynamoDBClientBuilder.standard()
      .withClientSideMonitoringConfigurationProvider(new StaticCsmConfigurationProvider(csmConfig))
      .build();

.. _csm-enable-agent-environ:

Option 2: Set Environment Variables
-----------------------------------

If :code:`AWS_CSM_ENABLED` is not set, the SDK first checks the profile specified in
the environment variable under :code:`AWS_PROFILE` to determine if |SDKM| is enabled.
By default this is set to ``false``.

To turn on |SDKM|, add the following to your environmental variables.

.. code-block:: ini

    export AWS_CSM_ENABLED=true

:ref:`Other configuration settings<csm-update-agent>` are available.

Note: Enabling |SDKM| does not configure your credentials to use an AWS service.

.. _csm-enable-agent-java-prop:

Option 3: Set Java System Property
----------------------------------

If no |SDKM| configuration is found in the environment variables,
the SDK looks at certain Java system properties.

To turn on |SDKM|, pass the following system property flag when you execute your application.

.. code-block:: ini

    -Dcom.amazonaws.sdk.csm.enabled="true"

You can also set the value programmatically using the Properties object.

.. code-block:: java

   Properties props = System.getProperties();
  props.setProperty("com.amazonaws.sdk.csm.enabled", "true");

:ref:`Other configuration settings<csm-update-agent>` are available.

Note: Enabling |SDKM| does not configure your credentials to use an AWS service.

.. _csm-enable-agent-shared-config:

Option 4: AWS Shared Config File
--------------------------------

If no |SDKM| configuration is found in the environment variables or the Java system properties,
the SDK looks for your default AWS profile field. If :code:`AWS_DEFAULT_PROFILE` is set to
something other than default, update that profile.
To enable |SDKM|, add :code:`csm_enabled` to the shared config file located at :file:`~/.aws/config`.

.. code-block:: ini

    [default]
    csm_enabled = true

    [profile aws_csm]
    csm_enabled = true

:ref:`Other configuration settings<csm-update-agent>` are available.

Note: Enabling |SDKM| is independent from configuring your credentials to use an AWS service. You can use a different profile to authenticate.

.. _csm-update-agent:

Update a |CW| Agent
===================

To make changes to the port, you need to set the values and then restart any AWS jobs that are currently active.

Option 1: Set Environment Variables
-----------------------------------

Most services use
the default port. But if your service requires a unique port ID, add `AWS_CSM_PORT=[port_number]`, to the host's environment variables.

.. code-block:: shell

    export AWS_CSM_ENABLED=true
    export AWS_CSM_PORT=1234

Option 2: Set Java System Property
-----------------------------------

Most services use the default port.
But if your service requires a unique port ID, specify the `-Dcom.amazonaws.sdk.csm.port=[port_number]`
system properties flag when executing your application.

.. code-block:: ini

    com.amazonaws.sdk.csm.enabled=true
    com.amazonaws.sdk.csm.port=1234

Option 3: AWS Shared Config File
-----------------------------------

Most services use the default port. But if your service requires a
unique port ID, add `csm_port = [port_number]` to `~/.aws/config`.

.. code-block:: ini

    [default]
    csm_enabled = false
    csm_port = 1234

    [profile aws_csm]
    csm_enabled = false
    csm_port = 1234

Restart |SDKM|
--------------

To restart a job, run the following commands.

.. code-block:: shell

    amazon-cloudwatch-agent-ctl –a stop;
    amazon-cloudwatch-agent-ctl –a start;


.. _csm-disable-agent:

Disable |SDKM|
==============

To turn off |SDKM|, set `csm_enabled` to `false` in your environment variables, or in your AWS Shared config file located at :file:`~/.aws/config`.
Then restart your |CW| agent so that the changes can take effect.

**Environment Variables**

.. code-block:: shell

    export AWS_CSM_ENABLED=false


**AWS Shared Config File**

Remove `csm_enabled` from the profiles in your AWS Shared config file located at :file:`~/.aws/config`.

.. note:: Environment variables override the AWS Shared config file. If |SDKM| is enabled in the environment variables, the |SDKM| remain enabled.

.. code-block:: ini

    [default]
    csm_enabled = false

    [profile aws_csm]
    csm_enabled = false

To disable |SDKM|, use the following command to stop |CW| agent.

.. code-block:: shell

    sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl -a stop &&
    echo "Done"

If you are using other |CW| features, restart the |CW| agent with the following command.

.. code-block:: shell

    amazon-cloudwatch-agent-ctl –a start;


Restart |SDKM|
--------------

To restart a |SDKM|, run the following commands.

.. code-block:: shell

    amazon-cloudwatch-agent-ctl –a stop;
    amazon-cloudwatch-agent-ctl –a start;


Definitions for |SDKM|
======================

You can use the following descriptions of |SDKM| to interpret your results. In general, these metrics are available for review
with your Technical Account Manager during regular business reviews. AWS Support resources and your Technical Account Manager
should have access to SDK Metrics data to help you resolve cases, but if you discover data that is confusing or unexpected, but
doesn’t seem to be negatively impacting your applications’ performance, it is best to review that data during scheduled
business reviews.

.. list-table::
   :widths: 1 2
   :header-rows: 1

   * - Metric:
     - CallCount

   * - Definition
     - Total number of successful or failed API calls from your code to AWS services

   * - How to use it
     - Use it as a baseline to correlate with other metrics like errors or throttling.


.. list-table::
   :widths: 1 2
   :header-rows: 1

   * - Metric:
     - ClientErrorCount

   * - Definition
     - Number of API calls that fail with client errors (4xx HTTP response codes). *Examples: Throttling, Access denied, S3 bucket does not exist, and Invalid parameter value.*

   * - How to use it
     - Except in certain cases related to throttling (ex. when throttling occurs due to a limit that needs to be increased) this metric can indicate something in your application that needs to be fixed.


.. list-table::
   :widths: 1 2
   :header-rows: 1

   * - Metric:
     - ConnectionErrorCount

   * - Definition
     - Number of API calls that fail because of errors connecting to the service. These can be caused by network issues between the customer application and AWS services including load balancers, DNS failures, transit providers. In some cases, AWS issues may result in this error.

   * - How to use it
     - Use this metric to determine whether issues are specific to your application or are caused by your infrastructure and/or network. High ConnectionErrorCount could also indicate short timeout values for API calls.


.. list-table::
   :widths: 1 2
   :header-rows: 1

   * - Metric:
     - ThrottleCount

   * - Definition
     - Number of API calls that fail due to throttling by AWS services.

   * - How to use it
     - Use this metric to assess if your application has reached throttle limits, as well as to determine the cause of retries and application latency. Consider distributing calls over a window instead of batching your calls.


.. list-table::
   :widths: 1 2
   :header-rows: 1

   * - Metric:
     - ServerErrorCount

   * - Definition
     - Number of API calls that fail due to server errors (5xx HTTP response codes) from AWS Services. These are typically caused by AWS services.

   * - How to use it
     - Determine cause of SDK retries or latency. This metric will not always indicate that AWS services are at fault, as some AWS teams classify latency as an HTTP 503 response.

.. list-table::
   :widths: 1 2
   :header-rows: 1

   * - Metric:
     - EndToEndLatency

   * - Definition
     - Total time for your application to make a call using the AWS SDK, inclusive of retries. In other words, regardless of whether it is successful after several attempts, or as soon as a call fails due to an unretriable error.

   * - How to use it
     - Determine how AWS API calls contribute to your application’s overall latency. Higher than expected latency may be caused by issues with network, firewall, or other configuration settings, or by latency that occurs as a result of SDK retries.
