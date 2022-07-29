--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Using IAM Account Aliases<a name="examples-iam-account-aliases"></a>

If you want the URL for your sign\-in page to contain your company name or other friendly identifier instead of your AWS account ID, you can create an alias for your AWS account\.

**Note**  
 AWS supports exactly one account alias per account\.

## Creating an Account Alias<a name="creating-an-account-alias"></a>

To create an account alias, call the AmazonIdentityManagementClient’s `createAccountAlias` method with a [CreateAccountAliasRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/identitymanagement/model/CreateAccountAliasRequest.html) object that contains the alias name\.

 **Imports** 

```
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.CreateAccountAliasRequest;
import com.amazonaws.services.identitymanagement.model.CreateAccountAliasResult;
```

 **Code** 

```
final AmazonIdentityManagement iam =
    AmazonIdentityManagementClientBuilder.defaultClient();

CreateAccountAliasRequest request = new CreateAccountAliasRequest()
    .withAccountAlias(alias);

CreateAccountAliasResult response = iam.createAccountAlias(request);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/iam/src/main/java/aws/example/iam/CreateAccountAlias.java) on GitHub\.

## Listing Account Aliases<a name="listing-account-aliases"></a>

To list your account’s alias, if any, call the AmazonIdentityManagementClient’s `listAccountAliases` method\.

**Note**  
The returned [ListAccountAliasesResponse](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/identitymanagement/model/ListAccountAliasesResponse.html) supports the same `getIsTruncated` and `getMarker` methods as other AWS SDK for Java *list* methods, but an AWS account can have only *one* account alias\.

 **imports** 

```
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.ListAccountAliasesResult;
```

 **code** 

```
final AmazonIdentityManagement iam =
    AmazonIdentityManagementClientBuilder.defaultClient();

ListAccountAliasesResult response = iam.listAccountAliases();

for (String alias : response.getAccountAliases()) {
    System.out.printf("Retrieved account alias %s", alias);
}
```

see the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/iam/src/main/java/aws/example/iam/ListAccountAliases.java) on GitHub\.

## Deleting an account alias<a name="deleting-an-account-alias"></a>

To delete your account’s alias, call the AmazonIdentityManagementClient’s `deleteAccountAlias` method\. When deleting an account alias, you must supply its name using a [DeleteAccountAliasRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/identitymanagement/model/DeleteAccountAliasRequest.html) object\.

 **imports** 

```
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.DeleteAccountAliasRequest;
import com.amazonaws.services.identitymanagement.model.DeleteAccountAliasResult;
```

 **Code** 

```
final AmazonIdentityManagement iam =
    AmazonIdentityManagementClientBuilder.defaultClient();

DeleteAccountAliasRequest request = new DeleteAccountAliasRequest()
    .withAccountAlias(alias);

DeleteAccountAliasResult response = iam.deleteAccountAlias(request);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/iam/src/main/java/aws/example/iam/DeleteAccountAlias.java) on GitHub\.

## More Information<a name="more-information"></a>
+  [Your AWS Account ID and Its Alias](http://docs.aws.amazon.com/IAM/latest/UserGuide/console_account-alias.html) in the IAM User Guide
+  [CreateAccountAlias](http://docs.aws.amazon.com/IAM/latest/APIReference/API_CreateAccountAlias.html) in the IAM API Reference
+  [ListAccountAliases](http://docs.aws.amazon.com/IAM/latest/APIReference/API_ListAccountAliases.html) in the IAM API Reference
+  [DeleteAccountAlias](http://docs.aws.amazon.com/IAM/latest/APIReference/API_DeleteAccountAlias.html) in the IAM API Reference