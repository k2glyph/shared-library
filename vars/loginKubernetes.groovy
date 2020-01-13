def call(Map param) {
   container("gcloud") {
     println("Login into kubernetes cluster: ${param.cluster_name}")
     withCredentials([file(credentialsId:param.credential_id, variable: 'credential')]) {
       sh("gcloud auth activate-service-account --key-file=${credential}")
       sh("gcloud container clusters get-credentials ${param.cluster_name} --zone ${param.zone_name} --project ${param.project_name}")
     }
   }
}
