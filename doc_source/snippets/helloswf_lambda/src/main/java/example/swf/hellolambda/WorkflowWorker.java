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
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorkflowWorker {

    private static final AmazonSimpleWorkflow swf =
        AmazonSimpleWorkflowClientBuilder.defaultClient();

    public static void main(String[] args) {
        PollForDecisionTaskRequest task_request =
            new PollForDecisionTaskRequest()
                .withDomain(HelloTypes.DOMAIN)
                .withTaskList(new TaskList().withName(HelloTypes.TASKLIST));

        while (true) {
            System.out.println("Polling for a decision task from the tasklist '"
                    + HelloTypes.TASKLIST + "' in the domain '" +
                    HelloTypes.DOMAIN + "'.");

            DecisionTask task = swf.pollForDecisionTask(task_request);

            String taskToken = task.getTaskToken();
            if (taskToken != null) {
                try {
                    executeDecisionTask(taskToken, task.getEvents());
                }
                catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        }
    }

    /**
     * The goal of this workflow is to execute at least one HelloFunction
     * successfully.
     *
     * We pass the workflow execution's input to the activity, and we use the
     * activity's result as the output of the workflow.
     */
    private static void executeDecisionTask(
            String taskToken, List<HistoryEvent> events)
        throws Throwable {
        List<Decision> decisions = new ArrayList<Decision>();
        String workflow_input = null;
        int scheduled_functions = 0;
        int running_functions = 0;
        boolean function_completed = false;
        String result = null;

        System.out.println("Executing the decision task for the history events: [");
        for (HistoryEvent event : events) {
            System.out.println("  " + event);
            EventType event_type = EventType.fromValue(event.getEventType());
            switch(event_type) {
            case WorkflowExecutionStarted:
                workflow_input =
                    event.getWorkflowExecutionStartedEventAttributes()
                         .getInput();
                break;
            case LambdaFunctionScheduled:
                scheduled_functions++;
                break;
            case ScheduleLambdaFunctionFailed:
                scheduled_functions--;
                break;
            case LambdaFunctionStarted:
                scheduled_functions--;
                running_functions++;
                break;
            case LambdaFunctionCompleted:
                running_functions--;
                function_completed = true;
                result = event.getLambdaFunctionCompletedEventAttributes()
                              .getResult();
                break;
            case LambdaFunctionFailed:
                running_functions--;
                break;
            case LambdaFunctionTimedOut:
                running_functions--;
                break;
            }
        }
        System.out.println("]");

        if (function_completed) {
            decisions.add(
                new Decision()
                    .withDecisionType(DecisionType.CompleteWorkflowExecution)
                    .withCompleteWorkflowExecutionDecisionAttributes(
                        new CompleteWorkflowExecutionDecisionAttributes()
                            .withResult(result)));
        }
        else {
            if (running_functions == 0 && scheduled_functions == 0) {
                AWSLambda lam = AWSLambdaClientBuilder.defaultClient();
                GetFunctionConfigurationResult function_config =
                    lam.getFunctionConfiguration(
                            new GetFunctionConfigurationRequest()
                                .withFunctionName("HelloFunction"));
                String function_arn = function_config.getFunctionArn();

                ScheduleLambdaFunctionDecisionAttributes attrs =
                    new ScheduleLambdaFunctionDecisionAttributes()
                        .withId("HelloFunction (Lambda task example)")
                        .withName(function_arn)
                        .withInput(workflow_input);

                decisions.add(
                        new Decision()
                            .withDecisionType(DecisionType.ScheduleLambdaFunction)
                            .withScheduleLambdaFunctionDecisionAttributes(attrs));
            }
            else {
                // an instance of HelloFunction is already scheduled or running.
                // Do nothing, another task will be scheduled once the activity
                // completes, fails or times out
            }
        }

        System.out.println("Exiting the decision task with the decisions " +
                decisions);

        swf.respondDecisionTaskCompleted(
            new RespondDecisionTaskCompletedRequest()
                .withTaskToken(taskToken)
                .withDecisions(decisions));
    }
}
