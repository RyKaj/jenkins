#!/usr/bin/env groovy

/*
    Author: Ryan Kajiura - https://www.linkedin.com/in/ryankajiura/




    Date					Name					Description
	-----------------------------------------------------------------------
	March 29 2021				R. Kajiura			Initial 

*/

package org

class Constants {
    // Emails
	public static final String EMAIL_REPLY_FROM = '<noreply_email_address>';
	public static final String EMAIL_DISTRIBUTION_LIST = '<email_1, email_2>';
  
    // Slack
	public static final String SLACK_TOKEN = '<slack_token>';
	public static final String SLACK_CHANNEL = '<slack_channel>';   // do not add # to channel name
	public static final String SLACK_DOMAIN = '<slack_domain>';
  
    // Source Control 
	public static final String GIT_REPO_URL = 'ssh://<username>@<server_name:port>/... .git';
	public static final String GIT_REPO_CREDENTIALID = '<repo_credential_GUILD>';
	public static final String GIT_BRANCH = '<branch>';
  
}


