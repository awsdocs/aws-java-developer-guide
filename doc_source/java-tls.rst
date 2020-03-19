.. Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

#####################################
AWS SDK for Java support for TLS 1.2
#####################################

.. meta::
   :description: Applies to Java SSL implementation (default SSL implementation in the SDK). Learn how the AWS shared responsibility model applies to data protection in this AWS product or service.
   :keywords:

The following information applies only to Java SSL implementation (the default SSL implementation in the AWS SDK for Java). If you're using a different SSL implementation,
see your specific SSL implementation to learn how to enforce TLS versions.

TLS support in Java
===================
TLS 1.2 is supported starting in Java 7.

How to check the TLS version
============================
To check what TLS version is supported in your Java virtual machine (JVM), you can use the following code.

.. code-block:: java

   System*.out.println(*Arrays*.toString(*SSLContext*.getDefault().getSupportedSSLParameters().getProtocols()));

To see the SSL handshake in action and what version of TLS is used, you can use the system property **javax.net.debug**.

.. code-block:: java

   java app.jar -Djavax.net.debug=ssl

How to set the TLS version
==========================

**AWS SDK for Java 1.x**

* Apache HTTP client: The SDK always prefers TLS 1.2 (if it's supported in the platform).

**AWS SDK for Java 2.x**

* ApacheHttpClient: The SDK always prefers TLS 1.2 (if it's supported in the platform).

* UrlHttpConnectionClient: To enforce only TLS 1.2, you can use this Java command.

.. code-block:: java

   java app.jar -Djdk.tls.client.protocols=TLSv1.2

Or use this code.

.. code-block:: java

   System.setProperty("jdk.tls.client.protocols", "TLSv1.2");

* NettyNioHttpClient: The SDK dependency for Netty is TLS 1.2 (if it's supported in the platform).
