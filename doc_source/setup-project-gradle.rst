.. Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

#########################
Using the SDK with Gradle
#########################


.. meta::
   :description: How to use Gradle to set up your AWS SDK for Java project
   :keywords: AWS SDK for Java, Gradle, BOM, install, download, setup


To manage SDK dependencies for your Gradle_ project, import the Maven BOM for the |sdk-java| into the :file:`build.gradle` file.

.. note:: In the following examples, replace *1.11.X* in the build file with a valid version of the |sdk-java|. Find the latest version in the 
          `AWS SDK for Java 1.11.x Reference <https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/index.html>`_.


Project setup for Gradle 4.6 or higher
======================================

`Since Gradle 4.6 <https://docs.gradle.org/4.6/release-notes.html#bom-import>`_, you can
use Gradle's improved POM support feature for importing bill of materials (BOM) files by declaring a dependency on a BOM.


.. topic:: To configure the |sdk-java| for Gradle 4.6 or later

    #. If you're using Gradle 5.0 or later, skip to step 2. Otherwise, enable the *IMPROVED_POM_SUPPORT* feature in the :file:`settings.gradle` file.

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

    #. Specify the SDK modules to use in the *dependencies* section. For example, the following includes a dependency for |S3long| (|S3|).

       .. code-block:: groovy

          ...
          dependencies {
              implementation 'com.amazonaws:aws-java-sdk-s3'
              ...
          }

Gradle automatically resolves the correct version of your SDK dependencies by using the information from the BOM.

The following is an example of a complete :file:`build.gradle` file that includes a dependency for |S3|.

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

.. note:: In the previous example, replace the dependency for |S3| with the dependencies of the AWS services you will use in your project. The modules (dependencies) that are managed by the |sdk-java| BOM are listed on Maven central repository (https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk-bom/latest).


Project setup for Gradle versions earlier than 4.6
==================================================

Gradle versions earlier than 4.6 lack native BOM support. To manage |sdk-java| dependencies for your project,
use Spring's `dependency management plugin
<https://github.com/spring-gradle-plugins/dependency-management-plugin>`_ for Gradle to import the Maven BOM for the SDK.

.. topic:: To configure the SDK for Gradle versions earlier than 4.6

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

    #. Specify the SDK modules that you'll use in the *dependencies* section. For example, the following includes a dependency for |S3|.

       .. code-block:: groovy

          dependencies {
              compile 'com.amazonaws:aws-java-sdk-s3'
          }

Gradle automatically resolves the correct version of your SDK dependencies by using the information from the BOM.

The following is an example of a complete :file:`build.gradle` file that includes a dependency for |S3|.

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

.. note:: In the previous example, replace the dependency for |S3| with the dependencies of the AWS service you will use in your project. The modules (dependencies) that are managed by the |sdk-java| BOM are listed on Maven central repository (https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk-bom/latest).

For more information about specifying SDK dependencies by using the BOM, see
:doc:`setup-project-maven`.
