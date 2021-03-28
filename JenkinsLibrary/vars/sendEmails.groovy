#!/usr/bin/env groovy

/*
    Author: Ryan Kajiura

    


    Date					Name					Description
	-----------------------------------------------------------------------
	March 29 2021				R. Kajiura			Initial 
*/


def call (emailFrom, emailTo, subject, body, fileName, attachEventLog, compressEventLog, verboseLogging) {
	if (verboseLogging > 1) {
		echo "function start 'sendEmails' parameter: emailFrom: '${emailFrom}' ;;  emailTo: '${emailTo}' ;; subject: '${subject}' ;; body: '${body}' ;; fileName: '${fileName}' ;; attachEventLog: '${attachEventLog}' ;; compressEventLog: '${compressEventLog}' ";		
	}
	try {
		if (fileName.empty) {
			echo "Emailing Test Report with no attachment...";
			emailext  	to: "${emailTo}",
						from: "${emailFrom}",
						subject: "${subject}",
						body: "${body}",
						attachLog: attachEventLog,
						compressLog: compressEventLog;
		}
		else {
			echo "Emailing Test Report with attachment...";
			emailext  	to: "${emailTo}",
						from: "${emailFrom}",
						subject: "${subject}",
						body: "${body}", 
						attachmentsPattern: "*${fileName}*.zip",
						attachLog: attachEventLog,
						compressLog: compressEventLog;
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

	if (verboseLogging > 1) {
		echo "function start 'sendEmails'...";
	}

	return true;
}