.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

#####################
Run an |EC2| Instance
#####################

Use the following procedure to launch one or more identically configured EC2 instances from the same
Amazon Machine Image (AMI). After you create your EC2 instances, you can check their status. After
your EC2 instances are running, you can connect to them.

.. topic:: To launch an |EC2| instance

   #. Create and initialize a :aws-java-class:`RunInstancesRequest
      <services/ec2/model/RunInstancesRequest>` instance. Make sure that the AMI, key pair, and
      security group that you specify exist in the region that you specified when you created the
      client object.

      .. code-block:: java

         RunInstancesRequest runInstancesRequest =
            new RunInstancesRequest();

         runInstancesRequest.withImageId("ami-a9d09ed1")
                            .withInstanceType(InstanceType.T1Micro)
                            .withMinCount(1)
                            .withMaxCount(1)
                            .withKeyName("my-key-pair")
                            .withSecurityGroups("my-security-group");

      :aws-java-ref:`withImageId <services/ec2/model/RunInstancesRequest.html#withImageId-java.lang.String->`
         The ID of the AMI. To learn how to find public AMIs provided by Amazon or
         create your own, see :ec2-ug:`Amazon Machine Image (AMI) <AMIs>`.

      :aws-java-ref:`withInstanceType <services/ec2/model/RunInstancesRequest.html#withInstanceType-java.lang.String->`
         An instance type that is compatible with the specified AMI. For more information, see
         :ec2-ug:`Instance Types <instance-types>` in the |EC2-ug|.

      :aws-java-ref:`withMinCount <services/ec2/model/RunInstancesRequest.html#withMinCount-java.lang.Integer->`
         The minimum number of EC2 instances to launch. If this is more instances than |EC2| can
         launch in the target Availability Zone, |EC2| launches no instances.

      :aws-java-ref:`withMaxCount <services/ec2/model/RunInstancesRequest.html#withMaxCount-java.lang.Integer->`
         The maximum number of EC2 instances to launch. If this is more instances than |EC2| can
         launch in the target Availability Zone, |EC2| launches the largest possible number of
         instances above :code:`MinCount`. You can launch between 1 and the maximum number of
         instances you're allowed for the instance type. For more information, see How many
         instances can I run in Amazon EC2 in the |EC2| General FAQ.

      :aws-java-ref:`withKeyName <services/ec2/model/RunInstancesRequest.html#withKeyName-java.lang.String->`
         The name of the EC2 key pair. If you launch an instance without specifying a key pair, you
         can't connect to it. For more information, see :doc:`create-key-pair`.

      :aws-java-ref:`withSecurityGroups <services/ec2/model/RunInstancesRequest.html#withSecurityGroups-java.util.Collection->`
         One or more security groups. For more information, see :doc:`create-security-group`.

   #. Launch the instances by passing the request object to the :aws-java-ref:`runInstances
      <services/ec2/AmazonEC2Client.html#runInstances-com.amazonaws.services.ec2.model.RunInstancesRequest->`
      method. The method returns a :aws-java-class:`RunInstancesResult
      <services/ec2/model/RunInstancesResult>` object, as follows:

      .. code-block:: java

         RunInstancesResult result = amazonEC2Client.runInstances(
                                       runInstancesRequest);

After your instance is running, you can connect to it using your key pair. For more information, see
:ec2-ug:`Connect to Your Linux Instance <AccessingInstances>`. in the |EC2-ug|.
