.. Copyright 2010-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

############################
SSL Certificate Requirements
############################

Amazon Web Services (AWS) has been updating service endpoints to use SHA256-signed hashes in SSL
certificates for HTTPS access, and will retire support for SHA1-signed hashes by September 30, 2015.
Clients that don't have the correct CA root certificate to verify AWS' new SHA256-signed
certificates will be unable to access AWS.

.. note:: This guidance affects primarily those who are using Java version 1.5 and early versions of
   1.6. Later versions of Java 1.6 or newer are packaged with SHA256 root certificates by default,
   so you can ignore this section.

.. contents:: **Contents**
   :depth: 1
   :local:

.. _use-sha256-test-java:

Test your development environment
=================================

To test whether your development or production environment is compatible with SHA256-signed
certificates, write code that performs an :code:`HTTPS GET` to
:code:`https://www.amazonsha256.com/`. If the TLS handshake succeeds, then your Java environment is
fine, and there's no need to update it.

Amazon has provided a set of scripts for :emphasis:`Java`, :emphasis:`JavaScript`, :emphasis:`PHP`,
:emphasis:`Python` and :emphasis:`Ruby` that you can use to test your installation of these
languages. For other programming languages, consult your language's documentation for information
about how to access websites using HTTPS.

This guidance will focus on Java. For guidance about other development languages, refer to the AWS
security bulletin: `AWS to Switch to SHA256 Hash Algorithm for SSL Certificates
<http://aws.amazon.com/security/security-bulletins/aws-to-switch-to-sha256-hash-algorithm-for-ssl-certificates/>`_

**To test your Java development environment**

1.  Download the AWS SHA256 test scripts: https://www.amazonsha256.com/shaTest.zip

2.  Extract the :code:`shaTest.zip` archive locally.

3.  Open a terminal (command-line) session and navigate to the directory where you extracted the
    archive. For example:

    .. code-block:: sh

        cd Downloads/shaTest

4.  Run the following commands

    .. code-block:: sh

        javac ShaTest.java
        java ShaTest

    If the text succeeds, you'll see the following output:

    .. code-block:: sh

        Success


.. _use-sha256-import-cert:

Manually updating SSL Certificates for SHA256 support
=====================================================

If your Java Runtime Environment (JRE) fails the test for support for SHA256-signed certificates and
if upgrading your JRE to the latest 1.6 version or above isn't feasible, then you can manually
import the `root SSL certificate <https://en.wikipedia.org/wiki/Root_certificate>`_ necessary for
using AWS' new SHA256 certificates into your environment.

**To manually update your root SSL certificates**

1.  Download the root certificate from
    https://www.symantec.com/content/en/us/enterprise/verisign/roots/VeriSign-Class%203-Public-Primary-Certification-Authority-G5.pem

    Put this file in an easy-to-remember location.

2.  Open a terminal (command-line) window.

3.  Find the location of your JRE's security directory. Open a terminal (command-line) window and
    type:

    .. code-block:: sh

        cd $JAVA_HOME/jre/lib/security

    .. note:: The value of :envvar:`JAVA_HOME` can vary widely by operating system. Here are some
        typical locations for common OSes:

        *   **OS X**:

            .. code-block:: sh

                export JAVA_HOME=$(/usr/libexec/java_home)

        *   **Windows**:

            .. code-block:: bat

                set JAVA_HOME="C:\Program Files\Java\java_ver"

            where :emphasis:`java_ver` is the directory for your particular version of Java.

4.  Import the root certificate using the following command:

    .. code-block:: sh

        keytool -keystore cacerts -storepass changeit -importcert -alias verisignclass3g5ca \
        -file path/to/certpemfile

    where :emphasis:`path/to/certpemfile` is the location where you downloaded the root certificate
    in the previous step.

    .. note:: In the above command, "changeit" is default password for Java's keystore. If you have
        changed this password to something else, then substitute that password for the value
        "changeit".

5.  Verify the root certificate that you've installed. From within your
    :code:`$JAVA_HOME/jre/lib/security` directory, type the following command:

    .. code-block:: sh

        keytool -keystore cacerts -storepass changeit -list -v|grep -A8 -B6 \
        -i 18dad19e267de8bb4a2158cdcc6b3b4a

    The command should generate output that describes the certificate. No output is emitted if the
    certificate is not found. If the certificate was successfully imported, then running the
    :file:`ShaTest.java` program should now result in success. See :ref:`use-sha256-test-java` for
    details.

