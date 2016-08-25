.. Copyright 2010-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

#######################
Access Control Policies
#######################

AWS access control policies allow you to specify fine-grained access controls on your AWS resources.
You can allow or deny access to your AWS resources based on:

*   what :emphasis:`resource` is being accessed.

*   who is accessing the resource (i.e., the principal).

*   what action is being taken on the resource.

*   a variety of other conditions including date restrictions, IP address restrictions, etc.

Access control policies are a collection of statements. Each statement takes the form: "A has
permission to do B to C where D applies".

:emphasis:`A is the principal`
    The AWS account that is making a request to access or modify one of your AWS resources.

:emphasis:`B is the action`
    The way in which your AWS resource is being accessed or modified, such as sending a message to
    an Amazon SQS queue, or storing an object in an Amazon S3 bucket.

:emphasis:`C is the resource`
    Your AWS entity that the principal wants to access, such as an Amazon SQS queue, or an object
    stored in Amazon S3.

:emphasis:`D is the set of conditions`
    The optional constraints that specify when to allow or deny access for the principal to access
    your resource. Many expressive conditions are available, some specific to each service. For
    example, you can use date conditions to allow access to your resources only after or before a
    specific time.


Amazon S3 Example
=================

The following example demonstrates a policy that allows anyone access to read all the objects in a
bucket, but restricts access to uploading objects to that bucket to two specific AWS accounts (in
addition to the bucket owner's account).

.. code-block:: java

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


Amazon SQS Example
==================

One common use of policies is to authorize an Amazon SQS queue to receive messages from an Amazon
SNS topic.

.. code-block:: java

    /*
     * This policy allows an SNS topic to send messages to an SQS queue.
     * You can find your SNS topic's ARN through the SNS getTopicAttributes operation.
     */
    Policy policy = new Policy().withStatements(
        new Statement(Effect.Allow)
            .withPrincipals(Principal.AllUsers)
            .withActions(SQSActions.SendMessage)
            .withConditions(ConditionFactory.newSourceArnCondition(myTopicArn)));

    Map queueAttributes = new HashMap();
    queueAttributes.put(QueueAttributeName.Policy.toString(), policy.toJson());

    AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
    sqs.setQueueAttributes(new SetQueueAttributesRequest(myQueueUrl, queueAttributes));


Amazon SNS Example
==================

Some services offer additional conditions that can be used in policies. Amazon SNS provides
conditions for allowing or denying subscriptions to SNS topics based on the protocol (e.g., email,
HTTP, HTTPS, SQS) and endpoint (e.g., email address, URL, SQS ARN) of the request to subscribe to a
topic.

.. code-block:: java

    /*
     * This SNS condition allows you to restrict subscriptions to an Amazon SNS topic
     * based on the requested endpoint (email address, SQS queue ARN, etc.) used when
     * someone tries to subscribe to your SNS topic.
     */
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

