--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Creating Campaigns in Amazon Pinpoint<a name="examples-pinpoint-create-campaign"></a>

You can use campaigns to help increase engagement between your app and your users\. You can create a campaign to reach out to a particular segment of your users with tailored messages or special promotions\. This example demonstrates how to create a new standard campaign that sends a custom push notification to a specified segment\.

## Create a Campaign<a name="create-a-campaign"></a>

Before creating a new campaign, you must define a [Schedule](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/pinpoint/model/Schedule.html) and a [Message](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/pinpoint/model/Message.html) and set these values in a [WriteCampaignRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/pinpoint/model/WriteCampaignRequest.html) object\.

 **Imports** 

```
import com.amazonaws.services.pinpoint.AmazonPinpoint;
import com.amazonaws.services.pinpoint.AmazonPinpointClientBuilder;
import com.amazonaws.services.pinpoint.model.CreateCampaignRequest;
import com.amazonaws.services.pinpoint.model.CreateCampaignResult;
import com.amazonaws.services.pinpoint.model.Action;
import com.amazonaws.services.pinpoint.model.CampaignResponse;
import com.amazonaws.services.pinpoint.model.Message;
import com.amazonaws.services.pinpoint.model.MessageConfiguration;
import com.amazonaws.services.pinpoint.model.Schedule;
import com.amazonaws.services.pinpoint.model.WriteCampaignRequest;
```

 **Code** 

```
Schedule schedule = new Schedule()
        .withStartTime("IMMEDIATE");

Message defaultMessage = new Message()
        .withAction(Action.OPEN_APP)
        .withBody("My message body.")
        .withTitle("My message title.");

MessageConfiguration messageConfiguration = new MessageConfiguration()
        .withDefaultMessage(defaultMessage);

WriteCampaignRequest request = new WriteCampaignRequest()
        .withDescription("My description.")
        .withSchedule(schedule)
        .withSegmentId(segmentId)
        .withName("MyCampaign")
        .withMessageConfiguration(messageConfiguration);
```

Then create a new campaign in Amazon Pinpoint by providing the [WriteCampaignRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/pinpoint/model/WriteCampaignRequest.html) with the campaign configuration to a [CreateCampaignRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/CreateCampaignRequest.html) object\. Finally, pass the CreateCampaignRequest object to the AmazonPinpointClient’s `createCampaign` method\.

 **Code** 

```
CreateCampaignRequest createCampaignRequest = new CreateCampaignRequest()
        .withApplicationId(appId).withWriteCampaignRequest(request);

CreateCampaignResult result = client.createCampaign(createCampaignRequest);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/pinpoint/src/main/java/com/example/pinpoint/CreateApp.java) on GitHub\.

## More Information<a name="more-information"></a>
+  [Amazon Pinpoint Campaigns](https://docs.aws.amazon.com/pinpoint/latest/userguide/campaigns.html) in the Amazon Pinpoint User Guide
+  [Creating Campaigns](http://docs.aws.amazon.com/pinpoint/latest/developerguide/campaigns.html) in the Amazon Pinpoint Developer Guide
+  [Campaigns](http://docs.aws.amazon.com/pinpoint/latest/apireference/rest-api-campaigns.html) in the Amazon Pinpoint API Reference
+  [Campaign](http://docs.aws.amazon.com/pinpoint/latest/apireference/rest-api-campaign.html) in the Amazon Pinpoint API Reference
+  [Campaign Activities](http://docs.aws.amazon.com/pinpoint/latest/apireference/rest-api-campaign-activities.html) in the Amazon Pinpoint API Reference
+  [Campaign Versions](http://docs.aws.amazon.com/pinpoint/latest/apireference/rest-api-campaign-versions.html) in the Amazon Pinpoint API Reference
+  [Campaign Version](http://docs.aws.amazon.com/pinpoint/latest/apireference/rest-api-campaign-version.html) in the Amazon Pinpoint API Reference