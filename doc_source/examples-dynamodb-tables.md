# Working with Tables in DynamoDB<a name="examples-dynamodb-tables"></a>

Tables are the containers for all items in a DynamoDB database\. Before you can add or remove data from DynamoDB, you must create a table\.

For each table, you must define:
+ A table *name* that is unique for your account and region\.
+ A *primary key* for which every value must be unique; no two items in your table can have the same primary key value\.

  A primary key can be *simple*, consisting of a single partition \(HASH\) key, or *composite*, consisting of a partition and a sort \(RANGE\) key\.

  Each key value has an associated *data type*, enumerated by the [ScalarAttributeType](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/dynamodbv2/model/ScalarAttributeType.html) class\. The key value can be binary \(B\), numeric \(N\), or a string \(S\)\. For more information, see [Naming Rules and Data Types](http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/HowItWorks.NamingRulesDataTypes.html) in the Amazon DynamoDB Developer Guide\.
+  *Provisioned throughput* values that define the number of reserved read/write capacity units for the table\.
**Note**  
 [Amazon DynamoDB pricing](http://aws.amazon.com/dynamodb/pricing/) is based on the provisioned throughput values that you set on your tables, so reserve only as much capacity as you think you’ll need for your table\.

Provisioned throughput for a table can be modified at any time, so you can adjust capacity if your needs change\.

## Create a Table<a name="dynamodb-create-table"></a>

Use the [DynamoDB client](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/dynamodbv2/AmazonDynamoDB.html)'s `createTable` method to create a new DynamoDB table\. You need to construct table attributes and a table schema, both of which are used to identify the primary key of your table\. You must also supply initial provisioned throughput values and a table name\. Only define key table attributes when creating your DynamoDB table\.

**Note**  
If a table with the name you chose already exists, an [AmazonServiceException](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/AmazonServiceException.html) is thrown\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
```

### Create a Table with a Simple Primary Key<a name="dynamodb-create-table-simple"></a>

This code creates a table with a simple primary key \("Name"\)\.

 **Code** 

```
CreateTableRequest request = new CreateTableRequest()
    .withAttributeDefinitions(new AttributeDefinition(
             "Name", ScalarAttributeType.S))
    .withKeySchema(new KeySchemaElement("Name", KeyType.HASH))
    .withProvisionedThroughput(new ProvisionedThroughput(
             new Long(10), new Long(10)))
    .withTableName(table_name);

final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();

try {
    CreateTableResult result = ddb.createTable(request);
    System.out.println(result.getTableDescription().getTableName());
} catch (AmazonServiceException e) {
    System.err.println(e.getErrorMessage());
    System.exit(1);
}
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/dynamodb/src/main/java/aws/example/dynamodb/CreateTable.java) on GitHub\.

### Create a Table with a Composite Primary Key<a name="dynamodb-create-table-composite"></a>

Add another [AttributeDefinition](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/dynamodbv2/model/AttributeDefinition.html) and [KeySchemaElement](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/dynamodbv2/model/KeySchemaElement.html) to [CreateTableRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/dynamodbv2/model/CreateTableRequest.html)\.

 **Code** 

```
CreateTableRequest request = new CreateTableRequest()
    .withAttributeDefinitions(
          new AttributeDefinition("Language", ScalarAttributeType.S),
          new AttributeDefinition("Greeting", ScalarAttributeType.S))
    .withKeySchema(
          new KeySchemaElement("Language", KeyType.HASH),
          new KeySchemaElement("Greeting", KeyType.RANGE))
    .withProvisionedThroughput(
          new ProvisionedThroughput(new Long(10), new Long(10)))
    .withTableName(table_name);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/dynamodb/src/main/java/aws/example/dynamodb/CreateTableCompositeKey.java) on GitHub\.

## List Tables<a name="dynamodb-list-tables"></a>

You can list the tables in a particular region by calling the [DynamoDB client](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/dynamodbv2/AmazonDynamoDB.html)'s `listTables` method\.

**Note**  
If the named table doesn’t exist for your account and region, a [ResourceNotFoundException](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/dynamodbv2/model/ResourceNotFoundException.html) is thrown\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.ListTablesRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
```

 **Code** 

```
final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();

ListTablesRequest request;

boolean more_tables = true;
String last_name = null;

while(more_tables) {
    try {
        if (last_name == null) {
        	request = new ListTablesRequest().withLimit(10);
        }
        else {
        	request = new ListTablesRequest()
        			.withLimit(10)
        			.withExclusiveStartTableName(last_name);
        }

        ListTablesResult table_list = ddb.listTables(request);
        List<String> table_names = table_list.getTableNames();

        if (table_names.size() > 0) {
            for (String cur_name : table_names) {
                System.out.format("* %s\n", cur_name);
            }
        } else {
            System.out.println("No tables found!");
            System.exit(0);
        }

        last_name = table_list.getLastEvaluatedTableName();
        if (last_name == null) {
            more_tables = false;
        }
```

By default, up to 100 tables are returned per call—​use `getLastEvaluatedTableName` on the returned [ListTablesResult](https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/services/dynamodbv2/model/ListTablesResult.html) object to get the last table that was evaluated\. You can use this value to start the listing after the last returned value of the previous listing\.

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/dynamodb/src/main/java/aws/example/dynamodb/ListTables.java) on GitHub\.

## Describe \(Get Information about\) a Table<a name="dynamodb-describe-table"></a>

Call the [DynamoDB client](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/dynamodbv2/AmazonDynamoDB.html)'s `describeTable` method\.

**Note**  
If the named table doesn’t exist for your account and region, a [ResourceNotFoundException](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/dynamodbv2/model/ResourceNotFoundException.html) is thrown\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughputDescription;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
```

 **Code** 

```
final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();

try {
    TableDescription table_info =
       ddb.describeTable(table_name).getTable();

    if (table_info != null) {
        System.out.format("Table name  : %s\n",
              table_info.getTableName());
        System.out.format("Table ARN   : %s\n",
              table_info.getTableArn());
        System.out.format("Status      : %s\n",
              table_info.getTableStatus());
        System.out.format("Item count  : %d\n",
              table_info.getItemCount().longValue());
        System.out.format("Size (bytes): %d\n",
              table_info.getTableSizeBytes().longValue());

        ProvisionedThroughputDescription throughput_info =
           table_info.getProvisionedThroughput();
        System.out.println("Throughput");
        System.out.format("  Read Capacity : %d\n",
              throughput_info.getReadCapacityUnits().longValue());
        System.out.format("  Write Capacity: %d\n",
              throughput_info.getWriteCapacityUnits().longValue());

        List<AttributeDefinition> attributes =
           table_info.getAttributeDefinitions();
        System.out.println("Attributes");
        for (AttributeDefinition a : attributes) {
            System.out.format("  %s (%s)\n",
                  a.getAttributeName(), a.getAttributeType());
        }
    }
} catch (AmazonServiceException e) {
    System.err.println(e.getErrorMessage());
    System.exit(1);
}
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/dynamodb/src/main/java/aws/example/dynamodb/DescribeTable.java) on GitHub\.

## Modify \(Update\) a Table<a name="dynamodb-update-table"></a>

You can modify your table’s provisioned throughput values at any time by calling the [DynamoDB client](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/dynamodbv2/AmazonDynamoDB.html)'s `updateTable` method\.

**Note**  
If the named table doesn’t exist for your account and region, a [ResourceNotFoundException](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/dynamodbv2/model/ResourceNotFoundException.html) is thrown\.

 **Imports** 

```
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.AmazonServiceException;
```

 **Code** 

```
ProvisionedThroughput table_throughput = new ProvisionedThroughput(
      read_capacity, write_capacity);

final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();

try {
    ddb.updateTable(table_name, table_throughput);
} catch (AmazonServiceException e) {
    System.err.println(e.getErrorMessage());
    System.exit(1);
}
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/dynamodb/src/main/java/aws/example/dynamodb/UpdateTable.java) on GitHub\.

## Delete a Table<a name="dynamodb-delete-table"></a>

Call the [DynamoDB client](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/dynamodbv2/AmazonDynamoDB.html)'s `deleteTable` method and pass it the table’s name\.

**Note**  
If the named table doesn’t exist for your account and region, a [ResourceNotFoundException](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/dynamodbv2/model/ResourceNotFoundException.html) is thrown\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
```

 **Code** 

```
final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();

try {
    ddb.deleteTable(table_name);
} catch (AmazonServiceException e) {
    System.err.println(e.getErrorMessage());
    System.exit(1);
}
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/dynamodb/src/main/java/aws/example/dynamodb/DeleteTable.java) on GitHub\.

## More Info<a name="more-info"></a>
+  [Guidelines for Working with Tables](http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/GuidelinesForTables.html) in the Amazon DynamoDB Developer Guide
+  [Working with Tables in DynamoDB](http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/WorkingWithTables.html) in the Amazon DynamoDB Developer Guide