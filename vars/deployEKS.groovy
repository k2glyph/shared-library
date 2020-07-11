def call(Map param) {
     if (param.type == 'MIGRATE') {
        println("Migration job running")
        def secret_name=""   
        if(param.org) {
           secret_name=sh(returnStdout: true, script: "kubectl --kubeconfig ${param.kubeconfig} -n ${param.namespace} get secrets --sort-by=.metadata.creationTimestamp | grep secret | tail -1 | awk '{print \$1}'")
        }else {
          secret_name=sh(returnStdout: true, script: "kubectl --kubeconfig ${param.kubeconfig} -n ${param.namespace}  get secrets --sort-by=.metadata.creationTimestamp | grep secret | grep ${param.grep} | tail -1 | awk '{print \$1}'")
        }
        sh """
           export DB_HOST=${param.db_host}
           export SECRET_NAME=${secret_name}
           export CI_COMMIT_SHA=${param.version}
           envsubst '\${SECRET_NAME},\${CI_COMMIT_SHA},\${DB_HOST}' < ci/${param.job}.yaml | kubectl --kubeconfig ${param.kubeconfig} -n ${param.namespace} create -f -
        """
       return
     }
}
