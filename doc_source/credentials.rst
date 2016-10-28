.. Copyright 2010-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

############################
Working with AWS Credentials
############################

To make requests to Amazon Web Services, you will need to supply AWS credentials to the |sdk-java|.
There are a number of ways to do this:

* Use the default credential provider chain :emphasis:`(recommended)`

* Use a specific credential provider or provider chain (or create your own).

* Supply the credentials yourself. These can be either root account credentials, |IAM| credentials
  or temporary credentials retrieved from |STS|.

.. important:: It is *strongly recommended*, from a security standpoint, that you *use IAM users*
   instead of the root account for AWS access. For more information, see `IAM Best Practices
   <http://docs.aws.amazon.com/IAM/latest/UserGuide/IAMBestPractices.html>`_ in |iam-ug|.

This topic provides information about how to load credentials for AWS using the |sdk-java|.

.. _credentials-default:

Using the Default Credential Provider Chain
===========================================

When you initialize a new service client without supplying any arguments, the |sdk-java| will
attempt to find AWS credentials using the :emphasis:`default credential provider chain` implemented
by the :java-api:`DefaultAWSCredentialsProviderChain <auth/DefaultAWSCredentialsProviderChain>`
class. The default credential provider chain looks for credentials in this order:

1.  **Environment Variables** |ndash| :envvar:`AWS_ACCESS_KEY_ID` and
    :envvar:`AWS_SECRET_ACCESS_KEY`. The |sdk-java| uses the
    :java-api:`EnvironmentVariableCredentialsProvider <auth/EnvironmentVariableCredentialsProvider>`
    class to load these credentials.

2.  **Java System Properties** |ndash| :code:`aws.accessKeyId` and :code:`aws.secretKey`.  The
    |sdk-java| uses the :java-api:`SystemPropertiesCredentialsProvider
    <auth/SystemPropertiesCredentialsProvider>` to load these credentials.

3.  **The default credential profiles file** |ndash| typically located at
    :file:`~/.aws/credentials` (this location may vary per platform), this credentials file is
    shared by many of the AWS SDKs and by the AWS CLI. The |sdk-java| uses the
    :java-api:`ProfileCredentialsProvider <auth/profile/ProfileCredentialsProvider>` to load these
    credentials.

    You can create a credentials file by using the :code:`aws configure` command provided by the AWS
    CLI, or you can create it by hand-editing the file with a text editor. For information about the
    credentials file format, see :ref:`credentials-file-format`.

4.  **Instance profile credentials** |ndash| these credentials can be used on EC2 instances, and are
    delivered through the Amazon EC2 metadata service. The |sdk-java| uses the
    :java-api:`InstanceProfileCredentialsProvider <auth/InstanceProfileCredentialsProvider>` to load
    these credentials.


Setting Credentials
-------------------

AWS credentials must be set in :emphasis:`at least one` of the preceding locations in order to be
used. For information about setting credentials, visit one of the following topics:

*   For information about specifying credentials in the :emphasis:`environment` or in the default
    :emphasis:`credential profiles file`, see :doc:`setup-credentials`.

*   For information about setting Java :emphasis:`system properties`, see the `System Properties
    <http://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html>`_ tutorial on the
    official :title:`Java Tutorials` website.

*   For information about how to set up and use :emphasis:`instance profile credentials` for use
    with your EC2 instances, see :doc:`java-dg-roles`.

Setting an Alternate Credentials Profile
----------------------------------------
The SDK for Java will use the `default` profile by default but there are a couple of ways to customize
which profile is sourced from the credentials file.

The AWS Profile environment variable can be used to change the profile loaded by the SDK.

For example, on |unixes| you would run the following command to change the profile to `myProfile`

.. code-block:: sh

    export AWS_PROFILE="myProfile"

On Windows you would use the following:

.. code-block:: bat

    set AWS_PROFILE="myProfile"

Setting the AWS_PROFILE environment variable will affect credential loading for all other officially
supported AWS SDKs and Tools (including the AWS CLI and the AWS CLI for PowerShell). If you want
to only change the profile for a Java application, you can use the system property `aws.profile` instead.
Please note that the environment variable takes precedence over the system property.

Setting an Alternate Credentials File Location
----------------------------------------------

Although the SDK for Java will load AWS credentials automatically from the default credentials file
location, you can also specify the location yourself by setting the |aws-credfile-var| environment
variable with the full pathname to the credentials file.

This feature can be used to temporarily change the location where the SDK for Java looks for your
credentials file (by setting this variable with the command-line, for example), or you can set the
environment variable in your user or system environment to change it for the user or system-wide.

.. include:: common/procedure-override-shared-credfile-location.txt


.. _credentials-file-format:

AWS Credentials File Format
---------------------------

When you create an AWS credentials file using the :code:`aws configure` command, it creates a file
with the following format:

.. code-block:: ini

    [default]
    aws_access_key_id={YOUR_ACCESS_KEY_ID}
    aws_secret_access_key={YOUR_SECRET_ACCESS_KEY}

    [profile2]
    aws_access_key_id={YOUR_ACCESS_KEY_ID}
    aws_secret_access_key={YOUR_SECRET_ACCESS_KEY}

The profile name is specified in square brackets (For example: :code:`[default]`), followed by the
configurable fields in that profile as key/value pairs. You can have multiple profiles in your
credentials file, which can be added or edited using :samp:`aws configure --profile {PROFILE_NAME}`
to select the profile to configure.

You can specify additional fields, such as :code:`aws_session_token`,
:code:`metadata_service_timeout` and :code:`metadata_service_num_attempts`. These are not
configurable with the CLI |mdash| you must edit the file by hand if you wish to use them. For more
information about the configuration file and its available fields, see :cli-ug:`Configuring the AWS
Command Line Interface <cli-chap-getting-started>` in the |cli-ug|.


Loading Credentials
-------------------

Once credentials have been set, you can load them using the default credential provider
chain.

:emphasis:`To load credentials using the default credential provider chain`

*   Instantiate an AWS Service client without explicitly providing credentials to the builder. For example:

    .. code-block:: java

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                                .withRegion(Regions.US_WEST_2)
                                .build();



.. _credentials-specify-provider:

Specifying a Credential Provider or Provider Chain
==================================================

If you want to specify a different credential provider than the :emphasis:`default` credential
provider chain, you can specify it via the client builder.

:emphasis:`To specify a specific credentials provider`

*   Provide an instance of a credentials provider or provider chain to a client builder
    that takes an :java-api:`AWSCredentialsProvider <auth/AWSCredentialsProvider>` interface as input.
    For example, to use :emphasis:`environment` credentials specifically:

    .. code-block:: java

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                                .withCredentials(new EnvironmentVariableCredentialsProvider())
                                .build();

For the full list of |sdk-java|-supplied credential providers and provider chains, see the list of
"All known implementing classes" in the reference topic for :java-api:`AWSCredentialsProvider
<auth/AWSCredentialsProvider>`.

.. tip:: You can use this technique to supply credential providers or provider chains that you
   create, by implementing your own credential provider that implements the
   :emphasis:`AWSCredentialsProvider` interface, or by sub-classing the
   :java-api:`AWSCredentialsProviderChain <auth/AWSCredentialsProviderChain>` class.


.. _credentials-explicit:

Explicitly Specifying Credentials
=================================

If neither the default credential chain or a specific or custom provider or provider chain works for
your code, you can set credentials explicitly by supplying them yourself. If you have retrieved
temporary credentials using |STS|, use this method to specify the credentials for AWS access.


.. topic:: To explicitly supply credentials to an AWS client:

    #. Instantiate a class that provides the :java-api:`AWSCredentials <auth/AWSCredentials>`
       interface, such as :java-api:`BasicAWSCredentials <auth/BasicAWSCredentials>`, supplying it
       with the AWS access key and secret key you will use for the connection.

    #. Create a :java-api:`AWSStaticCredentialsProvider <auth/AWSStaticCredentialsProvider>` with
       the AWSCredentials object.

    #. Configure the client builder with the AWSStaticCredentialsProvider and build the client.


For example:

.. code-block:: java

    BasicAWSCredentials awsCreds = new BasicAWSCredentials("access_key_id", "secret_key_id");
    AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                            .build();

When using :doc:`temporary credentials obtained from STS <prog-services-sts>`, create a
:java-api:`BasicSessionCredentials <auth/BasicSessionCredentials>` object, passing it the
STS-supplied credentials and session token:

.. literalinclude:: snippets/sts_basic_session_creds.java
    :language: java
    :lines: 14-


See Also
========

*   :doc:`signup-create-iam-user`
*   :doc:`setup-credentials`
*   :doc:`java-dg-roles`

