def call(Map param) {
  if(param.check=="jobs") {
    sh "echo \"Checking migrate job status\""
    sh """
    result=$(kubectl -n ${param.namespace} wait --for=condition=complete --timeout=50s jobs/$(kubectl get jobs -n ${param.namespace} | grep ${param.grep} | awk 'NR==1{print $1}') | cut -d" " -f2,3)
    if(result=="condition met") {
        kubectl delete jobs -n {param.namespace} $(kubectl get jobs -n ${param.namespace} | grep "migrate" | awk 'NR==1{print $1}')
    }else {
        exit 128
    }
  """
  }
}
