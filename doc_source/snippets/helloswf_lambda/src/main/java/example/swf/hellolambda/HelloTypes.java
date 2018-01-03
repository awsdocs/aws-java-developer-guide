/*
 * Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights
 * Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package example.swf.hellolambda;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClientBuilder;
import com.amazonaws.services.simpleworkflow.model.*;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.*;

public class HelloTypes {
    public static final String DOMAIN = "HelloDomain";
    public static final String TASKLIST = "HelloTasklist";
    public static final String WORKFLOW = "HelloWorkflow";
    public static final String WORKFLOW_VERSION = "1.0-lambda";

    private static final AmazonSimpleWorkflow swf =
        AmazonSimpleWorkflowClientBuilder.defaultClient();

    public static void registerDomain() {
        try {
            System.out.println("** Registering the domain '" + DOMAIN + "'.");
            swf.registerDomain(new RegisterDomainRequest()
                .withName(DOMAIN)
                .withWorkflowExecutionRetentionPeriodInDays("1"));
        }
        catch (DomainAlreadyExistsException e) {
            System.out.println("** Domain already exists!");
        }
    }

    public static void registerWorkflowType() {
        String lambda_role_arn = createLambdaRole();

        if (lambda_role_arn == null) {
            System.err.println("Could not get Lambda role ARN!");
        }

        System.out.println("** Registering the workflow type '" + WORKFLOW + "-" + WORKFLOW_VERSION
                + "'.");
        try {
            swf.registerWorkflowType(new RegisterWorkflowTypeRequest()
                .withDomain(DOMAIN)
                .withName(WORKFLOW)
                .withDefaultLambdaRole(lambda_role_arn)
                .withVersion(WORKFLOW_VERSION)
                .withDefaultChildPolicy(ChildPolicy.TERMINATE)
                .withDefaultTaskList(new TaskList().withName(TASKLIST))
                .withDefaultTaskStartToCloseTimeout("30"));
        }
        catch (TypeAlreadyExistsException e) {
            System.out.println("** Workflow type already exists!");
        }
    }

    /**
     * Creeate an IAM role that gives SWF permissions for Lambda, and return its ARN.
     */
    public static String createLambdaRole() {
        final String ROLE_NAME = "hello-swf-lambda-role";
        System.out.println("** Attempting to create Lambda role: " + ROLE_NAME);

        final String ROLE_POLICY = "{"
            + "  \"Version\": \"2012-10-17\","
            + "  \"Statement\": [{"
            + "    \"Effect\": \"Allow\","
            + "    \"Action\": ["
            + "      \"lambda:InvokeFunction\""
            + "    ],"
            + "    \"Resource\": [\"*\"]"
            + "  }]"
            + "}";

        final String SWF_LAMBDA_TRUST = "{"
            + "  \"Version\": \"2012-10-17\","
            + "  \"Statement\": ["
            + "    {"
            + "      \"Sid\": \"\","
            + "      \"Effect\": \"Allow\","
            + "      \"Principal\": {"
            + "        \"Service\": ["
            + "          \"lambda.amazonaws.com\","
            + "          \"swf.amazonaws.com\""
            + "        ]"
            + "      },"
            + "      \"Action\": \"sts:AssumeRole\""
            + "    }"
            + "  ]"
            + "}";

        AmazonIdentityManagement iam = AmazonIdentityManagementClientBuilder.defaultClient();
        CreateRoleRequest request = new CreateRoleRequest()
                .withRoleName(ROLE_NAME)
                .withAssumeRolePolicyDocument(SWF_LAMBDA_TRUST);

        CreateRoleResult result = null;
        String role_arn = null;

        try {
            result = iam.createRole(request);
            role_arn = result.getRole().getArn();
        }
        catch (EntityAlreadyExistsException e) {
            System.out.println("** IAM Role already exists!");
            role_arn = iam.getRole(new GetRoleRequest().withRoleName(ROLE_NAME))
                          .getRole()
                          .getArn();
        }

        return role_arn;
    }

    public static void main(String[] args) {
        registerDomain();
        registerWorkflowType();
    }
}
