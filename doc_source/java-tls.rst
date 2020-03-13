.. Copyright 2010-2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

#####################################
Java SDK TLS 1.2
#####################################

.. meta::
   :description: Applies to Java SSL implementation (default SSL implementation in the SDK)Learn how the AWS shared responsibility model applies to data protection in this AWS product or service.
   :keywords:

The following information only applies to Java SSL implementation (default SSL implementation in the SDK). If you are using a different SSL implementations, you need to
look at your specific ssl implementation for how to enforce tls versions.

TLS support in Java
===================
TLS 1.2 is supported starting from Java 7.

How to check TLS version
========================
To check what TLS version is supported in your JVM, you can use the following code.

.. code-block:: java

   System*.out.println(*Arrays*.toString(*SSLContext*.getDefault().getSupportedSSLParameters().getProtocols()));

To see the SSL handshake in action and what version of TLS is used, you can use the system property **javax.net.debug**.

.. code-block:: java

   java app.jar -Djavax.net.debug=ssl

How to set TLS version
======================

**AWS Java SDK 1.x**

* Apache http client: the SDK always prefers TLS 1.2 if it's supported in the platform.

**AWS Java SDK 2.x**

* ApacheHttpClient: the SDK always prefers TLS 1.2 if it's supported in the platform.

* UrlHttpConnectionClient:
    * To enforce only tls 1.2, using the system property

.. code-block:: java

   java app.jar -Djdk.tls.client.protocols=TLSv1.2

or in code

.. code-block:: java

   System.setProperty("jdk.tls.client.protocols", "TLSv1.2");

* NettyNioHttpClient: The SDK dependency Netty by default always prefers TLS 1.2 if it's supported in the platform.
