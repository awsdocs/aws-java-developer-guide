--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Managing IAM Access Keys<a name="examples-iam-access-keys"></a>

## Creating an Access Key<a name="creating-an-access-key"></a>

To create an IAM access key, call the AmazonIdentityManagementClient`createAccessKey` method with an [CreateAccessKeyRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/identitymanagement/model/CreateAccessKeyRequest.html) object\.

 `CreateAccessKeyRequest` has two constructors — one that takes a user name and another with no parameters\. If you use the version that takes no parameters, you must set the user name using the `withUserName` setter method before passing it to the `createAccessKey` method\.

 **Imports** 

```
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.CreateAccessKeyRequest;
import com.amazonaws.services.identitymanagement.model.CreateAccessKeyResult;
```

 **Code** 

```
final AmazonIdentityManagement iam =
    AmazonIdentityManagementClientBuilder.defaultClient();

CreateAccessKeyRequest request = new CreateAccessKeyRequest()
    .withUserName(user);

CreateAccessKeyResult response = iam.createAccessKey(request);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/iam/src/main/java/aws/example/iam/CreateAccessKey.java) on GitHub\.

## Listing Access Keys<a name="listing-access-keys"></a>

To list the access keys for a given user, create a [ListAccessKeysRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/identitymanagement/model/ListAccessKeysRequest.html) object that contains the user name to list keys for, and pass it to the AmazonIdentityManagementClient’s `listAccessKeys` method\.

**Note**  
If you do not supply a user name to `listAccessKeys`, it will attempt to list access keys associated with the AWS account that signed the request\.

 **Imports** 

```
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.AccessKeyMetadata;
import com.amazonaws.services.identitymanagement.model.ListAccessKeysRequest;
import com.amazonaws.services.identitymanagement.model.ListAccessKeysResult;
```

 **Code** 

```
final AmazonIdentityManagement iam =
    AmazonIdentityManagementClientBuilder.defaultClient();

boolean done = false;
ListAccessKeysRequest request = new ListAccessKeysRequest()
        .withUserName(username);

while (!done) {

    ListAccessKeysResult response = iam.listAccessKeys(request);

    for (AccessKeyMetadata metadata :
            response.getAccessKeyMetadata()) {
        System.out.format("Retrieved access key %s",
                metadata.getAccessKeyId());
    }

    request.setMarker(response.getMarker());

    if (!response.getIsTruncated()) {
        done = true;
    }
}
```

The results of `listAccessKeys` are paged \(with a default maximum of 100 records per call\)\. You can call `getIsTruncated` on the returned [ListAccessKeysResult](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/identitymanagement/model/ListAccessKeysResult.html) object to see if the query returned fewer results then are available\. If so, then call `setMarker` on the `ListAccessKeysRequest` and pass it back to the next invocation of `listAccessKeys`\.

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/iam/src/main/java/aws/example/iam/ListAccessKeys.java) on GitHub\.

## Retrieving an Access Key’s Last Used Time<a name="retrieving-an-access-key-s-last-used-time"></a>

To get the time an access key was last used, call the AmazonIdentityManagementClient’s `getAccessKeyLastUsed` method with the access key’s ID \(which can be passed in using a [GetAccessKeyLastUsedRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/identitymanagement/model/GetAccessKeyLastUsedRequest.html) object, or directly to the overload that takes the access key ID directly\.

You can then use the returned [GetAccessKeyLastUsedResult](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/identitymanagement/model/GetAccessKeyLastUsedResult.html) object to retrieve the key’s last used time\.

 **Imports** 

```
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.GetAccessKeyLastUsedRequest;
import com.amazonaws.services.identitymanagement.model.GetAccessKeyLastUsedResult;
```

 **Code** 

```
final AmazonIdentityManagement iam =
    AmazonIdentityManagementClientBuilder.defaultClient();

GetAccessKeyLastUsedRequest request = new GetAccessKeyLastUsedRequest()
    .withAccessKeyId(access_id);

GetAccessKeyLastUsedResult response = iam.getAccessKeyLastUsed(request);

System.out.println("Access key was last used at: " +
        response.getAccessKeyLastUsed().getLastUsedDate());
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/iam/src/main/java/aws/example/iam/AccessKeyLastUsed.java) on GitHub\.

## Activating or Deactivating Access Keys<a name="iam-access-keys-update"></a>

You can activate or deactivate an access key by creating an [UpdateAccessKeyRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/identitymanagement/model/UpdateAccessKeyRequest.html) object, providing the access key ID, optionally the user name, and the desired [Status](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/identitymanagement/model/StatusType.html), then passing the request object to the AmazonIdentityManagementClient’s `updateAccessKey` method\.

 **Imports** 

```
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.UpdateAccessKeyRequest;
import com.amazonaws.services.identitymanagement.model.UpdateAccessKeyResult;
```

 **Code** 

```
final AmazonIdentityManagement iam =
    AmazonIdentityManagementClientBuilder.defaultClient();

UpdateAccessKeyRequest request = new UpdateAccessKeyRequest()
    .withAccessKeyId(access_id)
    .withUserName(username)
    .withStatus(status);

UpdateAccessKeyResult response = iam.updateAccessKey(request);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/iam/src/main/java/aws/example/iam/UpdateAccessKey.java) on GitHub\.

## Deleting an Access Key<a name="deleting-an-access-key"></a>

To permanently delete an access key, call the AmazonIdentityManagementClient’s `deleteKey` method, providing it with a [DeleteAccessKeyRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/identitymanagement/model/DeleteAccessKeyRequest.html) containing the access key’s ID and username\.

**Note**  
Once deleted, a key can no longer be retrieved or used\. To temporarily deactivate a key so that it can be activated again later, use [updateAccessKey](#iam-access-keys-update) method instead\.

 **Imports** 

```
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.DeleteAccessKeyRequest;
import com.amazonaws.services.identitymanagement.model.DeleteAccessKeyResult;
```

 **Code** 

```
final AmazonIdentityManagement iam =
    AmazonIdentityManagementClientBuilder.defaultClient();

DeleteAccessKeyRequest request = new DeleteAccessKeyRequest()
    .withAccessKeyId(access_key)
    .withUserName(username);

DeleteAccessKeyResult response = iam.deleteAccessKey(request);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/iam/src/main/java/aws/example/iam/DeleteAccessKey.java) on GitHub\.

## More Information<a name="more-information"></a>
+  [CreateAccessKey](http://docs.aws.amazon.com/IAM/latest/APIReference/API_CreateAccessKey.html) in the IAM API Reference
+  [ListAccessKeys](http://docs.aws.amazon.com/IAM/latest/APIReference/API_ListAccessKeys.html) in the IAM API Reference
+  [GetAccessKeyLastUsed](http://docs.aws.amazon.com/IAM/latest/APIReference/API_GetAccessKeyLastUsed.html) in the IAM API Reference
+  [UpdateAccessKey](http://docs.aws.amazon.com/IAM/latest/APIReference/API_UpdateAccessKey.html) in the IAM API Reference
+  [DeleteAccessKey](http://docs.aws.amazon.com/IAM/latest/APIReference/API_DeleteAccessKey.html) in the IAM API Reference