###################
Working with Tables
###################

.. meta::
   :description: How to create, describe, modify (update) and delete Amazon DynamoDB tables.
   :keywords: database, dynamodb, tables, create table, delete table, describe table, modify table,
              update table

Tables are the container for all items in a |DDB| database. Before you can add or remove data from
|DDB|, you must create a table.

For each table, you must define:

* A table *name*, which must be unique for your account and region.

* A *primary key* for which every value must be unique; no two items in your table can have the same
  primary key value. A primary key can be *simple*, consisting of a single partition (HASH) key, or
  *composite*, consisting of both a partition and a sort (RANGE) key.

  Each key value has an associated *data type*, enumerated by the
  :aws-java-class:`ScalarAttributeType <services/dynamodbv2/model/ScalarAttributeType>` class. It
  can be either binary (B), numeric (N), or a string (S). For more information, refer to
  :ddb-dg:`Naming Rules and Data Types <HowItWorks.NamingRulesDataTypes>` in the |ddb-dg|.

* *Provisioned throughput* values, which define the number of reserved read / write capacity units
  for the table.

  .. tip:: :pricing:`Amazon DynamoDB pricing <dynamodb>` is based on the provisioned throughput
     values that you set on your tables, so you should only reserve as much capacity as you expect
     you will need for your table.

     Provisioned throughput can be modified at any time for a table, so you can adjust capacity if
     your needs change.

.. _dynamodb-create-table:

Create a Table
==============

Use the :aws-java-class:`DynamoDB client <services/dynamodbv2/AmazonDynamoDB>`'s
:methodname:`createTable` method to create a new |DDB| table. You will need to construct table
attributes and a table schema, both of which serve to identify the primary key of your table. You
must also supply initial provisioned throughput values and give the table a name.

.. note:: If a table already exists with the name that you've chosen, then an
   :aws-java-class:`AmazonServiceException` will be thrown.

**Imports:**

.. literalinclude:: ../../example_code/dynamodb/src/main/java/aws/example/dynamodb/CreateTable.java
   :lines: 15-23

.. _dynamodb-create-table-simple:

Creating a table with a simple primary key
------------------------------------------

This code creates a table with a simple primary key ("Name"):

**Code:**

.. literalinclude:: ../../example_code/dynamodb/src/main/java/aws/example/dynamodb/CreateTable.java
   :lines: 58-73
   :dedent: 8

See the :sdk-examples-java-dynamodb:`complete sample <CreateTable.java>`.

.. _dynamodb-create-table-composite:

Creating a table with a composite primary key
---------------------------------------------

To create a table with a composite primary key, add an additional
:aws-java-class:`AttributeDefinition <services/dynamodbv2/model/AttributeDefinition>` and
:aws-java-class:`KeySchemaElement <services/dynamodbv2/model/KeySchemaElement>` to the
:aws-java-class:`CreateTableRequest <dynamodbv2/model/CreateTableRequest>`.

**Code:**

.. literalinclude:: ../../example_code/dynamodb/src/main/java/aws/example/dynamodb/CreateTableCompositeKey.java
   :lines: 58-67
   :dedent: 8

See the :sdk-examples-java-dynamodb:`complete sample <CreateTableCompositeKey.java>`.

.. _dynamodb-list-tables:

Listing Tables
==============

You can list the tables in a particular region by calling the :aws-java-class:`DynamoDB client
<services/dynamodbv2/AmazonDynamoDB>`'s :methodname:`listTables` method.

.. note:: If the named table doesn't exist for your account and region, then a
   :aws-java-class:`ResourceNotFoundException <services/dynamodbv2/model/ResourceNotFoundException>`
   exception will result.

**Imports:**

.. literalinclude:: ../../example_code/dynamodb/src/main/java/aws/example/dynamodb/ListTables.java
   :lines: 15-18

**Code:**

.. literalinclude:: ../../example_code/dynamodb/src/main/java/aws/example/dynamodb/ListTables.java
   :lines: 32-64
   :dedent: 8

By default, up to 100 tables will be returned per call |mdash| use
:methodname:`getLastEvaluatedTableName` on the returned :aws-java-class:`ListTablesResult <>` object
to get the last table evaluated. You can use this value to start the listing after the last returned
value of the previous listing.

See the :sdk-examples-java-dynamodb:`complete sample <ListTables.java>`.

.. _dynamodb-describe-table:

Describe a Table
================

You can describe (get information about) a table by calling the :aws-java-class:`DynamoDB client
<services/dynamodbv2/AmazonDynamoDB>`'s :methodname:`describeTable` method.

.. note:: If the named table doesn't exist for your account and region, then a
   :aws-java-class:`ResourceNotFoundException <services/dynamodbv2/model/ResourceNotFoundException>`
   exception will result.

**Imports:**

.. literalinclude:: ../../example_code/dynamodb/src/main/java/aws/example/dynamodb/DescribeTable.java
   :lines: 15-20

**Code:**

.. literalinclude:: ../../example_code/dynamodb/src/main/java/aws/example/dynamodb/DescribeTable.java
   :lines: 50-87
   :dedent: 8

See the :sdk-examples-java-dynamodb:`complete sample <DescribeTable.java>`.


.. _dynamodb-update-table:

Modify (Update) a Table
=======================

You can update the provisioned throughput values for your table at any time by calling the
:aws-java-class:`DynamoDB client <services/dynamodbv2/AmazonDynamoDB>`'s :methodname:`updateTable`
method.

.. note:: If the named table doesn't exist for your account and region, then a
   :aws-java-class:`ResourceNotFoundException <services/dynamodbv2/model/ResourceNotFoundException>`
   exception will result.

**Imports:**

.. literalinclude:: ../../example_code/dynamodb/src/main/java/aws/example/dynamodb/UpdateTable.java
   :lines: 15-22

**Code:**

.. literalinclude:: ../../example_code/dynamodb/src/main/java/aws/example/dynamodb/UpdateTable.java
   :lines: 82-105
   :dedent: 8

See the :sdk-examples-java-dynamodb:`complete sample <UpdateTable.java>`.


.. _dynamodb-delete-table:

Delete a Table
==============

To delete a table, call the :aws-java-class:`DynamoDB client <services/dynamodbv2/AmazonDynamoDB>`'s
:methodname:`deleteTable` method, passing it the table's name.

.. note:: If the named table doesn't exist for your account and region, then a
   :aws-java-class:`ResourceNotFoundException <services/dynamodbv2/model/ResourceNotFoundException>`
   exception will result.

**Imports:**

.. literalinclude:: ../../example_code/dynamodb/src/main/java/aws/example/dynamodb/DeleteTable.java
   :lines: 15-16

**Code:**

.. literalinclude:: ../../example_code/dynamodb/src/main/java/aws/example/dynamodb/DeleteTable.java
   :lines: 51-58
   :dedent: 8

See the :sdk-examples-java-dynamodb:`complete sample <DeleteTable.java>`.


See Also
========

* :ddb-dg:`Guidelines for Working with Tables <GuidelinesForTables>` in the |ddb-dg|
* :ddb-dg:`Working with Tables in DynamoDB <WorkingWithTables>` in the |ddb-dg|

