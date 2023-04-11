# Registering Domains<a name="prog-services-swf-register-domain"></a>

Every workflow and activity in [Amazon SWF](http://aws.amazon.com/swf/) needs a *domain* to run in\.

1. Create a new [RegisterDomainRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/RegisterDomainRequest.html) object, providing it with at least the domain name and workflow execution retention period \(these parameters are both required\)\.

1. Call the [AmazonSimpleWorkflowClient\.registerDomain](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/AmazonSimpleWorkflowClient.html#registerDomain-com.amazonaws.services.simpleworkflow.model.RegisterDomainRequest-) method with the *RegisterDomainRequest* object\.

1. Catch the [DomainAlreadyExistsException](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/simpleworkflow/model/DomainAlreadyExistsException.html) if the domain you’re requesting already exists \(in which case, no action is usually required\)\.

The following code demonstrates this procedure:

```
public void register_swf_domain(AmazonSimpleWorkflowClient swf, String name)
{
    RegisterDomainRequest request = new RegisterDomainRequest().withName(name);
    request.setWorkflowExecutionRetentionPeriodInDays("10");
    try
    {
        swf.registerDomain(request);
    }
    catch (DomainAlreadyExistsException e)
    {
        System.out.println("Domain already exists!");
    }
}
```