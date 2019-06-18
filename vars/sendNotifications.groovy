#!/usr/bin/env groovy

def call (String buildStatus = 'Started') {
     // build status of null means successful
    buildStatus =  buildStatus ?: 'SUCCESSFUL'

    // Default values
    def color = 'RED'
    def colorCode = '#FF0000'
    def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
    def summary = "${subject} (${env.BUILD_URL})"
    def details = """<p>${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
        <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>"""

    // Override default values based on build status
    if (buildStatus == 'STARTED') {
        color = 'YELLOW'
        colorCode = '#FFFF00'
    } else if (buildStatus == 'SUCCESSFUL') {
        color = 'GREEN'
        colorCode = '#00FF00'
    }

    // // Send notifications
    // slackSend (color: colorCode, message: summary)
    // hipchatSend (color: color, notify: true, message: summary)
    if(color == 'RED' ){
        emailext(
            attachLog: true,
            attachmentsPattern: 'pipeline.log',
            body: details,
            replyTo: '$DEFAULT_REPLYTO',
            subject: subject,
            to: '$DEFAULT_RECIPIENTS')
    }else if(color == 'GREEN') {
        emailext(
            attachLog: true,
            attachmentsPattern: 'pipeline.log',
            body: '$BUILD_URL\n\n$FAILED_TESTS',
            replyTo: '$DEFAULT_REPLYTO',
            subject: '$DEFAULT_SUBJECT',
            to: '$DEFAULT_RECIPIENTS')
    }else {
        emailext(
            attachLog: true,
            attachmentsPattern: 'pipeline.log',
            body: details,
            replyTo: '$DEFAULT_REPLYTO',
            subject: subject,
            to: '$DEFAULT_RECIPIENTS')
    }
    
}