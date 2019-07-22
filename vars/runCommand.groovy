#!/usr/bin/env groovy

def call(Map param) {
	param.remote.allowAnyHosts = true
	sshCommand remote: param.remote, command: "${param.command}"
}