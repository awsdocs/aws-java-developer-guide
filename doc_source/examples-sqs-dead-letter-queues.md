--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Using Dead Letter Queues in Amazon SQS<a name="examples-sqs-dead-letter-queues"></a>

 Amazon SQS provides support for *dead letter queues*\. A dead letter queue is a queue that other \(source\) queues can target for messages that can’t be processed successfully\. You can set aside and isolate these messages in the dead letter queue to determine why their processing did not succeed\.

## Creating a Dead Letter Queue<a name="sqs-dead-letter-queue-create-dl-queue"></a>

A dead letter queue is created the same way as a regular queue, but it has the following restrictions:
+ A dead letter queue must be the same type of queue \(FIFO or standard\) as the source queue\.
+ A dead letter queue must be created using the same AWS account and region as the source queue\.

Here we create two identical Amazon SQS queues, one of which will serve as the dead letter queue:

 **Imports** 

```
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
```

 **Code** 

```
final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

// Create source queue
try {
    sqs.createQueue(src_queue_name);
} catch (AmazonSQSException e) {
    if (!e.getErrorCode().equals("QueueAlreadyExists")) {
        throw e;
    }
}

// Create dead-letter queue
try {
    sqs.createQueue(dl_queue_name);
} catch (AmazonSQSException e) {
    if (!e.getErrorCode().equals("QueueAlreadyExists")) {
        throw e;
    }
}
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/sqs/src/main/java/aws/example/sqs/DeadLetterQueues.java) on GitHub\.

## Designating a Dead Letter Queue for a Source Queue<a name="sqs-dead-letter-queue-set-redrive-policy"></a>

To designate a dead letter queue, you must first create a *redrive policy*, and then set the policy in the queue’s attributes\. A redrive policy is specified in JSON, and specifies the ARN of the dead letter queue and the maximum number of times the message can be received and not processed before it’s sent to the dead letter queue\.

To set the redrive policy for your source queue, call the AmazonSQS class' `setQueueAttributes` method with a [SetQueueAttributesRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/sqs/model/SetQueueAttributesRequest.html) object for which you’ve set the `RedrivePolicy` attribute with your JSON redrive policy\.

 **Imports** 

```
import com.amazonaws.services.sqs.model.GetQueueAttributesRequest;
import com.amazonaws.services.sqs.model.GetQueueAttributesResult;
import com.amazonaws.services.sqs.model.SetQueueAttributesRequest;
```

 **Code** 

```
String dl_queue_url = sqs.getQueueUrl(dl_queue_name)
                         .getQueueUrl();

GetQueueAttributesResult queue_attrs = sqs.getQueueAttributes(
        new GetQueueAttributesRequest(dl_queue_url)
            .withAttributeNames("QueueArn"));

String dl_queue_arn = queue_attrs.getAttributes().get("QueueArn");

// Set dead letter queue with redrive policy on source queue.
String src_queue_url = sqs.getQueueUrl(src_queue_name)
                          .getQueueUrl();

SetQueueAttributesRequest request = new SetQueueAttributesRequest()
        .withQueueUrl(src_queue_url)
        .addAttributesEntry("RedrivePolicy",
                "{\"maxReceiveCount\":\"5\", \"deadLetterTargetArn\":\""
                + dl_queue_arn + "\"}");

sqs.setQueueAttributes(request);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/sqs/src/main/java/aws/example/sqs/DeadLetterQueues.java) on GitHub\.

## More Info<a name="more-info"></a>
+  [Using Amazon SQS Dead Letter Queues](https://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/sqs-dead-letter-queues.html) in the Amazon SQS Developer Guide
+  [SetQueueAttributes](http://docs.aws.amazon.com/AWSSimpleQueueService/latest/APIReference/API_SetQueueAttributes.html) in the Amazon SQS API Reference