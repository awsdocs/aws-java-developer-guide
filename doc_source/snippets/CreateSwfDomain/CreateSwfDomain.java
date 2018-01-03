/*
   Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This file is licensed under the Apache License, Version 2.0 (the "License").
   You may not use this file except in compliance with the License. A copy of
   the License is located at

    http://aws.amazon.com/apache2.0/

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
   CONDITIONS OF ANY KIND, either express or implied. See the License for the
   specific language governing permissions and limitations under the License.
*/

//
// Example of creating a domain in Amazon SWF using the AWS SDK for Java
//
import com.amazonaws.services.simpleworkflow.*;
import com.amazonaws.services.simpleworkflow.model.*;
import com.amazonaws.regions.*;

public class CreateSwfDomain
{
    /**
     * List the domains for the provided SWF client.
     * @param swf The SWF client that will be used to list domains.
     */
    public void listSwfDomains(AmazonSimpleWorkflow swf)
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

    /**
     * Register a domain. Does nothing if the domain already exists.
     * @param swf The SWF client that will be used to register the domain.
     * @param name The name of the domain to register.
     */
    public void RegisterSwfDomain(AmazonSimpleWorkflow swf, String name)
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

    /**
     * Run the app.
     */
    public static void main(String[] args)
    {
        CreateSwfDomain app = new CreateSwfDomain();

        AmazonSimpleWorkflow swf = AmazonSimpleWorkflowClientBuilder.standard()
                                        .withRegion(Regions.US_WEST_2)
                                        .build();

        // List the SWF domains in this region.
        app.listSwfDomains(swf);

        // Create a new domain (or retrieve the existing one);
        app.RegisterSwfDomain(swf, "ExampleDomain");
    }
}
