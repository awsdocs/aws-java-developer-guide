--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Creating Segments in Amazon Pinpoint<a name="examples-pinpoint-create-segment"></a>

A user segment represents a subset of your users that’s based on shared characteristics, such as how recently a user opened your app or which device they use\. The following example demonstrates how to define a segment of users\.

## Create a Segment<a name="create-a-segment"></a>

Create a new segment in Amazon Pinpoint by defining dimensions of the segment in a [SegmentDimensions](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/pinpoint/model/SegmentDimensions.html) object\.

 **Imports** 

```
import com.amazonaws.services.pinpoint.AmazonPinpoint;
import com.amazonaws.services.pinpoint.AmazonPinpointClientBuilder;
import com.amazonaws.services.pinpoint.model.CreateSegmentRequest;
import com.amazonaws.services.pinpoint.model.CreateSegmentResult;
import com.amazonaws.services.pinpoint.model.AttributeDimension;
import com.amazonaws.services.pinpoint.model.AttributeType;
import com.amazonaws.services.pinpoint.model.RecencyDimension;
import com.amazonaws.services.pinpoint.model.SegmentBehaviors;
import com.amazonaws.services.pinpoint.model.SegmentDemographics;
import com.amazonaws.services.pinpoint.model.SegmentDimensions;
import com.amazonaws.services.pinpoint.model.SegmentLocation;
import com.amazonaws.services.pinpoint.model.SegmentResponse;
import com.amazonaws.services.pinpoint.model.WriteSegmentRequest;
```

 **Code** 

```
Pinpoint pinpoint = AmazonPinpointClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
Map<String, AttributeDimension> segmentAttributes = new HashMap<>();
segmentAttributes.put("Team", new AttributeDimension().withAttributeType(AttributeType.INCLUSIVE).withValues("Lakers"));

SegmentBehaviors segmentBehaviors = new SegmentBehaviors();
SegmentDemographics segmentDemographics = new SegmentDemographics();
SegmentLocation segmentLocation = new SegmentLocation();

RecencyDimension recencyDimension = new RecencyDimension();
recencyDimension.withDuration("DAY_30").withRecencyType("ACTIVE");
segmentBehaviors.setRecency(recencyDimension);

SegmentDimensions dimensions = new SegmentDimensions()
        .withAttributes(segmentAttributes)
        .withBehavior(segmentBehaviors)
        .withDemographic(segmentDemographics)
        .withLocation(segmentLocation);
```

Next set the [SegmentDimensions](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/pinpoint/model/SegmentDimensions.html) object in a [WriteSegmentRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/pinpoint/model/WriteSegmentRequest.html), which in turn is used to create a [CreateSegmentRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/pinpoint/model/CreateSegmentRequest.html) object\. Then pass the CreateSegmentRequest object to the AmazonPinpointClient’s `createSegment` method\.

 **Code** 

```
WriteSegmentRequest writeSegmentRequest = new WriteSegmentRequest()
        .withName("MySegment").withDimensions(dimensions);

CreateSegmentRequest createSegmentRequest = new CreateSegmentRequest()
        .withApplicationId(appId).withWriteSegmentRequest(writeSegmentRequest);

CreateSegmentResult createSegmentResult = client.createSegment(createSegmentRequest);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/pinpoint/src/main/java/com/example/pinpoint/CreateSegment.java) on GitHub\.

## More Information<a name="more-information"></a>
+  [Amazon Pinpoint Segments](https://docs.aws.amazon.com/pinpoint/latest/userguide/segments.html) in the Amazon Pinpoint User Guide
+  [Creating Segments](http://docs.aws.amazon.com/pinpoint/latest/developerguide/segments.html) in the Amazon Pinpoint Developer Guide
+  [Segments](http://docs.aws.amazon.com/pinpoint/latest/apireference/rest-api-segments.html) in the Amazon Pinpoint API Reference
+  [Segment](http://docs.aws.amazon.com/pinpoint/latest/apireference/rest-api-segment.html) in the Amazon Pinpoint API Reference