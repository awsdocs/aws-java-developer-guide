# Using IAM Roles to Grant Access to AWS Resources on Amazon EC2<a name="java-dg-roles"></a>

All requests to Amazon Web Services \(AWS\) must be cryptographically signed using credentials issued by AWS\. You can use *IAM roles* to conveniently grant secure access to AWS resources from your Amazon EC2 instances\.

This topic provides information about how to use IAM roles with Java SDK applications running on Amazon EC2\. For more information about IAM instances, see [IAM Roles for Amazon EC2](http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/iam-roles-for-amazon-ec2.html) in the Amazon EC2 User Guide for Linux Instances\.

## The default provider chain and EC2 instance profiles<a name="default-provider-chain"></a>

If your application creates an AWS client using the default constructor, then the client will search for credentials using the *default credentials provider chain*, in the following order:

1. In the Java system properties: `aws.accessKeyId` and `aws.secretKey`\.

1. In system environment variables: `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY`\.

1. In the default credentials file \(the location of this file varies by platform\)\.

1. Credentials delivered through the Amazon EC2 container service if the `AWS_CONTAINER_CREDENTIALS_RELATIVE_URI` environment variable is set and security manager has permission to access the variable\.

1. In the *instance profile credentials*, which exist within the instance metadata associated with the IAM role for the EC2 instance\.

1. Web Identity Token credentials from the environment or container\.

The *instance profile credentials* step in the default provider chain is available only when running your application on an Amazon EC2 instance, but provides the greatest ease of use and best security when working with Amazon EC2 instances\. You can also pass an [InstanceProfileCredentialsProvider](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/auth/InstanceProfileCredentialsProvider.html) instance directly to the client constructor to get instance profile credentials without proceeding through the entire default provider chain\.

For example:

```
AmazonS3 s3 = AmazonS3ClientBuilder.standard()
              .withCredentials(new InstanceProfileCredentialsProvider(false))
              .build();
```

When using this approach, the SDK retrieves temporary AWS credentials that have the same permissions as those associated with the IAM role associated with the Amazon EC2 instance in its instance profile\. Although these credentials are temporary and would eventually expire, `InstanceProfileCredentialsProvider` periodically refreshes them for you so that the obtained credentials continue to allow access to AWS\.

**Important**  
The automatic credentials refresh happens *only* when you use the default client constructor, which creates its own `InstanceProfileCredentialsProvider` as part of the default provider chain, or when you pass an `InstanceProfileCredentialsProvider` instance directly to the client constructor\. If you use another method to obtain or pass instance profile credentials, you are responsible for checking for and refreshing expired credentials\.

If the client constructor can’t find credentials using the credentials provider chain, it will throw an [AmazonClientException](https://docs.aws.amazon.com/sdk-for-java/v1/reference/com/amazonaws/AmazonClientException.html)\.

## Walkthrough: Using IAM roles for EC2 instances<a name="roles-walkthrough"></a>

The following walkthrough shows you how to retrieve an object from Amazon S3 using an IAM role to manage access\.

### Create an IAM Role<a name="java-dg-create-the-role"></a>

Create an IAM role that grants read\-only access to Amazon S3\.

1. Open the [IAM console](https://console.aws.amazon.com/iam/home)\.

1. In the navigation pane, select **Roles**, then **Create New Role**\.

1. Enter a name for the role, then select **Next Step**\. Remember this name, since you’ll need it when you launch your Amazon EC2 instance\.

1. On the **Select Role Type** page, under ** AWS service Roles**, select ** Amazon EC2 **\.

1. On the **Set Permissions** page, under **Select Policy Template**, select ** Amazon S3 Read Only Access**, then **Next Step**\.

1. On the **Review** page, select **Create Role**\.

### Launch an EC2 Instance and Specify Your IAM Role<a name="java-dg-launch-ec2-instance-with-instance-profile"></a>

You can launch an Amazon EC2 instance with an IAM role using the Amazon EC2 console or the AWS SDK for Java\.
+ To launch an Amazon EC2 instance using the console, follow the directions in [Getting Started with Amazon EC2 Linux Instances](http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/EC2_GetStarted.html) in the Amazon EC2 User Guide for Linux Instances\.

  When you reach the **Review Instance Launch** page, select **Edit instance details**\. In **IAM role**, choose the IAM role that you created previously\. Complete the procedure as directed\.
**Note**  
You’ll need to create or use an existing security group and key pair to connect to the instance\.
+ To launch an Amazon EC2 instance with an IAM role using the AWS SDK for Java, see [Run an Amazon EC2 Instance](run-instance.md)\.

### Create your Application<a name="java-dg-remove-the-credentials"></a>

Let’s build the sample application to run on the EC2 instance\. First, create a directory that you can use to hold your tutorial files \(for example, `GetS3ObjectApp`\)\.

Next, copy the AWS SDK for Java libraries into your newly\-created directory\. If you downloaded the AWS SDK for Java to your `~/Downloads` directory, you can copy them using the following commands:

```
cp -r ~/Downloads/aws-java-sdk-{1.7.5}/lib .
cp -r ~/Downloads/aws-java-sdk-{1.7.5}/third-party .
```

Open a new file, call it `GetS3Object.java`, and add the following code:

```
import java.io.*;

import com.amazonaws.auth.*;
import com.amazonaws.services.s3.*;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

public class GetS3Object {
  private static final String bucketName = "text-content";
  private static final String key = "text-object.txt";

  public static void main(String[] args) throws IOException
  {
    AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();

    try {
      System.out.println("Downloading an object");
      S3Object s3object = s3Client.getObject(
          new GetObjectRequest(bucketName, key));
      displayTextInputStream(s3object.getObjectContent());
    }
    catch(AmazonServiceException ase) {
      System.err.println("Exception was thrown by the service");
    }
    catch(AmazonClientException ace) {
      System.err.println("Exception was thrown by the client");
    }
  }

  private static void displayTextInputStream(InputStream input) throws IOException
  {
    // Read one text line at a time and display.
    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
    while(true)
    {
      String line = reader.readLine();
      if(line == null) break;
      System.out.println( "    " + line );
    }
    System.out.println();
  }
}
```

Open a new file, call it `build.xml`, and add the following lines:

```
<project name="Get {S3} Object" default="run" basedir=".">
  <path id="aws.java.sdk.classpath">
    <fileset dir="./lib" includes="**/*.jar"/>
    <fileset dir="./third-party" includes="**/*.jar"/>
    <pathelement location="lib"/>
    <pathelement location="."/>
  </path>

  <target name="build">
  <javac debug="true"
    includeantruntime="false"
    srcdir="."
    destdir="."
    classpathref="aws.java.sdk.classpath"/>
  </target>

  <target name="run" depends="build">
    <java classname="GetS3Object" classpathref="aws.java.sdk.classpath" fork="true"/>
  </target>
</project>
```

Build and run the modified program\. Note that there are no credentials are stored in the program\. Therefore, unless you have your AWS credentials specified already, the code will throw `AmazonServiceException`\. For example:

```
$ ant
Buildfile: /path/to/my/GetS3ObjectApp/build.xml

build:
  [javac] Compiling 1 source file to /path/to/my/GetS3ObjectApp

run:
   [java] Downloading an object
   [java] AmazonServiceException

BUILD SUCCESSFUL
```

### Transfer the Compiled Program to Your EC2 Instance<a name="java-dg-transfer-compiled-program-to-ec2-instance"></a>

Transfer the program to your Amazon EC2 instance using secure copy \(** `` **\), along with the AWS SDK for Java libraries\. The sequence of commands looks something like the following\.

```
scp -p -i {my-key-pair}.pem GetS3Object.class ec2-user@{public_dns}:GetS3Object.class
scp -p -i {my-key-pair}.pem build.xml ec2-user@{public_dns}:build.xml
scp -r -p -i {my-key-pair}.pem lib ec2-user@{public_dns}:lib
scp -r -p -i {my-key-pair}.pem third-party ec2-user@{public_dns}:third-party
```

**Note**  
Depending on the Linux distribution that you used, the *user name* might be "ec2\-user", "root", or "ubuntu"\. To get the public DNS name of your instance, open the [EC2 console](https://console.aws.amazon.com/ec2/home) and look for the **Public DNS** value in the **Description** tab \(for example, `ec2-198-51-100-1.compute-1.amazonaws.com`\)\.

In the preceding commands:
+  `GetS3Object.class` is your compiled program
+  `build.xml` is the ant file used to build and run your program
+ the `lib` and `third-party` directories are the corresponding library folders from the AWS SDK for Java\.
+ The `-r` switch indicates that `scp` should do a recursive copy of all of the contents of the `library` and `third-party` directories in the AWS SDK for Java distribution\.
+ The `-p` switch indicates that `scp` should preserve the permissions of the source files when it copies them to the destination\.
**Note**  
The `-p` switch works only on Linux, macOS, or Unix\. If you are copying files from Windows, you may need to fix the file permissions on your instance using the following command:

```
chmod -R u+rwx GetS3Object.class build.xml lib third-party
```

### Run the Sample Program on the EC2 Instance<a name="java-dg-run-the-program"></a>

To run the program, connect to your Amazon EC2 instance\. For more information, see [Connect to Your Linux Instance](http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/AccessingInstances.html) in the Amazon EC2 User Guide for Linux Instances\.

If ** ` ant ` ** is not available on your instance, install it using the following command:

```
sudo yum install ant
```

Then, run the program using `ant` as follows:

```
ant run
```

The program will write the contents of your Amazon S3 object to your command window\.