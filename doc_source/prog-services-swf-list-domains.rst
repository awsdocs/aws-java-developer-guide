.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

###############
Listing Domains
###############

You can list the |SWF|_ domains associated with your account and AWS region by registration type.

.. topic:: To list |SWF| domains

   #. Create a :aws-java-class:`ListDomainsRequest
      <services/simpleworkflow/model/ListDomainsRequest>` object, and specify the registration
      status of the domains that you're interested in |mdash| this is required.

   #. Call :aws-java-ref:`AmazonSimpleWorkflowClient.listDomains
      <services/simpleworkflow/AmazonSimpleWorkflowClient.html#listDomains-com.amazonaws.services.simpleworkflow.model.ListDomainsRequest->`
      with the :emphasis:`ListDomainRequest` object. Results are provided in a
      :aws-java-class:`DomainInfos <services/simpleworkflow/model/DomainInfos>` object.

   #. Call :aws-java-ref:`getDomainInfos
      <services/simpleworkflow/model/DomainInfos.html#getDomainInfos-->` on the returned object to
      get a list of :aws-java-class:`DomainInfo <services/simpleworkflow/model/DomainInfo>` objects.

   #. Call :aws-java-ref:`getName </services/simpleworkflow/model/DomainInfo.html#getName-->` on
      each :emphasis:`DomainInfo` object to get its name.

The following code demonstrates this procedure:

.. literalinclude:: snippets/CreateSwfDomain-list_swf_domains.java
   :language: java
   :lines: 14-

