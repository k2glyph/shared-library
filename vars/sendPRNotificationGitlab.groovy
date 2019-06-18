#!/usr/bin/env groovy

def call(String name="CI Jenkins", String state) {
    if(state == "pending") {
        post {
            always {
                updateGitlabCommitStatus name: name, state: state
            }
        }
    }
    
    
}