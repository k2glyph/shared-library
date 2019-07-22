#!/usr/bin/env groovy

def call(String state, String name="CI Jenkins" ) {
        updateGitlabCommitStatus name: name, state: state.toLowerCase()   
}
