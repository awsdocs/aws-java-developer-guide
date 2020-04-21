.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

######################
Client Configuration
######################

.. meta::
   :description: How to change proxy configuration, HTTP transport configuration, and TCP socket
                 buffer size hints by using the AWS SDK for Java.

The |sdk-java| enables you to change the default client configuration, which is helpful when you
want to:

* Connect to the Internet through proxy

* Change HTTP transport settings, such as connection timeout and request retries

* Specify TCP socket buffer size hints

Proxy Configuration
===================

When constructing a client object, you can pass in an optional :aws-java-class:`ClientConfiguration`
object to customize the client's configuration.

If you connect to the Internet through a proxy server, you'll need to configure your proxy server
settings (proxy host, port, and username/password) through the :classname:`ClientConfiguration`
object.


HTTP Transport Configuration
============================

You can configure several HTTP transport options by using the :aws-java-class:`ClientConfiguration`
object. New options are occasionally added; to see the full list of options you can retrieve or set,
see the |sdk-java-ref|.

.. note::
   Each of the configurable values has a default value defined by a constant. For a list of the
   constant values for :classname:`ClientConfiguration`, see :sdk-java-ref:`Constant Field Values
   <constant-values>` in the |sdk-java-ref|.


Maximum Connections
-------------------

You can set the maximum allowed number of open HTTP connections by using the
:aws-java-ref:`ClientConfiguration.setMaxConnections
<ClientConfiguration.html#setMaxConnections-int->` method.

.. important::
   Set the maximum connections to the number of concurrent transactions to avoid
   connection contentions and poor performance. For the default maximum connections value,
   see :sdk-java-ref:`Constant Field Values <constant-values>` in the |sdk-java-ref|.


Timeouts and Error Handling
---------------------------

You can set options related to timeouts and handling errors with HTTP connections.

* :strong:`Connection Timeout`

  The connection timeout is the amount of time (in milliseconds) that the HTTP connection will wait
  to establish a connection before giving up. The default is 10,000 ms.

  To set this value yourself, use the :aws-java-ref:`ClientConfiguration.setConnectionTimeout
  <ClientConfiguration.html#setConnectionTimeout-int->` method.

* :strong:`Connection Time to Live (TTL)`

  By default, the SDK will attempt to reuse HTTP connections as long as possible. In failure
  situations where a connection is established to a server that has been brought out of service,
  having a finite TTL can help with application recovery. For example, setting a 15 minute TTL will
  ensure that even if you have a connection established to a server that is experiencing issues,
  you'll reestablish a connection to a new server within 15 minutes.

  To set the HTTP connection TTL, use the :aws-java-ref:`ClientConfiguration.setConnectionTTL
  <ClientConfiguration.html#setConnectionTTL-long->` method.

* :strong:`Maximum Error Retries`

  The default maximum retry count for retriable errors is 3. You can set a different value
  by using the :aws-java-ref:`ClientConfiguration.setMaxErrorRetry
  <ClientConfiguration.html#setMaxErrorRetry-int->` method.


Local Address
-------------

To set the local address that the HTTP client will bind to, use
:aws-java-ref:`ClientConfiguration.setLocalAddress
<ClientConfiguration.html#setLocalAddress-java.net.InetAddress->`.




TCP Socket Buffer Size Hints
============================

Advanced users who want to tune low-level TCP parameters can additionally set TCP buffer size hints
through the :aws-java-class:`ClientConfiguration` object. The majority of users will never need to
tweak these values, but they are provided for advanced users.

Optimal TCP buffer sizes for an application are highly dependent on network and operating system
configuration and capabilities. For example, most modern operating systems provide auto-tuning logic
for TCP buffer sizes.This can have a big impact on performance for TCP connections that are held
open long enough for the auto-tuning to optimize buffer sizes.

Large buffer sizes (e.g., 2 MB) allow the operating system to buffer more data in memory without
requiring the remote server to acknowledge receipt of that information, and so can be particularly
useful when the network has high latency.

This is only a *hint*, and the operating system might not honor it. When using this option, users
should always check the operating system's configured limits and defaults. Most operating systems
have a maximum TCP buffer size limit configured, and won't let you go beyond that limit unless you
explicitly raise the maximum TCP buffer size limit.

Many resources are available to help with configuring TCP buffer sizes and operating system-specific
TCP settings, including the following:

* `Host Tuning <http://fasterdata.es.net/host-tuning/>`_
