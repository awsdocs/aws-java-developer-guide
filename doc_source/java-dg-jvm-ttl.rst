.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

########################################
Setting the JVM TTL for DNS Name Lookups
########################################

.. meta::
   :description: How to set Java virtual machine (JVM) for DNS name lookups using the AWS SDK for
                 Java.

The Java virtual machine (JVM) caches DNS name lookups. When the JVM resolves a hostname to an IP
address, it caches the IP address for a specified period of time, known as the *time-to-live* (TTL).

Because AWS resources use DNS name entries that occasionally change, we recommend that you configure
your JVM with a TTL value of no more than 60 seconds. This ensures that when a resource's IP address
changes, your application will be able to receive and use the resource's new IP address by
requerying the DNS.

On some Java configurations, the JVM default TTL is set so that it will *never* refresh DNS entries
until the JVM is restarted. Thus, if the IP address for an AWS resource changes while your
application is still running, it won't be able to use that resource until you *manually restart* the
JVM and the cached IP information is refreshed. In this case, it's crucial to set the JVM's TTL so
that it will periodically refresh its cached IP information.

.. note::  The default TTL can vary according to the version of your JVM and whether a `security
   manager <http://docs.oracle.com/javase/tutorial/essential/environment/security.html>`_ is
   installed. Many JVMs provide a default TTL less than 60 seconds. If you're using such a JVM and
   not using a security manager, you can ignore the remainder of this topic.

How to Set the JVM TTL
======================

To modify the JVM's TTL, set the `networkaddress.cache.ttl
<http://docs.oracle.com/javase/7/docs/technotes/guides/net/properties.html>`_ property value. Use
one of the following methods, depending on your needs:

* **globally, for all applications that use the JVM**. Set ``networkaddress.cache.ttl`` in the
  :file:`$JAVA_HOME/jre/lib/security/java.security` file:

  .. code-block:: java

      networkaddress.cache.ttl=60

* **for your application only**, set ``networkaddress.cache.ttl`` in your application's
  initialization code:

  .. code-block:: java

      java.security.Security.setProperty("networkaddress.cache.ttl" , "60");

