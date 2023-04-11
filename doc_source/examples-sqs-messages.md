# Sending, Receiving, and Deleting Amazon SQS Messages<a name="examples-sqs-messages"></a>

This topic describes how to send, receive and delete Amazon SQS messages\. Messages are always delivered using an [SQS Queue](examples-sqs-message-queues.md)\.

## Send a Message<a name="sqs-message-send"></a>

Add a single message to an Amazon SQS queue by calling the AmazonSQS client’s `sendMessage` method\. Provide a [SendMessageRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/sqs/model/SendMessageRequest.html) object that contains the queue’s [URL](examples-sqs-message-queues.md#sqs-get-queue-url), message body, and optional delay value \(in seconds\)\.

 **Imports** 

```
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
```

 **Code** 

```
SendMessageRequest send_msg_request = new SendMessageRequest()
        .withQueueUrl(queueUrl)
        .withMessageBody("hello world")
        .withDelaySeconds(5);
sqs.sendMessage(send_msg_request);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/sqs/src/main/java/aws/example/sqs/SendReceiveMessages.java) on GitHub\.

### Send Multiple Messages at Once<a name="sqs-messages-send-multiple"></a>

You can send more than one message in a single request\. To send multiple messages, use the AmazonSQS client’s `sendMessageBatch` method, which takes a [SendMessageBatchRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/sqs/model/SendMessageBatchRequest.html) containing the queue URL and a list of messages \(each one a [SendMessageBatchRequestEntry](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/sqs/model/SendMessageBatchRequestEntry.html)\) to send\. You can also set an optional delay value per message\.

 **Imports** 

```
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
```

 **Code** 

```
SendMessageBatchRequest send_batch_request = new SendMessageBatchRequest()
        .withQueueUrl(queueUrl)
        .withEntries(
                new SendMessageBatchRequestEntry(
                        "msg_1", "Hello from message 1"),
                new SendMessageBatchRequestEntry(
                        "msg_2", "Hello from message 2")
                        .withDelaySeconds(10));
sqs.sendMessageBatch(send_batch_request);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/sqs/src/main/java/aws/example/sqs/SendReceiveMessages.java) on GitHub\.

## Receive Messages<a name="sqs-messages-receive"></a>

Retrieve any messages that are currently in the queue by calling the AmazonSQS client’s `receiveMessage` method, passing it the queue’s URL\. Messages are returned as a list of [Message](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/sqs/model/Message.html) objects\.

 **Imports** 

```
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
```

 **Code** 

```
List<Message> messages = sqs.receiveMessage(queueUrl).getMessages();
```

## Delete Messages after Receipt<a name="sqs-messages-delete"></a>

After receiving a message and processing its contents, delete the message from the queue by sending the message’s receipt handle and queue URL to the AmazonSQS client’s `deleteMessage` method\.

 **Code** 

```
for (Message m : messages) {
    sqs.deleteMessage(queueUrl, m.getReceiptHandle());
}
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/sqs/src/main/java/aws/example/sqs/SendReceiveMessages.java) on GitHub\.

## More Info<a name="more-info"></a>
+  [How Amazon SQS Queues Work](https://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/sqs-how-it-works.html) in the Amazon SQS Developer Guide
+  [SendMessage](http://docs.aws.amazon.com/AWSSimpleQueueService/latest/APIReference/API_SendMessage.html) in the Amazon SQS API Reference
+  [SendMessageBatch](http://docs.aws.amazon.com/AWSSimpleQueueService/latest/APIReference/API_SendMessageBatch.html) in the Amazon SQS API Reference
+  [ReceiveMessage](http://docs.aws.amazon.com/AWSSimpleQueueService/latest/APIReference/API_ReceiveMessage.html) in the Amazon SQS API Reference
+  [DeleteMessage](http://docs.aws.amazon.com/AWSSimpleQueueService/latest/APIReference/API_DeleteMessage.html) in the Amazon SQS API Reference