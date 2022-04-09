/*	
	Author: Ryan Kajiura

	Plugin References:
		- Console Output - https://plugins.jenkins.io/ansicolor/
		- Slack https://plugins.jenkins.io/slack/
	
	External Library - https://github.com/RyKaj/PowerShell
		- DeploymentDebugLogs.ps1
		- DeploymentDebugLogs.sh



	Shared Library:
        resources
		src
            org/ConsoleLog.groovy
            org/Constants.groovy
		var
            diagnosticLogs.groovy
		    stageCSM.groovy


	Resources:
	https://www.youtube.com/watch?v=bYi4IXep2mk

*/



// @Library(['<library_1>', '<library_1>']) _
@Library('<reponame>@<branch>') _ 

// Source Control Information
def gitRepoURL = 'ssh://<username>@<server_name:port>/... .git';
def gitRepoCredentialId = 'f96cf400-5c1d-463a-beac-08c04379fa3d';
def gitBranch = '<branch>';

// Notification Information
def slackToken = '<slack_token> ';
def slackChannel = '<slack_channel>';
def slackDomain = '<slack_domain>';

def emailFrom  = '<no-reply@<domain name>';
def emailDistribution = '<email_address>';	// comma separator for multiple emails


def oScriptOutput = "";			// PowerShell returnStatus or returnStdout or SH
def osPlatform = true;


node() {
	// ---------------------------------------------------------------------
	// Shared Library Configuration
	// ---------------------------------------------------------------------
	// https://medium.com/@AndrzejRehmann/private-jenkins-shared-libraries-540abe7a0ab7
	// https://github.com/aleksei-bulgak/jenkins-shared-library-example
	// https://github.com/hoto/jenkinsfile-examples/blob/master/jenkinsfiles/052-shared-library-using-classes.groovy
	// jsl = library (
	// 	identifier: 'CommonService',
	// 	retriever: modernSCM ([
	// 		$class: 'GitSCMSource',
	// 		remote: 'https://rr_admin:117e2eed75077338029ccdcb98b4eee821@git.pyrsoftware.ca/stash/scm/av/jenkinsfile.git',
	// 		changelog: false
	// 	])
	// ) // library
	// // def commonLibrary = jsl.com.mycompany.jenkins.Build.new(this)
  	// // commonLibrary.setBuildDescription...


}

pipeline { 
	agent any
	//agent { label "${paramAgent}" }  // http://10.11.6.39:8080/computer/AV-IOM-W10-161/
	// agent {
	// 	dockerfile {
	// 		args  '-v $HOME/.gradle:/root/.gradle'
	// 	}
	// }


	// tools { 
	// 	jdk 'jdk1.8.0_101'
	// 	nodejs "12.16.1"
	// } //tools

	// triggers {
	// 	cron('0 */4 * * 1-5')
	//  pollCSM('0 */4 * * 1-5')
	// }

	environment { 
		major = 1;
		minor = 1;
		version = "${env.JOB_BASE_NAME}-${env.major}.${env.minor}.${env.BUILD_NUMBER}";
	} //environment
		
 	options {		
		// ansiColor('xterm');


		// Persist artifacts and console output for the specific number of recent Pipeline runs. 
		// daysToKeepStr: history is only kept up to this days.
		// numToKeepStr: only this number of build logs are kept.
		// artifactDaysToKeepStr: artifacts are only kept up to this days.
		// artifactNumToKeepStr: only this number of builds have their artifacts kept.		
		buildDiscarder(
				logRotator(
					numToKeepStr: '3', 
					artifactNumToKeepStr: '3'
				)
			);

		// Perform the automatic source control checkout in a subdirectory of the workspace.
		// checkoutToSubdirectory('foldername');

		// Disallow concurrent executions of the Pipeline. Can be useful for preventing simultaneous accesses to shared resources, etc.
		// disableConcurrentBuilds();

		// Do not allow the pipeline to resume if the controller restarts.
		// disableResume();

		// This is used in top-level agent declaration and this will allow the process to run in the different Container each time.
		// newContainerPerStage ();

		// Allows overriding default treatment of branch indexing triggers. If branch indexing triggers are disabled at the multibranch or organization label
		// true: will enable them for this job only.
		// false: will disable branch indexing triggers for this job only.	
		// overrideIndexTriggers(true); or overrideIndexTriggers(false);

		// Preserve stashes from completed builds, for use with stage restarting. 
		// preserveStashes(); - No parameter: to preserve the stashes from the most recent completed build
		// preserveStashes( buildCount: 5); - set parameter: to preserve the stashes from the n most recent completed builds.

		// Set the quiet period, in seconds, for the Pipeline, overriding the global default.
		// quietPeriod(30);

		// On failure, retry the entire Pipeline the specified number of times.
		// retry(3);

		// Skip checking out code from source control by default in the agent directive. 
		// skipDefaultCheckout();

		// Skip stages once the build status has gone to UNSTABLE. 
		// skipStagesAfterUnstable();

		// Set a timeout period for the Pipeline run, after which Jenkins should abort the Pipeline. 
		// https://www.jenkins.io/doc/pipeline/steps/workflow-basic-steps/#timeout-enforce-time-limit
			// time:		The length of time for which this step will wait before cancelling the nested block.
			// activity: 	Timeout after no activity in logs for this block instead of absolute duration.
			// unit: 		The unit of the time parameter. Defaults to 'MINUTES' if not specified.
			// 			Values: NANOSECONDS, MICROSECONDS, MILLISECONDS, SECONDS, MINUTES, HOURS, DAYS
		// timeout(time: 1, unit: 'HOURS');

		// Prepend all console output generated by the Pipeline run with the time at which the line was emitted.
		// timestamps();

		// Set failfast true for all subsequent parallel stages in the pipeline. 
		// parallelsAlwaysFailFast();

	} // options

	stages { 
		stage ('Setup') { 
			steps  {
				script {
					echo " ";				
					echo "--------------------- Pre-Build Start ------------------------------";

					// echo " ";
					// echo "Evaluating Loggging....";
					// LoggingLevel logLevel = paramLogging;
					verboseLogging = true;

					echo " ";
					echo "Identifying Jenkins OS Platform....";
					osPlatform = identifyOSPlatform(
							logging: verboseLogging
						);

					if ( verboseLogging > 1 ) {
                        echo "Executing DiagnosticLogs Information...";
                        diagnosticLogs(
                            platform: osPlatform,
                            logging: verboseLogging
                        );					
					}

					echo " ";
					echo "--------------------- Jenkins Local Variables Values ------------------------------";	
					echo "Jenkins Job - User Id: ${currentBuild.getBuildCauses()[0].userId}";
					echo "Jenkins Job - User Name: ${currentBuild.getBuildCauses()[0].userName}";
					echo "Jenkins Job - Short Description: ${currentBuild.getBuildCauses()[0].shortDescription}";
					echo "BRANCH_NAME: ${BRANCH_NAME}";
					echo "BUILD_ID: ${BUILD_ID}";
					echo "BUILD_DISPLAY_NAME: ${BUILD_DISPLAY_NAME}";
					echo "BUILD_TAG: ${BUILD_TAG}";
					echo "BUILD_NUMBER: ${BUILD_NUMBER}";
					echo "BUILD_URL: ${BUILD_URL}";
					echo "EXECUTOR_NUMBER: ${EXECUTOR_NUMBER}";
					echo "JENKINS_HOME: ${JENKINS_HOME}";
					echo "JENKINS_URL: ${JENKINS_URL}";
					echo "JOB_NAME: ${JOB_NAME}";
					echo "JOB_BASE_NAME: ${JOB_BASE_NAME}";
					echo "NODE_NAME: ${NODE_NAME}";
					echo "NODE_LABELS: ${NODE_LABELS}";
					echo "WORKSPACE: ${WORKSPACE}";
					echo "JOB_URL: ${JOB_URL}";
					echo " ";
					echo "--------------------- Pre-Build End ------------------------------";
				} // Script			

			} // step

		} // Stage - setup
		
		stage ('Setup - SonarQube') {
			agent { label 'master' }
			echo "SonarQube Setup..."
			echo 'Setting up Prep for SonarStashing ant build.xml'
			dir("${env.JENKINS_HOME}/buildContent/linux/") {
				stash includes: 'build.xml', 
				name: 'ant-sonar'
			}
		}

		stage ("Source Control") {
			steps {
				checkout (
					changelog: true,
					poll: false, 
					scm: [
						$class: "GitSCM",
						branches: [[ name: gitBranch ]],
						doGenerateSubmoduleConfigurations: false,
						extensions: [[
									$class: "SubmoduleOption",
									disableSubmodules: false,
									parentCredentials: false,
									recursiveSubmodules: true,
									reference: '', 
									timeout: 60, 
									trackingSubmodules: false
								]], 
						gitTool: "git", 
						submoduleCfg: [],
						userRemoteConfigs: [[
							credentialsId: gitRepoCredentialId,
							url: gitRepoURL
						]]
					]
				)
				
				script {
					if ( verboseLogging == true ) {
						echo "Showing workspace...";
						if (osPlatform == 'windows') {
							powershell """
								dir
							""";
						}
						else {
							sh """
								ls -l
							"""
						}
					}
				} // script
			} // steps
		} // Stage "Source Control"

		stage ('Build') { 
			steps { 
				echo  'Running build phase. ' 
			}
		} // Stage - Build
		stage ('Unit Test & Code Coverage') { 
			steps { 
				echo  'Unit Test...' 
			}
			steps { 
				echo  'Code Coverage Report...' 
			}
		} // Stage - Unit Test * Code Coverage

		stage ('Static Code Analysis') {
			parallel {
				stage ( 'SonarQube' ) {
					steps {
						unstash 'ant-sonar'
						sh 'ant sonar'
					}
				}
				stage ( 'Xray-Scan' ) {
					steps {
						xrayScan (
							serverId: 'artifactory',
							failBuild: false
						)
					}
				}
			}
		} // Stage - Static Code Analysis

		stage ('Artifactory') { 
			agent { label 'jenkins-ls05' }
			steps  {
				script {
						oScriptOutput = sh script: "curl -X GET -u ${ARTIFACTORY_USER}:${ARTIFACTORY_JENKINS_CI_API_TOKEN} '${ARTIFACTORY_SERVER}/api/storage/packagesraw/${params.Package}' ",
								encoding: 'UTF-8', 
								label: "Artifactory - GET",
								returnStatus: true;
						echo "Response GET - Return Status: ${oScriptOutput}";

						oScriptOutput = sh script: "curl -X POST -u ${ARTIFACTORY_USER}:${ARTIFACTORY_JENKINS_CI_API_TOKEN} '${ARTIFACTORY_SERVER}/api/copy/packagesraw/${params.Package}?to=/releasesraw/${params.Package}' ",
								encoding: 'UTF-8', 
								label: "Artifactory - POST",
								returnStatus: true;
						echo "Response POST - Return Status: ${oScriptOutput}";
				}
// PATH = '/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/home/jenkins/.local/bin:/home/jenkins/bin'
// ImageName = 'starsweb/sw-vader'
// DockerRegistry = 'devdocker.artifactory.pyrsoftware.ca'
// DockerVersionTag = "${version}".replaceAll("\\+", '-')
// DockerTag = "${DockerRegistry}/${ImageName}:${DockerVersionTag}"
// clientFolder =  "starsweb-client-${version}"
// staticFolder =  "starsweb-static-${version}"
// clientTarball = "${clientFolder}.tar.gz"
// staticTarball = "${staticFolder}.tar.gz"
// HELM_PACKAGE = "sw-vader-${version}.tgz"
// HELM_VERSION = "${version}"
// ARTIFACTORY_CREDS = credentials('096681c5-1b9d-4789-82cd-ef9851bae9c0')
// AwsDefaultRegion = 'eu-west-2'
// BaseImage = '126680238105.dkr.ecr.eu-west-2.amazonaws.com/parent/node:14.15.1-alpine'
// Jf_Repository = 'packagesraw'
// Jf_folder = 'starswebclient'
// script {
//   packaging.Run(AwsDefaultRegion, ARTIFACTORY_CREDS, version, HELM_VERSION, HELM_PACKAGE, staticTarball, clientTarball, staticFolder, clientFolder, DockerTag, DockerRegistry, BaseImage, GIT_COMMIT, buildUser, buildTime, branchName)
// }
// rtUpload (
//   serverId: 'artifactory',
//   spec:
//     """{
//     "files": [
//       {
//       "pattern": "${clientTarball}",
//       "target": "${Jf_Repository}/${Jf_folder}/${clientTarball}"
//       },
//       {
//       "pattern": "${staticTarball}",
//       "target": "${Jf_Repository}/${Jf_folder}/${staticTarball}"
//       }
//     ]
//     }"""
// )
// rtPublishBuildInfo (
//   serverId: 'artifactory',
// )


			} 
		}		

		stage ('Deploy to QA') { 
			steps  {} 
		} // Stage - Static Code Analysis

		stage ('Deploy to UAT') { 
			steps  {} 
		}
		stage ('Deploy to Prod') { 
			steps  {} 
		}

		// Execute dependency Jenkins job within the same instance
		stage ('Execute Dependency Job - Internal') { 			
            when {
				expression {
                    return ( "${currentBuild.currentResult}" == "SUCCESS" )
                }
			}					
			steps  {
				script {
					try {
						def dependencyJenkinsJob = "AV SmokeTest";
						echo "Executing Job - '${ dependencyJenkinsJob}'... ";
						build 	job:  dependencyJenkinsJob, 
								wait: false, 
								parameters: [
									[ 
										$class: 'StringParameterValue',
										name: 'paramEnvironment', 
										value: paramEnvironment
									],
									[
										$class: 'StringParameterValue', 
										name: 'paramLicenses',
										value: paramLicenses
									],
									[
										$class: 'StringParameterValue', 
										name: 'paramBrowser',
										value: paramBrowser
									]
								]
					}
					catch (e) {}
					finally {}
			
				}
			} 
		}

		// Execute dependency Jenkins job on a different Jenkins instance
		stage ('Execute Dependency Job - External') { 
            when {
				expression {
                    return ( "${currentBuild.currentResult}" == "SUCCESS" )
                }
			}				
			steps  {
				script {
					try {
						def paramJenkinsURL = "10.11.6.39:8080";
						def paramJenkinsSmokeTest  = "_RKPOC";
						def paramJenkinsUser = "rr_admin";
						def paramJenkinsToken = "117e2eed75077338029ccdcb98b4eee821";
						def url = "http://${paramJenkinsURL}/job/${paramJenkinsSmokeTest}/buildWithParameters";
						def parms = "{\"parameter\": [{ paramAgent: \"${paramAgent}\", paramMobile: ${paramMobile}, paramBrowser: \"${paramBrowser}\", paramEnvironment: \"${paramEnvironment}\", paramLicenses: \"${paramLicenses}\", paramCallerJobName: \"${paramCallerJobName}\" }] }";


						def oScriptOutput = sh script: "curl -v -X POST ${url} --user ${paramJenkinsUser}:${paramJenkinsToken} -H 'Content-Type: application/json' --data-urlencode json='${parms}' ",
									encoding: 'UTF-8', 
									label: "ExecuteSmokeTest",
									returnStatus: true;
						echo "Response - Return Status: ${oScriptOutput}";

					}
					catch (e) {}
					finally {}
				}

			}
		}
	}
	post {
		always {
			// Run the steps in the post section regardless of the completion status of the Pipeline’s or stage’s run.
			echo "Post - always...";
		}
		changed {
			// Only run the steps in post if the current Pipeline’s or stage’s run has a different completion status from its previous run.
			echo "Post - changed...";
		} 
		fixed {
			// Only run the steps in post if the current Pipeline’s or stage’s run is successful and the previous run failed or was unstable.
			echo "Post - fixed...";
		}		
		regression {
			// Only run the steps in post if the current Pipeline’s or stage’s run’s status is failure, unstable, or aborted and the previous run was successful.
			echo "Post - regression...";
		}	
		aborted {
			// Only run the steps in post if the current Pipeline’s or stage’s run has an "aborted" status, usually due to the Pipeline being manually aborted. This is typically denoted by gray in the web UI.
			echo "Post - aborted...";
		}
		failure {
			// Only run the steps in post if the current Pipeline’s or stage’s run has a "failed" status, typically denoted by red in the web UI.
			echo "Post - failure...";
			// script {
				// slackSend(
				// 	message: "Build ${currentBuild.currentResult} - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)",
				// 	channel: slackChannel,
				// 	teamDomain: slackDomain,
				// 	tokenCredentialId: slackToken,
				// 	color: 'red',
				// 	iconEmoji: ':octagonal_sign:'
				// )

				// sendEmails (
				// 	"${emailFrom}", 
				// 	"${emailDistribution}", 
				// 	"Test Automation: ${currentBuild.currentResult}: ${currentBuild.fullDisplayName}", 
				// 	"Job ${currentBuild.fullDisplayName} ${currentBuild.currentResult} failed. See attchments to troubleshoot. ", 
				// 	"", 
				// 	true, 
				// 	true, 
				// 	verboseLogging
				// );
			// } // script
		}
		success {
			// Only run the steps in post if the current Pipeline’s or stage’s run has a "success" status, typically denoted by blue or green in the web UI
			echo "Post - success...";
			// script {
				// slackSend(
				// 	message: "Build ${currentBuild.currentResult} - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)",
				// 	channel: slackChannel,
				// 	teamDomain: slackDomain,
				// 	tokenCredentialId: slackToken,
				// 	color: 'green',
				// 	iconEmoji: ':rocket:'
				// )

				// sendEmails (
				// 	"${emailFrom}", 
				// 	"${emailDistribution}", 
				// 	"Test Automation: ${currentBuild.currentResult}: ${currentBuild.fullDisplayName}", 
				// 	"Job ${currentBuild.fullDisplayName} ${currentBuild.currentResult} ran successfully. ", 
				// 	"", 
				// 	false, 
				// 	true, 
				// 	verboseLogging
				// );


				// } // for
			// } // script
		}
		unstable {
			// Only run the steps in post if the current Pipeline’s or stage’s run has an "unstable" status, usually caused by test failures, code violations, etc. This is typically denoted by yellow in the web UI.
			echo "Post - unstable...";
		}
		unsuccessful {
			// Only run the steps in post if the current Pipeline’s or stage’s run has not a "success" status. This is typically denoted in the web UI depending on the status previously mentioned.
			echo "Post - unsuccessful...";
		}
		cleanup {
            // Run the steps in this post condition after every other post condition has been evaluated, regardless of the Pipeline or stage’s status
			echo "Post - cleanup...";
			// cleanWs();
			// cleanWs(patterns: [[pattern: '*.zip', type: 'INCLUDE']])
		}
	}
}


