#!/usr/bin/env groovy

def call (Map param) {

    // Default values
    def color = 'RED'
    def slack_notification=param.slack?:true
    def email_notification=param.email?:true
    def disable_changelog=param.nochange?:false
    def slack_channel=param.channel
    def changes=param.changes?:"No Changes"
    def colorCode = '#FF0000'
    def subject = "${param.status}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'${param.title?:""}"
    if(param.status =='APPROVAL') {
        subject = "*Waiting for ${param.status}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'${param.title?:""}*"
    }
    def summary = "${subject} (${env.BUILD_URL})"
    def details = """<p>${param.status}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
        <p>Check console output at <a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a></p>"""
    def to='$DEFAULT_RECIPIENTS'+",${param.to?:""}"
    
    
    // Override default values based on build status 
    if (param.status == 'STARTED') {
        color = 'YELLOW'
        colorCode = '#FFFF00'
    } else if (param.status == 'SUCCESS') {
        color = 'GREEN'
        colorCode = '#00FF00'
    }else if (param.status == 'UNSTABLE') {
        color = 'PINK'
        colorCode = '#FFC0CB'
    } else if(param.status =='ABORTED') {
        color="GRAY"
        colorCode="#DCDCDC"
    } else if(param.status =='APPROVAL') {
        color="Blue"
        colorCode="#2C6CDE"
    
    }

    // // Send notifications
    if(slack_notification==true) {
        if(disable_changelog==false) {
            summary +="\n *ChangeLogs* \n"
            summary +=changes
        }
        if(param.status =='APPROVAL') {
            if(param.summary==""){
                summary +="\n *This changes will be deployed on Production. Waiting for your Approval.* \n"
            }else{
                summary+="\n*${param.summary}*\n"
            }
            
        }
        if(slack_channel) {
            slackSend (channel:slack_channel, color: colorCode, message: summary)
        }else {
            slackSend (color: colorCode, message: summary)
        }
        
    }
    // hipchatSend (color: color, notify: true, message: summary)
    if(email_notification==true) {
      if(color == 'RED' || color == 'GREEN' ){
          emailext(
              attachLog: true,
              body: details,
              replyTo: '$DEFAULT_REPLYTO',
              subject: subject,
              to: to)
      }else {
          emailext(
              body: details,
              replyTo: '$DEFAULT_REPLYTO',
              subject: subject,
              to: to)
      }
     }
}
