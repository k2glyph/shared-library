def call(Map param) {
  if(param.backup=="true") {
    sh "echo\"Requesting gcloud to create backup---\n\""
    sh "gcloud sql backups create --instance=${param.instance} --project ${param.project}"
   }
   if(param.restore=="true") {
      sh "echo\"Requesting gcloud to restore from latest backup---\n\""
      sh "gcloud sql backups restore $(gcloud sql backups list --instance=$instance --project $projet | grep "SUCCESSFUL" | awk 'NR==1{print $1}') --restore-instance=$instance --backup-instance=$instance"
   }
}
