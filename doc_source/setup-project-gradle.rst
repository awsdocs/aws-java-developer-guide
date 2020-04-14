.. Copyright 2010-2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

#########################
Using the SDK with Gradle
#########################


Project setup for Gradle 4.6 or higher
===============

`Since Gradle 4.6 <https://docs.gradle.org/4.6/release-notes.html#bom-import>`_ it is possible to
use Gradle's improved POM support feature for importing bill of materials (BOM) files by simply declaring a dependency on a BOM.

To manage SDK dependencies for your Gradle_ project, import the |sdk-java|'s Maven Bill of Materials (BOM) into the :file:`build.gradle` file.

.. note:: Replace *1.11.X* in the build file examples below with a valid version of the |sdk-java|. Find the latest version in the 
          `AWS SDK for Java 1.11.x Reference <https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/index.html>`_


.. topic:: To configure the |sdk-java| for Gradle 4.6 or higher:

    #. If you are using Gradle 5.0 or higher, skip to Step 2. Otherwise, enable the `IMPROVED_POM_SUPPORT` feature in the :file:`settings.gradle` file

       .. code-block:: groovy

          enableFeaturePreview('IMPROVED_POM_SUPPORT')

    #. Add the BOM to the *dependencies* section of the :file:`build.gradle` file.

       .. code-block:: groovy

          ...
          dependencies {
              implementation platform('com.amazonaws:aws-java-sdk-bom:1.11.X')

              // Declare individual SDK dependencies without version
              ...
          }

    #. Specify the SDK modules you want to use in the *dependencies* section. For example, the following includes a dependency for |S3long|.

       .. code-block:: groovy

          ...
          dependencies {
              implementation 'com.amazonaws:aws-java-sdk-s3'
              ...
          }

Gradle automatically resolves the correct version of your SDK dependencies using the information from the BOM.

The following is an example of a complete :file:`build.gradle` file that includes a dependency for |S3|:

.. code-block:: groovy

   group 'aws.test'
   version '1.0-SNAPSHOT'

   apply plugin: 'java'

   sourceCompatibility = 1.8

   repositories {
     mavenCentral()
   }

   dependencies {
     implementation platform('com.amazonaws:aws-java-sdk-bom:1.11.X')
     implementation 'com.amazonaws:aws-java-sdk-s3'
     testCompile group: 'junit', name: 'junit', version: '4.11'
   }

.. note:: Replace the dependency for |S3| above with the dependency or dependencies of the Amazon service(s) you will be using in your project. The modules (dependencies) that are managed by the |sdk-java| BOM are listed on Maven Central (https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk-bom/latest).


Project setup for Gradle versions prior to 4.6
===============

Gradle versions prior to 4.6 lack native BOM support. To manage |sdk-java| dependencies for your project,
use Spring's `dependency management plugin
<https://github.com/spring-gradle-plugins/dependency-management-plugin>`_ for Gradle to import the SDK's Maven Bill of Materials (BOM).

.. note:: Replace *1.11.X* in the build file examples below with a valid version of the |sdk-java|. Find the latest version in the 
          `AWS SDK for Java 1.11.x Reference <https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/index.html>`_

.. topic:: To configure the SDK for Gradle versions prior to 4.6:

    #. Add the dependency management plugin to your :file:`build.gradle` file.

       .. code-block:: groovy

          buildscript {
              repositories {
                  mavenCentral()
              }
              dependencies {
                  classpath "io.spring.gradle:dependency-management-plugin:1.0.9.RELEASE"
              }
          }

          apply plugin: "io.spring.dependency-management"

    #. Add the BOM to the *dependencyManagement* section of the file.

       .. code-block:: groovy

          dependencyManagement {
              imports {
                  mavenBom 'com.amazonaws:aws-java-sdk-bom:1.11.X'
              }
          }

    #. Specify the SDK modules that you'll be using in the *dependencies* section. For example, the following includes a dependency for |S3long|.

       .. code-block:: groovy

          dependencies {
              compile 'com.amazonaws:aws-java-sdk-s3'
          }

Gradle automatically resolves the correct version of your SDK dependencies using the information from the BOM.

The following is an example of a complete :file:`build.gradle` file that includes a dependency for |S3|:

.. code-block:: groovy

   group 'aws.test'
   version '1.0'

   apply plugin: 'java'

   sourceCompatibility = 1.8

   repositories {
     mavenCentral()
   }

   buildscript {
     repositories {
       mavenCentral()
     }
     dependencies {
       classpath "io.spring.gradle:dependency-management-plugin:1.0.9.RELEASE"
     }
   }

   apply plugin: "io.spring.dependency-management"

   dependencyManagement {
     imports {
       mavenBom 'com.amazonaws:aws-java-sdk-bom:1.11.X'
     }
   }

   dependencies {
     compile 'com.amazonaws:aws-java-sdk-s3'
     testCompile group: 'junit', name: 'junit', version: '4.11'
   }

.. note:: Replace the dependency for |S3| above with the dependency or dependencies of the Amazon service(s) you will be using in your project. The modules (dependencies) that are managed by the |sdk-java| BOM are listed on Maven Central (https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk-bom/latest).

For more detail about specifying SDK dependencies using the BOM, see
:doc:`setup-project-maven`.
