# Exception Handling<a name="java-dg-exceptions"></a>

Understanding how and when the AWS SDK for Java throws exceptions is important to building high\-quality applications using the SDK\. The following sections describe the different cases of exceptions that are thrown by the SDK and how to handle them appropriately\.

## Why Unchecked Exceptions?<a name="why-unchecked-exceptions"></a>

The AWS SDK for Java uses runtime \(or unchecked\) exceptions instead of checked exceptions for these reasons:
+ To allow developers fine\-grained control over the errors they want to handle without forcing them to handle exceptional cases they aren’t concerned about \(and making their code overly verbose\)
+ To prevent scalability issues inherent with checked exceptions in large applications

In general, checked exceptions work well on small scales, but can become troublesome as applications grow and become more complex\.

For more information about the use of checked and unchecked exceptions, see:
+  [Unchecked Exceptions—​The Controversy](http://docs.oracle.com/javase/tutorial/essential/exceptions/runtime.html) 
+  [The Trouble with Checked Exceptions](http://www.artima.com/intv/handcuffs2.html) 
+  [Java’s checked exceptions were a mistake \(and here’s what I would like to do about it\)](http://radio-weblogs.com/0122027/stories/2003/04/01/JavasCheckedExceptionsWereAMistake.html) 

## AmazonServiceException \(and Subclasses\)<a name="amazonserviceexception-and-subclasses"></a>

 [AmazonServiceException](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/AmazonServiceException.html) is the most common exception that you’ll experience when using the AWS SDK for Java\. This exception represents an error response from an AWS service\. For example, if you try to terminate an Amazon EC2 instance that doesn’t exist, EC2 will return an error response and all the details of that error response will be included in the `AmazonServiceException` that’s thrown\. For some cases, a subclass of `AmazonServiceException` is thrown to allow developers fine\-grained control over handling error cases through catch blocks\.

When you encounter an `AmazonServiceException`, you know that your request was successfully sent to the AWS service but couldn’t be successfully processed\. This can be because of errors in the request’s parameters or because of issues on the service side\.

 `AmazonServiceException` provides you with information such as:
+ Returned HTTP status code
+ Returned AWS error code
+ Detailed error message from the service
+  AWS request ID for the failed request

 `AmazonServiceException` also includes information about whether the failed request was the caller’s fault \(a request with illegal values\) or the AWS service's fault \(an internal service error\)\.

## AmazonClientException<a name="amazonclientexception"></a>

 [AmazonClientException](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/AmazonClientException.html) indicates that a problem occurred inside the Java client code, either while trying to send a request to AWS or while trying to parse a response from AWS\. An `AmazonClientException` is generally more severe than an `AmazonServiceException`, and indicates a major problem that is preventing the client from making service calls to AWS services\. For example, the AWS SDK for Java throws an `AmazonClientException` if no network connection is available when you try to call an operation on one of the clients\.