.. Copyright 2010-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

#######################################################
Set Up your AWS Credentials for Use with the |sdk-java|
#######################################################

To connect to any of the supported services with the |sdk-java|, you must provide your AWS
credentials. The AWS SDKs and CLIs use :emphasis:`provider chains` to look for AWS credentials in a
number of different places, including system or user environment variables and local AWS
configuration files.

Setting your credentials for use by the |sdk-java| can be done in a number of ways, but here are the
recommended approaches:

.. the following file is in the shared content...

.. include:: sdk-shared-credentials.txt

* To set credentials for an EC2 instance, you should specify an IAM role and then give your EC2
  instance access to that role as shown in :doc:`java-dg-roles`.

Once you have set your AWS credentials using one of these methods, they can be loaded automatically
by the |sdk-java| by using the default credential provider chain. For complete information about how
to load and use AWS credentials in your |sdk-java| applications, see :doc:`credentials`.

.. note:: You can override the default location of the AWS credential file by setting the
   |aws-credfile-env-var| environment variable. For more information, see :doc:`credentials`.

