# Working with Items in DynamoDB<a name="examples-dynamodb-items"></a>

In DynamoDB, an item is a collection of *attributes*, each of which has a *name* and a *value*\. An attribute value can be a scalar, set, or document type\. For more information, see [Naming Rules and Data Types](http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/HowItWorks.NamingRulesDataTypes.html) in the Amazon DynamoDB Developer Guide\.

## Retrieve \(Get\) an Item from a Table<a name="dynamodb-get-item"></a>

Call the AmazonDynamoDB’s `getItem` method and pass it a [GetItemRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/dynamodbv2/model/GetItemRequest.html) object with the table name and primary key value of the item you want\. It returns a [GetItemResult](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/dynamodbv2/model/GetItemResult.html) object\.

You can use the returned `GetItemResult` object’s `getItem()` method to retrieve a [Map](https://docs.oracle.com/javase/8/docs/api/index.html?java/util/Map.html) of key \(String\) and value \([AttributeValue](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/dynamodbv2/model/AttributeValue.html)\) pairs that are associated with the item\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import java.util.HashMap;
import java.util.Map;
```

 **Code** 

```
HashMap<String,AttributeValue> key_to_get =
    new HashMap<String,AttributeValue>();

key_to_get.put("DATABASE_NAME", new AttributeValue(name));

GetItemRequest request = null;
if (projection_expression != null) {
    request = new GetItemRequest()
        .withKey(key_to_get)
        .withTableName(table_name)
        .withProjectionExpression(projection_expression);
} else {
    request = new GetItemRequest()
        .withKey(key_to_get)
        .withTableName(table_name);
}

final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();

try {
    Map<String,AttributeValue> returned_item =
       ddb.getItem(request).getItem();
    if (returned_item != null) {
        Set<String> keys = returned_item.keySet();
        for (String key : keys) {
            System.out.format("%s: %s\n",
                    key, returned_item.get(key).toString());
        }
    } else {
        System.out.format("No item found with the key %s!\n", name);
    }
} catch (AmazonServiceException e) {
    System.err.println(e.getErrorMessage());
    System.exit(1);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/dynamodb/src/main/java/aws/example/dynamodb/GetItem.java) on GitHub\.

## Add a New Item to a Table<a name="dynamodb-add-item"></a>

Create a [Map](https://docs.oracle.com/javase/8/docs/api/index.html?java/util/Map.html) of key\-value pairs that represent the item’s attributes\. These must include values for the table’s primary key fields\. If the item identified by the primary key already exists, its fields are *updated* by the request\.

**Note**  
If the named table doesn’t exist for your account and region, a [ResourceNotFoundException](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/dynamodbv2/model/ResourceNotFoundException.html) is thrown\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import java.util.ArrayList;
```

 **Code** 

```
HashMap<String,AttributeValue> item_values =
    new HashMap<String,AttributeValue>();

item_values.put("Name", new AttributeValue(name));

for (String[] field : extra_fields) {
    item_values.put(field[0], new AttributeValue(field[1]));
}

final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();

try {
    ddb.putItem(table_name, item_values);
} catch (ResourceNotFoundException e) {
    System.err.format("Error: The table \"%s\" can't be found.\n", table_name);
    System.err.println("Be sure that it exists and that you've typed its name correctly!");
    System.exit(1);
} catch (AmazonServiceException e) {
    System.err.println(e.getMessage());
    System.exit(1);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/dynamodb/src/main/java/aws/example/dynamodb/PutItem.java) on GitHub\.

## Update an Existing Item in a Table<a name="dynamodb-update-item"></a>

You can update an attribute for an item that already exists in a table by using the AmazonDynamoDB’s `updateItem` method, providing a table name, primary key value, and a map of fields to update\.

**Note**  
If the named table doesn’t exist for your account and region, or if the item identified by the primary key you passed in doesn’t exist, a [ResourceNotFoundException](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/dynamodbv2/model/ResourceNotFoundException.html) is thrown\.

 **Imports** 

```
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeAction;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import java.util.ArrayList;
```

 **Code** 

```
HashMap<String,AttributeValue> item_key =
   new HashMap<String,AttributeValue>();

item_key.put("Name", new AttributeValue(name));

HashMap<String,AttributeValueUpdate> updated_values =
    new HashMap<String,AttributeValueUpdate>();

for (String[] field : extra_fields) {
    updated_values.put(field[0], new AttributeValueUpdate(
                new AttributeValue(field[1]), AttributeAction.PUT));
}

final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();

try {
    ddb.updateItem(table_name, item_key, updated_values);
} catch (ResourceNotFoundException e) {
    System.err.println(e.getMessage());
    System.exit(1);
} catch (AmazonServiceException e) {
    System.err.println(e.getMessage());
    System.exit(1);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/dynamodb/src/main/java/aws/example/dynamodb/UpdateItem.java) on GitHub\.

## Use the DynamoDBMapper class<a name="use-the-dynamodbmapper-class"></a>

The [AWS SDK for Java](http://aws.amazon.com/sdk-for-java/) provides a [DynamoDBMapper](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/dynamodbv2/datamodeling/DynamoDBMapper.html) class, allowing you to map your client\-side classes to Amazon DynamoDB tables\. To use the [DynamoDBMapper](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/dynamodbv2/datamodeling/DynamoDBMapper.html) class, you define the relationship between items in a DynamoDB table and their corresponding object instances in your code by using annotations \(as shown in the following code example\)\. The [DynamoDBMapper](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/dynamodbv2/datamodeling/DynamoDBMapper.html) class enables you to access your tables; perform various create, read, update, and delete \(CRUD\) operations; and execute queries\.

**Note**  
The [DynamoDBMapper](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/dynamodbv2/datamodeling/DynamoDBMapper.html) class does not allow you to create, update, or delete tables\.

 **Imports** 

```
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
```

 **Code** 

The following Java code example shows you how to add content to the *Music* table by using the [DynamoDBMapper](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/dynamodbv2/datamodeling/DynamoDBMapper.html) class\. After the content is added to the table, notice that an item is loaded by using the *Partition* and *Sort* keys\. Then the *Awards* item is updated\. For information on creating the *Music* table, see [Create a Table](http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/getting-started-step-1.html) in the Amazon DynamoDB Developer Guide\.

```
       AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
       MusicItems items = new MusicItems();

       try{
           // Add new content to the Music table
           items.setArtist(artist);
           items.setSongTitle(songTitle);
           items.setAlbumTitle(albumTitle);
           items.setAwards(Integer.parseInt(awards)); //convert to an int

           // Save the item
           DynamoDBMapper mapper = new DynamoDBMapper(client);
           mapper.save(items);

           // Load an item based on the Partition Key and Sort Key
           // Both values need to be passed to the mapper.load method
           String artistName = artist;
           String songQueryTitle = songTitle;

           // Retrieve the item
           MusicItems itemRetrieved = mapper.load(MusicItems.class, artistName, songQueryTitle);
           System.out.println("Item retrieved:");
           System.out.println(itemRetrieved);

           // Modify the Award value
           itemRetrieved.setAwards(2);
           mapper.save(itemRetrieved);
           System.out.println("Item updated:");
           System.out.println(itemRetrieved);

           System.out.print("Done");
       } catch (AmazonDynamoDBException e) {
           e.getStackTrace();
       }
   }

   @DynamoDBTable(tableName="Music")
   public static class MusicItems {

       //Set up Data Members that correspond to columns in the Music table
       private String artist;
       private String songTitle;
       private String albumTitle;
       private int awards;

       @DynamoDBHashKey(attributeName="Artist")
       public String getArtist() {
           return this.artist;
       }

       public void setArtist(String artist) {
           this.artist = artist;
       }

       @DynamoDBRangeKey(attributeName="SongTitle")
       public String getSongTitle() {
           return this.songTitle;
       }

       public void setSongTitle(String title) {
           this.songTitle = title;
       }

       @DynamoDBAttribute(attributeName="AlbumTitle")
       public String getAlbumTitle() {
           return this.albumTitle;
       }

       public void setAlbumTitle(String title) {
           this.albumTitle = title;
       }

       @DynamoDBAttribute(attributeName="Awards")
       public int getAwards() {
           return this.awards;
       }

       public void setAwards(int awards) {
           this.awards = awards;
       }
   }
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/dynamodb/src/main/java/aws/example/dynamodb/UseDynamoMapping.java) on GitHub\.

## More Info<a name="more-info"></a>
+  [Guidelines for Working with Items](http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/GuidelinesForItems.html) in the Amazon DynamoDB Developer Guide
+  [Working with Items in DynamoDB](http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/WorkingWithItems.html) in the Amazon DynamoDB Developer Guide