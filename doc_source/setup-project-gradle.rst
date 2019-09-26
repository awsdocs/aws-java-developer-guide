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

`Since Gradle 4.6 <https://docs.gradle.org/4.6/release-notes.html#bom-import>`_ it is possible to
use Gradle's improved POM support feature for importing bill of materials (BOM) files by simply declaring a dependency on a BOM.

.. topic:: To configure the SDK for Gradle 4.6 and upper

    #. Enable `IMPROVED_POM_SUPPORT` feature in :file:`settings.gradle` file (not needed in Gradle 5 and upper)

       .. code-block:: groovy

          enableFeaturePreview('IMPROVED_POM_SUPPORT')

    #. Import BOM as a usual dependency in the *dependencies* section

       .. code-block:: groovy

          dependencies {
              implementation platform('com.amazonaws:aws-java-sdk-bom:1.11.228')
          }

    #. Specify the SDK modules that you'll be using in the *dependencies* section

       .. code-block:: groovy

          dependencies {
              implementation 'com.amazonaws:aws-java-sdk-s3'
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

   dependencies {
       implementation platform('com.amazonaws:aws-java-sdk-bom:1.11.228')
       implementation 'com.amazonaws:aws-java-sdk-s3'
       testCompile group: 'junit', name: 'junit', version: '4.11'
   }

Gradle versions prior to 4.6 lack of native BOM support, so Spring's `dependency management plugin
<https://github.com/spring-gradle-plugins/dependency-management-plugin>`_ for Gradle can be used
to import the SDK's Maven Bill of Materials (BOM) to manage SDK dependencies for your project.

.. topic:: To configure the SDK for Gradle prior 4.6

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
