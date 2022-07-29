--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Working with IAM Server Certificates<a name="examples-iam-server-certificates"></a>

To enable HTTPS connections to your website or application on AWS, you need an SSL/TLS *server certificate*\. You can use a server certificate provided by AWS Certificate Manager or one that you obtained from an external provider\.

We recommend that you use ACM to provision, manage, and deploy your server certificates\. With ACM you can request a certificate, deploy it to your AWS resources, and let ACM handle certificate renewals for you\. Certificates provided by ACM are free\. For more information about ACM , see the [ACM User Guide](https://docs.aws.amazon.com/acm/latest/userguide/)\.

## Getting a Server Certificate<a name="getting-a-server-certificate"></a>

You can retrieve a server certificate by calling the AmazonIdentityManagementClient’s `getServerCertificate` method, passing it a [GetServerCertificateRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/identitymanagement/model/GetServerCertificateRequest.html) with the certificate’s name\.

 **Imports** 

```
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.GetServerCertificateRequest;
import com.amazonaws.services.identitymanagement.model.GetServerCertificateResult;
```

 **Code** 

```
final AmazonIdentityManagement iam =
    AmazonIdentityManagementClientBuilder.defaultClient();

GetServerCertificateRequest request = new GetServerCertificateRequest()
            .withServerCertificateName(cert_name);

GetServerCertificateResult response = iam.getServerCertificate(request);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/iam/src/main/java/aws/example/iam/GetServerCertificate.java) on GitHub\.

## Listing Server Certificates<a name="listing-server-certificates"></a>

To list your server certificates, call the AmazonIdentityManagementClient’s `listServerCertificates` method with a [ListServerCertificatesRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/identitymanagement/model/ListServerCertificatesRequest.html)\. It returns a [ListServerCertificatesResult](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/identitymanagement/model/ListServerCertificatesResult.html)\.

Call the returned `ListServerCertificateResult` object’s `getServerCertificateMetadataList` method to get a list of [ServerCertificateMetadata](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/identitymanagement/model/ServerCertificateMetadata.html) objects that you can use to get information about each certificate\.

Results may be truncated; if the `ListServerCertificateResult` object’s `getIsTruncated` method returns `true`, call the `ListServerCertificatesRequest` object’s `setMarker` method and use it to call `listServerCertificates` again to get the next batch of results\.

 **Imports** 

```
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.ListServerCertificatesRequest;
import com.amazonaws.services.identitymanagement.model.ListServerCertificatesResult;
import com.amazonaws.services.identitymanagement.model.ServerCertificateMetadata;
```

 **Code** 

```
final AmazonIdentityManagement iam =
    AmazonIdentityManagementClientBuilder.defaultClient();

boolean done = false;
ListServerCertificatesRequest request =
        new ListServerCertificatesRequest();

while(!done) {

    ListServerCertificatesResult response =
        iam.listServerCertificates(request);

    for(ServerCertificateMetadata metadata :
            response.getServerCertificateMetadataList()) {
        System.out.printf("Retrieved server certificate %s",
                metadata.getServerCertificateName());
    }

    request.setMarker(response.getMarker());

    if(!response.getIsTruncated()) {
        done = true;
    }
}
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/iam/src/main/java/aws/example/iam/ListServerCertificates.java) on GitHub\.

## Updating a Server Certificate<a name="updating-a-server-certificate"></a>

You can update a server certificate’s name or path by calling the AmazonIdentityManagementClient’s `updateServerCertificate` method\. It takes a [UpdateServerCertificateRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/identitymanagement/model/UpdateServerCertificateRequest.html) object set with the server certificate’s current name and either a new name or new path to use\.

 **Imports** 

```
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.UpdateServerCertificateRequest;
import com.amazonaws.services.identitymanagement.model.UpdateServerCertificateResult;
```

 **Code** 

```
final AmazonIdentityManagement iam =
    AmazonIdentityManagementClientBuilder.defaultClient();

UpdateServerCertificateRequest request =
    new UpdateServerCertificateRequest()
        .withServerCertificateName(cur_name)
        .withNewServerCertificateName(new_name);

UpdateServerCertificateResult response =
    iam.updateServerCertificate(request);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/iam/src/main/java/aws/example/iam/UpdateServerCertificate.java) on GitHub\.

## Deleting a Server Certificate<a name="deleting-a-server-certificate"></a>

To delete a server certificate, call the AmazonIdentityManagementClient’s `deleteServerCertificate` method with a [DeleteServerCertificateRequest](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/services/identitymanagement/model/DeleteServerCertificateRequest.html) containing the certificate’s name\.

 **Imports** 

```
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.DeleteServerCertificateRequest;
import com.amazonaws.services.identitymanagement.model.DeleteServerCertificateResult;
```

 **Code** 

```
final AmazonIdentityManagement iam =
    AmazonIdentityManagementClientBuilder.defaultClient();

DeleteServerCertificateRequest request =
    new DeleteServerCertificateRequest()
        .withServerCertificateName(cert_name);

DeleteServerCertificateResult response =
    iam.deleteServerCertificate(request);
```

See the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/iam/src/main/java/aws/example/iam/DeleteServerCertificate.java) on GitHub\.

## More Information<a name="more-information"></a>
+  [Working with Server Certificates](http://docs.aws.amazon.com/IAM/latest/UserGuide/id_credentials_server-certs.html) in the IAM User Guide
+  [GetServerCertificate](http://docs.aws.amazon.com/IAM/latest/APIReference/API_GetServerCertificate.html) in the IAM API Reference
+  [ListServerCertificates](http://docs.aws.amazon.com/IAM/latest/APIReference/API_ListServerCertificates.html) in the IAM API Reference
+  [UpdateServerCertificate](http://docs.aws.amazon.com/IAM/latest/APIReference/API_UpdateServerCertificate.html) in the IAM API Reference
+  [DeleteServerCertificate](http://docs.aws.amazon.com/IAM/latest/APIReference/API_DeleteServerCertificate.html) in the IAM API Reference
+  [ACM User Guide](https://docs.aws.amazon.com/acm/latest/userguide/) 