.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

############################
Working with Tables in |DDB|
############################

.. meta::
   :description: How to create, describe, modify (update) and delete Amazon DynamoDB tables.
   :keywords: AWS SDK for Java code examples, DynamoDB tables


Tables are the containers for all items in a |DDB| database. Before you can add or remove data from
|DDB|, you must create a table.

For each table, you must define:

* A table *name* that is unique for your account and region.

* A *primary key* for which every value must be unique; no two items in your table can have the
  same primary key value.

  A primary key can be *simple*, consisting of a single partition (HASH) key, or *composite*, consisting
  of a partition and a sort (RANGE) key.

  Each key value has an associated *data type*, enumerated by the
  :aws-java-class:`ScalarAttributeType <services/dynamodbv2/model/ScalarAttributeType>` class. The key
  value can be binary (B), numeric (N), or a string (S). For more information, see
  :ddb-dg:`Naming Rules and Data Types <HowItWorks.NamingRulesDataTypes>` in the |ddb-dg|.

* *Provisioned throughput* values that define the number of reserved read/write capacity units
  for the table.

  .. tip:: :pricing:`Amazon DynamoDB pricing <dynamodb>` is based on the provisioned throughput
     values that you set on your tables, so reserve only as much capacity as you think
     you'll need for your table.

     Provisioned throughput for a table can be modified at any time, so you can adjust capacity
     if your needs change.

.. _dynamodb-create-table:

Create a Table
==============

Use the :aws-java-class:`DynamoDB client <services/dynamodbv2/AmazonDynamoDB>`'s
:methodname:`createTable` method to create a new |DDB| table. You need to construct table
attributes and a table schema, both of which are used to identify the primary key of your table. You
must also supply initial provisioned throughput values and a table name. Only define key table attributes 
when creating your |DDB| table.

.. note:: If a table with the name you chose already exists, an
   :aws-java-class:`AmazonServiceException` is thrown.

.. These Imports/Code headings are sufficient without the colons

**Imports**

.. literalinclude:: example_code/dynamodb/src/main/java/aws/example/dynamodb/CreateTable.java
   :lines: 15-24
   :language: java

.. _dynamodb-create-table-simple:

Create a Table with a Simple Primary Key
----------------------------------------

This code creates a table with a simple primary key ("Name"). 

**Code**

.. literalinclude:: example_code/dynamodb/src/main/java/aws/example/dynamodb/CreateTable.java
   :lines: 59-75
   :dedent: 8
   :language: java

See the :sdk-examples-java-dynamodb:`complete example <CreateTable.java>` on GitHub.

.. _dynamodb-create-table-composite:

Create a Table with a Composite Primary Key
---------------------------------------------

Add another
:aws-java-class:`AttributeDefinition <services/dynamodbv2/model/AttributeDefinition>` and
:aws-java-class:`KeySchemaElement <services/dynamodbv2/model/KeySchemaElement>` to
:aws-java-class:`CreateTableRequest <services/dynamodbv2/model/CreateTableRequest>`.

**Code**

.. literalinclude:: example_code/dynamodb/src/main/java/aws/example/dynamodb/CreateTableCompositeKey.java
   :lines: 59-68
   :dedent: 8
   :language: java

See the :sdk-examples-java-dynamodb:`complete example <CreateTableCompositeKey.java>` on GitHub.

.. _dynamodb-list-tables:

List Tables
===========

You can list the tables in a particular region by calling the :aws-java-class:`DynamoDB client
<services/dynamodbv2/AmazonDynamoDB>`'s :methodname:`listTables` method.

.. note:: If the named table doesn't exist for your account and region, a
   :aws-java-class:`ResourceNotFoundException <services/dynamodbv2/model/ResourceNotFoundException>`
   is thrown.

**Imports**

.. literalinclude:: example_code/dynamodb/src/main/java/aws/example/dynamodb/ListTables.java
   :lines: 15-19
   :language: java

**Code**

.. literalinclude:: example_code/dynamodb/src/main/java/aws/example/dynamodb/ListTables.java
   :lines: 33-67
   :dedent: 8
   :language: java

By default, up to 100 tables are returned per call |mdash| use
:methodname:`getLastEvaluatedTableName` on the returned :aws-java-class:`ListTablesResult <ListTablesResult>` object
to get the last table that was evaluated. You can use this value to start the listing after the last
returned
value of the previous listing.

See the :sdk-examples-java-dynamodb:`complete example <ListTables.java>` on GitHub.

.. _dynamodb-describe-table:

Describe (Get Information about) a Table
========================================

Call the :aws-java-class:`DynamoDB client
<services/dynamodbv2/AmazonDynamoDB>`'s :methodname:`describeTable` method.

.. note:: If the named table doesn't exist for your account and region, a
   :aws-java-class:`ResourceNotFoundException <services/dynamodbv2/model/ResourceNotFoundException>`
   is thrown.

**Imports**

.. literalinclude:: example_code/dynamodb/src/main/java/aws/example/dynamodb/DescribeTable.java
   :lines: 15-20
   :language: java

**Code**

.. literalinclude:: example_code/dynamodb/src/main/java/aws/example/dynamodb/DescribeTable.java
   :lines: 51-88
   :dedent: 8
   :language: java

See the :sdk-examples-java-dynamodb:`complete example <DescribeTable.java>` on GitHub.


.. _dynamodb-update-table:

Modify (Update) a Table
=======================

You can modify your table's provisioned throughput values at any time by calling the
:aws-java-class:`DynamoDB client <services/dynamodbv2/AmazonDynamoDB>`'s :methodname:`updateTable`
method.

.. note:: If the named table doesn't exist for your account and region, a
   :aws-java-class:`ResourceNotFoundException <services/dynamodbv2/model/ResourceNotFoundException>`
   is thrown.

**Imports**

.. literalinclude:: example_code/dynamodb/src/main/java/aws/example/dynamodb/UpdateTable.java
   :lines: 15-18
   :language: java

**Code**

.. literalinclude:: example_code/dynamodb/src/main/java/aws/example/dynamodb/UpdateTable.java
   :lines: 57-68
   :dedent: 8
   :language: java

See the :sdk-examples-java-dynamodb:`complete example <UpdateTable.java>` on GitHub.


.. _dynamodb-delete-table:

Delete a Table
==============

Call the :aws-java-class:`DynamoDB client <services/dynamodbv2/AmazonDynamoDB>`'s
:methodname:`deleteTable` method and pass it the table's name.

.. note:: If the named table doesn't exist for your account and region, a
   :aws-java-class:`ResourceNotFoundException <services/dynamodbv2/model/ResourceNotFoundException>`
   is thrown.

**Imports**

.. literalinclude:: example_code/dynamodb/src/main/java/aws/example/dynamodb/DeleteTable.java
   :lines: 15-17
   :language: java

**Code**

.. literalinclude:: example_code/dynamodb/src/main/java/aws/example/dynamodb/DeleteTable.java
   :lines: 52-59
   :dedent: 8
   :language: java

See the :sdk-examples-java-dynamodb:`complete example <DeleteTable.java>` on GitHub.

More Info
=========

* :ddb-dg:`Guidelines for Working with Tables <GuidelinesForTables>` in the |ddb-dg|
* :ddb-dg:`Working with Tables in DynamoDB <WorkingWithTables>` in the |ddb-dg|
