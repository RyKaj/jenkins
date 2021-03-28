#!/usr/bin/env groovy

/*
    Author: Ryan Kajiura

    Requires Plugin
		https://github.com/jenkinsci/cucumber-reports-plugin        


    Date					Name					Description
	-----------------------------------------------------------------------
	March 29 2021				R. Kajiura			Initial 
*/


def call (path, fileName, paramBrowser, verboseLogging) {
	if (verboseLogging > 1) {
		echo "function start 'publishCucumberReports' parameter: path: '${path}' ;;  fileName: '${fileName}' ;; paramBrowser: '${paramBrowser}' ";		
	}

	try {
 		cucumber buildStatus: "${currentBuild.currentResult}",
					fileIncludePattern: "**/*.json",
					trendsLimit: 10,
					classifications: [[
						'key': 'Browser',
							'value': "${paramBrowser}"
					]];
	
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

	if (verboseLogging > 1) {
		echo "function start 'publishCucumberReports' -  '${fileName}' Cucumber Report...";
	}
	return true;
}