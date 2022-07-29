--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Enabling Long Polling for Amazon SQS Message Queues<a name="examples-sqs-long-polling"></a>

 Amazon SQS uses *short polling* by default, querying only a subset of the servers—​based on a weighted random distribution—​to determine whether any messages are available for inclusion in the response\.

Long polling helps reduce your cost of using Amazon SQS by reducing the number of empty responses when there are no messages available to return in reply to a ReceiveMessage request sent to an Amazon SQS queue and eliminating false empty responses\.

**Note**  
You can set a long polling frequency from *1\-20 seconds*\.

## Enabling Long Polling when Creating a Queue<a name="sqs-long-polling-create-queue"></a>

To enable long polling when creating an Amazon SQS queue, set the `ReceiveMessageWaitTimeSeconds` attribute on the [CreateQueueRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/sqs/model/CreateQueueRequest.html) object before calling the AmazonSQS class' `createQueue` method\.

 **Imports** 

```
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
```

 **Code** 

```
final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

// Enable long polling when creating a queue
CreateQueueRequest create_request = new CreateQueueRequest()
        .withQueueName(queue_name)
        .addAttributesEntry("ReceiveMessageWaitTimeSeconds", "20");

try {
    sqs.createQueue(create_request);
} catch (AmazonSQSException e) {
    if (!e.getErrorCode().equals("QueueAlreadyExists")) {
        throw e;
    }
}
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/sqs/src/main/java/aws/example/sqs/LongPolling.java) on GitHub\.

## Enabling Long Polling on an Existing Queue<a name="sqs-long-polling-existing-queue"></a>

In addition to enabling long polling when creating a queue, you can also enable it on an existing queue by setting `ReceiveMessageWaitTimeSeconds` on the [SetQueueAttributesRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/sqs/model/SetQueueAttributesRequest.html) before calling the AmazonSQS class' `setQueueAttributes` method\.

 **Imports** 

```
import com.amazonaws.services.sqs.model.SetQueueAttributesRequest;
```

 **Code** 

```
SetQueueAttributesRequest set_attrs_request = new SetQueueAttributesRequest()
        .withQueueUrl(queue_url)
        .addAttributesEntry("ReceiveMessageWaitTimeSeconds", "20");
sqs.setQueueAttributes(set_attrs_request);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/sqs/src/main/java/aws/example/sqs/LongPolling.java) on GitHub\.

## Enabling Long Polling on Message Receipt<a name="sqs-long-polling-receive-message"></a>

You can enable long polling when receiving a message by setting the wait time in seconds on the [ReceiveMessageRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/sqs/model/ReceiveMessageRequest.html) that you supply to the AmazonSQS class' `receiveMessage` method\.

**Note**  
You should make sure that the AWS client’s request timeout is larger than the maximum long poll time \(20s\) so that your `receiveMessage` requests don’t time out while waiting for the next poll event\!

 **Imports** 

```
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
```

 **Code** 

```
ReceiveMessageRequest receive_request = new ReceiveMessageRequest()
        .withQueueUrl(queue_url)
        .withWaitTimeSeconds(20);
sqs.receiveMessage(receive_request);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/sqs/src/main/java/aws/example/sqs/LongPolling.java) on GitHub\.

## More Info<a name="more-info"></a>
+  [Amazon SQS Long Polling](https://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/sqs-long-polling.html) in the Amazon SQS Developer Guide
+  [CreateQueue](http://docs.aws.amazon.com/AWSSimpleQueueService/latest/APIReference/API_CreateQueue.html) in the Amazon SQS API Reference
+  [ReceiveMessage](http://docs.aws.amazon.com/AWSSimpleQueueService/latest/APIReference/API_ReceiveMessage.html) in the Amazon SQS API Reference
+  [SetQueueAttributes](http://docs.aws.amazon.com/AWSSimpleQueueService/latest/APIReference/API_SetQueueAttributes.html) in the Amazon SQS API Reference