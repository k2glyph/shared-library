
def call(Map param) {
    sh "git archive --remote=git@'${param.repo}' HEAD:'${param.path}' '${param.fileName}'"
}