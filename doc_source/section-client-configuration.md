--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Client Configuration<a name="section-client-configuration"></a>

The AWS SDK for Java enables you to change the default client configuration, which is helpful when you want to:
+ Connect to the Internet through proxy
+ Change HTTP transport settings, such as connection timeout and request retries
+ Specify TCP socket buffer size hints

## Proxy Configuration<a name="proxy-configuration"></a>

When constructing a client object, you can pass in an optional [ClientConfiguration](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/ClientConfiguration.html) object to customize the client’s configuration\.

If you connect to the Internet through a proxy server, you’ll need to configure your proxy server settings \(proxy host, port, and username/password\) through the `ClientConfiguration` object\.

## HTTP Transport Configuration<a name="http-transport-configuration"></a>

You can configure several HTTP transport options by using the [ClientConfiguration](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/ClientConfiguration.html) object\. New options are occasionally added; to see the full list of options you can retrieve or set, see the AWS SDK for Java API Reference\.

**Note**  
Each of the configurable values has a default value defined by a constant\. For a list of the constant values for `ClientConfiguration`, see [Constant Field Values](https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/constant-values.html) in the AWS SDK for Java API Reference\.

### Maximum Connections<a name="maximum-connections"></a>

You can set the maximum allowed number of open HTTP connections by using the [ClientConfiguration\.setMaxConnections](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/ClientConfiguration.html#setMaxConnections-int-) method\.

**Important**  
Set the maximum connections to the number of concurrent transactions to avoid connection contentions and poor performance\. For the default maximum connections value, see [Constant Field Values](https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/constant-values.html) in the AWS SDK for Java API Reference\.

### Timeouts and Error Handling<a name="timeouts-and-error-handling"></a>

You can set options related to timeouts and handling errors with HTTP connections\.
+  **Connection Timeout** 

  The connection timeout is the amount of time \(in milliseconds\) that the HTTP connection will wait to establish a connection before giving up\. The default is 10,000 ms\.

  To set this value yourself, use the [ClientConfiguration\.setConnectionTimeout](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/ClientConfiguration.html#setConnectionTimeout-int-) method\.
+  **Connection Time to Live \(TTL\)** 

  By default, the SDK will attempt to reuse HTTP connections as long as possible\. In failure situations where a connection is established to a server that has been brought out of service, having a finite TTL can help with application recovery\. For example, setting a 15 minute TTL will ensure that even if you have a connection established to a server that is experiencing issues, you’ll reestablish a connection to a new server within 15 minutes\.

  To set the HTTP connection TTL, use the [ClientConfiguration\.setConnectionTTL](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/ClientConfiguration.html#setConnectionTTL-long-) method\.
+  **Maximum Error Retries** 

  The default maximum retry count for retriable errors is 3\. You can set a different value by using the [ClientConfiguration\.setMaxErrorRetry](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/ClientConfiguration.html#setMaxErrorRetry-int-) method\.

### Local Address<a name="local-address"></a>

To set the local address that the HTTP client will bind to, use [ClientConfiguration\.setLocalAddress](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/ClientConfiguration.html#setLocalAddress-java.net.InetAddress-)\.

## TCP Socket Buffer Size Hints<a name="tcp-socket-buffer-size-hints"></a>

Advanced users who want to tune low\-level TCP parameters can additionally set TCP buffer size hints through the [ClientConfiguration](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/ClientConfiguration.html) object\. The majority of users will never need to tweak these values, but they are provided for advanced users\.

Optimal TCP buffer sizes for an application are highly dependent on network and operating system configuration and capabilities\. For example, most modern operating systems provide auto\-tuning logic for TCP buffer sizes\.This can have a big impact on performance for TCP connections that are held open long enough for the auto\-tuning to optimize buffer sizes\.

Large buffer sizes \(e\.g\., 2 MB\) allow the operating system to buffer more data in memory without requiring the remote server to acknowledge receipt of that information, and so can be particularly useful when the network has high latency\.

This is only a *hint*, and the operating system might not honor it\. When using this option, users should always check the operating system’s configured limits and defaults\. Most operating systems have a maximum TCP buffer size limit configured, and won’t let you go beyond that limit unless you explicitly raise the maximum TCP buffer size limit\.

Many resources are available to help with configuring TCP buffer sizes and operating system\-specific TCP settings, including the following:
+  [Host Tuning](http://fasterdata.es.net/host-tuning/) 