def call(Map param) {
  if(param.check=="jobs") {
    sh "echo \"Checking migrate job status :${param}\""
    def result=sh(returnStdout: true, script: "kubectl -n ${param.namespace} wait --for=condition=complete --timeout=50s jobs/\$(kubectl get jobs -n ${param.namespace} | grep ${param.grep} | awk 'NR==1{print \$1}') | cut -\d" \" -f2,3").trim()
  }
}
