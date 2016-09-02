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

	To make requests to |AWSlong|, you first create a service client object. The
	preferred way to do this is to use the service client builder. 
	
	Each AWS service has a service interface
	that has methods for each action in the service API. For example, the service interface for
	|DDBlong| is named :java-api:`AmazonDynamoDB <services/dynamodbv2/AmazonDynamoDB>`. Each
	service interface has a corresponding client builder you can use to construct
	an implementation of the service interface. The client builder class for |DDB| is named
	:java-api:`AmazonDynamoDBClientBuilder <services/dynamodbv2/AmazonDynamoDBClientBuilder>`.

	To obtain an instance of the client builder, use the static factory method ``standard``, as shown in the following example.

	.. code-block:: java

		AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard();


	Once you obtain a builder, you can customize properties of the client by using the many fluent setters
	in the builder API. For example, the following shows how to set a custom region and a custom credentials provider.

	.. code-block:: java

		AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard()
								.withRegion(Regions.US_WEST_2)
								.withCredentials(new ProfileCredentialsProvider("myProfile"))
								.build();

	.. note:: The fluent withXXX methods return the ``builder`` object so that you can chain the method calls for convenience and more readable code. After you configure all the properties you want, you can call the ``build`` method to create the client. After a client is
	created, it's immutable and any calls to ``setRegion`` or ``setEndpoint`` fail.

	A builder can create multiple clients with the same configuration. When you're writing your application, beware that the builder is
	mutable and not thread safe. The following
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

	The following complete example overrides all configurable properties.

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
	The |sdk-java| also has async clients and a corresponding async
	client builder for every service, except |S3long|. 

	**To create an async |DDB| client with the default ExecutorService**

	.. code-block:: java

			AmazonDynamoDBAsync ddbAsync = AmazonDynamoDBAsyncClientBuilder.standard()
					.withRegion(Regions.US_WEST_2)
					.withCredentials(new ProfileCredentialsProvider("myProfile"))
					.build();

	In addition to the configuration options that the synchronous client builder supports,
	the async client allows you to set a custom :java-api:`ExecutorFactory <client/builder/ExecutorFactory>`
	to change the ``ExecutorService`` that the async client uses. ``ExecutorFactory`` is a functional
	interface, so it interoperates with Java 8 lambda expressions and method references.

	**To create an async client with a custom executor**

	.. code-block:: java

		AmazonDynamoDBAsync ddbAsync = AmazonDynamoDBAsyncClientBuilder.standard()
					.withExecutorFactory(() -> Executors.newFixedThreadPool(10))
					.build();

	Default Client
	==============
	Both the sync and async client builders have another factory method called ``defaultClient``. This
	creates a service client with the default configuration and with credentials and region
	obtained from the environment. If credentials or region can't be determined from the environment
	that the application is running in, the call to ``defaultClient`` fails. See :doc:`credentials` and
	:doc:`java-dg-region-selection` for more information on how credentials and region are
	determined.

	**To create a default service client**

	.. code-block:: java

		AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();

	Client Lifecycle
	================
	Service clients in the |sdk-java| are thread safe, and for best performance it's recommended that you treat them as long-lived objects.
	Each client has its own connection pool resource that's shut down when the client is garbage collected.
	To explicitly shut down a client, you can call the ``shutdown`` method. After calling ``shutdown``, all client
	resources are released and the client is unusable.

	**To shut down a client**

	.. code-block:: java

		AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();
		ddb.shutdown();
		// Client is now unusable


