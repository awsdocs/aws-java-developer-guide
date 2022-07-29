--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Access Control Policies<a name="java-dg-access-control"></a>

 AWS *access control policies* enable you to specify fine\-grained access controls on your AWS resources\. An access control policy consists of a collection of *statements*, which take the form:

 *Account A* has permission to perform *action B* on *resource C* where *condition D* applies\.

Where:
+  *A* is the *principal*\- The AWS account that is making a request to access or modify one of your AWS resources\.
+  *B* is the *action*\- The way in which your AWS resource is being accessed or modified, such as sending a message to an Amazon SQS queue, or storing an object in an Amazon S3 bucket\.
+  *C* is the *resource*\- The AWS entity that the principal wants to access, such as an Amazon SQS queue, or an object stored in Amazon S3\.
+  *D* is a *set of conditions*\- The optional constraints that specify when to allow or deny access for the principal to access your resource\. Many expressive conditions are available, some specific to each service\. For example, you can use date conditions to allow access to your resources only after or before a specific time\.

## Amazon S3 Example<a name="s3-example"></a>

The following example demonstrates a policy that allows anyone access to read all the objects in a bucket, but restricts access to uploading objects to that bucket to two specific AWS accounts \(in addition to the bucket owner’s account\)\.

```
Statement allowPublicReadStatement = new Statement(Effect.Allow)
    .withPrincipals(Principal.AllUsers)
    .withActions(S3Actions.GetObject)
    .withResources(new S3ObjectResource(myBucketName, "*"));
Statement allowRestrictedWriteStatement = new Statement(Effect.Allow)
    .withPrincipals(new Principal("123456789"), new Principal("876543210"))
    .withActions(S3Actions.PutObject)
    .withResources(new S3ObjectResource(myBucketName, "*"));

Policy policy = new Policy()
    .withStatements(allowPublicReadStatement, allowRestrictedWriteStatement);

AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
s3.setBucketPolicy(myBucketName, policy.toJson());
```

## Amazon SQS Example<a name="sqs-example"></a>

One common use of policies is to authorize an Amazon SQS queue to receive messages from an Amazon SNS topic\.

```
Policy policy = new Policy().withStatements(
    new Statement(Effect.Allow)
        .withPrincipals(Principal.AllUsers)
        .withActions(SQSActions.SendMessage)
        .withConditions(ConditionFactory.newSourceArnCondition(myTopicArn)));

Map queueAttributes = new HashMap();
queueAttributes.put(QueueAttributeName.Policy.toString(), policy.toJson());

AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
sqs.setQueueAttributes(new SetQueueAttributesRequest(myQueueUrl, queueAttributes));
```

## Amazon SNS Example<a name="sns-example"></a>

Some services offer additional conditions that can be used in policies\. Amazon SNS provides conditions for allowing or denying subscriptions to SNS topics based on the protocol \(e\.g\., email, HTTP, HTTPS, Amazon SQS\) and endpoint \(e\.g\., email address, URL, Amazon SQS ARN\) of the request to subscribe to a topic\.

```
Condition endpointCondition =
    SNSConditionFactory.newEndpointCondition("*@mycompany.com");

Policy policy = new Policy().withStatements(
    new Statement(Effect.Allow)
        .withPrincipals(Principal.AllUsers)
        .withActions(SNSActions.Subscribe)
        .withConditions(endpointCondition));

AmazonSNS sns = AmazonSNSClientBuilder.defaultClient();
sns.setTopicAttributes(
    new SetTopicAttributesRequest(myTopicArn, "Policy", policy.toJson()));
```