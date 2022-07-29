--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Working with Amazon SQS Message Queues<a name="examples-sqs-message-queues"></a>

A *message queue* is the logical container used for sending messages reliably in Amazon SQS\. There are two types of queues: *standard* and *first\-in, first\-out* \(FIFO\)\. To learn more about queues and the differences between these types, see the [Amazon SQS Developer Guide](https://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/)\.

This topic describes how to create, list, delete, and get the URL of an Amazon SQS queue by using the AWS SDK for Java\.

## Create a Queue<a name="sqs-create-queue"></a>

Use the AmazonSQS client’s `createQueue` method, providing a [CreateQueueRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/sqs/model/CreateQueueRequest.html) object that describes the queue parameters\.

 **Imports** 

```
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
```

 **Code** 

```
AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
CreateQueueRequest create_request = new CreateQueueRequest(QUEUE_NAME)
        .addAttributesEntry("DelaySeconds", "60")
        .addAttributesEntry("MessageRetentionPeriod", "86400");

try {
    sqs.createQueue(create_request);
} catch (AmazonSQSException e) {
    if (!e.getErrorCode().equals("QueueAlreadyExists")) {
        throw e;
    }
}
```

You can use the simplified form of `createQueue`, which needs only a queue name, to create a standard queue\.

```
sqs.createQueue("MyQueue" + new Date().getTime());
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/sqs/src/main/java/aws/example/sqs/UsingQueues.java) on GitHub\.

## Listing Queues<a name="sqs-list-queues"></a>

To list the Amazon SQS queues for your account, call the AmazonSQS client’s `listQueues` method\.

 **Imports** 

```
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.ListQueuesResult;
```

 **Code** 

```
AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
ListQueuesResult lq_result = sqs.listQueues();
System.out.println("Your SQS Queue URLs:");
for (String url : lq_result.getQueueUrls()) {
    System.out.println(url);
}
```

Using the `listQueues` overload without any parameters returns *all queues*\. You can filter the returned results by passing it a `ListQueuesRequest` object\.

 **Imports** 

```
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.ListQueuesRequest;
```

 **Code** 

```
AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
String name_prefix = "Queue";
lq_result = sqs.listQueues(new ListQueuesRequest(name_prefix));
System.out.println("Queue URLs with prefix: " + name_prefix);
for (String url : lq_result.getQueueUrls()) {
    System.out.println(url);
}
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/sqs/src/main/java/aws/example/sqs/UsingQueues.java) on GitHub\.

## Get the URL for a Queue<a name="sqs-get-queue-url"></a>

Call the AmazonSQS client’s `getQueueUrl` method\.

 **Imports** 

```
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
```

 **Code** 

```
AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
String queue_url = sqs.getQueueUrl(QUEUE_NAME).getQueueUrl();
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/sqs/src/main/java/aws/example/sqs/UsingQueues.java) on GitHub\.

## Delete a Queue<a name="sqs-delete-queue"></a>

Provide the queue’s [URL](#sqs-get-queue-url) to the AmazonSQS client’s `deleteQueue` method\.

 **Imports** 

```
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
```

 **Code** 

```
AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
sqs.deleteQueue(queue_url);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/sqs/src/main/java/aws/example/sqs/UsingQueues.java) on GitHub\.

## More Info<a name="more-info"></a>
+  [How Amazon SQS Queues Work](https://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/sqs-how-it-works.html) in the Amazon SQS Developer Guide
+  [CreateQueue](http://docs.aws.amazon.com/AWSSimpleQueueService/latest/APIReference/API_CreateQueue.html) in the Amazon SQS API Reference
+  [GetQueueUrl](http://docs.aws.amazon.com/AWSSimpleQueueService/latest/APIReference/API_GetQueueUrl.html) in the Amazon SQS API Reference
+  [ListQueues](http://docs.aws.amazon.com/AWSSimpleQueueService/latest/APIReference/API_ListQueues.html) in the Amazon SQS API Reference
+  [DeleteQueues](http://docs.aws.amazon.com/AWSSimpleQueueService/latest/APIReference/API_DeleteQueues.html) in the Amazon SQS API Reference