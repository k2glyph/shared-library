#!/usr/bin/env groovy

def call(Map param) {
    sh "git archive --remote=git@'${param.repo}' HEAD:'${param.path}' '${param.fileName}' | tar -x"
}