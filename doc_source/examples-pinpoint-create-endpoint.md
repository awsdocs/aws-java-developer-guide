--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Creating Endpoints in Amazon Pinpoint<a name="examples-pinpoint-create-endpoint"></a>

An endpoint uniquely identifies a user device to which you can send push notifications with Amazon Pinpoint\. If your app is enabled with Amazon Pinpoint support, your app automatically registers an endpoint with Amazon Pinpoint when a new user opens your app\. The following example demonstrates how to add a new endpoint programmatically\.

## Create an Endpoint<a name="create-an-endpoint"></a>

Create a new endpoint in Amazon Pinpoint by providing the endpoint data in an [EndpointRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/pinpoint/model/EndpointRequest.html) object\.

 **Imports** 

```
import com.amazonaws.services.pinpoint.AmazonPinpoint;
import com.amazonaws.services.pinpoint.AmazonPinpointClientBuilder;
import com.amazonaws.services.pinpoint.model.UpdateEndpointRequest;
import com.amazonaws.services.pinpoint.model.UpdateEndpointResult;
import com.amazonaws.services.pinpoint.model.EndpointDemographic;
import com.amazonaws.services.pinpoint.model.EndpointLocation;
import com.amazonaws.services.pinpoint.model.EndpointRequest;
import com.amazonaws.services.pinpoint.model.EndpointResponse;
import com.amazonaws.services.pinpoint.model.EndpointUser;
import com.amazonaws.services.pinpoint.model.GetEndpointRequest;
import com.amazonaws.services.pinpoint.model.GetEndpointResult;
```

 **Code** 

```
HashMap<String, List<String>> customAttributes = new HashMap<>();
List<String> favoriteTeams = new ArrayList<>();
favoriteTeams.add("Lakers");
favoriteTeams.add("Warriors");
customAttributes.put("team", favoriteTeams);


EndpointDemographic demographic = new EndpointDemographic()
        .withAppVersion("1.0")
        .withMake("apple")
        .withModel("iPhone")
        .withModelVersion("7")
        .withPlatform("ios")
        .withPlatformVersion("10.1.1")
        .withTimezone("America/Los_Angeles");

EndpointLocation location = new EndpointLocation()
        .withCity("Los Angeles")
        .withCountry("US")
        .withLatitude(34.0)
        .withLongitude(-118.2)
        .withPostalCode("90068")
        .withRegion("CA");

Map<String,Double> metrics = new HashMap<>();
metrics.put("health", 100.00);
metrics.put("luck", 75.00);

EndpointUser user = new EndpointUser()
        .withUserId(UUID.randomUUID().toString());

DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
String nowAsISO = df.format(new Date());

EndpointRequest endpointRequest = new EndpointRequest()
        .withAddress(UUID.randomUUID().toString())
        .withAttributes(customAttributes)
        .withChannelType("APNS")
        .withDemographic(demographic)
        .withEffectiveDate(nowAsISO)
        .withLocation(location)
        .withMetrics(metrics)
        .withOptOut("NONE")
        .withRequestId(UUID.randomUUID().toString())
        .withUser(user);
```

Then create an [UpdateEndpointRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/pinpoint/model/UpdateEndpointRequest.html) object with that EndpointRequest object\. Finally, pass the UpdateEndpointRequest object to the AmazonPinpointClient’s `updateEndpoint` method\.

 **Code** 

```
UpdateEndpointRequest updateEndpointRequest = new UpdateEndpointRequest()
        .withApplicationId(appId)
        .withEndpointId(endpointId)
        .withEndpointRequest(endpointRequest);

UpdateEndpointResult updateEndpointResponse = client.updateEndpoint(updateEndpointRequest);
System.out.println("Update Endpoint Response: " + updateEndpointResponse.getMessageBody());
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/pinpoint/src/main/java/com/example/pinpoint/CreateEndpoint.java) on GitHub\.

## More Information<a name="more-information"></a>
+  [Adding Endpoint](http://docs.aws.amazon.com/pinpoint/latest/developerguide/endpoints.html) in the Amazon Pinpoint Developer Guide
+  [Endpoint](http://docs.aws.amazon.com/pinpoint/latest/apireference/rest-api-endpoint.html) in the Amazon Pinpoint API Reference