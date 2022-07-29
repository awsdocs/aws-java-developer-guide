--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Setting Visibility Timeout in Amazon SQS<a name="examples-sqs-visibility-timeout"></a>

When a message is received in Amazon SQS, it remains on the queue until it’s deleted in order to ensure receipt\. A message that was received, but not deleted, will be available in subsequent requests after a given *visibility timeout* to help prevent the message from being received more than once before it can be processed and deleted\.

**Note**  
When using [standard queues](https://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/standard-queues.html), visibility timeout isn’t a guarantee against receiving a message twice\. If you are using a standard queue, be sure that your code can handle the case where the same message has been delivered more than once\.

## Setting the Message Visibility Timeout for a Single Message<a name="sqs-visibility-timeout-receipt"></a>

When you have received a message, you can modify its visibility timeout by passing its receipt handle in a [ChangeMessageVisibilityRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/sqs/model/ChangeMessageVisibilityRequest.html) that you pass to the AmazonSQS class' `changeMessageVisibility` method\.

 **Imports** 

```
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
```

 **Code** 

```
AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

// Get the receipt handle for the first message in the queue.
String receipt = sqs.receiveMessage(queue_url)
                    .getMessages()
                    .get(0)
                    .getReceiptHandle();

sqs.changeMessageVisibility(queue_url, receipt, timeout);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/sqs/src/main/java/aws/example/sqs/VisibilityTimeout.java) on GitHub\.

## Setting the Message Visibility Timeout for Multiple Messages at Once<a name="setting-the-message-visibility-timeout-for-multiple-messages-at-once"></a>

To set the message visibility timeout for multiple messages at once, create a list of [ChangeMessageVisibilityBatchRequestEntry](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/sqs/model/ChangeMessageVisibilityBatchRequestEntry.html) objects, each containing a unique ID string and a receipt handle\. Then, pass the list to the Amazon SQS client class' `changeMessageVisibilityBatch` method\.

 **Imports** 

```
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.ChangeMessageVisibilityBatchRequestEntry;
import java.util.ArrayList;
import java.util.List;
```

 **Code** 

```
AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

List<ChangeMessageVisibilityBatchRequestEntry> entries =
    new ArrayList<ChangeMessageVisibilityBatchRequestEntry>();

entries.add(new ChangeMessageVisibilityBatchRequestEntry(
            "unique_id_msg1",
            sqs.receiveMessage(queue_url)
               .getMessages()
               .get(0)
               .getReceiptHandle())
        .withVisibilityTimeout(timeout));

entries.add(new ChangeMessageVisibilityBatchRequestEntry(
            "unique_id_msg2",
            sqs.receiveMessage(queue_url)
               .getMessages()
               .get(0)
               .getReceiptHandle())
        .withVisibilityTimeout(timeout + 200));

sqs.changeMessageVisibilityBatch(queue_url, entries);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/sqs/src/main/java/aws/example/sqs/VisibilityTimeout.java) on GitHub\.

## More Info<a name="more-info"></a>
+  [Visibility Timeout](https://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/sqs-visibility-timeout.html) in the Amazon SQS Developer Guide
+  [SetQueueAttributes](http://docs.aws.amazon.com/AWSSimpleQueueService/latest/APIReference/API_SetQueueAttributes.html) in the Amazon SQS API Reference
+  [GetQueueAttributes](http://docs.aws.amazon.com/AWSSimpleQueueService/latest/APIReference/API_GetQueueAttributes.html) in the Amazon SQS API Reference
+  [ReceiveMessage](http://docs.aws.amazon.com/AWSSimpleQueueService/latest/APIReference/API_ReceiveMessage.html) in the Amazon SQS API Reference
+  [ChangeMessageVisibility](http://docs.aws.amazon.com/AWSSimpleQueueService/latest/APIReference/API_ChangeMessageVisibility.html) in the Amazon SQS API Reference
+  [ChangeMessageVisibilityBatch](http://docs.aws.amazon.com/AWSSimpleQueueService/latest/APIReference/API_ChangeMessageVisibilityBatch.html) in the Amazon SQS API Reference