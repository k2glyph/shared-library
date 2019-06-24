
def call(Map param) {
    sh "git archive ${param.branch ?:""} --remote=git@'${param.repo}' HEAD:'${param.path}' '${param.fileName}' | tar -x"
}