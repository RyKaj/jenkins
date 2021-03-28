#!/usr/bin/env groovy

/*
    Author: Ryan Kajiura

    Repo: https://git.pyrsoftware.ca/stash/projects/AV/repos/jenkinsfile/browse
    
    Code Snippit: 
        stageCSM (
            branch: constant.GIT_BRANCH,
            credentialsId: constant.GIT_REPO_CREDENTIALID,
            url: constant.GIT_REPO_URL
        )    


    Date					Name					Description
	-----------------------------------------------------------------------
	March 29 2021				R. Kajiura			Initial 
*/

// https://www.nclouds.com/blog/jenkins-shared-libraries/
// https://github.com/AndreyVMarkelov/jenkins-pipeline-shared-lib-sample/blob/master/vars/gitCheckout.groovy

def call(Map stageParams = [:]) {
 
    checkout (
        changelog: true,
        poll: false, 
        scm: [
            $class: "GitSCM",
            branches: [[ name: stageParams.branch ]],
            doGenerateSubmoduleConfigurations: false,
            extensions: [[
                        $class: "SubmoduleOption",
                        disableSubmodules: false,
                        parentCredentials: false,
                        recursiveSubmodules: true,
                        reference: '', 
                        timeout: 60, 
                        trackingSubmodules: false
                    ]], 
            gitTool: "git", 
            submoduleCfg: [],
            userRemoteConfigs: [[
                credentialsId: stageParams.credentialsId,
                url: stageParams.url
            ]]
        ]
    )

  }