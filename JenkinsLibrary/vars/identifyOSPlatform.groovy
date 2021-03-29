#!/usr/bin/env groovy

/*
    Author: Ryan Kajiura

    Repo: https://git.pyrsoftware.ca/stash/projects/AV/repos/jenkinsfile/browse

    Used in:
        - AV SmokeTest.jenkinsfile
        - AV E2ETests.jenkinsfile
    
    Code Snippit
		identifyOSPlatform(
				verboseLogging: verboseLogging
			);

	Return options
		windows
		macosx
		linux


    Date					Name					Description
	-----------------------------------------------------------------------
	March 29 2021				R. Kajiura			Initial 
*/


def call(Map stageParams = [:]) {
	if (stageParams.verboseLogging > 1) {
		echo "function start 'identifyOSPlatform'...";
        stageParams.each { it -> 
            echo "parameters.... '${it.key}': '${it.value}' "; 
        };
        echo " ";		
	}    

	// https://stackoverflow.com/questions/44105814/how-to-determine-the-current-operating-system-in-a-jenkins-pipeline
    String osname = System.getProperty('os.name');

    if ( osname.startsWith('Windows') ) {
		return 'windows';
	}        
    else if ( osname.startsWith('Mac') ) {
		return 'macosx';
	}        
    else if ( osname.contains('nux') ) {
		return 'linux';
	}        
    else {
		throw new Exception("Unsupported os: ${osname}");
	}


	if (stageParams.verboseLogging > 1) {
		echo "function end 'identifyOSPlatform' ";
	}	
}