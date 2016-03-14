.. Copyright 2010-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

######################################
Sign Up for AWS and Create an IAM User
######################################

To use the |sdk-java| to access |AWSlong| (AWS), you will need an AWS account and AWS credentials.
To increase the security of your AWS account, we recommend that you use an *IAM user* to provide
access credentials instead of using your root account credentials.

.. tip:: For an overview of IAM users and why they are important for the security of your account,
   see `Overview of Identity Management: Users
   <http://docs.aws.amazon.com/IAM/latest/UserGuide/introduction_identity-management.html>`_ in the
   |IAM-ug|.

**To sign up for AWS**

1. Open http://aws.amazon.com/ and click :guilabel:`Sign Up`.

2. Follow the on-screen instructions. Part of the sign-up procedure involves receiving a phone call
   and entering a PIN using your phone keypad.

Next, create an IAM user and download (or copy) its secret access key.

**To create an IAM user**

#.  Go to the :console:`IAM console <iam>` (you may need to sign in to AWS first).

#.  Click :guilabel:`Users` in the sidebar to view your IAM users.

#.  If you don't have any IAM users set up, click :guilabel:`Create New Users` to create one.

#.  Select the IAM user in the list that you'll use to access AWS.

#.  Open the :guilabel:`Security Credentials` tab, and click :guilabel:`Create Access Key`.

    .. note:: You can have a maximum of two active access keys for any given IAM user. If your IAM
        user has two access keys already, then you'll need to delete one of them before creating a
        new key.

#.  On the resulting dialog, click the :guilabel:`Download Credentials` button to download the
    credential file to your computer, or click :guilabel:`Show User Security Credentials` to view
    the IAM user's access key ID and secret access key (which you can copy and paste).

    .. important:: There is no way to obtain the secret access key once you close the dialog. You
        can, however, delete its associated access key ID and create a new one.

Next, you should :doc:`set your credentials <set-up-creds>` in the AWS shared credentials file or in
the environment.

.. tip:: If you use the Eclipse IDE, you should consider installing the |tke|_ and providing your
   credentials as described in `Working with AWS Access Credentials
   <http://docs.aws.amazon.com/AWSToolkitEclipse/latest/ug/tke_setup_creds.html>`_ in the |tke-ug|.



