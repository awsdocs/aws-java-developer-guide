--------

The AWS SDK for Java team is hiring [software development engineers](https://github.com/aws/aws-sdk-java-v2/issues/3156) that are excited about open source software and the AWS developer experience\!

--------

# Sign Up for AWS and Create an IAM User<a name="signup-create-iam-user"></a>

To use the AWS SDK for Java to access Amazon Web Services \(AWS\), you will need an AWS account and AWS credentials\. To increase the security of your AWS account, we recommend that you use an *IAM user* to provide access credentials instead of using your root account credentials\.

**Note**  
For an overview of IAM users and why they are important for the security of your account, see [Overview of Identity Management: Users](http://docs.aws.amazon.com/IAM/latest/UserGuide/introduction_identity-management.html) in the IAM User Guide\.

1. Open [the AWS website](http://aws.amazon.com/) and click **Sign Up**\.

1. Follow the on\-screen instructions\. Part of the sign\-up procedure involves receiving a phone call and entering a PIN using your phone keypad\.

Next, create an IAM user and download \(or copy\) its secret access key\.

1. Go to the [IAM console](https://console.aws.amazon.com/iam/home) \(you may need to sign in to AWS first\)\.

1. Click **Users** in the sidebar to view your IAM users\.

1. If you don’t have any IAM users set up, click **Create New Users** to create one\.

1. Select the IAM user in the list that you’ll use to access AWS\.

1. Open the **Security Credentials** tab, and click **Create Access Key**\.
**Note**  
You can have a maximum of two active access keys for any given IAM user\. If your IAM user has two access keys already, then you’ll need to delete one of them before creating a new key\.

1. On the resulting dialog box, click the **Download Credentials** button to download the credential file to your computer, or click **Show User Security Credentials** to view the IAM user’s access key ID and secret access key \(which you can copy and paste\)\.
**Important**  
There is no way to obtain the secret access key once you close the dialog box\. You can, however, delete its associated access key ID and create a new one\.

Next, you should [set your credentials](setup-credentials.md) in the AWS shared credentials file or in the environment\.

**Note**  
If you use the Eclipse IDE, you should consider installing the [AWS Toolkit for Eclipse](http://aws.amazon.com/eclipse/) and providing your credentials as described in [Set up AWS Credentials](https://docs.aws.amazon.com/toolkit-for-eclipse/v1/user-guide/setup-credentials.html) in the AWS Toolkit for Eclipse User Guide\.