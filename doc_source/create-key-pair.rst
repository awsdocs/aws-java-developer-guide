.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

#################
Create a Key Pair
#################

You must specify a key pair when you launch an EC2 instance and then specify the private key of the
key pair when you connect to the instance. You can create a key pair or use an existing key pair
that you've used when launching other instances. For more information, see :ec2-ug:`Amazon EC2 Key
Pairs <ec2-key-pairs>` in the |EC2-ug|.

.. topic:: To create a key pair and save the private key

    #.  Create and initialize a :aws-java-class:`CreateKeyPairRequest
        <services/ec2/model/CreateKeyPairRequest>` instance. Use the :aws-java-ref:`withKeyName
        <services/ec2/model/CreateKeyPairRequest.html#withKeyName-java.lang.String->`
        method to set the key pair name, as follows:

        .. code-block:: java

            CreateKeyPairRequest createKeyPairRequest = new CreateKeyPairRequest();

            createKeyPairRequest.withKeyName(keyName);

        .. important:: Key pair names must be unique. If you attempt to create a key pair with the
           same key name as an existing key pair, you'll get an exception.

    #.  Pass the request object to the :aws-java-ref:`createKeyPair
        <services/ec2/AmazonEC2.html#createKeyPair-com.amazonaws.services.ec2.model.CreateKeyPairRequest-->`
        method. The method returns a :aws-java-class:`CreateKeyPairResult
        <services/ec2/model/CreateKeyPairResult>` instance, as follows:

        .. code-block:: java

            CreateKeyPairResult createKeyPairResult =
              amazonEC2Client.createKeyPair(createKeyPairRequest);

    #.  Call the result object's :aws-java-ref:`getKeyPair
        <services/ec2/model/CreateKeyPairResult.html#getKeyPair-->` method to obtain a
        :aws-java-class:`KeyPair <services/ec2/model/KeyPair>` object. Call the :code:`KeyPair`
        object's :aws-java-ref:`getKeyMaterial <services/ec2/model/KeyPair.html#getKeyMaterial-->`
        method to obtain the unencrypted PEM-encoded private key, as follows:

        .. code-block:: java

            KeyPair keyPair = new KeyPair();

            keyPair = createKeyPairResult.getKeyPair();

            String privateKey = keyPair.getKeyMaterial();

