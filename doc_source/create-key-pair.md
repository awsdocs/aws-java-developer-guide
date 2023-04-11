# Create a Key Pair<a name="create-key-pair"></a>

You must specify a key pair when you launch an EC2 instance and then specify the private key of the key pair when you connect to the instance\. You can create a key pair or use an existing key pair that you’ve used when launching other instances\. For more information, see [Amazon EC2 Key Pairs](http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/ec2-key-pairs.html) in the Amazon EC2 User Guide for Linux Instances\.

1. Create and initialize a [CreateKeyPairRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/CreateKeyPairRequest.html) instance\. Use the [withKeyName](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/CreateKeyPairRequest.html#withKeyName-java.lang.String-) method to set the key pair name, as follows:

   ```
   CreateKeyPairRequest createKeyPairRequest = new CreateKeyPairRequest();
   
   createKeyPairRequest.withKeyName(keyName);
   ```
**Important**  
Key pair names must be unique\. If you attempt to create a key pair with the same key name as an existing key pair, you’ll get an exception\.

1. Pass the request object to the [createKeyPair](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/AmazonEC2.html#createKeyPair-com.amazonaws.services.ec2.model.CreateKeyPairRequest--) method\. The method returns a [CreateKeyPairResult](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/CreateKeyPairResult.html) instance, as follows:

   ```
   CreateKeyPairResult createKeyPairResult =
     amazonEC2Client.createKeyPair(createKeyPairRequest);
   ```

1. Call the result object’s [getKeyPair](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/CreateKeyPairResult.html#getKeyPair--) method to obtain a [KeyPair](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/KeyPair.html) object\. Call the `KeyPair` object’s [getKeyMaterial](http://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/ec2/model/KeyPair.html#getKeyMaterial--) method to obtain the unencrypted PEM\-encoded private key, as follows:

   ```
   KeyPair keyPair = new KeyPair();
   
   keyPair = createKeyPairResult.getKeyPair();
   
   String privateKey = keyPair.getKeyMaterial();
   ```