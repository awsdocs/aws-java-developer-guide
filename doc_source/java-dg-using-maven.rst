.. Copyright 2010-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

######################################
Using the |sdk-java| with Apache Maven
######################################

You can use *Apache Maven* to build |sdk-java| projects and to build the |sdk-java| itself.

.. note:: You must have Maven installed to use the guidance in this topic. If it isn't already
   installed, visit http://maven.apache.org/ to download and install it.

.. _configuring-maven:

Configuring the SDK as a Maven dependency
=========================================

To configure the SDK as a dependency in your Maven project, import the SDK's bill of materials (BOM)
into your project and then specify which modules you will need (or import all modules).

Importing the |sdk-java| BOM
----------------------------

To import the |sdk-java|'s BOM, set it as a dependency in your application's :file:`pom.xml` file:

.. code-block:: xml

    <dependencyManagement>
      <dependencies>
        <dependency>
          <groupId>com.amazonaws</groupId>
          <artifactId>aws-java-sdk-bom</artifactId>
          <version>1.10.43</version>
          <type>pom</type>
          <scope>import</scope>
        </dependency>
      </dependencies>
    </dependencyManagement>

.. note:: Because you declared the SDK version when pulling in the |sdk-java| BOM, you don't need to
   declare version numbers when you declare the SDK module dependencies. It also ensures that all
   AWS modules are from the same SDK version and are compatible with each other.


Specifying individual SDK modules
---------------------------------

Beginning with version 1.9.0 of the |sdk-java|, a modular Maven project structure can be used to
selectively pick components of the SDK that you want in your project. For example, if your project
uses only |S3| and |DDB|, you can configure the dependencies in your :file:`pom.xml` file like this:

.. code-block:: xml

    <dependencies>
        <dependency>
            <groupId>com.amazonaws</groupId>
            <artifactId>aws-java-sdk-s3</artifactId>
        </dependency>
        <dependency>
            <groupId>com.amazonaws</groupId>
            <artifactId>aws-java-sdk-dynamodb</artifactId>
        </dependency>
    </dependencies>


Importing all SDK modules
-------------------------

If you would like to pull the entire |sdk-java| as a dependency, declare it in your :file:`pom.xml`
like this:

.. code-block:: xml

    <dependencies>
        <dependency>
            <groupId>com.amazonaws</groupId>
            <artifactId>aws-java-sdk</artifactId>
        </dependency>
    </dependencies>


.. _building-with-maven:

Building the SDK with Maven
===========================

You can use Apache Maven to build the |sdk-java| from source. To do so, `download the SDK code from
GitHub <https://github.com/aws/aws-sdk-java>`_, unpack it locally, and then execute the following
Maven command:

.. code-block:: java

    mvn clean install

