#!/usr/bin/env groovy

/*
    Author: Ryan Kajiura

    Repo: https://git.pyrsoftware.ca/stash/projects/AV/repos/jenkinsfile/browse

    Used in:
        - AV SmokeTest.jenkinsfile
        - AV E2ETests.jenkinsfile
    
    Code Snippit
		sendEmails(
			emailFrom: "${constant.EMAIL_REPLY_FROM}", 
			emailTo: "${constant.EMAIL_DISTRIBUTION_LIST}", 
			subject: "Test Automation: ${currentBuild.fullDisplayName}", 
			body: "Please see attachment for static local test results on ${currentBuild.fullDisplayName}", 
			fileName: "${currentBuild.fullDisplayName}",
			attachEventLog: false, 
			compressEventLog: true, 
			verboseLogging: verboseLogging
		);

    Date					Name					Description
	-----------------------------------------------------------------------
	March 29 2021				R. Kajiura			Initial 
*/

def call(Map stageParams = [:]) {
	if (stageParams.verboseLogging > 1) {
		echo "function start 'sendEmails'...";
        stageParams.each { it -> 
            echo "parameters.... '${it.key}': '${it.value}' "; 
        };
        echo " ";
	}

	try {
		if (fileName.empty) {
			echo "Emailing Test Report with no attachment...";
			emailext  	to: "${stageParams.emailTo}",
						from: "${stageParams.emailFrom}",
						subject: "${stageParams.subject}",
						body: "${stageParams.body}",
						attachLog: stageParams.attachEventLog,
						compressLog: stageParams.compressEventLog;
		}
		else {
			echo "Emailing Test Report with attachment...";
			emailext  	to: "${stageParams.emailTo}",
						from: "${stageParams.emailFrom}",
						subject: "${stageParams.subject}",
						body: "${stageParams.body}", 
						attachmentsPattern: "*${stageParams.fileName}*.zip",
						attachLog: stageParams.attachEventLog,
						compressLog: stageParams.compressEventLog;
		}

	}
	catch(e) { 
		echo "**********************************************************************";
		echo " ";
		echo " ";
		echo "ERROR: Function 'sendEmails' - Error Exception: ${e}";
		// echo console.Error("ERROR: Function 'sendEmails' - Error Exception: ${e}");
		echo " ";
		echo " ";
		echo "**********************************************************************";

	}
	finally {}

	if (stageParams.verboseLogging > 1) {
		echo "function start 'sendEmails'...";
	}

	return true;
}