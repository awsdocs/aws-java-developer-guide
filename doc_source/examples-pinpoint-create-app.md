--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Creating and Deleting Apps in Amazon Pinpoint<a name="examples-pinpoint-create-app"></a>

An app is an Amazon Pinpoint project in which you define the audience for a distinct application, and you engage this audience with tailored messages\. The examples on this page demonstrate how to create a new app or delete an existing one\.

## Create an App<a name="create-an-app"></a>

Create a new app in Amazon Pinpoint by providing an app name to the [CreateAppRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/pinpoint/model/CreateAppRequest.html) object, and then passing that object to the AmazonPinpointClient’s `createApp` method\.

 **Imports** 

```
import com.amazonaws.services.pinpoint.AmazonPinpoint;
import com.amazonaws.services.pinpoint.AmazonPinpointClientBuilder;
import com.amazonaws.services.pinpoint.model.CreateAppRequest;
import com.amazonaws.services.pinpoint.model.CreateAppResult;
import com.amazonaws.services.pinpoint.model.CreateApplicationRequest;
```

 **Code** 

```
CreateApplicationRequest appRequest = new CreateApplicationRequest()
		.withName(appName);

CreateAppRequest request = new CreateAppRequest();
request.withCreateApplicationRequest(appRequest);
CreateAppResult result = pinpoint.createApp(request);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/pinpoint/src/main/java/com/example/pinpoint/CreateApp.java) on GitHub\.

## Delete an App<a name="delete-an-app"></a>

To delete an app, call the AmazonPinpointClient’s `deleteApp` request with a [DeleteAppRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/pinpoint/model/DeleteAppRequest.html) object that’s set with the app name to delete\.

 **Imports** 

```
import com.amazonaws.services.pinpoint.AmazonPinpoint;
import com.amazonaws.services.pinpoint.AmazonPinpointClientBuilder;
```

 **Code** 

```
DeleteAppRequest deleteRequest = new DeleteAppRequest()
		.withApplicationId(appID);

pinpoint.deleteApp(deleteRequest);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/pinpoint/src/main/java/com/example/pinpoint/DeleteApp.java) on GitHub\.

## More Information<a name="more-information"></a>
+  [Apps](http://docs.aws.amazon.com/pinpoint/latest/apireference/rest-api-apps.html) in the Amazon Pinpoint API Reference
+  [App](http://docs.aws.amazon.com/pinpoint/latest/apireference/rest-api-app.html) in the Amazon Pinpoint API Reference