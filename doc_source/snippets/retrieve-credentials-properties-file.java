/*
   Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This file is licensed under the Apache License, Version 2.0 (the "License").
   You may not use this file except in compliance with the License. A copy of
   the License is located at

    http://aws.amazon.com/apache2.0/

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
   CONDITIONS OF ANY KIND, either express or implied. See the License for the
   specific language governing permissions and limitations under the License.
*/
// Create the AmazonEC2 client so we can call various APIs.
AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

// Create a new security group.
try {
CreateSecurityGroupRequest securityGroupRequest =
  new CreateSecurityGroupRequest(
      "GettingStartedGroup", "Getting Started Security Group");
  ec2.createSecurityGroup(securityGroupRequest);
}
catch (AmazonServiceException ase) {
  // Likely this means that the group is already created, so ignore.
  System.out.println(ase.getMessage());
}

String ipAddr = "0.0.0.0/0";

// Get the IP of the current host, so that we can limit the Security Group by
// default to the ip range associated with your subnet.
try {
  InetAddress addr = InetAddress.getLocalHost();
  // Get IP Address
  ipAddr = addr.getHostAddress()+"/10";
}
catch (UnknownHostException e) {
}

// Create a range that you would like to populate.
ArrayList&lt;String&gt; ipRanges = new ArrayList&lt;String&gt;();
ipRanges.add(ipAddr);

// Open up port 22 for TCP traffic to the associated IP from
// above (e.g. ssh traffic).
ArrayList&lt;IpPermission&gt; ipPermissions = new ArrayList&lt;IpPermission&gt; ();
IpPermission ipPermission = new IpPermission();
ipPermission.setIpProtocol("tcp");
ipPermission.setFromPort(new Integer(22));
ipPermission.setToPort(new Integer(22));
ipPermission.setIpRanges(ipRanges);
ipPermissions.add(ipPermission);

try {
  // Authorize the ports to the used.
  AuthorizeSecurityGroupIngressRequest ingressRequest =
    new AuthorizeSecurityGroupIngressRequest(
        "GettingStartedGroup",ipPermissions);
  ec2.authorizeSecurityGroupIngress(ingressRequest);
}
catch (AmazonServiceException ase) {
  // Ignore because this likely means the zone has already
  // been authorized.
  System.out.println(ase.getMessage());
}

