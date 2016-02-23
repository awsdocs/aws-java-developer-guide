.. Copyright 2010-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

############################
aws-java-developer-guide
############################

.. Links used in the README. For sanity's sake, keep this list sorted alphabetically. ;)
.. _`available sphinx builders`: http://www.sphinx-doc.org/en/stable/builders.html
.. _`aws sdk for java developer guide`: http://docs.aws.amazon.com/AWSSdkDocsJava/latest/DeveloperGuide/welcome.html
.. _`forking the repository`: https://help.github.com/articles/fork-a-repo/
.. _`pull request`: https://help.github.com/articles/using-pull-requests/
.. _`shared content`: https://github.com/awsdocs/aws-doc-shared-content
.. _extlinks: http://www.sphinx-doc.org/en/stable/ext/extlinks.html
.. _issues: https://github.com/awsdocs/aws-java-developer-guide/issues
.. _restructuredtext: http://docutils.sourceforge.net/rst.html
.. _sphinx: http://www.sphinx-doc.org/en/stable/
.. _substitutions: http://www.sphinx-doc.org/en/stable/rest.html#substitutions


This repository contains content for the official `AWS SDK for Java Developer Guide`_.

The developer guide is written in reStructuredText_ and built using Sphinx_. It relies on shared
content that is provided in the `shared content`_ repository, which is also provided on GitHub.


Reporting issues
================

You can use the Issues_ section of this repository to report problems in the documentation. *When
submitting an issue, please indicate*:

* what page (a URL or filename is best) the issue occurs on.

* what the issue is, using as much detail as you can provide. For many issues, this might be as
  simple as "The page has a typo; the word 'complie' in the third paragraph shoud be 'compile'." If
  the issue is more complex, please describe it with enough detail that it's clear to the AWS
  documentation team what the problem is.


Contributing fixes and updates
==============================

To contribute your own documentation fixes or updates, please use the Github-standard procedures for
`forking the repository`_ and submitting a `pull request`_.

Note that many common substitutions_ and extlinks_ found in these docs are sourced from the `shared
content`_ repository--if you see a substitution used that is not declared at the top of the source
file or in the ``_includes.txt`` file, then it is probably defined in the shared content.


Building the documentation
--------------------------

If you are planning to contribute to the docs, you should build your changes and review them before
submitting your pull request.

**To build the docs:**

1. Make sure that you have downloaded and installed Sphinx_.
2. Run the ``build_docs.py`` script in the repository's root directory.

The build process will automatically download a snapshot of the `shared content`_, combine it in the
``build`` directory and will generate output into the ``output`` directory.

``build_docs.py`` can take any of the `available Sphinx builders`_ as its argument. For example, to
build the docs into a single HTML page, you can use the ``htmlsingle`` target, like so::

 python build_docs.py htmlsingle


Copyright and license
=====================

All content in this repository, unless otherwise stated, is Copyright © 2010-2016, Amazon Web
Services, Inc. or its affiliates. All rights reserved.

Except where otherwise noted, this work is licensed under a `Creative Commons
Attribution-NonCommercial-ShareAlike 4.0 International License
<http://creativecommons.org/licenses/by-nc-sa/4.0/>`_ (the "License"). Use the preceding link for a
human-readable summary of the license terms. The full license text is available at:
http://creativecommons.org/licenses/by-nc-sa/4.0/legalcode and in the LICENSE file accompanying this
repository.

