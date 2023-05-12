# Managing IAM Users<a name="examples-iam-users"></a>

## Creating a User<a name="creating-a-user"></a>

Create a new IAM user by providing the user name to the AmazonIdentityManagementClient’s `createUser` method, either directly or using a [CreateUserRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/identitymanagement/model/CreateUserRequest.html) object containing the user name\.

 **Imports** 

```
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.CreateUserRequest;
import com.amazonaws.services.identitymanagement.model.CreateUserResult;
```

 **Code** 

```
final AmazonIdentityManagement iam =
    AmazonIdentityManagementClientBuilder.defaultClient();

CreateUserRequest request = new CreateUserRequest()
    .withUserName(username);

CreateUserResult response = iam.createUser(request);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/iam/src/main/java/aws/example/iam/CreateUser.java) on GitHub\.

## Listing Users<a name="listing-users"></a>

To list the IAM users for your account, create a new [ListUsersRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/identitymanagement/model/ListUsersRequest.html) and pass it to the AmazonIdentityManagementClient’s `listUsers` method\. You can retrieve the list of users by calling `getUsers` on the returned [ListUsersResult](https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/services/identitymanagement/model/ListUsersResult.html) object\.

The list of users returned by `listUsers` is paged\. You can check to see there are more results to retrieve by calling the response object’s `getIsTruncated` method\. If it returns `true`, then call the request object’s `setMarker()` method, passing it the return value of the response object’s `getMarker()` method\.

 **Imports** 

```
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.ListUsersRequest;
import com.amazonaws.services.identitymanagement.model.ListUsersResult;
import com.amazonaws.services.identitymanagement.model.User;
```

 **Code** 

```
final AmazonIdentityManagement iam =
    AmazonIdentityManagementClientBuilder.defaultClient();

boolean done = false;
ListUsersRequest request = new ListUsersRequest();

while(!done) {
    ListUsersResult response = iam.listUsers(request);

    for(User user : response.getUsers()) {
        System.out.format("Retrieved user %s", user.getUserName());
    }

    request.setMarker(response.getMarker());

    if(!response.getIsTruncated()) {
        done = true;
    }
}
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/iam/src/main/java/aws/example/iam/ListUsers.java) on GitHub\.

## Updating a User<a name="updating-a-user"></a>

To update a user, call the AmazonIdentityManagementClient object’s `updateUser` method, which takes a [UpdateUserRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/identitymanagement/model/UpdateUserRequest.html) object that you can use to change the user’s *name* or *path*\.

 **Imports** 

```
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.UpdateUserRequest;
import com.amazonaws.services.identitymanagement.model.UpdateUserResult;
```

 **Code** 

```
final AmazonIdentityManagement iam =
    AmazonIdentityManagementClientBuilder.defaultClient();

UpdateUserRequest request = new UpdateUserRequest()
    .withUserName(cur_name)
    .withNewUserName(new_name);

UpdateUserResult response = iam.updateUser(request);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/iam/src/main/java/aws/example/iam/UpdateUser.java) on GitHub\.

## Deleting a User<a name="deleting-a-user"></a>

To delete a user, call the AmazonIdentityManagementClient’s `deleteUser` request with a [UpdateUserRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/identitymanagement/model/UpdateUserRequest.html) object set with the user name to delete\.

 **Imports** 

```
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.DeleteConflictException;
import com.amazonaws.services.identitymanagement.model.DeleteUserRequest;
```

 **Code** 

```
final AmazonIdentityManagement iam =
    AmazonIdentityManagementClientBuilder.defaultClient();

DeleteUserRequest request = new DeleteUserRequest()
    .withUserName(username);

try {
    iam.deleteUser(request);
} catch (DeleteConflictException e) {
    System.out.println("Unable to delete user. Verify user is not" +
            " associated with any resources");
    throw e;
}
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/iam/src/main/java/aws/example/iam/DeleteUser.java) on GitHub\.

## More Information<a name="more-information"></a>
+  [IAM Users](http://docs.aws.amazon.com/IAM/latest/UserGuide/id_users.html) in the IAM User Guide
+  [Managing IAM Users](http://docs.aws.amazon.com/IAM/latest/UserGuide/id_users_manage.html) in the IAM User Guide
+  [CreateUser](http://docs.aws.amazon.com/IAM/latest/APIReference/API_CreateUser.html) in the IAM API Reference
+  [ListUsers](http://docs.aws.amazon.com/IAM/latest/APIReference/API_ListUsers.html) in the IAM API Reference
+  [UpdateUser](http://docs.aws.amazon.com/IAM/latest/APIReference/API_UpdateUser.html) in the IAM API Reference
+  [DeleteUser](http://docs.aws.amazon.com/IAM/latest/APIReference/API_DeleteUser.html) in the IAM API Reference