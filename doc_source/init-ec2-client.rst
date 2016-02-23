.. Copyright 2010-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

######################
Create an |EC2| Client
######################

Create an :emphasis:`Amazon EC2 client` in order to manage your EC2 resources, such as instances and
security groups.

**To create and initialize an Amazon EC2 client**

#.  Create and initialize an :java-api:`AWSCredentials <auth/AWSCredentials>` instance.  Specify the
    :file:`AwsCredentials.properties` file you created, as follows:

    .. code-block:: java

        AWSCredentials credentials = new PropertiesCredentials(
                 AwsConsoleApp.class.getResourceAsStream("AwsCredentials.properties"));

#.  Use the :code:`AWSCredentials` object to create a new :java-api:`AmazonEC2Client
    <services/ec2/AmazonEC2Client>` instance, as follows:

    .. code-block:: java

        amazonEC2Client = new AmazonEC2Client(credentials);

#.  By default, the service endpoint is :code:`ec2.us-east-1.amazonaws.com`. To specify a different
    endpoint, use the :java-ref-nf:`setEndpoint
    <com/amazonaws/AmazonWebServiceClient.html#setEndpoint%28java.lang.String%29>` method. For
    example:

    .. code-block:: java

        amazonEC2Client.setEndpoint("ec2.us-west-2.amazonaws.com");

    For more information, see |regions-and-endpoints|_.

    The |sdk-java| uses |region-api-default| as the default region. However, the |console| uses
    |region-console-default| as its default region. Therefore, when using the |console| in
    conjunction with the |sdk-java|, be sure to use the same region in both your code and the
    console.

