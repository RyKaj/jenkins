#!/usr/bin/env groovy

/*
    Author: Ryan Kajiura

    Requires Plugin
		https://github.com/jenkinsci/cucumber-reports-plugin      

    Repo: https://git.pyrsoftware.ca/stash/projects/AV/repos/jenkinsfile/browse

    Used in:
        - AV SmokeTest.jenkinsfile
        - AV E2ETests.jenkinsfile
    
    Code Snippit
		publishCucumberReports(
			path: env.cucumberDirectory, 
			fileName: testCase, 
			paramBrowser: paramBrowser, 
			verboseLogging: verboseLogging
		);
    

    Date					Name					Description
	-----------------------------------------------------------------------
	March 29 2021				R. Kajiura			Initial 
*/


def call(Map stageParams = [:]) {
	if (stageParams.verboseLogging > 1) {
		echo "function start 'publishCucumberReports'...";
        stageParams.each { it -> 
            echo "parameters.... '${it.key}': '${it.value}' "; 
        };
        echo " ";
	}

	try {

 		cucumber buildStatus: "${currentBuild.currentResult}",
					fileIncludePattern: "**/*.json",
					trendsLimit: 10,
					classifications: [[
						'key': 'Browser',
						'value': "${stageParams.paramBrowser}"
					]];
	
 		// cucumber buildStatus: "${currentBuild.currentResult}",
		// 			fileIncludePattern: "${path}\\*.json",
		// 			trendsLimit: 10,
		// 			classifications: [[
		// 				'key': 'Browser',
		// 				'value': 'Firefox'
		// 			]]
	}
	catch(e) { 
		echo "**********************************************************************";
		echo " ";
		echo " ";
		echo "ERROR: Function 'publishCucumberReports' - Error Exception: ${e}";
		// echo console.Error("ERROR: Function 'publishCucumberReports' - Error Exception: ${e}");
		echo " ";
		echo " ";
		echo "**********************************************************************";
	}
	finally {}

	if (stageParams.verboseLogging > 1) {
		echo "function END 'publishCucumberReports'...";
	}
	return true;
}