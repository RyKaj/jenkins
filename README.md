# JenkinsFiles
## Central location for all Test Automation Jobs

### Branching flow 
Master - Production code. Jenkins jobs will point to this branch <br />
Develop - this branch contains pre-production code. When the features are finished then they are merged into develop <br />
Feature - this branches are used to develop new features for the upcoming releases. Branch off from develop and must merge into develop <br />

https://medium.com/@patrickporto/4-branching-workflows-for-git-30d0aaee7bf#:~:text=master%20%E2%80%94%20this%20branch%20contains%20production,they%20are%20merged%20into%20develop <br />

#### Pull Request
After code has been merge from **feature** to **develop**, pull requst for code review will be done from **develop** to **master** <br />

### Configuration to Pipeline Jobs
- In Pipeline configuration, from **Definition** field, choose the **Pipeline script from SCM** option
- From the **CSM** field, choose **Bitbucket** as the source control system
- Complete the fields specific to your repository’s source control system
- In the **Script Path** field, specify the location (and name) of your *Jenkinsfile*. This location is the one that Jenkins checks out/clones the repository containing your *Jenkinsfile*, which should match that of the repository’s file structure. The default value of this field assumes that your *Jenkinsfile* is named "Jenkinsfile" and is located at the root of the repository.



```groovy
@Library('pipeline-library') _
stage('hello') {
    sayHello('Francesca')
}
```

Library files
---------------------------------------------------

| Name                                       | Type     | Description                                                                     |
|--------------------------------------------|----------|---------------------------------------------------------------------------------|
| [`abortPreviousBuilds`](vars/abortPreviousBuilds.groovy)         | function | Stops previous Jobs                           |
| [`identifyOSPlatform`](vars/identifyOSPlatform.groovy)         | function | Returns string on what OS Jenkins is running off                           |
| [`publishCucumberReports`](vars/publishCucumberReports.groovy)       | variable | Wrapper to execute Cucumber Reports. Required plugin - https://github.com/jenkinsci/cucumber-reports-plugin                            |
| [`publishHTMLReports`](vars/publishHTMLReports.groovy)       | function | Wrapper to publish HTML Reports. Required plugin https://github.com/jenkinsci/htmlpublisher-plugin                        |
| [`sendEmails`](vars/sendEmails.groovy)     | function | Send Email, optional attachment|
| [`Pipeline - stageCSM`](vars/stageCSM.groovy) | Pipeline | Source control configuration stage                             |
| [`timestamp`](vars/timestamp.groovy)     | function | Convert Console Output time to local time |
| [`validatingParameter`](vars/validatingParameter.groovy)         | function | Iterate parameters to validate inforamtion was provided |
| [`zip`](vars/zip.groovy)           | function | Zip file using PowerShell command                |
