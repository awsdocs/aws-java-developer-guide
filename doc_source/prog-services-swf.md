# Amazon SWF Examples Using the AWS SDK for Java<a name="prog-services-swf"></a>

 [Amazon SWF](http://aws.amazon.com/swf/) is a workflow\-management service that helps developers build and scale distributed workflows that can have parallel or sequential steps consisting of activities, child workflows or even [Lambda](http://aws.amazon.com/lambda/) tasks\.

There are two ways to work with Amazon SWF using the AWS SDK for Java, by using the SWF *client* object, or by using the AWS Flow Framework for Java\. The AWS Flow Framework for Java is more difficult to configure initially, since it makes heavy use of annotations and relies on additional libraries such as AspectJ and the Spring Framework\. However, for large or complex projects, you will save coding time by using the AWS Flow Framework for Java\. For more information, see the [AWS Flow Framework for Java Developer Guide](https://docs.aws.amazon.com/amazonswf/latest/awsflowguide/)\.

This section provides examples of programming Amazon SWF by using the AWS SDK for Java client directly\.

**Topics**
+ [SWF basics](swf-basics.md)
+ [Building a Simple Amazon SWF Application](swf-hello.md)
+ [Lambda Tasks](swf-lambda-task.md)
+ [Shutting Down Activity and Workflow Workers Gracefully](swf-graceful-shutdown.md)
+ [Registering Domains](prog-services-swf-register-domain.md)
+ [Listing Domains](prog-services-swf-list-domains.md)