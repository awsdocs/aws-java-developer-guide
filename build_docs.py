#!/usr/bin/env python
# -*- coding: utf-8 -*-
#
# Copyright 2010-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
#
# This file is licensed under the Apache License, Version 2.0 (the "License").
# You may not use this file except in compliance with the License. A copy of the
# License is located at
#
# http://aws.amazon.com/apache2.0/
#
# This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
# OF ANY KIND, either express or implied. See the License for the specific
# language governing permissions and limitations under the License.
#
# ------------------------------------------------------------------
# Documentation build script for AWS Sphinx documentation on GitHub.
# ------------------------------------------------------------------

import sys, os
import subprocess
import shutil

SPHINX_MISSING = """
You must have Sphinx installed to use this script!

Go to http://www.sphinx-doc.org for information about installing and using
Sphinx.
"""

FAILED_CHECKOUT = """
Couldn't clone repository. Please make sure that you have 'git' installed and
that you can access GitHub repositories using SSH.
"""

# The file to load extra dependencies from.
DEPENDENCIES_FILE = 'dependencies.txt'
DEPENDENCIES_DIR = 'build_dependencies'

BUILD_DIR = 'doc_build'
OUTPUT_DIR = 'doc_output'
SOURCE_DIR = 'doc_source'


def check_and_remove_dir(dir_name):
    """Check to see if the named directory exists. If it does, then remove it.
    Throw an exception if the directory can't be removed."""

    if os.path.exists(dir_name):
        print("Removing directory: " + dir_name)
        try:
            shutil.rmtree(dir_name)
        except:
            print("Couldn't remove " + dir_name)
            print("Remove this directory before building!")
            sys.exit(1)


def copy_dir_contents_with_overwrite(input_dir_name, output_dir_name):
    """Copy the contents of a directory into another, overwriting files if they
    exist."""

    # if output_dir_name isn't a location, make it so.
    if not os.path.exists(output_dir_name):
        os.makedirs(output_dir_name)

    dir_entries = os.listdir(input_dir_name)

    for dir_entry in dir_entries:
        input_path = os.path.join(input_dir_name, dir_entry)
        output_path = os.path.join(output_dir_name, dir_entry)

        if os.path.isdir(input_path):
            copy_dir_contents_with_overwrite(input_path, output_path)
        else:
            shutil.copyfile(input_path, output_path)


def clone_and_copy_dependency(git_url, dep, subdir = ''):
    print("Getting content from dependency: " + git_url)
    try:
        subprocess.check_call(['git', 'clone', '--depth', '1', git_url,
            os.path.join(DEPENDENCIES_DIR, dep)])
    except:
        print(FAILED_CHECKOUT)
        sys.exit(1)

    # copy the contents of the subdir into the build directory.
    path_to_copy = os.path.join(DEPENDENCIES_DIR, dep, subdir)
    copy_dir_contents_with_overwrite(path_to_copy, BUILD_DIR)


def run():
    # try to import requirements, and complain if they can't be found.
    try:
        from sphinx import version_info as sphinx_version
        print("Using Sphinx version %s.%s.%s" % sphinx_version[0:3])
    except:
        print(SPHINX_MISSING)
        sys.exit(1)

    build_target = 'html' # by default
    cmd_switches = []

    for arg in sys.argv[1:]:
        if arg.startswith('--'):
            cmd_switches.append(arg)
        else:
            # the only non-switch argument is the output format.
            build_target = arg

    print("Building '%s' target." % build_target)

    #
    # Step 0: empty the build dir if it's there.
    #
    check_and_remove_dir(BUILD_DIR)
    check_and_remove_dir(DEPENDENCIES_DIR)
    check_and_remove_dir(OUTPUT_DIR)

    # Step 1: Load any dependencies. Dependencies consist of a tuple: a git repository and a
    # subdirectory to copy.
    dependencies = []
    if os.path.exists(DEPENDENCIES_FILE):
        os.mkdir(DEPENDENCIES_DIR)
        dep_num = 0
        dep_file_contents = open(DEPENDENCIES_FILE, 'r').readlines()
        for line in dep_file_contents:
            # ignore comments
            bare_line = line.strip()
            if bare_line == '':
                continue
            elif not bare_line.startswith('#'):
                (git_url, subdir) = line.split()
                if subdir == None:
                    subdir = ''
                clone_and_copy_dependency(git_url, str(dep_num), subdir)
                dep_num += 1

    #
    # Step 2: copy the contents of SOURCE_DIR into the BUILD_DIR.
    #
    print("Copying doc sources from %s to %s" % (SOURCE_DIR, BUILD_DIR))
    copy_dir_contents_with_overwrite(SOURCE_DIR, BUILD_DIR)

    #
    # Append the contents of any files in the 'build/_conf' directory to the
    # project's conf.py file (so that shared content can add commonly-used
    # extlinks, etc.).
    #
    conf_py_path = os.path.join(BUILD_DIR, 'conf.py')
    extra_conf_path = os.path.join(BUILD_DIR, '_conf')
    # first, open the conf.py file for appending...
    with open(conf_py_path, 'a') as conf_file:
        # now, add the contents of each file in alpha-sorted order.
        for filename in sorted(os.listdir(extra_conf_path)):
            print(" - %s" % filename)
            conf_file.write('# ** added by extra conf file: %s **\n' % filename)
            with open(os.path.join(extra_conf_path, filename), 'r') as extra_conf_file:
                conf_file.write(extra_conf_file.read())
            conf_file.write('# ** end of content from %s **\n' % filename)

    #
    # Step 3: build the docs
    #
    print("Building documentation.")
    try:
        subprocess.check_call(['sphinx-build', '-b', build_target, BUILD_DIR,
            OUTPUT_DIR])
    except:
        print(FAILED_CHECKOUT)
        sys.exit(1)

    #
    # Step 4: Clean up the build dir and shared content.
    #
    if '--noclean' not in cmd_switches:
        print("Cleaning up.")
        check_and_remove_dir(BUILD_DIR)
        check_and_remove_dir(DEPENDENCIES_DIR)

    print("Finished! You'll find the built docs in the '%s' directory." %
            OUTPUT_DIR)


# If run from the command-line...
if __name__ == '__main__':
    run()

