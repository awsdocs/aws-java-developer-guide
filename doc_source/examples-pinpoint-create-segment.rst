.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

##############################
Creating Segments in |PINlong|
##############################

.. meta::
   :description: How to update an app segment in Amazon pinpoint.
   :keywords: AWS for Java SDK code examples, amazon pinpoint segment

A user segment represents a subset of your users that's based on shared characteristics, such as
how recently a user opened your app or which device they use. The following example demonstrates
how to define a segment of users.

Create a Segment
================

Create a new segment in |PINlong| by defining dimensions of the segment in a
:aws-java-class:`SegmentDimensions <services/pinpoint/model/SegmentDimensions>` object.

**Imports**

.. literalinclude:: example_code/pinpoint/src/main/java/com/example/pinpoint/CreateSegment.java
   :lines: 18-30
   :language: java

**Code**

.. literalinclude:: example_code/pinpoint/src/main/java/com/example/pinpoint/CreateSegment.java
   :lines: 50, 58-74
   :dedent: 8
   :language: java

Next set the :aws-java-class:`SegmentDimensions <services/pinpoint/model/SegmentDimensions>`
object in a :aws-java-class:`WriteSegmentRequest
<services/pinpoint/model/WriteSegmentRequest>`, which in turn is used to create a
:aws-java-class:`CreateSegmentRequest <services/pinpoint/model/CreateSegmentRequest>` object.
Then pass the CreateSegmentRequest object to the
|pinpointclient|'s :methodname:`createSegment` method.

**Code**

.. literalinclude:: example_code/pinpoint/src/main/java/com/example/pinpoint/CreateSegment.java
   :lines: 76-82
   :dedent: 8
   :language: java

See the :sdk-examples-java-pinpoint:`complete example <CreateSegment.java>` on GitHub.

More Information
================

* :pin-ug:`Amazon Pinpoint Segments <segments>` in the |pin-ug|
* :pin-dg:`Creating Segments <segments>` in the |pin-dg|
* :pin-api:`Segments <segments>` in the |pin-api|
* :pin-api:`Segment <segment>` in the |pin-api|
