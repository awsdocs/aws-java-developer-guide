.. Copyright 2010-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

.. meta::
    :description:
         Welcome to the AWS Java Developer Guide

########################
AWS Java Developer Guide
########################

The |sdk-java|_ provides a Java API for |AWSlong|. Using the SDK, you can easily build Java
applications that work with |S3|, |EC2|, |SDB|, and more. We regularly add support for new services
to the |sdk-java|. For a list of the supported services and their API versions that are included
with each release of the SDK, view the `release notes`_ for the version that you're working with.

.. contents::
   :local:
   :depth: 1


.. _additional-resources:

Additional documentation and resources
======================================

In addition to this guide, there are a number of valuable online resources available for |sdk-java|
developers:

* |sdk-java-ref|_

* `Java developer blog <http://java.awsblog.com/>`_

* :forum:`Java developer forums <70>`

* GitHub:

  + :github:`Documentation source <awsdocs/aws-java-developer-guide>`

  + :github:`Documentation issues <awsdocs/aws-java-developer-guide/issues>`

  + :github:`SDK source <aws/aws-sdk-java>`

  + :github:`SDK issues <aws/aws-sdk-java/issues>`

  + :github:`SDK samples <aws/aws-sdk-java/tree/master/src/samples>`

  + `Gitter channel <http://gitter.im/aws/aws-sdk-java>`_

* `@awsforjava (Twitter) <http://twitter.com/awsforjava>`_

* `release notes <http://aws.amazon.com/releasenotes/Java>`_


.. _eclipse-support:

Eclipse IDE support
===================

If you develop code using the Eclipse IDE, you can use the |tke|_ to add the |sdk-java| to an
existing Eclipse project or to create a new AWS Java project. The toolkit also supports the creation
and uploading of |LAM| functions, launching and monitoring |EC2| instances, management of IAM users
and security groups, a |CFN| template editor and more.

Visit the |tke-ug|_ for full documentation.


.. _android-support:

Developing AWS applications for Android
=======================================

If you are an Android developer, |AWSlong| publishes a SDK made specifically for Android
development: the |sdk-android|_. Visit the |sdk-android-dg|_ for full documentation.


.. _java-sdk-history:

Viewing the SDK's revision history
==================================

To view the release history of the |sdk-java|, including changes and supported services per SDK
version, see the SDK's `release notes`_.


.. _build-old-reference-docs:

Building Java reference documentation for old SDK versions
==========================================================

The |sdk-java-ref|_ represents the most recent version of the SDK. If you're using an older SDK
version, you may wish to access the SDK reference documentation that matches the version you're
using.

The easiest way to build the documentation is using Apache's Maven_ build tool. *Download and
install Maven first if you don't already have it on your system*, then use the following
instructions to build the reference documentation.

**To build reference documentation for an old SDK version**

#. Locate and select the SDK version that you're using on the :github:`releases
   <aws/aws-sdk-java/releases>` page of the SDK repository on GitHub.

#. Choose either the ``zip`` (most platforms, including Windows) or ``tar.gz`` (unix-like platforms
   such as OS X or Linux) link to download the SDK to your computer.

#. Unpack the archive to a local directory.

#. On the command-line, navigate to the directory where you unpacked the archive, and type::

    mvn javadoc:javadoc

#. After building is complete, you'll find the generated HTML documentation in the
   :file:`aws-java-sdk/target/site/apidocs/` directory.

