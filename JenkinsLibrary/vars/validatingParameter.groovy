#!/usr/bin/env groovy

/*
    Author: Ryan Kajiura

    


    Date					Name					Description
	-----------------------------------------------------------------------
	March 29 2021				R. Kajiura			Initial 
*/


def call (oParam, verboseLogging) {
	if (verboseLogging > 1) {
		echo "function start 'validatingParameter' parameter: oParam: '${oParam}'  ";
	}

	oParam.each { key, value ->
		switch ( key ) {
			// Single select items
			case [ "paramLogging", "paramBrowser", "paramEnvironment" ]:
				// echo "Switch expression Single-Select...";
				if (value.size() == 0) {
					echo "**********************************************************************";
					echo " ";
					echo " ";
					echo "ERROR: Parameter '${key}' was not set...;";
					// echo console.Error("ERROR: Parameter '${key}' was not set...;");
					echo " ";
					echo " ";
					echo "**********************************************************************";
					throw new Exception( "ERROR: Parameter '${key}' was not set..." );
				}
				break;

			// Muli-Select items
			case [ "paramLicenses", "paramTestSuites" ]:
				// echo "Switch expression Multi-Select..."

				if ( value.size() == 0 ) {
					echo "**********************************************************************";
					echo " ";
					echo " ";
					echo "ERROR: Parameter '${key}' was not set...;";
					// echo console.Error("ERROR: Parameter '${key}' was not set...;");
					echo " ";
					echo " ";
					echo "**********************************************************************";
					throw new Exception( "ERROR: Parameter '${key}' was not set..." );
				}
				break;

			// Checkbox / Boolean
			case [ "paramMobile" ]:
				// println "Switch expression Boolean...'${value}' ";
				break;
			default:
				// println "Switch expression Default...";
				break;

		} // switch
	} // params.each
	
	if (verboseLogging > 1) { 
		echo "function End 'validatingParameter'...";
	}

}