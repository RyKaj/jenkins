#!/usr/bin/env groovy

/*
    Author: Ryan Kajiura

   Note:
      Try catch is a hack to quckly identify if the Jenkins has AnsiColor plug-in installed or not
         AnsiColor - https://plugins.jenkins.io/ansicolor/

    Date					Name					Description
	-----------------------------------------------------------------------
	March 29 2021				R. Kajiura			Initial 

*/

// https://stackoverflow.com/questions/59632315/ansicolor-plugin-within-jenkins-pipeline-shared-library-usage-issue
// https://blog.mphomphego.co.za/blog/2017/04/13/jenkins-add-color-to-console-output.html

package org

public class ConsoleLog implements Serializable { 
   private String temp = "";

   public String Error (String message) {
      
      // try {
      //    temp = "\u001B[1;31m [ERROR] ${message} \u001B[0m"; 
      // }
      // catch (e) {
          temp = "[ERROR] ${message}"; 
      // }
      // finally {}
      
      return  temp;
   }

   public String Info (String message) { 
      // try {
      //    temp = "\u001B[1;34m [INFO] ${message} \u001B[0m"; 
      // }
      // catch (e) {
          temp =  "[INFO] ${message}"; 
      // }
      // finally {}
   
	  return  temp;
   } 
  
   public String Warning (String message) {
      // try {
      //    temp =  "\u001B[1;33m [WARNING] ${message} \u001B[0m";
      // }
      // catch (e) {
          temp =  "[WARNING] ${message}";
      // }
      // finally {}
	  
      return  temp;
   }   

}