.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

#########################
Using the SDK with Gradle
#########################

To use the |sdk-java| in your Gradle_ project, use Spring's `dependency management plugin
<https://github.com/spring-gradle-plugins/dependency-management-plugin>`_ for Gradle, which can be
used to import the SDK's Maven Bill of Materials (BOM) to manage SDK dependencies for your project.

.. topic:: To configure the SDK for Gradle

    #. Add the dependency management plugin to your :file:`build.gradle` file

       .. code-block:: groovy

          buildscript {
              repositories {
                  mavenCentral()
              }
              dependencies {
                  classpath "io.spring.gradle:dependency-management-plugin:1.0.3.RELEASE"
              }
          }

          apply plugin: "io.spring.dependency-management"

    #. Add the BOM to the *dependencyManagement* section of the file

       .. code-block:: groovy

          dependencyManagement {
              imports {
                  mavenBom 'com.amazonaws:aws-java-sdk-bom:1.11.228'
              }
          }

    #. Specify the SDK modules that you'll be using in the *dependencies* section

       .. code-block:: groovy

          dependencies {
              compile 'com.amazonaws:aws-java-sdk-s3'
              testCompile group: 'junit', name: 'junit', version: '4.11'
          }

Gradle will automatically resolve the correct version of your SDK dependencies using the information
from the BOM.

Here's the complete :file:`build.gradle` file:

.. code-block:: groovy

   group 'aws.test'
   version '1.0-SNAPSHOT'

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
         classpath "io.spring.gradle:dependency-management-plugin:1.0.3.RELEASE"
     }
   }

   apply plugin: "io.spring.dependency-management"

   dependencyManagement {
     imports {
         mavenBom 'com.amazonaws:aws-java-sdk-bom:1.11.228'
     }
   }

   dependencies {
     compile 'com.amazonaws:aws-java-sdk-s3'
     testCompile group: 'junit', name: 'junit', version: '4.11'
   }

.. note:: For more detail about specifying SDK dependencies using the BOM, see
   :doc:`setup-project-maven`.
