#!/usr/bin/env groovy

def call (Map param) {
    // Default values
    def color = 'RED'
    def colorCode = '#FF0000'
    def subject = "${param.status}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'${param.title}"
    def summary = "${subject} (${env.BUILD_URL})"
    def details = """<p>${param.status}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
        <p>Check console output at <a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a></p>"""
    def to="${env.DEFAULT_RECIPIENTS},${param.to}"
    // Override default values based on build status 
    if (param.status == 'STARTED') {
        color = 'YELLOW'
        colorCode = '#FFFF00'
    } else if (param.status == 'SUCCESS') {
        color = 'GREEN'
        colorCode = '#00FF00'
    }

    // // Send notifications
    // slackSend (color: colorCode, message: summary)
    // hipchatSend (color: color, notify: true, message: summary)
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