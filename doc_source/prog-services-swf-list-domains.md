--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Listing Domains<a name="prog-services-swf-list-domains"></a>

You can list the [Amazon SWF](http://aws.amazon.com/swf/) domains associated with your account and AWS region by registration type\.

1. Create a [ListDomainsRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/ListDomainsRequest.html) object, and specify the registration status of the domains that you’re interested in—​this is required\.

1. Call [AmazonSimpleWorkflowClient\.listDomains](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/AmazonSimpleWorkflowClient.html#listDomains-com.amazonaws.services.simpleworkflow.model.ListDomainsRequest-) with the *ListDomainRequest* object\. Results are provided in a [DomainInfos](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/DomainInfos.html) object\.

1. Call [getDomainInfos](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/DomainInfos.html#getDomainInfos--) on the returned object to get a list of [DomainInfo](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/DomainInfo.html) objects\.

1. Call [getName](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/DomainInfo.html#getName--) on each *DomainInfo* object to get its name\.

The following code demonstrates this procedure:

```
public void list_swf_domains(AmazonSimpleWorkflowClient swf)
{
    ListDomainsRequest request = new ListDomainsRequest();
    request.setRegistrationStatus("REGISTERED");
    DomainInfos domains = swf.listDomains(request);
    System.out.println("Current Domains:");
    for (DomainInfo di : domains.getDomainInfos())
    {
        System.out.println(" * " + di.getName());
    }
}
```