.. Copyright 2010-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.

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

.. contents::
    :local:
    :depth: 1

Create a new Maven package
==========================

To create a basic Maven package, open a terminal (command-line) window and run:

.. code-block:: sh

   mvn -B archetype:generate \
     -DarchetypeGroupId=org.apache.maven.archetypes \
     -DgroupId=org.example.basicapp \
     -DartifactId=myapp

Replace *com.example.basicapp* with the full package namespace of your application, and *myapp* with
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

To configure the SDK as a dependency in your Maven project, import the SDK's bill of materials (BOM)
into your project and then specify which modules you will need (or import all modules).

Import the |sdk-java| BOM
-------------------------

To import the SDK's BOM, set it as a dependency in your application's :file:`pom.xml` file:

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

.. note:: Because you declared the SDK version when pulling in the SDK's BOM, you don't need to
   declare version numbers when you declare the SDK module dependencies. It also ensures that all
   AWS modules are from the same SDK version and are compatible with each other.


Specify individual SDK modules
------------------------------

Beginning with version 1.9.0 of the SDK, a modular Maven project structure can be used to
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


Import all SDK modules
----------------------

If you would like to pull the entire SDK as a dependency, declare it in your :file:`pom.xml`
like this:

.. code-block:: xml

    <dependencies>
        <dependency>
            <groupId>com.amazonaws</groupId>
            <artifactId>aws-java-sdk</artifactId>
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

