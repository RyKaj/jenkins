#!/usr/bin/env groovy

/*
    Author: Ryan Kajiura




    Date					Name					Description
	-----------------------------------------------------------------------
	March 29 2021				R. Kajiura			Initial 

*/

package org

class Constants {
    // Emails
	public static final String EMAIL_REPLY_FROM = "no_reply@starstechnologies.com"  
	public static final String EMAIL_DISTRIBUTION_LIST = 'kajiurya@starsgroup.com, sunnyl@starsgroup.com';
  
    // Slack
	public static final String SLACK_TOKEN = 'qaops-jenkins-slack-token'
	public static final String SLACK_CHANNEL = 'itsm-av-jenkins-notification'
	// Sunny Lam 			= 	Platform Test Automation  Notification Channel	 		'itsm-av-jenkins-notification'
	// Michael Lawrence 	= 	QAOps Notification Channel				 				'qaops-alerts'
	public static final String SLACK_DOMAIN = 'thestarsgroup'
  
    // Source Control 
	public static final String GIT_REPO_URL = 'ssh://git@git.pyrsoftware.ca:2222/av/testng-test.git';
	public static final String GIT_REPO_CREDENTIALID = 'f96cf400-5c1d-463a-beac-08c04379fa3d';
	public static final String GIT_BRANCH = 'master';
  
}


