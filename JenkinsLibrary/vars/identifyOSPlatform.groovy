#!/usr/bin/env groovy

/*
    Author: Ryan Kajiura

    


    Date					Name					Description
	-----------------------------------------------------------------------
	March 29 2021				R. Kajiura			Initial 
*/


def call (verboseLogging) {
	if (verboseLogging > 1) {
		echo "function start 'identifyOSPlatform' ";
	}

    String osname = System.getProperty('os.name');

    if ( osname.startsWith('Windows') ) {

	switch(osname) {
		case string s when s.startsWith('Windows'):
			//return 'windows';
			return true;
		case string s when s.startsWith('Mac'):
			//return 'mac';
			return false;
		case string s when s.contains('nux'):
			//return 'linux';
			return false;
		default:
			throw new Exception("Unsupported os: ${osname}");	
	}


	if (verboseLogging > 1) {
		echo "function end 'identifyOSPlatform' ";
	}	
}