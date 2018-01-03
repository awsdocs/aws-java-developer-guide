.. Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

###################################
|SWF| Examples Using the |sdk-java|
###################################

|SWF|_ is a workflow-management service that helps developers build and scale distributed
workflows that can have parallel or sequential steps consisting of activities, child workflows or
even |LAM|_ tasks.

There are two ways to work with |SWF| using the |sdk-java|, by using the SWF *client* object, or by
using the |jflow|. The |jflow| is more difficult to configure initially, since it makes heavy use of
annotations and relies on additional libraries such as AspectJ and the Spring Framework. However,
for large or complex projects, you will save coding time by using the |jflow|. For more information,
see the |jflow-dg|_.

This section provides examples of programming |SWF| by using the |sdk-java| client directly.

.. toctree::
   :maxdepth: 1
   :titlesonly:

   swf-basics
   swf-hello
   swf-lambda-task
   swf-graceful-shutdown
   prog-services-swf-register-domain
   prog-services-swf-list-domains

