def call(Map param) {
   container("gcloud") {
     withCredentials([file(credentialsId:param.credential_id, variable: 'credential')]) {
       sh("gcloud auth activate-service-account --key-file=${credential}")
        sh("gcloud container clusters get-credentials ${param.cluster_name} --zone ${param.zone_name} --project ${param.project_name}")
     }
     script {
        sh "kubectl -n ${param.namespace} set image deployment.v1.apps/${param.deployment} '${param.deployment}=${param.imageTag}'"
     }
   }
}