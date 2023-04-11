# AWS Region Selection<a name="java-dg-region-selection"></a>

Regions enable you to access AWS services that physically reside in a specific geographic area\. This can be useful both for redundancy and to keep your data and applications running close to where you and your users will access them\.

## Checking for Service Availability in a Region<a name="region-selection-query-service"></a>

To see if a particular AWS service is available in a region, use the `isServiceSupported` method on the region that you’d like to use\.

```
Region.getRegion(Regions.US_WEST_2)
    .isServiceSupported(AmazonDynamoDB.ENDPOINT_PREFIX);
```

See the [Regions](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/regions/Regions.html) class documentation for the regions you can specify, and use the endpoint prefix of the service to query\. Each service’s endpoint prefix is defined in the service interface\. For example, the DynamoDB endpoint prefix is defined in [AmazonDynamoDB](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/dynamodbv2/AmazonDynamoDB.html)\.

## Choosing a Region<a name="region-selection-choose-region"></a>

Beginning with version 1\.4 of the AWS SDK for Java, you can specify a region name and the SDK will automatically choose an appropriate endpoint for you\. To choose the endpoint yourself, see [Choosing a Specific Endpoint](#region-selection-choose-endpoint)\.

To explicitly set a region, we recommend that you use the [Regions](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/regions/Regions.html) enum\. This is an enumeration of all publicly available regions\. To create a client with a region from the enum, use the following code\.

```
AmazonEC2 ec2 = AmazonEC2ClientBuilder.standard()
                    .withRegion(Regions.US_WEST_2)
                    .build();
```

If the region you are attempting to use isn’t in the `Regions` enum, you can set the region using a *string* that represents the name of the region\.

```
AmazonEC2 ec2 = AmazonEC2ClientBuilder.standard()
                    .withRegion("{region_api_default}")
                    .build();
```

**Note**  
After you build a client with the builder, it’s *immutable* and the region *cannot be changed*\. If you are working with multiple AWS Regions for the same service, you should create multiple clients—​one per region\.

## Choosing a Specific Endpoint<a name="region-selection-choose-endpoint"></a>

Each AWS client can be configured to use a *specific endpoint* within a region by calling the `withEndpointConfiguration` method when creating the client\.

For example, to configure the Amazon S3 client to use the Europe \(Ireland\) Region, use the following code\.

```
AmazonS3 s3 = AmazonS3ClientBuilder.standard()
     .withEndpointConfiguration(new EndpointConfiguration(
          "https://s3.eu-west-1.amazonaws.com",
          "eu-west-1"))
     .withCredentials(CREDENTIALS_PROVIDER)
     .build();
```

See [Regions and Endpoints](https://docs.aws.amazon.com/general/latest/gr/rande.html) for the current list of regions and their corresponding endpoints for all AWS services\.

## Automatically Determine the Region from the Environment<a name="automatically-determine-the-aws-region-from-the-environment"></a>

**Important**  
This section applies only when using a [client builder](creating-clients.md) to access AWS services\. AWS clients created by using the client constructor will not automatically determine region from the environment and will, instead, use the *default* SDK region \(USEast1\)\.

When running on Amazon EC2 or Lambda, you might want to configure clients to use the same region that your code is running on\. This decouples your code from the environment it’s running in and makes it easier to deploy your application to multiple regions for lower latency or redundancy\.

 *You must use client builders to have the SDK automatically detect the region your code is running in\.* 

To use the default credential/region provider chain to determine the region from the environment, use the client builder’s `defaultClient` method\.

```
AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();
```

This is the same as using `standard` followed by `build`\.

```
AmazonEC2 ec2 = AmazonEC2ClientBuilder.standard()
                    .build();
```

If you don’t explicitly set a region using the `withRegion` methods, the SDK consults the default region provider chain to try and determine the region to use\.

### Default Region Provider Chain<a name="default-region-provider-chain"></a>

 **The following is the region lookup process:** 

1. Any explicit region set by using `withRegion` or `setRegion` on the builder itself takes precedence over anything else\.

1. The `AWS_REGION` environment variable is checked\. If it’s set, that region is used to configure the client\.
**Note**  
This environment variable is set by the Lambda container\.

1. The SDK checks the AWS shared configuration file \(usually located at `~/.aws/config`\)\. If the *region* property is present, the SDK uses it\.
   + The `AWS_CONFIG_FILE` environment variable can be used to customize the location of the shared config file\.
   + The `AWS_PROFILE` environment variable or the `aws.profile` system property can be used to customize the profile that is loaded by the SDK\.

1. The SDK attempts to use the Amazon EC2 instance metadata service to determine the region of the currently running Amazon EC2 instance\.

1. If the SDK still hasn’t found a region by this point, client creation fails with an exception\.

When developing AWS applications, a common approach is to use the *shared configuration file* \(described in [Using the Default Credential Provider Chain](credentials.md#credentials-default)\) to set the region for local development, and rely on the default region provider chain to determine the region when running on AWS infrastructure\. This greatly simplifies client creation and keeps your application portable\.