--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Best Practices for AWS Development with the AWS SDK for Java<a name="best-practices"></a>

The following best practices can help you avoid issues or trouble as you develop AWS applications with the AWS SDK for Java\. We’ve organized best practices by service\.

## S3<a name="s3"></a>

### Avoid ResetExceptions<a name="s3-avoid-resetexception"></a>

When you upload objects to Amazon S3 by using streams \(either through an `AmazonS3` client or `TransferManager`\), you might encounter network connectivity or timeout issues\. By default, the AWS SDK for Java attempts to retry failed transfers by marking the input stream before the start of a transfer and then resetting it before retrying\.

If the stream doesn’t support mark and reset, the SDK throws a [ResetException](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/ResetException.html) when there are transient failures and retries are enabled\.

 **Best Practice** 

We recommend that you use streams that support mark and reset operations\.

The most reliable way to avoid a [ResetException](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/ResetException.html) is to provide data by using a [File](https://docs.oracle.com/javase/8/docs/api/index.html?java/io/File.html) or [FileInputStream](https://docs.oracle.com/javase/8/docs/api/index.html?java/io/FileInputStream.html), which the AWS SDK for Java can handle without being constrained by mark and reset limits\.

If the stream isn’t a [FileInputStream](https://docs.oracle.com/javase/8/docs/api/index.html?java/io/FileInputStream.html) but does support mark and reset, you can set the mark limit by using the `setReadLimit` method of [RequestClientOptions](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/RequestClientOptions.html)\. Its default value is 128 KB\. Setting the read limit value to *one byte greater than the size of stream* will reliably avoid a [ResetException](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/ResetException.html)\.

For example, if the maximum expected size of a stream is 100,000 bytes, set the read limit to 100,001 \(100,000 \+ 1\) bytes\. The mark and reset will always work for 100,000 bytes or less\. Be aware that this might cause some streams to buffer that number of bytes into memory\.