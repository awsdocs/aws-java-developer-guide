.. Copyright 2010-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

################
Document History
################

This topic describes important changes to the |sdk-java-dg| over the course of its history.

**Last documentation update:** |today|

Mar 04, 2016
    The :doc:`prog-services-swf` section has been updated with new content:

    * :doc:`swf-basics` |ndash| Provides basic information about how to include SWF in your
      projects.

    * :doc:`swf-hello` |ndash| A new tutorial that provides step-by-step guidance for Java
      developers new to |SWF|.

    * :doc:`swf-graceful-shutdown` |ndash| Describes how you can gracefully shut down |SWF|
      worker classes using Java's concurrency classes.

Feb 23, 2016
    The source for the |sdk-java-dg| has been moved to :github:`<awsdocs/aws-java-developer-guide>`.

Dec 28, 2015
    :doc:`java-dg-jvm-ttl` has been moved from :doc:`advanced` into the :doc:`basics` section, and
    has been rewritten for clarity.

    :doc:`java-dg-using-maven` has been updated with information about how to include the
    SDK's bill of materials (BOM) in your project.

Aug 04, 2015
    :doc:`use-sha256` is a new topic in the :doc:`getting-started` section that describes AWS' move to
    SHA256-signed certificates for SSL connections, and how to fix early 1.6 and previous Java
    environments to use these certificates, which are :emphasis:`required` for AWS access after
    September 30, 2015.

    .. note:: Java 1.7+ is already capable of working with SHA256-signed certificates.

May 14, 2014
    The :doc:`introduction <welcome>` and :doc:`getting started <getting-started>` material has been
    heavily revised to support the new guide structure and now includes guidance about how to
    :doc:`set-up-creds`.

    The discussion of :doc:`code samples <java-dg-samples>` has been moved into its own topic in the
    :ref:`additional-resources` section.

    Information about how to :ref:`view the SDK revision history <java-sdk-history>` has been moved
    into the introduction.

May 9, 2014
    The overall structure of the |sdk-java| documentation has been simplified, and the
    :doc:`getting-started` and :ref:`additional-resources` topics have been updated.

    New topics have been added:

    * :doc:`credentials` |ndash| discusses the various ways that you can specify credentials for use
      with the |sdk-java|.

    * :doc:`java-dg-roles` |ndash| provides information about how to securely specify credentials
      for applications running on EC2 instances.

September 9, 2013
    This topic, *Document History*, tracks changes to the |sdk-java-dg|. It is intended as a companion
    to the release notes history.


