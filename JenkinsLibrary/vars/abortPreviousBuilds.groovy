#!/usr/bin/env groovy

/*
    Author: Ryan Kajiura

    


    Date					Name					Description
	-----------------------------------------------------------------------
	March 29 2021				R. Kajiura			Initial 
*/
 

def call() {
	def buildNumber = env.BUILD_NUMBER.toInteger();
	def currentJob = Jenkins.instance.getItemByFullName(env.JOB_NAME);

	def stoppedBuild = false;
	for (def build : currentJob.builds) {
		if ( build.isBuilding() && build.number.toInteger() < buildNumber ) {
			build.doStop();
			echo "build ${build.number.toInteger()} is aborted";
			stoppedBuild = true;
		}
	}
	
	if (!stoppedBuild){
		echo "No previous builds are in building status";
	}
}