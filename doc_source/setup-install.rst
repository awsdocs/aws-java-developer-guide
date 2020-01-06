.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

#####################
Set up the |sdk-java|
#####################

Describes how to use the |sdk-java| in your project.

Prerequisites
=============

To use the |sdk-java|, you must have:

* a suitable :ref:`Java Development Environment <java-dg-java-env>`.

* An AWS account and access keys. For instructions, see :doc:`signup-create-iam-user`.

* AWS credentials (access keys) set in your environment or using the shared (by the AWS CLI and
  other SDKs) credentials file. For more information, see :doc:`setup-credentials`.


.. _include-sdk:

Including the SDK in your project
=================================

To include the SDK your project, use one of the following methods depending on your build
system or IDE:

* **Apache Maven** |ndash| If you use |mvnlong|_, you can specify the entire SDK (or specific SDK
  components) as dependencies in your project. See :doc:`setup-project-maven` for details about how
  to set up the SDK when using Maven.

* **Gradle** |ndash| If you use Gradle_, you can import the Maven Bill of Materials (BOM) in your
  Gradle project to automatically manage SDK dependencies. See :doc:`setup-project-gradle` for more
  infomation.

* **Eclipse IDE** |ndash| If you use the Eclipse IDE, you may want to install and use the |tke|_,
  which will automatically download, install and update the Java SDK for you. For more information
  and setup instructions, see the |tke-ug|_.

If you are using one the above methods (for example,
you are using Maven), then you do not need to download and install the AWS JAR files (you can skip the following section). If you intend to build your projects using a different IDE, with Apache Ant or by any other means,
then download and extract the SDK as shown in the next section.

.. _download-and-extract-sdk:

Downloading and extracting the SDK
==================================

We recommend that you use the most recent pre-built version of the SDK for new projects, which
provides you with the latest support for all AWS services.

.. note:: For information about how to download and build previous versions of the SDK, see
   :ref:`install-prev-sdk`.

.. topic:: To download and extract the latest version of the SDK

    #. Download the SDK from |sdk-java-dl|.

    #. After downloading the SDK, extract the contents into a local directory.

The SDK contains the following directories:

* :file:`documentation` |ndash| contains the API documentation (also available on the web:
  |sdk-java-ref|_).

* :file:`lib` |ndash| contains the SDK :file:`.jar` files.

* :file:`samples` |ndash| contains working sample code that demonstrates how to use the SDK.

* :file:`third-party/lib` |ndash| contains third-party libraries that are used by the SDK, such as
  Apache commons logging, AspectJ and the Spring framework.

To use the SDK, add the full path to the ``lib`` and ``third-party`` directories to the dependencies
in your build file, and add them to your java ``CLASSPATH`` to run your code.

.. _install-prev-sdk:

Installing previous versions of the SDK
=======================================

Only the latest version of the SDK is provided in pre-built form. However, you can build a previous
version of the SDK using Apache Maven (open source). Maven will download all necessary dependencies,
build and install the SDK in one step. Visit http://maven.apache.org/ for installation instructions
and more information.

.. topic:: To install a previous version of the SDK

    #. Go to the SDK's GitHub page at: |sdk-java-github|_.

    #. Choose the tag corresponding to the version number of the SDK that you want. For example,
       ``1.6.10``.

    #. Click the :guilabel:`Download ZIP` button to download the version of the SDK you selected.

    #. Unzip the file to a directory on your development system. On many systems, you can use your
       graphical file manager to do this, or use the ``unzip`` utility in a terminal window.

    #. In a terminal window, navigate to the directory where you unzipped the SDK source.

    #. Build and install the SDK with the following command (Maven_ required)::

        mvn clean install

       The resulting :file:`.jar` file is built into the :file:`target` directory.

    #. (Optional) Build the API Reference documentation using the following command::

        mvn javadoc:javadoc

       The documentation is built into the :file:`target/site/apidocs/` directory.


.. _java-dg-java-env:

Installing a Java Development Environment
=========================================

The |sdk-java| requires J2SE Development Kit *6.0 or later*. You can download the latest Java
software from http://www.oracle.com/technetwork/java/javase/downloads/.

.. important:: Java version 1.6 (JS2E 6.0) did not have built-in support for SHA256-signed SSL
   certificates, which are required for all HTTPS connections with AWS after September 30, 2015.

   Java versions 1.7 or newer are packaged with updated certificates and are unaffected by this
   issue.

Choosing a JVM
--------------

For the best performance of your server-based applications with the AWS SDK for Java, we recommend
that you use the *64-bit version* of the Java Virtual Machine (JVM). This JVM runs only in server
mode, even if you specify the ``-Client`` option at run time.

Using the 32-bit version of the JVM with the ``-Server`` option at run time should provide
comparable performance to the 64-bit JVM.
