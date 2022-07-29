--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Enabling Metrics for the AWS SDK for Java<a name="generating-sdk-metrics"></a>

The AWS SDK for Java can generate metrics for visualization and monitoring with [CloudWatch](http://aws.amazon.com/cloudwatch/) that measure:
+ your application’s performance when accessing AWS 
+ the performance of your JVMs when used with AWS 
+ runtime environment details such as heap memory, number of threads, and opened file descriptors

## How to Enable Java SDK Metric Generation<a name="how-to-enable-sdk-java-metric-generation"></a>

 AWS SDK for Java metrics are *disabled by default*\. To enable it for your local development environment, include a system property that points to your AWS security credential file when starting up the JVM\. For example:

```
-Dcom.amazonaws.sdk.enableDefaultMetrics=credentialFile=/path/aws.properties
```

You need to specify the path to your credential file so that the SDK can upload the gathered datapoints to CloudWatch for later analysis\.

**Note**  
If you are accessing AWS from an Amazon EC2 instance using the Amazon EC2 instance metadata service, you don’t need to specify a credential file\. In this case, you need only specify:  

```
-Dcom.amazonaws.sdk.enableDefaultMetrics
```

All metrics captured by the AWS SDK for Java are under the namespace **AWSSDK/Java**, and are uploaded to the CloudWatch default region \(*us\-east\-1*\)\. To change the region, specify it by using the `cloudwatchRegion` attribute in the system property\. For example, to set the CloudWatch region to *us\-east\-1*, use:

```
-Dcom.amazonaws.sdk.enableDefaultMetrics=credentialFile=/path/aws.properties,cloudwatchRegion={region_api_default}
```

Once you enable the feature, every time there is a service request to AWS from the AWS SDK for Java, metric data points will be generated, queued for statistical summary, and uploaded asynchronously to CloudWatch about once every minute\. Once metrics have been uploaded, you can visualize them using the [AWS Management Console](https://console.aws.amazon.com/console/home) and set alarms on potential problems such as memory leakage, file descriptor leakage, and so on\.

## Available Metric Types<a name="available-metric-types"></a>

The default set of metrics is divided into three major categories:

 AWS Request Metrics  
+ Covers areas such as the latency of the HTTP request/response, number of requests, exceptions, and retries\.  
![\[RequestMetric 131111\]](http://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/images/RequestMetric-131111.png)

 AWS service Metrics  
+ Include AWS service\-specific data, such as the throughput and byte count for S3 uploads and downloads\.  
![\[ServiceMetric 131111\]](http://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/images/ServiceMetric-131111.png)

Machine Metrics  
+ Cover the runtime environment, including heap memory, number of threads, and open file descriptors\.  
![\[MachineMetric 131111\]](http://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/images/MachineMetric-131111.png)

  If you want to exclude Machine Metrics, add `excludeMachineMetrics` to the system property:

  ```
  -Dcom.amazonaws.sdk.enableDefaultMetrics=credentialFile=/path/aws.properties,excludeMachineMetrics
  ```

## More Information<a name="more-information"></a>
+ See the [amazonaws/metrics package summary](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/metrics/package-summary.html) for a full list of the predefined core metric types\.
+ Learn about working with CloudWatch using the AWS SDK for Java in [CloudWatch Examples Using the AWS SDK for Java](examples-cloudwatch.md)\.
+ Learn more about performance tuning in [Tuning the AWS SDK for Java to Improve Resiliency](http://aws.amazon.com/blogs/developer/tuning-the-aws-sdk-for-java-to-improve-resiliency) blog post\.