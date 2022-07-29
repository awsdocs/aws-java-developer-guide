--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Creating Service Clients<a name="creating-clients"></a>

To make requests to Amazon Web Services, you first create a service client object\. The recommended way is to use the service client builder\.

Each AWS service has a service interface with methods for each action in the service API\. For example, the service interface for Amazon DynamoDB is named link:sdk\-for\-java/v1/reference/com/amazonaws/services/dynamodbv2/AmazonDynamoDB \.html\["AmazonDynamoDB", type="documentation"\]\. Each service interface has a corresponding client builder you can use to construct an implementation of the service interface\. The client builder class for DynamoDB is named [AmazonDynamoDBClientBuilder](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/dynamodbv2/AmazonDynamoDBClientBuilder.html)\.

## Obtaining a Client Builder<a name="obtaining-a-client-builder"></a>

To obtain an instance of the client builder, use the static factory method `standard`, as shown in the following example\.

```
AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard();
```

Once you have a builder, you can customize the client’s properties by using many fluent setters in the builder API\. For example, you can set a custom region and a custom credentials provider, as follows\.

```
AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard()
                        .withRegion(Regions.US_WEST_2)
                        .withCredentials(new ProfileCredentialsProvider("myProfile"))
                        .build();
```

**Note**  
The fluent `withXXX` methods return the `builder` object so that you can chain the method calls for convenience and for more readable code\. After you configure the properties you want, you can call the `build` method to create the client\. Once a client is created, it’s immutable and any calls to `setRegion` or `setEndpoint` will fail\.

A builder can create multiple clients with the same configuration\. When you’re writing your application, be aware that the builder is mutable and not thread\-safe\.

The following code uses the builder as a factory for client instances\.

```
public class DynamoDBClientFactory {
    private final AmazonDynamoDBClientBuilder builder =
        AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.US_WEST_2)
            .withCredentials(new ProfileCredentialsProvider("myProfile"));

    public AmazonDynamoDB createClient() {
        return builder.build();
    }
}
```

The builder also exposes fluent setters for [ClientConfiguration](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/ClientConfiguration.html) and [RequestMetricCollector](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/metrics/RequestMetricCollector.html), and a custom list of [RequestHandler2](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/handlers/RequestHandler2.html)\.

The following is a complete example that overrides all configurable properties\.

```
AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard()
        .withRegion(Regions.US_WEST_2)
        .withCredentials(new ProfileCredentialsProvider("myProfile"))
        .withClientConfiguration(new ClientConfiguration().withRequestTimeout(5000))
        .withMetricsCollector(new MyCustomMetricsCollector())
        .withRequestHandlers(new MyCustomRequestHandler(), new MyOtherCustomRequestHandler)
        .build();
```

## Creating Async Clients<a name="creating-async-clients"></a>

The AWS SDK for Java has asynchronous \(or async\) clients for every service \(except for Amazon S3\), and a corresponding async client builder for every service\.

### To create an async DynamoDB client with the default ExecutorService<a name="w3aab9c13b9b5"></a>

```
AmazonDynamoDBAsync ddbAsync = AmazonDynamoDBAsyncClientBuilder.standard()
        .withRegion(Regions.US_WEST_2)
        .withCredentials(new ProfileCredentialsProvider("myProfile"))
        .build();
```

In addition to the configuration options that the synchronous \(or sync\) client builder supports, the async client enables you to set a custom [ExecutorFactory](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/client/builder/ExecutorFactory.html) to change the `ExecutorService` that the async client uses\. `ExecutorFactory` is a functional interface, so it interoperates with Java 8 lambda expressions and method references\.

### To create an async client with a custom executor<a name="w3aab9c13b9b9"></a>

```
AmazonDynamoDBAsync ddbAsync = AmazonDynamoDBAsyncClientBuilder.standard()
            .withExecutorFactory(() -> Executors.newFixedThreadPool(10))
            .build();
```

## Using DefaultClient<a name="using-defaultclient"></a>

Both the sync and async client builders have another factory method named `defaultClient`\. This method creates a service client with the default configuration, using the default provider chain to load credentials and the AWS Region\. If credentials or the region can’t be determined from the environment that the application is running in, the call to `defaultClient` fails\. See [Working with AWS Credentials](credentials.md) and [AWS Region Selection](java-dg-region-selection.md) for more information about how credentials and region are determined\.

### To create a default service client<a name="w3aab9c13c11b5"></a>

```
AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();
```

## Client Lifecycle<a name="client-lifecycle"></a>

Service clients in the SDK are thread\-safe and, for best performance, you should treat them as long\-lived objects\. Each client has its own connection pool resource\. Explicitly shut down clients when they are no longer needed to avoid resource leaks\.

To explicitly shut down a client, call the `shutdown` method\. After calling `shutdown`, all client resources are released and the client is unusable\.

### To shut down a client<a name="w3aab9c13c13b7"></a>

```
AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();
ddb.shutdown();
// Client is now unusable
```