#!/usr/bin/env groovy

def call(String name="CI Jenkins", String state) {
        updateGitlabCommitStatus name: name, state: state   
}