--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# AWS SDK for Java support for TLS<a name="security-java-tls"></a>

The following information applies only to Java SSL implementation \(the default SSL implementation in the AWS SDK for Java\)\. If you’re using a different SSL implementation, see your specific SSL implementation to learn how to enforce TLS versions\.

## How to check the TLS version<a name="how-to-check-the-tls-version"></a>

Consult your Java virtual machine \(JVM\) provider's documentation to determine which TLS versions are supported on your platform\. For some JVMs, the following code will print which SSL versions are supported\.

```
System.out.println(Arrays.toString(SSLContext.getDefault().getSupportedSSLParameters().getProtocols()));
```

To see the SSL handshake in action and what version of TLS is used, you can use the system property **javax\.net\.debug**\.

```
java app.jar -Djavax.net.debug=ssl
```

**Note**  
TLS 1\.3 is incompatible with SDK for Java versions 1\.9\.5 to 1\.10\.31\. For more information, see the following blog post\.  
[https://aws\.amazon\.com/blogs/developer/tls\-1\-3\-incompatibility\-with\-aws\-sdk\-for\-java\-versions\-1\-9\-5\-to\-1\-10\-31/](https://aws.amazon.com/blogs/developer/tls-1-3-incompatibility-with-aws-sdk-for-java-versions-1-9-5-to-1-10-31/)

## Enforcing a minimum TLS version<a name="enforcing-minimum-tls-version"></a>

The SDK always prefers the latest TLS version supported by the platform and service\. If you wish to enforce a specific minimum TLS version, consult your JVM's documentation\. For OpenJDK\-based JVMs, you can use the system property `jdk.tls.client.protocols`\.

```
java app.jar -Djdk.tls.client.protocols=PROTOCOLS
```

 Consult your JVM's documentation for the supported values of PROTOCOLS\. 