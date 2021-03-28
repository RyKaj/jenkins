#!/usr/bin/env groovy

/*
    Author: Ryan Kajiura

	Requires Plugin https://github.com/jenkinsci/htmlpublisher-plugin
    


    Date					Name					Description
	-----------------------------------------------------------------------
	March 29 2021				R. Kajiura			Initial 
*/


def call (path, fileName, verboseLogging) {
	if (verboseLogging > 1) {
		echo "function start 'publishHTMLReports' parameter: path: '${path}' ;;  fileName: '${fileName}'  ";
	}

	try {
		publishHTML ([
						allowMissing: true,
						alwaysLinkToLastBuild: false,
						keepAll: false, 
						reportDir: "${path}\\${fileName}", 
						reportFiles: 'index.html', 
						reportName: "HTML Reports", 
						reportTitles: "${fileName} Reports"
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

	if (verboseLogging > 1) { 
		echo "function end 'publishHTMLReports' -  '${fileName}' HTML Report...";
	}
	return true;
}