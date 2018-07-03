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
package com.amazonaws.codesamples.advanced;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.codesamples.getting_started.GettingStartedApp;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.CancelSpotInstanceRequestsRequest;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.DescribeSpotInstanceRequestsRequest;
import com.amazonaws.services.ec2.model.DescribeSpotInstanceRequestsResult;
import com.amazonaws.services.ec2.model.LaunchSpecification;
import com.amazonaws.services.ec2.model.RequestSpotInstancesRequest;
import com.amazonaws.services.ec2.model.RequestSpotInstancesResult;
import com.amazonaws.services.ec2.model.SpotInstanceRequest;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;

public class InlineTaggingCodeSampleApp {

  /**
   * @param args
   */
  public static void main(String[] args) {
    //==============================================================//
    //================ Submitting a Request ========================//
    //==============================================================//

    // Create the AmazonEC2 client so we can call various APIs.
    AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

    // Initializes a Spot Instance Request
    RequestSpotInstancesRequest requestRequest = new RequestSpotInstancesRequest();

    // Request 1 x t1.micro instance with a bid price of $0.03.
    requestRequest.setSpotPrice("0.03");
    requestRequest.setInstanceCount(Integer.valueOf(1));

    // Set up the specifications of the launch. This includes
    // the instance type (e.g. t1.micro) and the latest Amazon
    // Linux AMI id available. Note, you should always use the
    // latest Amazon Linux AMI id or another of your choosing.
    LaunchSpecification launchSpecification = new LaunchSpecification();
    launchSpecification.setImageId("ami-a9d09ed1");
    launchSpecification.setInstanceType("t1.micro");

    // Add the security group to the request.
    ArrayList&lt;String&gt; securityGroups = new ArrayList&lt;String&gt;();
    securityGroups.add("GettingStartedGroup");
    launchSpecification.setSecurityGroups(securityGroups);

    // Add the launch specifications to the request.
    requestRequest.setLaunchSpecification(launchSpecification);

    //============================================================//
    //======== Getting the Request ID from the Request ===========//
    //============================================================//

    // Call the RequestSpotInstance API.
    RequestSpotInstancesResult requestResult = ec2.requestSpotInstances(requestRequest);
    List&lt;SpotInstanceRequest&gt; requestResponses = requestResult.getSpotInstanceRequests();

    // Set up an arraylist to collect all of the request ids we want to
    // watch hit the running state.
    ArrayList&lt;String&gt; spotInstanceRequestIds = new ArrayList&lt;String&gt;();

    // Add all of the request ids to the hashset, so we can
    // determine when they hit the active state.
    for (SpotInstanceRequest requestResponse : requestResponses) {
      System.out.println("Created Spot Request: "+requestResponse.getSpotInstanceRequestId());
      spotInstanceRequestIds.add(requestResponse.getSpotInstanceRequestId());
    }

    //==========================================================//
    //============= Tag the Spot Requests ======================//
    //==========================================================//

    // Create the list of tags we want to create
    ArrayList&lt;Tag&gt; requestTags = new ArrayList&lt;Tag&gt;();
    requestTags.add(new Tag("keyname1","value1"));

    // Create a tag request for the requests.
    CreateTagsRequest createTagsRequest_requests = new CreateTagsRequest();
    createTagsRequest_requests.setResources(spotInstanceRequestIds);
    createTagsRequest_requests.setTags(requestTags);

    // Try to tag the Spot request submitted.
    try {
      ec2.createTags(createTagsRequest_requests);
    } catch (AmazonServiceException e) {
      // Write out any exceptions that may have occurred.
      System.out.println("Error terminating instances");
      System.out.println("Caught Exception: " + e.getMessage());
      System.out.println("Reponse Status Code: " + e.getStatusCode());
      System.out.println("Error Code: " + e.getErrorCode());
      System.out.println("Request ID: " + e.getRequestId());
    }

    //===========================================================//
    //======= Determining the State of the Spot Request =========//
    //===========================================================//

    // Create a variable that will track whether there are any
    // requests still in the open state.
    boolean anyOpen;

    // Initialize variables.
    ArrayList&lt;String&gt; instanceIds = new ArrayList&lt;String&gt;();

    do {
      // Create the describeRequest with tall of the request
      // id to monitor (e.g. that we started).
      DescribeSpotInstanceRequestsRequest describeRequest = new DescribeSpotInstanceRequestsRequest();
      describeRequest.setSpotInstanceRequestIds(spotInstanceRequestIds);

      // Initialize the anyOpen variable to false - which assumes there are no requests open unless
      // we find one that is still open.
      anyOpen = false;

      try {
        // Retrieve all of the requests we want to monitor.
        DescribeSpotInstanceRequestsResult describeResult = ec2.describeSpotInstanceRequests(describeRequest);
        List&lt;SpotInstanceRequest&gt; describeResponses = describeResult.getSpotInstanceRequests();

        // Look through each request and determine if they are all
        // in the active state.
        for (SpotInstanceRequest describeResponse : describeResponses) {
          // If the state is open, it hasn't changed since we
          // attempted to request it. There is the potential
          // for it to transition almost immediately to closed or
          // canceled so we compare against open instead of active.
          if (describeResponse.getState().equals("open")) {
            anyOpen = true;
            break;
          }

          // Add the instance id to the list we will
          // eventually terminate.
          instanceIds.add(describeResponse.getInstanceId());
        }
      } catch (AmazonServiceException e) {
        // If we have an exception, ensure we don't break out
        // of the loop. This prevents the scenario where there
        // was blip on the wire.
        anyOpen = true;
      }

      try {
        // Sleep for 60 seconds.
        Thread.sleep(60*1000);
      } catch (Exception e) {
        // Do nothing because it woke up early.
      }
    } while (anyOpen);

    //========================================================//
    //============= Tag the Spot Instances ====================//
    //========================================================//

    // Create the list of tags we want to create
    ArrayList&lt;Tag&gt; instanceTags = new ArrayList&lt;Tag&gt;();
    instanceTags.add(new Tag("keyname1","value1"));

    // Create a tag request for instances.
    CreateTagsRequest createTagsRequest_instances = new CreateTagsRequest();
    createTagsRequest_instances.setResources(instanceIds);
    createTagsRequest_instances.setTags(instanceTags);

    // Try to tag the Spot instance started.
    try {
      ec2.createTags(createTagsRequest_instances);
    } catch (AmazonServiceException e) {
      // Write out any exceptions that may have occurred.
      System.out.println("Error terminating instances");
      System.out.println("Caught Exception: " + e.getMessage());
      System.out.println("Reponse Status Code: " + e.getStatusCode());
      System.out.println("Error Code: " + e.getErrorCode());
      System.out.println("Request ID: " + e.getRequestId());
    }

    //===========================================================//
    //================== Canceling the Request ==================//
    //===========================================================//

    try {
      // Cancel requests.
      CancelSpotInstanceRequestsRequest cancelRequest = new CancelSpotInstanceRequestsRequest(spotInstanceRequestIds);
      ec2.cancelSpotInstanceRequests(cancelRequest);
    } catch (AmazonServiceException e) {
      // Write out any exceptions that may have occurred.
      System.out.println("Error canceling instances");
      System.out.println("Caught Exception: " + e.getMessage());
      System.out.println("Reponse Status Code: " + e.getStatusCode());
      System.out.println("Error Code: " + e.getErrorCode());
      System.out.println("Request ID: " + e.getRequestId());
    }

    //===========================================================//
    //=============== Terminating any Instances =================//
    //===========================================================//
    try {
      // Terminate instances.
      TerminateInstancesRequest terminateRequest = new TerminateInstancesRequest(instanceIds);
      ec2.terminateInstances(terminateRequest);
    } catch (AmazonServiceException e) {
      // Write out any exceptions that may have occurred.
      System.out.println("Error terminating instances");
      System.out.println("Caught Exception: " + e.getMessage());
      System.out.println("Reponse Status Code: " + e.getStatusCode());
      System.out.println("Error Code: " + e.getErrorCode());
      System.out.println("Request ID: " + e.getRequestId());
    }
  } // main
}
