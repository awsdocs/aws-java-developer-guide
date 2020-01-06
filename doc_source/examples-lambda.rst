.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

#################################################
Invoking, Listing, and Deleting Lambda Functions
#################################################

.. meta::
    :description: How to invoke, list, and delete a Lambda function by using the AWS SDK for Java 2.x.
    :keywords: Amazon Lambda, AWS SDK for Java 2.x, Lambda code examples,
               deleteFunction, invoke, listFunctions


This section provides examples of programming with the Lambda service client by using the AWS SDK for Java. To learn how to
create a Lambda function, see `How to Create AWS Lambda functions <https://docs.aws.amazon.com/toolkit-for-eclipse/v1/user-guide/lambda-tutorial.html>`_.

.. contents::
    :local:
    :depth: 1

.. _invoke-function:
Invoke a Lambda function
========================

You can invoke a Lambda function by creating an :aws-java-class:`AWSLambda <services/lambda/AWSLambda>`
object and invoking its :methodname:`invoke` method. Create an :aws-java-class:`InvokeRequest <services/lambda/model/InvokeRequest>`
object to specify additional information such as the function name and the payload to pass to the Lambda function. Function names
appear as *arn:aws:lambda:us-west-2:555556330391:function:HelloFunction*. You can retrieve the value by looking at the function in the AWS Console.

To pass payload data to a function, invoke the :aws-java-class:`InvokeRequest <services/lambda/model/InvokeRequest>`
object's :methodname:`withPayload` method and specify a String in JSON format, as shown in the following code example.

**Imports**

.. literalinclude:: lambda.java1.invoke.import.txt
   :language: java

**Code**

The following code example demonstrates how to invoke a Lambda function.

.. literalinclude:: lambda.java1.invoke.main.txt
   :language: java

See the complete example on `Github
<https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/lambda/src/main/java/com/example/lambda/LambdaInvokeFunction.java>`_.


.. _list-function:

List Lambda functions
=====================

Build an :aws-java-class:`AWSLambda <services/lambda/AWSLambda>`
object and invoke its :methodname:`listFunctions` method.
This method returns a :aws-java-class:`ListFunctionsResult <services/lambda/model/ListFunctionsResult>` object.
You can invoke this object's :methodname:`getFunctions` method to return a list of :aws-java-class:`FunctionConfiguration <services/lambda/model/FunctionConfiguration>` objects.
You can iterate through the list to retrieve information about the functions. For example, the following Java code example shows how to get each function name.


**Imports**

.. literalinclude:: lambda.java1.list.import.txt
   :language: java

**Code**

The following Java code example demonstrates how to retrieve a list of Lambda function names.

.. literalinclude:: lambda.java1.list.main.txt
   :language: java

See the complete example on `Github
<https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/lambda/src/main/java/com/example/lambda/ListFunctions.java>`_.


.. _delete-function:

Delete a Lambda function
========================

Build an :aws-java-class:`AWSLambda <services/lambda/AWSLambda>`
object and invoke its :methodname:`deleteFunction` method.
Create a :aws-java-class:`DeleteFunctionRequest <services/lambda/model/DeleteFunctionRequest>`
object and pass it to the :methodname:`deleteFunction` method. This object contains information such as the name of the function to delete.
Function names appear as *arn:aws:lambda:us-west-2:555556330391:function:HelloFunction*. You can retrieve the value by looking at the function in the AWS Console.

**Imports**

.. literalinclude:: lambda.java1.delete.import.txt
   :language: java

**Code**

The following Java code demonstrates how to delete a Lambda function.

.. literalinclude:: lambda.java1.delete.main.txt
   :language: java

See the complete example on `Github
<https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/lambda/src/main/java/com/example/lambda/DeleteFunction.java>`_.


