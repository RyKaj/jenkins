#!/usr/bin/env groovy

/*
    Author: Ryan Kajiura

    Requires Plugin 
		https://github.com/jenkinsci/htmlpublisher-plugin

    Repo: https://git.pyrsoftware.ca/stash/projects/AV/repos/jenkinsfile/browse

    Used in:
        - AV SmokeTest.jenkinsfile
        - AV E2ETests.jenkinsfile
    
    Code Snippit
		publishHTMLReports(
			path: env.cucumberDirectory, 
			fileName: testCase,
			verboseLogging: verboseLogging
		);



    Date					Name					Description
	-----------------------------------------------------------------------
	March 29 2021				R. Kajiura			Initial 
*/


def call(Map stageParams = [:]) {
	if (stageParams.verboseLogging > 1) {
		echo "function start 'publishHTMLReports'...";
        stageParams.each { it -> 
            echo "parameters.... '${it.key}': '${it.value}' "; 
        };
        echo " ";
	}


	try {
		publishHTML ([
						allowMissing: true,
						alwaysLinkToLastBuild: false,
						keepAll: false, 
						reportDir: "${stageParams.path}\\${stageParams.fileName}", 
						reportFiles: 'index.html', 
						reportName: "HTML Reports", 
						reportTitles: "${stageParams.fileName} Reports"
					]);
	}
	catch(e) { 
		echo "**********************************************************************";
		echo " ";
		echo " ";
		echo "ERROR: Function 'publishHTMLReports' - Error Exception: ${e}";
		// echo console.Error("ERROR: Function 'publishHTMLReports' - Error Exception: ${e}");
		echo " ";
		echo " ";
		echo "**********************************************************************";
	}
	finally {}

	if (stageParams.verboseLogging > 1) { 
		echo "function end 'publishHTMLReports' -  '${stageParams.fileName}' HTML Report...";
	}
	return true;
}