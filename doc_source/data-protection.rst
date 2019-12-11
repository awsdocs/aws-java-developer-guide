.. Copyright 2010-2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

#####################################
Data Protection in |SERVICENAMETITLE|
#####################################

.. meta::
   :description: Learn how the AWS shared responsibility model applies to data protection in this AWS product or service.
   :keywords:

.. include:: common/_security-includes.txt

|SERVICENAMESENTENCECASE| conforms to the `shared responsibility model <https://aws.amazon.com/compliance/shared-responsibility-model>`_, 
which includes regulations and guidelines for data protection. |AWSlong| (|AWS|) is responsible for protecting the global infrastructure 
that runs all the |AWS| services. |AWS| maintains control over data hosted on this infrastructure, including the security configuration 
controls for handling customer content and personal data. |AWS| customers and APN partners, acting either as data controllers or data 
processors, are responsible for any personal data that they put in the |AWS| Cloud.

For data protection purposes, we recommend that you protect |AWS| account credentials and set up individual user accounts with 
|IAMlong| (|IAM|), so that each user is given only the permissions necessary to fulfill their job duties. We also recommend that 
you secure your data in the following ways:

* Use multi-factor authentication (MFA) with each account.
* Use SSL/TLS to communicate with |AWS| resources.
* Set up API and user activity logging with |CTlong|.
* Use |AWS| encryption solutions, along with all default security controls within |AWS| services.
* Use advanced managed security services such as |MCElong|, which assists in discovering and securing personal data that 
  is stored in |S3|.

We strongly recommend that you never put sensitive identifying information, such as your customers' account numbers, into 
free-form fields such as a **Name** field. This includes when you work with |SERVICENAME| or other |AWS| services
using the console, API, |CLI|, or |AWS| SDKs. Any data that you enter into |SERVICENAME| or other services might get picked up 
for inclusion in diagnostic logs. When you provide a URL to an external server, don't include credentials information in the URL 
to validate your request to that server.

For more information about data protection, see the 
`AWS Shared Responsibility Model and GDPR <https://aws.amazon.com/blogs/security/the-aws-shared-responsibility-model-and-gdpr/>`_ 
blog post on the *AWS Security Blog*.