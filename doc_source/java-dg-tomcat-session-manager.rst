.. Copyright 2010-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

######################################
Manage Tomcat Session State with |DDB|
######################################

Tomcat applications often store session-state data in memory. However, this approach doesn't scale
well; once the application grows beyond a single web server, the session state must be shared
between servers. A common solution is to set up a dedicated session-state server with MySQL. This
approach also has drawbacks: you must administer another server, the session-state server is a
single pointer of failure, and the MySQL server itself can cause performance problems.

DynamoDB, a NoSQL database store from Amazon Web Services (AWS), avoids these drawbacks by providing
an effective solution for sharing session state across web servers.

.. _java-dg-tomcat-sess-download:

Downloading the Session Manager
===============================

You can download the session manager from the `aws/aws-dynamodb-session-tomcat
<http://github.com/aws/aws-dynamodb-session-tomcat>`_ project on GitHub. That project also hosts
the session manager source code if you want to contribute to the project by sending us pull requests
or opening issues.


.. _java-dg-tomcat-sess-config-provider:

Configure the Session-State Provider
====================================

To use the |DDB| session-state provider, you need to 1) configure the Tomcat server to use the
provider, and 2) set the security credentials of the provider so that it can access AWS.

.. _java-dg-tomcat-sess-config-for-ddb:

Configuring a Tomcat Server to Use |DDB| as the Session-State Server
--------------------------------------------------------------------

Copy :file:`AmazonDynamoDBSessionManagerForTomcat-1.x.x.jar` to the :code:`lib` directory of your
Tomcat installation. :file:`AmazonDynamoDBSessionManagerForTomcat-1.x.x.jar` is a complete,
standalone jar, containing all the code and dependencies to run the |DDB| Tomcat Session Manager.

Edit your server's :file:`context.xml` file to specify
:emphasis:`com.amazonaws.services.dynamodb.sessionmanager.DynamoDBSessionManager` as your session
manager.

.. code-block:: java

    <?xml version="1.0" encoding="UTF-8"?>
      <Context>
          <WatchedResource>WEB-INF/web.xml</WatchedResource>
          <Manager className="com.amazonaws.services.dynamodb.sessionmanager.DynamoDBSessionManager"
                   createIfNotExist="true" />
      </Context>


.. _java-dg-tomcat-sess-config-creds:

Configuring Your AWS Security Credentials
-----------------------------------------

You can specify AWS security credentials for the session manager in multiple ways, and they are
loaded in the following order of precedence:

1.  The :code:`AwsAccessKey` and :code:`AwsSecretKey` attributes of the :code:`Manager` element
    explicitly provide credentials.

2.  The :code:`AwsCredentialsFile` attribute on the :code:`Manager` element specifies a properties
    file from which to load credentials.

If no credentials are specified through the :code:`Manager` element,
:code:`DefaultAWSCredentialsProviderChain` will keep searching for credentials in the following
order:

1.  Environment Variables |ndash| :code:`AWS_ACCESS_KEY_ID` and :code:`AWS_SECRET_ACCESS_KEY`

2.  Java System Properties |ndash| :code:`aws.accessKeyId` and :code:`aws.secretKey`

3.  Instance profile credentials delivered through the |EC2| instance metadata service (IMDS).


.. _java-dg-tomcat-sess-config-elb:

Configuring with |EB|
---------------------

If you're using the session manager in |EB|, you need to ensure your project has a
:file:`.ebextensions` directory at the top level of your output artifact structure. In that
directory, place the following files:

*   The :file:`AmazonDynamoDBSessionManagerForTomcat-1.x.x.jar` file.

*   A :file:`context.xml` file as described previously to configure the session manager.

*   A config file that copies the jar into Tomcat's lib directory and applies the overridden
    :file:`context.xml` file.

For more information about customizing |EB| environments, see :eb-dg:`AWS Elastic Beanstalk
Environment Configuration <customize-containers>` in the |EB-dg|.

If you deploy to |EB| with the |tke|, then the session manager is set up for you if you go through
the :guilabel:`New AWS Java Web Project` wizard and select |DDB| for session management. The |tke|
configures all the needed files, and puts them in the :file:`.ebextensions` directory inside the
:file:`WebContent` directory of your project. If you have problems finding this directory, make sure
you aren't hiding files that begin with a period.



.. _java-dg-tomcat-sess-manage-with-ddb:

Manage Tomcat Session State with |DDB|
======================================

If the Tomcat server is running on an |EC2| instance that is configured to use |IAM| roles for EC2
Instances, you do not need to specify any credentials in the :file:`context.xml` file; in this case,
the |sdk-java| uses |IAM| roles credentials obtained through the instance metadata service (IMDS).

When your application starts, it looks for an |DDB| table called, by default,
:guilabel:`Tomcat_SessionState`. The table should have a string hash key named "sessionId"
(case-sensitive), no range key, and the desired values for :code:`ReadCapacityUnits` and
:code:`WriteCapacityUnits`.

We recommend that you create this table before first running your application. If you don't create
the table, however, the extension creates it during initialization. See the :file:`context.xml`
options in the next section for a list of attributes that configure how the session-state table is
created when it doesn't exist.

.. tip:: For information about working with |DDB| tables and provisioned throughput, see the
   |DDB-dg|_.

Once the application is configured and the table is created, you can use sessions with any other
session provider.


.. _java-dg-tomcat-sess-option:

Options Specified in context.xml
================================

Below are the configuration attributes that you can use in the :code:`Manager` element of your
:file:`context.xml` file:

*   :emphasis:`AwsAccessKey` |ndash| Access key ID to use.

*   :emphasis:`AwsSecretKey` |ndash| Secret key to use.

*   :emphasis:`AwsCredentialsFile` |ndash| A properties file containing :code:`accessKey` and
    :code:`secretKey` properties with your AWS security credentials.

*   :emphasis:`Table` |ndash| Optional string attribute. The name of the table used to store session
    data. The default is :guilabel:`Tomcat_SessionState`.

*   :emphasis:`RegionId` |ndash| Optional string attribute. The AWS region in which to use |DDB|.
    For a list of available AWS regions, see Regions and Endpoints in the :emphasis:`Amazon Web
    Services General Reference`.

*   :emphasis:`Endpoint` |ndash| Optional string attribute; if present, this option overrides any
    value set for the :code:`Region` option. The regional endpoint of the |DDB| service to use. For
    a list of available AWS regions, see Regions and Endpoints in the :emphasis:`Amazon Web Services
    General Reference`.

*   :emphasis:`ReadCapacityUnits` |ndash| Optional int attribute. The read capacity units to use if
    the session manager creates the table. The default is 10.

*   :emphasis:`WriteCapacityUnits` |ndash| Optional int attribute. The write capacity units to use
    if the session manager creates the table. The default is 5.

*   :emphasis:`CreateIfNotExist` |ndash| Optional Boolean attribute. The :code:`CreateIfNotExist`
    attribute controls whether the session manager autocreates the table if it doesn't exist. The
    default is true. If this flag is set to false and the table doesn't exist, an exception is
    thrown during Tomcat startup.


.. _java-dg-tomcat-sess-troubleshooting:

Troubleshooting
===============

If you encounter issues with the session manager, the first place to look is in
:file:`catalina.out`. If you have access to the Tomcat installation, you can go directly to this log
file and look for any error messages from the session manager. If you're using |EB|, you can view
the environment logs with the |console| or the |tke|.


.. _java-dg-tomcat-limits:

Limitations
===========

The session manager does not support session locking. Therefore, applications that use many
concurrent AJAX calls to manipulate session data may not be appropriate for use with the session
manager, due to race conditions on session data writes and saves back to the data store.


