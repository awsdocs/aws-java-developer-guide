.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

############################
Working with AWS Credentials
############################

.. meta::
   :description: How to load credentials for AWS using the AWS SDK for Java.
   :keywords:

To make requests to |AWSlong|, you must supply AWS credentials to the |sdk-java|.
You can do this in the following ways:

* Use the default credential provider chain :emphasis:`(recommended)`.

* Use a specific credential provider or provider chain (or create your own).

* Supply the credentials yourself. These can be root account credentials, |IAM| credentials,
  or temporary credentials retrieved from |STS|.

.. important:: For security, we *strongly recommend* that you *use IAM
   users* instead of the root account for AWS access. For more information, see :iam-ug:`IAM Best Practices
   <best-practices>` in the |iam-ug|.

.. _credentials-default:

Using the Default Credential Provider Chain
===========================================

When you initialize a new service client without supplying any arguments, the |sdk-java|
attempts to find AWS credentials by using the :emphasis:`default credential provider chain` implemented
by the :aws-java-class:`DefaultAWSCredentialsProviderChain <auth/DefaultAWSCredentialsProviderChain>`
class. The default credential provider chain looks for credentials in this order:

#. **Environment variables** |ndash| :envvar:`AWS_ACCESS_KEY_ID` and :envvar:`AWS_SECRET_ACCESS_KEY`.
   The |sdk-java| uses the :aws-java-class:`EnvironmentVariableCredentialsProvider <auth/EnvironmentVariableCredentialsProvider>`
   class to load these credentials.

#. **Java system properties** |ndash| :code:`aws.accessKeyId` and :code:`aws.secretKey`.
   The |sdk-java| uses the :aws-java-class:`SystemPropertiesCredentialsProvider <auth/SystemPropertiesCredentialsProvider>`
   to load these credentials.

#. **Web Identity Token credentials** from the environment or container.

#. **The default credential profiles file** |ndash| typically located at :file:`~/.aws/credentials`
   (location can vary per platform), and shared by many of the AWS SDKs and by the AWS CLI. The
   |sdk-java| uses the :aws-java-class:`ProfileCredentialsProvider <auth/profile/ProfileCredentialsProvider>` to load these credentials.

   You can create a credentials file by using the :code:`aws configure` command provided by the AWS
   CLI, or you can create it by editing the file with a text editor. For information about the
   credentials file format, see :ref:`credentials-file-format`.

#. **Amazon ECS container credentials** |ndash| loaded from the |ECS| if the environment
   variable :envvar:`AWS_CONTAINER_CREDENTIALS_RELATIVE_URI` is set. The |sdk-java| uses the
   :aws-java-class:`ContainerCredentialsProvider <auth/ContainerCredentialsProvider>` to load these
   credentials. You can specify the IP address for this value.

#. **Instance profile credentials** |ndash| used on EC2 instances, and delivered through the |EC2|
   metadata service. The |sdk-java| uses the :aws-java-class:`InstanceProfileCredentialsProvider
   <auth/InstanceProfileCredentialsProvider>` to load these credentials. You can specify the IP address for this value.

   .. note:: Instance profile credentials are used only if
      :envvar:`AWS_CONTAINER_CREDENTIALS_RELATIVE_URI` is not set. See
      :aws-java-class:`EC2ContainerCredentialsProviderWrapper
      <auth/EC2ContainerCredentialsProviderWrapper>` for more information.

Setting Credentials
-------------------

To be able to use AWS credentials, they must be set in :emphasis:`at least one` of the
preceding locations. For information about setting credentials, see the following topics:

* To specify credentials in the :emphasis:`environment` or in the default
  :emphasis:`credential profiles file`, see :doc:`setup-credentials`.

* To set Java :emphasis:`system properties`, see the
  `System Properties <http://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html>`_
  tutorial on the official :title:`Java Tutorials` website.

* To set up and use :emphasis:`instance profile credentials` with
  your EC2 instances, see :doc:`java-dg-roles`.

Setting an Alternate Credentials Profile
----------------------------------------
The |sdk-java| uses the `default` profile by default, but there are ways to customize
which profile is sourced from the credentials file.

You can use the AWS Profile environment variable to change the profile loaded by the SDK.

For example, on |unixes| you would run the following command to change the profile to `myProfile`.

.. code-block:: sh

    export AWS_PROFILE="myProfile"

On Windows you would use the following.

.. code-block:: bat

    set AWS_PROFILE="myProfile"

Setting the AWS_PROFILE environment variable affects credential loading for all officially
supported AWS SDKs and Tools (including the AWS CLI and the AWS CLI for PowerShell).
To change only the profile for a Java application, you can use the system property `aws.profile` instead.

.. note:: The environment variable takes precedence over the system property.

Setting an Alternate Credentials File Location
----------------------------------------------

The |sdk-java| loads AWS credentials automatically from the default credentials file
location. However, you can also specify the location by setting the |aws-credfile-var| environment
variable with the full path to the credentials file.

You can use this feature to temporarily change the location where the |sdk-java| looks for
your credentials file (for example, by setting this variable with the command line). Or you
can set the environment variable in your user or system environment to change it for the user or systemwide.

.. include:: common/procedure-override-shared-credfile-location.txt


.. _credentials-file-format:

AWS Credentials File Format
---------------------------

When you use the :code:`aws configure` command to create an AWS credentials file, the command creates
a file with the following format.

.. code-block:: ini

    [default]
    aws_access_key_id={YOUR_ACCESS_KEY_ID}
    aws_secret_access_key={YOUR_SECRET_ACCESS_KEY}

    [profile2]
    aws_access_key_id={YOUR_ACCESS_KEY_ID}
    aws_secret_access_key={YOUR_SECRET_ACCESS_KEY}

The profile name is specified in square brackets (for example, :code:`[default]`), followed by the
configurable fields in that profile as key-value pairs. You can have multiple profiles in your
credentials file, which can be added or edited using :samp:`aws configure --profile {PROFILE_NAME}`
to select the profile to configure.

You can specify additional fields, such as :code:`aws_session_token`,
:code:`metadata_service_timeout`, and :code:`metadata_service_num_attempts`. These are not
configurable with the CLI |mdash| you must edit the file by hand if you want to use them. For more
information about the configuration file and its available fields, see :cli-ug:`Configuring the AWS
Command Line Interface <cli-chap-getting-started>` in the |cli-ug|.


Loading Credentials
-------------------

After you set credentials, you can load them by using the default credential provider
chain.

To do this, you instantiate an AWS Service client without explicitly providing credentials to the builder, as follows.

.. code-block:: java

    AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                           .withRegion(Regions.US_WEST_2)
                           .build();


.. _credentials-specify-provider:

Specifying a Credential Provider or Provider Chain
==================================================

You can specify a credential provider that is different from the :emphasis:`default` credential
provider chain by using the client builder.

You provide an instance of a credentials provider or provider chain to a client builder that
takes an :aws-java-class:`AWSCredentialsProvider <auth/AWSCredentialsProvider>` interface as input. The
following example shows how to use :emphasis:`environment` credentials specifically.


.. code-block:: java

    AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                           .withCredentials(new EnvironmentVariableCredentialsProvider())
                           .build();

For the full list of |sdk-java|-supplied credential providers and provider chains,
see **All Known Implementing Classes** in :aws-java-class:`AWSCredentialsProvider <auth/AWSCredentialsProvider>`.

.. tip:: You can use this technique to supply credential providers or provider chains that you
   create by using your own credential provider that implements the
   :code-java:`AWSCredentialsProvider` interface, or by subclassing the
   :aws-java-class:`AWSCredentialsProviderChain <auth/AWSCredentialsProviderChain>` class.


.. _credentials-explicit:

Explicitly Specifying Credentials
=================================

If the default credential chain or a specific or custom provider or provider chain doesn't work for
your code, you can set credentials that you supply explicitly. If you've retrieved temporary
credentials using |STS|, use this method to specify the credentials for AWS access.


.. topic:: To explicitly supply credentials to an AWS client

    #. Instantiate a class that provides the :aws-java-class:`AWSCredentials <auth/AWSCredentials>`
       interface, such as :aws-java-class:`BasicAWSCredentials <auth/BasicAWSCredentials>`, and supply
       it with the AWS access key and secret key you will use for the connection.

    #. Create an :aws-java-class:`AWSStaticCredentialsProvider <auth/AWSStaticCredentialsProvider>` with
       the :code-java:`AWSCredentials` object.

    #. Configure the client builder with the :code-java:`AWSStaticCredentialsProvider` and build the client.


The following is an example.

.. code-block:: java

    BasicAWSCredentials awsCreds = new BasicAWSCredentials("access_key_id", "secret_key_id");
    AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                            .build();

When using :doc:`temporary credentials obtained from STS <prog-services-sts>`, create a
:aws-java-class:`BasicSessionCredentials <auth/BasicSessionCredentials>` object, passing it the
STS-supplied credentials and session token.

.. literalinclude:: snippets/sts_basic_session_creds.java
    :language: java
    :lines: 14-


More Info
=========

* :doc:`signup-create-iam-user`
* :doc:`setup-credentials`
* :doc:`java-dg-roles`
