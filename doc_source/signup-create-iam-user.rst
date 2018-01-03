.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

.. includes that start with 'common/' come from the awsdocs shared content, at
   https://github.com/awsdocs/aws-doc-shared-content

######################################
Sign Up for AWS and Create an IAM User
######################################

To use the |sdk-java| to access |AWSlong| (AWS), you will need an AWS account and AWS credentials.
To increase the security of your AWS account, we recommend that you use an *IAM user* to provide
access credentials instead of using your root account credentials.

.. tip:: For an overview of IAM users and why they are important for the security of your account,
   see :iam-ug:`Overview of Identity Management: Users <introduction_identity-management>` in the
   |IAM-ug|.

.. include:: common/procedure-sign-up-for-aws.txt

Next, create an IAM user and download (or copy) its secret access key.

.. include:: common/procedure-create-iam-user.txt

Next, you should :doc:`set your credentials <setup-credentials>` in the AWS shared credentials file
or in the environment.

.. tip:: If you use the Eclipse IDE, you should consider installing the |tke|_ and providing your
   credentials as described in :tke-ug:`Set up AWS Credentials <setup-credentials>` in the |tke-ug|.

