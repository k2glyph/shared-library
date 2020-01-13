def call(Map param) {
   container("gcloud") {
      if(param.credential_id) {
         withCredentials([file(credentialsId:param.credential_id, variable: 'credential')]) {
           sh("gcloud auth activate-service-account --key-file=${credential}")
           sh("gcloud container clusters get-credentials ${param.cluster_name} --zone ${param.zone_name} --project ${param.project_name}")
        }
      }  

     if (param.type == 'MIGRATE') {
        println("Migration job running")
        if(param.org) {
            sh """
               export SECRET_NAME=\$(kubectl -n ${param.namespace} get secrets -l org=${param.org} -o jsonpath="{.items[0].metadata.name}")
            """           
        }else {
           sh """
               export SECRET_NAME=\$(kubectl -n ${param.namespace} get secrets | grep ${param.grep} | awk '{print \$1}')
           """
        }
        sh """
           export CI_COMMIT_SHA=${param.version}
           envsubst < ci/${param.job}.yaml | kubectl -n ${param.namespace} create -f -
        """
       return
     }
     script {
        sh "kubectl -n ${param.namespace} set image deployment.v1.apps/${param.deployment} '${param.deployment}=${param.imageTag}'"
     }
   }
}
