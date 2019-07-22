#!/usr/bin/env groovy

def call(Map param) {
    param.remote.allowAnyHosts = true
    if(param.propsFrom}) {
    	sshPut remote: param.remote, from: "${param.propsFrom}", into: "${param.propsTo}"
    }
    sshPut remote: param.remote, from: "${param.artifactFrom}", into: "${param.artifactTo}"
}