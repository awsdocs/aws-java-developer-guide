--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Working with IAM Policies<a name="examples-iam-policies"></a>

## Creating a Policy<a name="creating-a-policy"></a>

To create a new policy, provide the policy’s name and a JSON\-formatted policy document in a [CreatePolicyRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/identitymanagement/model/CreatePolicyRequest.html) to the AmazonIdentityManagementClient’s `createPolicy` method\.

 **Imports** 

```
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.CreatePolicyRequest;
import com.amazonaws.services.identitymanagement.model.CreatePolicyResult;
```

 **Code** 

```
final AmazonIdentityManagement iam =
    AmazonIdentityManagementClientBuilder.defaultClient();

CreatePolicyRequest request = new CreatePolicyRequest()
    .withPolicyName(policy_name)
    .withPolicyDocument(POLICY_DOCUMENT);

CreatePolicyResult response = iam.createPolicy(request);
```

IAM policy documents are JSON strings with a [well\-documented syntax](http://docs.aws.amazon.com/IAM/latest/UserGuide/reference_policies_grammar.html)\. Here is an example that provides access to make particular requests to DynamoDB\.

```
public static final String POLICY_DOCUMENT =
    "{" +
    "  \"Version\": \"2012-10-17\"," +
    "  \"Statement\": [" +
    "    {" +
    "        \"Effect\": \"Allow\"," +
    "        \"Action\": \"logs:CreateLogGroup\"," +
    "        \"Resource\": \"%s\"" +
    "    }," +
    "    {" +
    "        \"Effect\": \"Allow\"," +
    "        \"Action\": [" +
    "            \"dynamodb:DeleteItem\"," +
    "            \"dynamodb:GetItem\"," +
    "            \"dynamodb:PutItem\"," +
    "            \"dynamodb:Scan\"," +
    "            \"dynamodb:UpdateItem\"" +
    "       ]," +
    "       \"Resource\": \"RESOURCE_ARN\"" +
    "    }" +
    "   ]" +
    "}";
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/iam/src/main/java/aws/example/iam/CreatePolicy.java) on GitHub\.

## Getting a Policy<a name="getting-a-policy"></a>

To retrieve an existing policy, call the AmazonIdentityManagementClient’s `getPolicy` method, providing the policy’s ARN within a [GetPolicyRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/identitymanagement/model/GetPolicyRequest.html) object\.

 **Imports** 

```
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.GetPolicyRequest;
import com.amazonaws.services.identitymanagement.model.GetPolicyResult;
```

 **Code** 

```
final AmazonIdentityManagement iam =
    AmazonIdentityManagementClientBuilder.defaultClient();

GetPolicyRequest request = new GetPolicyRequest()
    .withPolicyArn(policy_arn);

GetPolicyResult response = iam.getPolicy(request);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/iam/src/main/java/aws/example/iam/GetPolicy.java) on GitHub\.

## Attaching a Role Policy<a name="attaching-a-role-policy"></a>

You can attach a policy to an IAMhttp://docs\.aws\.amazon\.com/IAM/latest/UserGuide/id\_roles\.html\[role\] by calling the AmazonIdentityManagementClient’s `attachRolePolicy` method, providing it with the role name and policy ARN in an [AttachRolePolicyRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/identitymanagement/model/AttachRolePolicyRequest.html)\.

 **Imports** 

```
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.AttachRolePolicyRequest;
import com.amazonaws.services.identitymanagement.model.AttachedPolicy;
```

 **Code** 

```
final AmazonIdentityManagement iam =
    AmazonIdentityManagementClientBuilder.defaultClient();

AttachRolePolicyRequest attach_request =
    new AttachRolePolicyRequest()
        .withRoleName(role_name)
        .withPolicyArn(POLICY_ARN);

iam.attachRolePolicy(attach_request);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/iam/src/main/java/aws/example/iam/AttachRolePolicy.java) on GitHub\.

## Listing Attached Role Policies<a name="listing-attached-role-policies"></a>

List attached policies on a role by calling the AmazonIdentityManagementClient’s `listAttachedRolePolicies` method\. It takes a [ListAttachedRolePoliciesRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/identitymanagement/model/ListAttachedRolePoliciesRequest.html) object that contains the role name to list the policies for\.

Call `getAttachedPolicies` on the returned [ListAttachedRolePoliciesResult](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/identitymanagement/model/ListAttachedRolePoliciesResult.html) object to get the list of attached policies\. Results may be truncated; if the `ListAttachedRolePoliciesResult` object’s `getIsTruncated` method returns `true`, call the `ListAttachedRolePoliciesRequest` object’s `setMarker` method and use it to call `listAttachedRolePolicies` again to get the next batch of results\.

 **Imports** 

```
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.ListAttachedRolePoliciesRequest;
import com.amazonaws.services.identitymanagement.model.ListAttachedRolePoliciesResult;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
```

 **Code** 

```
final AmazonIdentityManagement iam =
    AmazonIdentityManagementClientBuilder.defaultClient();

ListAttachedRolePoliciesRequest request =
    new ListAttachedRolePoliciesRequest()
        .withRoleName(role_name);

List<AttachedPolicy> matching_policies = new ArrayList<>();

boolean done = false;

while(!done) {
    ListAttachedRolePoliciesResult response =
        iam.listAttachedRolePolicies(request);

    matching_policies.addAll(
            response.getAttachedPolicies()
                    .stream()
                    .filter(p -> p.getPolicyName().equals(role_name))
                    .collect(Collectors.toList()));

    if(!response.getIsTruncated()) {
        done = true;
    }
    request.setMarker(response.getMarker());
}
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/iam/src/main/java/aws/example/iam/AttachRolePolicy.java) on GitHub\.

## Detaching a Role Policy<a name="detaching-a-role-policy"></a>

To detach a policy from a role, call the AmazonIdentityManagementClient’s `detachRolePolicy` method, providing it with the role name and policy ARN in a [DetachRolePolicyRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/identitymanagement/model/DetachRolePolicyRequest.html)\.

 **Imports** 

```
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.DetachRolePolicyRequest;
import com.amazonaws.services.identitymanagement.model.DetachRolePolicyResult;
```

 **Code** 

```
final AmazonIdentityManagement iam =
    AmazonIdentityManagementClientBuilder.defaultClient();

DetachRolePolicyRequest request = new DetachRolePolicyRequest()
    .withRoleName(role_name)
    .withPolicyArn(policy_arn);

DetachRolePolicyResult response = iam.detachRolePolicy(request);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/iam/src/main/java/aws/example/iam/DetachRolePolicy.java) on GitHub\.

## More Information<a name="more-information"></a>
+  [Overview of IAM Policies](http://docs.aws.amazon.com/IAM/latest/UserGuide/access_policies.html) in the IAM User Guide\.
+  [AWS IAM Policy Reference](http://docs.aws.amazon.com/IAM/latest/UserGuide/reference_policies.html) in the IAM User Guide\.
+  [CreatePolicy](http://docs.aws.amazon.com/IAM/latest/APIReference/API_CreatePolicy.html) in the IAM API Reference
+  [GetPolicy](http://docs.aws.amazon.com/IAM/latest/APIReference/API_GetPolicy.html) in the IAM API Reference
+  [AttachRolePolicy](http://docs.aws.amazon.com/IAM/latest/APIReference/API_AttachRolePolicy.html) in the IAM API Reference
+  [ListAttachedRolePolicies](http://docs.aws.amazon.com/IAM/latest/APIReference/API_ListAttachedRolePolicies.html) in the IAM API Reference
+  [DetachRolePolicy](http://docs.aws.amazon.com/IAM/latest/APIReference/API_DetachRolePolicy.html) in the IAM API Reference