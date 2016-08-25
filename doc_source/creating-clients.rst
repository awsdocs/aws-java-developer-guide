.. Copyright 2010-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

########################
Creating Service Clients
########################

To make requests to Amazon Web Services, you first need to create a service client object. The
preferred way to do this is via the service client builder. Each AWS Service has a service interface
that has methods for each action in the service API. For example, the service interface for
Amazon DynamoDB is named :java-api:`AmazonDynamoDB <services/dynamodbv2/AmazonDynamoDB>`. For
each service interface there is also a corresponding client builder that can be used to construct
an implementation of the service interface. The client builder class for DynamoDB is named
:java-api:`AmazonDynamoDBClientBuilder <services/dynamodbv2/AmazonDynamoDBClientBuilder>` .

To obtain an instance of the client builder, use the static factory method `standard`.

For example,

.. code-block:: java

    AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard();


Once a builder has been obtained, you can customize properties of the client via many 'fluent' setters
in the builder API. For example, to set a custom region and a custom credentials provider:

.. code-block:: java

    AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard()
                            .withRegion(Regions.US_WEST_2)
                            .withCredentials(new ProfileCredentialsProvider("myProfile"))
                            .build();

Note that the 'fluent' withXXX methods return the builder object so that method calls can be
chained for convenience and more readable code. Once all desired properties have been
configured, you can call the `build` method to create the client. Once a client has
been created it is immutable and any calls to setRegion, or setEndpoint will fail.

A builder is capable of creating multiple clients with the same configuration. The builder is
mutable and not thread safe so keep that in mind when writing your application. The following
code uses the builder as a factory for client instances.

.. code-block:: java

    public class DynamoDBClientFactory {
        private final AmazonDynamoDBClientBuilder builder =
            AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.US_WEST_2)
                .withCredentials(new ProfileCredentialsProvider("myProfile"));

        public AmazonDynamoDB createClient() {
            return builder.build();
        }
    }

The builder also exposes fluent setters for :java-api:`ClientConfiguration <ClientConfiguration>`',
:java-api:`RequestMetricCollector <metrics/RequestMetricCollector>`, and custom
:java-api:`RequestHandler2 <handlers/RequestHandler2>`.

Here's a complete example that overrides all configurable properties:

.. code-block:: java

        AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.US_WEST_2)
                .withCredentials(new ProfileCredentialsProvider("myProfile"))
                .withClientConfiguration(new ClientConfiguration().withRequestTimeout(5000))
                .withMetricsCollector(new MyCustomMetricsCollector())
                .withRequestHandlers(new MyCustomRequestHandler(), new MyOtherCustomRequestHandler)
                .build();

Creating Async Clients
======================
The SDK also has Async clients for every service except for S3. There is a corresponding async
client builder for every service as well.

To create an async DynamoDB client with the default ExecutorService:

.. code-block:: java

        AmazonDynamoDBAsync ddbAsync = AmazonDynamoDBAsyncClientBuilder.standard()
                .withRegion(Regions.US_WEST_2)
                .withCredentials(new ProfileCredentialsProvider("myProfile"))
                .build();

In addition to all the configuration options that the synchronous client builder supports,
the async client allows setting a custom :java-api:`ExecutorFactory <client/builder/ExecutorFactory>`
to change the ExecutorService the async client uses. ExecutorFactory is a functional
interface so it interops with Java8 Lambda Expressions and Method References.

Creating an async client with a custom executor:

.. code-block:: java

    AmazonDynamoDBAsync ddbAsync = AmazonDynamoDBAsyncClientBuilder.standard()
                .withExecutorFactory(() -> Executors.newFixedThreadPool(10))
                .build();

Default Client
==============
Both the sync and async client builders have another factory method called `defaultClient`. This
will create a service client with the default configuration and with credentials and region
obtained from the environment. If either credentials or region cannot be determined from the environment
the application is running in, the call to `defaultClient` will fail. See :doc:`credentials` and
:doc:`java-dg-region-selection` for more information on how credentials are region are
determined.

Creating a default service client:

.. code-block:: java

    AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();

Client Lifecycle
================
Service clients in the SDK are thread safe and it's recommended to treat them as long lived objects
for the best performance.
Each client has it's own connection pool resource that's shutdown when the client is garbage collected.
To explicitly shutdown a client you can call the `shutdown` method. After calling shutdown, all client
resources will be released and the client will be unusable.

Shutting down a client:

.. code-block:: java

    AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();
    ddb.shutdown();
    // Client is now unusable


