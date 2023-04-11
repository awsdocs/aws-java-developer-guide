# Publishing Custom Metric Data<a name="examples-cloudwatch-publish-custom-metrics"></a>

A number of AWS services publish [their own metrics](https://docs.aws.amazon.com/AmazonCloudWatch/latest/monitoring/aws-namespaces.html) in namespaces beginning with " `AWS` " You can also publish custom metric data using your own namespace \(as long as it doesn’t begin with " `AWS` "\)\.

## Publish Custom Metric Data<a name="publish-custom-metric-data"></a>

To publish your own metric data, call the AmazonCloudWatchClient’s `putMetricData` method with a [PutMetricDataRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/cloudwatch/model/PutMetricDataRequest.html)\. The `PutMetricDataRequest` must include the custom namespace to use for the data, and information about the data point itself in a [MetricDatum](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/cloudwatch/model/MetricDatum.html) object\.

**Note**  
You cannot specify a namespace that begins with " `AWS` "\. Namespaces that begin with " `AWS` " are reserved for use by Amazon Web Services products\.

 **Imports** 

```
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.amazonaws.services.cloudwatch.model.PutMetricDataResult;
import com.amazonaws.services.cloudwatch.model.StandardUnit;
```

 **Code** 

```
final AmazonCloudWatch cw =
    AmazonCloudWatchClientBuilder.defaultClient();

Dimension dimension = new Dimension()
    .withName("UNIQUE_PAGES")
    .withValue("URLS");

MetricDatum datum = new MetricDatum()
    .withMetricName("PAGES_VISITED")
    .withUnit(StandardUnit.None)
    .withValue(data_point)
    .withDimensions(dimension);

PutMetricDataRequest request = new PutMetricDataRequest()
    .withNamespace("SITE/TRAFFIC")
    .withMetricData(datum);

PutMetricDataResult response = cw.putMetricData(request);
```

## More Information<a name="more-information"></a>
+  [Using Amazon CloudWatch Metrics](http://docs.aws.amazon.com/AmazonCloudWatch/latest/monitoring/working_with_metrics.html) in the Amazon CloudWatch User Guide\.
+  [AWS Namespaces](https://docs.aws.amazon.com/AmazonCloudWatch/latest/monitoring/aws-namespaces.html) in the Amazon CloudWatch User Guide\.
+  [PutMetricData](http://docs.aws.amazon.com/AmazonCloudWatch/latest/APIReference/API_PutMetricData.html) in the Amazon CloudWatch API Reference\.