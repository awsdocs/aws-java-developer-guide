# SWF basics<a name="swf-basics"></a>

These are general patterns for working with Amazon SWF using the AWS SDK for Java\. It is meant primarily for reference\. For a more complete introductory tutorial, see [Building a Simple Amazon SWF Application](swf-hello.md)\.

## Dependencies<a name="dependencies"></a>

Basic Amazon SWF applications will require the following dependencies, which are included with the AWS SDK for Java:
+ aws\-java\-sdk\-1\.12\.\*\.jar
+ commons\-logging\-1\.2\.\*\.jar
+ httpclient\-4\.3\.\*\.jar
+ httpcore\-4\.3\.\*\.jar
+ jackson\-annotations\-2\.12\.\*\.jar
+ jackson\-core\-2\.12\.\*\.jar
+ jackson\-databind\-2\.12\.\*\.jar
+ joda\-time\-2\.8\.\*\.jar

**Note**  
The version numbers of these packages will differ depending on the version of the SDK that you have, but the versions that are supplied with the SDK have been tested for compatibility, and are the ones you should use\.

 AWS Flow Framework for Java applications require additional setup, *and* additional dependencies\. See the [AWS Flow Framework for Java Developer Guide](https://docs.aws.amazon.com/amazonswf/latest/awsflowguide/) for more information about using the framework\.

## Imports<a name="imports"></a>

In general, you can use the following imports for code development:

```
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClientBuilder;
import com.amazonaws.services.simpleworkflow.model.*;
```

It’s a good practice to import only the classes you require, however\. You will likely end up specifying particular classes in the `com.amazonaws.services.simpleworkflow.model` workspace:

```
import com.amazonaws.services.simpleworkflow.model.PollForActivityTaskRequest;
import com.amazonaws.services.simpleworkflow.model.RespondActivityTaskCompletedRequest;
import com.amazonaws.services.simpleworkflow.model.RespondActivityTaskFailedRequest;
import com.amazonaws.services.simpleworkflow.model.TaskList;
```

If you are using the AWS Flow Framework for Java, you will import classes from the `com.amazonaws.services.simpleworkflow.flow` workspace\. For example:

```
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.flow.ActivityWorker;
```

**Note**  
The AWS Flow Framework for Java has additional requirements beyond those of the base AWS SDK for Java\. For more information, see the [AWS Flow Framework for Java Developer Guide](https://docs.aws.amazon.com/amazonswf/latest/awsflowguide/)\.

## Using the SWF client class<a name="using-the-swf-client-class"></a>

Your basic interface to Amazon SWF is through either the [AmazonSimpleWorkflowClient](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/AmazonSimpleWorkflowClient.html) or [AmazonSimpleWorkflowAsyncClient](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/AmazonSimpleWorkflowAsyncClient.html) classes\. The main difference between these is that the `\*AsyncClient` class return [Future](https://docs.oracle.com/javase/8/docs/api/index.html?java/util/concurrent/Future.html) objects for concurrent \(asynchronous\) programming\.

```
AmazonSimpleWorkflowClient swf = AmazonSimpleWorkflowClientBuilder.defaultClient();
```