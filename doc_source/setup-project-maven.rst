.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

###############################
Using the SDK with Apache Maven
###############################

You can use |mvnlong|_ to configure and build |sdk-java| projects, or to build the SDK itself.

.. note:: You must have Maven installed to use the guidance in this topic. If it isn't already
   installed, visit http://maven.apache.org/ to download and install it.


Create a new Maven package
==========================

To create a basic Maven package, open a terminal (command-line) window and run:

.. code-block:: sh

   mvn -B archetype:generate \
     -DarchetypeGroupId=org.apache.maven.archetypes \
     -DgroupId=org.example.basicapp \
     -DartifactId=myapp

Replace *org.example.basicapp* with the full package namespace of your application, and *myapp* with
the name of your project (this will become the name of the directory for your project).

By default, |mvn| creates a project template for you using the `quickstart
<http://maven.apache.org/archetypes/maven-archetype-quickstart/>`_ archetype, which is a good
starting place for many projects. There are more archetypes available; visit the `Maven archetypes
<https://maven.apache.org/archetypes/index.html>`_ page for a list of archetypes packaged with
|mvn|. You can choose a particular archetype to use by adding the ``-DarchetypeArtifactId`` argument
to the ``archetype:generate`` command. For example:

.. code-block:: sh
   :emphasize-lines: 3

   mvn archetype:generate \
     -DarchetypeGroupId=org.apache.maven.archetypes \
     -DarchetypeArtifactId=maven-archetype-webapp \
     -DgroupId=org.example.webapp \
     -DartifactId=mywebapp

.. tip:: Much more information about creating and configuring |mvn| projects is provided in the
   `Maven Getting Started Guide <https://maven.apache.org/guides/getting-started/>`_.


.. _configuring-maven:

Configure the SDK as a Maven dependency
=======================================

To use the |sdk-java| in your project, you'll need to declare it as a dependency in your project's
:file:`pom.xml` file. Beginning with version 1.9.0, you can import :ref:`individual components
<configuring-maven-individual-components>` or the :ref:`entire SDK <configuring-maven-entire-sdk>`.

.. _configuring-maven-individual-components:

Specifying individual SDK modules
---------------------------------

To select individual SDK modules, use the |sdk-java| bill of materials (BOM) for Maven, which will
ensure that the modules you specify use the same version of the SDK and that they're compatible with
each other.

To use the BOM, add a :code-xml:`<dependencyManagement>` section to your application's
:file:`pom.xml` file, adding ``aws-java-sdk-bom`` as a dependency and specifying the version of the
SDK you want to use:

.. code-block:: xml

    <dependencyManagement>
      <dependencies>
        <dependency>
          <groupId>com.amazonaws</groupId>
          <artifactId>aws-java-sdk-bom</artifactId>
          <version>1.11.327</version>
          <type>pom</type>
          <scope>import</scope>
        </dependency>
      </dependencies>
    </dependencyManagement>

To view the latest version of the |sdk-java| BOM that is available on Maven Central, visit:
https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk-bom. You can also use this page to see
which modules (dependencies) are managed by the BOM that you can include within the
:code-xml:`<dependencies>` section of your project's :file:`pom.xml` file.

You can now select individual modules from the SDK that you use in your application. Because you
already declared the SDK version in the BOM, you don't need to specify the version number for each
component.

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

You can also refer to the *AWS Code Catalog* to learn what dependencies to use for a given AWS service. Refer to the POM file under a specific service example.
For example, if you are interested in the dependencies for the AWS S3 service, see the :sdk-examples-java-s3:`complete example <GetAcl.java>` on GitHub. (Look at the pom under /java/example_code/s3).

.. _configuring-maven-entire-sdk:

Importing all SDK modules
-------------------------

If you would like to pull in the *entire* SDK as a dependency, don't use the BOM method, but simply
declare it in your :file:`pom.xml` like this:

.. code-block:: xml

  <dependencies>
    <dependency>
      <groupId>com.amazonaws</groupId>
      <artifactId>aws-java-sdk</artifactId>
      <version>1.11.327</version>
    </dependency>
  </dependencies>


Build your project
==================

Once you have your project set up, you can build it using Maven's ``package`` command::

 mvn package

This will create your ``.jar`` file in the ``target`` directory.


.. _building-with-maven:

Build the SDK with Maven
========================

You can use Apache Maven to build the SDK from source. To do so, `download the SDK code from
GitHub <https://github.com/aws/aws-sdk-java>`_, unpack it locally, and then execute the following
Maven command::

 mvn clean install
