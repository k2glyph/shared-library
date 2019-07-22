#!/usr/bin/env groovy

def call(Map param) {
	param.remote.allowAnyHosts = true
	sshCommand remote: remote, command: "${param.command}"
}