#!/usr/bin/env groovy

/*
    Author: Ryan Kajiura

    Repo: https://git.pyrsoftware.ca/stash/projects/AV/repos/jenkinsfile/browse

    Used in:
        - AV SmokeTest.jenkinsfile
        - AV E2ETests.jenkinsfile
    
    Code Snippit
		zip( 
			sourcePath: "${WORKSPACE}\\${env.cucumberDirectory}", 
			descPath: "${WORKSPACE}", 
			fileName: "${currentBuild.fullDisplayName}", 
			verboseLogging: verboseLogging
		);


    Date					Name					Description
	-----------------------------------------------------------------------
	March 29 2021				R. Kajiura			Initial 
*/


def call(Map stageParams = [:]) {
	if (stageParams.verboseLogging > 1) {
		echo "function start 'zip'...";
        stageParams.each { it -> 
            echo "parameters.... '${it.key}': '${it.value}' "; 
        };
        echo " ";		
	}
	
	try {
		if ( fileExists( sourcePath ) ) {
			if (verboseLogging > 1) {
				oPSOutput = powershell script: "Compress-Archive -Path '${stageParams.sourcePath}' -DestinationPath '${stageParams.descPath}\\${stageParams.fileName}.zip' -force -Verbose; ",
							returnStatus: true,
							encoding: "utf-8", 
							label: "ZippingVerbose";
				echo "PowerShell Status: ${oPSOutput}";
			}
			else {
				oPSOutput = powershell script: "Compress-Archive -Path '${stageParams.sourcePath}' -DestinationPath '${stageParams.descPath}\\${stageParams.fileName}.zip' -force; ",
							returnStatus: true,
							encoding: "utf-8", 
							label: "ZippingNonVerbose";
				echo "PowerShell Status: ${oPSOutput}";
			}
		}
		else {
			echo "File '${stageParams.sourcePath}' does not exists....";
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

	if (stageParams.verboseLogging > 1) {
		echo "function End 'zip' - '${stageParams.fileName}' report... ";
	}
	return true;
}