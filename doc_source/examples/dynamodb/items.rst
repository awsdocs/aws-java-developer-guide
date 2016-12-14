##################
Working with Items
##################

In DynamoDB, an item is a collection of *attributes*, which consist of a *name* and a *value*. An
attribute value can be a scalar, set, or document type. For more information, see :ddb-dg:`Naming
Rules and Data Types <HowItWorks.NamingRulesDataTypes>` in the |ddb-dg|.

.. _dynamodb-get-item:

Retrieve (get) an item from a table
===================================

To get an item from a table, call the :aws-java-class:`DynamoDB client
<services/dynamodbv2/AmazonDynamoDB>`'s :methodname:`getItem` method, passing it a
:aws-java-class:`GetItemRequest <services/dynamodbv2/model/GetItemRequest>` object with the table
name and primary key value of the desired item. It returns a `GetItemResult
<services/dynamodbv2/model/GetItemResult>` object.

You can use the returned :classname:`GetItemResult` object's :methodname:`getItem()` method to
retrieve a :javase-ref:`Map <java/util/Map>` of key (String) and value
(:aws-java-class:`AttributeValue <services/dynamodbv2/model/AttributeValue>`) pairs associated with
the item.

**Imports:**

.. literalinclude:: ../../example_code/dynamodb/src/main/java/aws/example/dynamodb/GetItem.java
   :lines: 15-21

**Code:**

.. literalinclude:: ../../example_code/dynamodb/src/main/java/aws/example/dynamodb/GetItem.java
   :lines: 68-102
   :dedent: 8

See the :sdk-examples-java-dynamodb:`complete sample <GetItem.java>`.


.. _dynamodb-add-item:

Add a new item to a table
=========================

To add a new item to a table, create a :javase-ref:`Map <java/util/Map>` of key/value pairs that
represent the attributes of the item. These must include values for the primary key field(s) of the
table. If the item identified by the primary key already exists, then its fields will be *updated*
by the request.

.. note:: If the named table doesn't exist for your account and region, then a
   :aws-java-class:`ResourceNotFoundException <services/dynamodbv2/model/ResourceNotFoundException>`
   exception will result.

**Imports:**

.. literalinclude:: ../../example_code/dynamodb/src/main/java/aws/example/dynamodb/PutItem.java
   :lines: 15-20

**Code:**

.. literalinclude:: ../../example_code/dynamodb/src/main/java/aws/example/dynamodb/PutItem.java
   :lines: 76-96
   :dedent: 8

See the :sdk-examples-java-dynamodb:`complete sample <PutItem.java>`.


.. _dynamodb-update-item:

Update an existing item in a table
==================================

You can update an attribute for an item that already exists in a table by using the
:aws-java-class:`DynamoDB client <services/dynamodbv2/AmazonDynamoDB>`'s :methodname:`updateItem`
method, providing a table name, primary key value and a map of fields to update.

.. note:: If the named table doesn't exist for your account and region, or if the item identified by
   the passed-in primary key doesn't exist, then a :aws-java-class:`ResourceNotFoundException
   <services/dynamodbv2/model/ResourceNotFoundException>` exception will result.

**Imports:**

.. literalinclude:: ../../example_code/dynamodb/src/main/java/aws/example/dynamodb/UpdateItem.java
   :lines: 15-22

**Code:**

.. literalinclude:: ../../example_code/dynamodb/src/main/java/aws/example/dynamodb/UpdateItem.java
   :lines: 82-105
   :dedent: 8

See the :sdk-examples-java-dynamodb:`complete sample <UpdateItem.java>`.


See Also
========

* :ddb-dg:`Guidelines for Working with Items <GuidelinesForItems>` in the |ddb-dg|
* :ddb-dg:`Working with Items in DynamoDB <WorkingWithItems>` in the |ddb-dg|

