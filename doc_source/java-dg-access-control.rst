.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

#######################
Access Control Policies
#######################

.. meta::
   :description: How to specify access control policies using the AWS SDK for Java, with examples
                 for Amazon S3, Amazon SQS, and Amazon SNS.

AWS *access control policies* enable you to specify fine-grained access controls on your AWS
resources. An access control policy consists of a collection of *statements*, which take the form:

    *Account A* has permission to perform *action B* on *resource C* where *condition D* applies.

Where:

* *A* is the *principal* |ndash| The AWS account that is making a request to access or modify one
  of your AWS resources.

* *B* is the *action* |ndash| The way in which your AWS resource is being accessed or modified,
  such as sending a message to an |SQS| queue, or storing an object in an |S3| bucket.

* *C* is the *resource* |ndash| The AWS entity that the principal wants to access, such as an
  |SQS| queue, or an object stored in |S3|.

* *D* is a *set of conditions* |ndash| The optional constraints that specify when to allow or deny
  access for the principal to access your resource. Many expressive conditions are available, some
  specific to each service. For example, you can use date conditions to allow access to your
  resources only after or before a specific time.


|S3| Example
============

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


|SQS| Example
=============

One common use of policies is to authorize an |SQS| queue to receive messages from an |SNS| topic.

.. code-block:: java

    Policy policy = new Policy().withStatements(
        new Statement(Effect.Allow)
            .withPrincipals(Principal.AllUsers)
            .withActions(SQSActions.SendMessage)
            .withConditions(ConditionFactory.newSourceArnCondition(myTopicArn)));

    Map queueAttributes = new HashMap();
    queueAttributes.put(QueueAttributeName.Policy.toString(), policy.toJson());

    AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
    sqs.setQueueAttributes(new SetQueueAttributesRequest(myQueueUrl, queueAttributes));


|SNS| Example
=============

Some services offer additional conditions that can be used in policies. |SNS| provides conditions
for allowing or denying subscriptions to SNS topics based on the protocol (e.g., email, HTTP, HTTPS,
|SQS|) and endpoint (e.g., email address, URL, |SQS| ARN) of the request to subscribe to a topic.

.. code-block:: java

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

