# Invoking, Listing, and Deleting Lambda Functions<a name="examples-lambda"></a>

This section provides examples of programming with the Lambda service client by using the AWS SDK for Java\. To learn how to create a Lambda function, see [How to Create AWS Lambda functions](https://docs.aws.amazon.com/toolkit-for-eclipse/v1/user-guide/lambda-tutorial.html)\.

**Topics**
+ [Invoke a function](#invoke-function)
+ [List functions](#list-function)
+ [Delete a function](#delete-function)

## Invoke a function<a name="invoke-function"></a>

You can invoke a Lambda function by creating an [AWSLambda](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/lambda/AWSLambda.html) object and invoking its `invoke` method\. Create an [InvokeRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/lambda/model/InvokeRequest.html) object to specify additional information such as the function name and the payload to pass to the Lambda function\. Function names appear as *arn:aws:lambda:us\-east\-1:555556330391:function:HelloFunction*\. You can retrieve the value by looking at the function in the AWS Management Console\.

To pass payload data to a function, invoke the [InvokeRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/lambda/model/InvokeRequest.html) object’s `withPayload` method and specify a String in JSON format, as shown in the following code example\.

 **Imports** 

```
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.services.lambda.model.ServiceException;

import java.nio.charset.StandardCharsets;
```

 **Code** 

The following code example demonstrates how to invoke a Lambda function\.

```
        String functionName = args[0];

        InvokeRequest invokeRequest = new InvokeRequest()
                .withFunctionName(functionName)
                .withPayload("{\n" +
                        " \"Hello \": \"Paris\",\n" +
                        " \"countryCode\": \"FR\"\n" +
                        "}");
        InvokeResult invokeResult = null;

        try {
            AWSLambda awsLambda = AWSLambdaClientBuilder.standard()
                    .withCredentials(new ProfileCredentialsProvider())
                    .withRegion(Regions.US_WEST_2).build();

            invokeResult = awsLambda.invoke(invokeRequest);

            String ans = new String(invokeResult.getPayload().array(), StandardCharsets.UTF_8);

            //write out the return value
            System.out.println(ans);

        } catch (ServiceException e) {
            System.out.println(e);
        }

        System.out.println(invokeResult.getStatusCode());
```

See the complete example on [Github](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/lambda/src/main/java/com/example/lambda/LambdaInvokeFunction.java)\.

## List functions<a name="list-function"></a>

Build an [AWSLambda](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/lambda/AWSLambda.html) object and invoke its `listFunctions` method\. This method returns a [ListFunctionsResult](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/lambda/model/ListFunctionsResult.html) object\. You can invoke this object’s `getFunctions` method to return a list of [FunctionConfiguration](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/lambda/model/FunctionConfiguration.html) objects\. You can iterate through the list to retrieve information about the functions\. For example, the following Java code example shows how to get each function name\.

 **Imports** 

```
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.FunctionConfiguration;
import com.amazonaws.services.lambda.model.ListFunctionsResult;
import com.amazonaws.services.lambda.model.ServiceException;
import java.util.Iterator;
import java.util.List;
```

 **Code** 

The following Java code example demonstrates how to retrieve a list of Lambda function names\.

```
        ListFunctionsResult functionResult = null;

        try {
            AWSLambda awsLambda = AWSLambdaClientBuilder.standard()
                    .withCredentials(new ProfileCredentialsProvider())
                    .withRegion(Regions.US_WEST_2).build();

            functionResult = awsLambda.listFunctions();

            List<FunctionConfiguration> list = functionResult.getFunctions();

            for (Iterator iter = list.iterator(); iter.hasNext(); ) {
                FunctionConfiguration config = (FunctionConfiguration)iter.next();

                System.out.println("The function name is "+config.getFunctionName());
            }

        } catch (ServiceException e) {
            System.out.println(e);
        }
```

See the complete example on [Github](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/lambda/src/main/java/com/example/lambda/ListFunctions.java)\.

## Delete a function<a name="delete-function"></a>

Build an [AWSLambda](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/lambda/AWSLambda.html) object and invoke its `deleteFunction` method\. Create a [DeleteFunctionRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/lambda/model/DeleteFunctionRequest.html) object and pass it to the `deleteFunction` method\. This object contains information such as the name of the function to delete\. Function names appear as *arn:aws:lambda:us\-east\-1:555556330391:function:HelloFunction*\. You can retrieve the value by looking at the function in the AWS Management Console\.

 **Imports** 

```
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.ServiceException;
import com.amazonaws.services.lambda.model.DeleteFunctionRequest;
```

 **Code** 

The following Java code demonstrates how to delete a Lambda function\.

```
        String functionName = args[0];
        try {
            AWSLambda awsLambda = AWSLambdaClientBuilder.standard()
                    .withCredentials(new ProfileCredentialsProvider())
                    .withRegion(Regions.US_WEST_2).build();

            DeleteFunctionRequest delFunc = new DeleteFunctionRequest();
            delFunc.withFunctionName(functionName);

            //Delete the function
            awsLambda.deleteFunction(delFunc);
            System.out.println("The function is deleted");

        } catch (ServiceException e) {
            System.out.println(e);
        }
```

See the complete example on [Github](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/lambda/src/main/java/com/example/lambda/DeleteFunction.java)\.