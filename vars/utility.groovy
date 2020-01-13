def call(Map param) {
  if(param.check=="jobs") {
    sh "echo \"Checking migrate job status :${param}\""
    def result=sh(returnStdout: true, script: "kubectl -n ${param.namespace} wait --for=condition=complete --timeout=90s jobs/\$(kubectl get jobs --sort-by=.metadata.creationTimestamp -n ${param.namespace} | tail -1 | awk '{print \$1}') | cut -d\" \" -f2,3").trim()
    if(result=="condition met") {
      sh "echo \"Deleting completed migrate job status\""
      sh "kubectl delete jobs -n ${param.namespace} \$(kubectl get jobs -n ${param.namespace} | grep ${param.grep} | awk 'NR==1{print \$1}')"
      return
    }
    error('Migration fail check kubernetes logs for details')
  }
  if(param.rollback=="image") {
    def image=sh(returnStdout: true, script: "kubectl rollout history -n ${param.namespace} deployment ${param.deployment} --revision=\$(kubectl rollout history deployment ${param.deployment} -n ${param.namespace} | grep -v \"^\$\" | tail -n 2 | head -n 1 | awk '{print \$1}') | grep \"${param.fixtag}\" | cut -d \":\" -f3")
    sh "kubectl -n ${param.namespace} set image deployment.v1.apps/${param.deployment} ${param.deployment_name}=${param.fixtag}:${image}"
  }
}
