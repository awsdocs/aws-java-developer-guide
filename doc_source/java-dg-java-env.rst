.. Copyright 2010-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

.. index::
    pair: JDK; installing

#########################################
Installing a Java Development Environment
#########################################

The |sdk-java| requires J2SE Development Kit *6.0 or later*. You can download the latest Java
software from http://developers.sun.com/downloads/.

.. important:: Java version 1.6 (JS2E 6.0) did not have built-in support for SHA256-signed SSL
   certificates, which are required for all HTTPS connections with AWS after September 30, 2015.

   Java versions 1.7 or newer are packaged with updated certificates and are unaffected by this
   issue.

Choosing a JVM
==============

For the best performance of your server-based applications with the AWS SDK for Java, we recommend
that you use the *64-bit version* of the Java Virtual Machine (JVM). This JVM runs only in server
mode, even if you specify the ``-Client`` option at run time.

Using the 32-bit version of the JVM with the ``-Server`` option at run time should provide
comparable performance to the 64-bit JVM.
