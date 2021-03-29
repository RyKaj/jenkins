#!/usr/bin/env groovy

/*
    Author: Ryan Kajiura

    


    Date					Name					Description
	-----------------------------------------------------------------------
	March 29 2021				R. Kajiura			Initial 
*/
 
import groovy.json.*

def timestamp() {
    TimeZone.stDefault(TimeZone.getTimeZone('UTC'))
    def dateTimeStamp = new Date()
    def deploymentNotificationTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    String deploymentNotificationTimeStamp = deploymentNotificationTimeFormat.format(dateTimeStamp)

    deploymentNotificationTimeStamp
}