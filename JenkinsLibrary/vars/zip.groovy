#!/usr/bin/env groovy

/*
    Author: Ryan Kajiura

    


    Date					Name					Description
	-----------------------------------------------------------------------
	March 29 2021				R. Kajiura			Initial 
*/


def call (sourcePath, descPath, fileName, verboseLogging) {
	if (verboseLogging > 1) {		
		echo "function Start 'zip' parameter: sourcePath: '${sourcePath}' ;;  descPath: '${descPath}' ;;  fileName: '${fileName}' ";
	}

	try {
		if ( fileExists( sourcePath ) ) {
			if (verboseLogging > 1) {
				oPSOutput = powershell script: "Compress-Archive -Path '${sourcePath}' -DestinationPath '${descPath}\\${fileName}.zip' -force -Verbose; ",
							returnStatus: true,
							encoding: "utf-8", 
							label: "ZippingVerbose";
				echo "PowerShell Status: ${oPSOutput}";
			}
			else {
				oPSOutput = powershell script: "Compress-Archive -Path '${sourcePath}' -DestinationPath '${descPath}\\${fileName}.zip' -force; ",
							returnStatus: true,
							encoding: "utf-8", 
							label: "ZippingNonVerbose";
				echo "PowerShell Status: ${oPSOutput}";
			}
		}
		else {
			echo "File '${sourcePath}' does not exists....";
		}
		
	}
	catch(e) {
		echo "**********************************************************************";
		echo " ";
		echo " ";
		echo "ERROR: Function 'zippingReports' - Error Exception: ${e}";
		// echo console.Error("ERROR: Function 'zippingReports' - Error Exception: ${e}");
		echo " ";
		echo " ";
		echo "**********************************************************************";
	}
	finally {}

	if (verboseLogging > 1) {
		echo "function End 'zip' - '${fileName}' report... ";
	}
	return true;
}