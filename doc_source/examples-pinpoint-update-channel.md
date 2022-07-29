--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Updating Channels in Amazon Pinpoint<a name="examples-pinpoint-update-channel"></a>

A channel defines the types of platforms to which you can deliver messages\. This example shows how to use the APNs channel to send a message\.

## Update a Channel<a name="update-a-channel"></a>

Enable a channel in Amazon Pinpoint by providing an app ID and a request object of the channel type you want to update\. This example updates the APNs channel, which requires the [APNSChannelRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/pinpoint/model/APNSChannelRequest.html) object\. Set these in the [UpdateApnsChannelRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/pinpoint/model/UpdateApnsChannelRequest.html) and pass that object to the AmazonPinpointClient’s `updateApnsChannel` method\.

 **Imports** 

```
import com.amazonaws.services.pinpoint.AmazonPinpoint;
import com.amazonaws.services.pinpoint.AmazonPinpointClientBuilder;
import com.amazonaws.services.pinpoint.model.APNSChannelRequest;
import com.amazonaws.services.pinpoint.model.APNSChannelResponse;
import com.amazonaws.services.pinpoint.model.GetApnsChannelRequest;
import com.amazonaws.services.pinpoint.model.GetApnsChannelResult;
import com.amazonaws.services.pinpoint.model.UpdateApnsChannelRequest;
import com.amazonaws.services.pinpoint.model.UpdateApnsChannelResult;
```

 **Code** 

```
APNSChannelRequest request = new APNSChannelRequest()
		.withEnabled(enabled);

UpdateApnsChannelRequest updateRequest = new UpdateApnsChannelRequest()
		.withAPNSChannelRequest(request)
		.withApplicationId(appId);
UpdateApnsChannelResult result = client.updateApnsChannel(updateRequest);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/pinpoint/src/main/java/com/example/pinpoint/UpdateChannel.java) on GitHub\.

## More Information<a name="more-information"></a>
+  [Amazon Pinpoint Channels](https://docs.aws.amazon.com/pinpoint/latest/userguide/channels.html) in the Amazon Pinpoint User Guide
+  [ADM Channel](http://docs.aws.amazon.com/pinpoint/latest/apireference/rest-api-adm-channel.html) in the Amazon Pinpoint API Reference
+  [APNs Channel](http://docs.aws.amazon.com/pinpoint/latest/apireference/rest-api-apns-channel.html) in the Amazon Pinpoint API Reference
+  [APNs Sandbox Channel](http://docs.aws.amazon.com/pinpoint/latest/apireference/rest-api-apns-sandbox-channel.html) in the Amazon Pinpoint API Reference
+  [APNs VoIP Channel](http://docs.aws.amazon.com/pinpoint/latest/apireference/rest-api-apns-voip-channel.html) in the Amazon Pinpoint API Reference
+  [APNs VoIP Sandbox Channel](http://docs.aws.amazon.com/pinpoint/latest/apireference/rest-api-apns-voip-sandbox-channel.html) in the Amazon Pinpoint API Reference
+  [Baidu Channel](http://docs.aws.amazon.com/pinpoint/latest/apireference/rest-api-baidu-channel.html) in the Amazon Pinpoint API Reference
+  [Email Channel](http://docs.aws.amazon.com/pinpoint/latest/apireference/rest-api-email-channel.html) in the Amazon Pinpoint API Reference
+  [GCM Channel](http://docs.aws.amazon.com/pinpoint/latest/apireference/rest-api-gcm-channel.html) in the Amazon Pinpoint API Reference
+  [SMS Channel](http://docs.aws.amazon.com/pinpoint/latest/apireference/rest-api-sms-channel.html) in the Amazon Pinpoint API Reference