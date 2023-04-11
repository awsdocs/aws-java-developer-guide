# Getting Metrics from CloudWatch<a name="examples-cloudwatch-get-metrics"></a>

## Listing Metrics<a name="listing-metrics"></a>

To list CloudWatch metrics, create a [ListMetricsRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/cloudwatch/model/ListMetricsRequest.html) and call the AmazonCloudWatchClient’s `listMetrics` method\. You can use the `ListMetricsRequest` to filter the returned metrics by namespace, metric name, or dimensions\.

**Note**  
A list of metrics and dimensions that are posted by AWS services can be found within the \{https\-\-\-docs\-aws\-amazon\-com\-AmazonCloudWatch\-latest\-monitoring\-CW\-Support\-For\-AWS\-html\}\[Amazon CloudWatch Metrics and Dimensions Reference\] in the Amazon CloudWatch User Guide\.

 **Imports** 

```
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.ListMetricsRequest;
import com.amazonaws.services.cloudwatch.model.ListMetricsResult;
import com.amazonaws.services.cloudwatch.model.Metric;
```

 **Code** 

```
final AmazonCloudWatch cw =
    AmazonCloudWatchClientBuilder.defaultClient();

ListMetricsRequest request = new ListMetricsRequest()
        .withMetricName(name)
        .withNamespace(namespace);

boolean done = false;

while(!done) {
    ListMetricsResult response = cw.listMetrics(request);

    for(Metric metric : response.getMetrics()) {
        System.out.printf(
            "Retrieved metric %s", metric.getMetricName());
    }

    request.setNextToken(response.getNextToken());

    if(response.getNextToken() == null) {
        done = true;
    }
}
```

The metrics are returned in a [ListMetricsResult](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/cloudwatch/model/ListMetricsResult.html) by calling its `getMetrics` method\. The results may be *paged*\. To retrieve the next batch of results, call `setNextToken` on the original request object with the return value of the `ListMetricsResult` object’s `getNextToken` method, and pass the modified request object back to another call to `listMetrics`\.

## More Information<a name="more-information"></a>
+  [ListMetrics](http://docs.aws.amazon.com/AmazonCloudWatch/latest/APIReference/API_ListMetrics.html) in the Amazon CloudWatch API Reference\.