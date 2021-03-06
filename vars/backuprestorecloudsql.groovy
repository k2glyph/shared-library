def call(Map param) {
  if(param.backup=="true") {
    println "Requesting gcloud to create backup"
    sh "gcloud sql backups create --instance=${param.instance} --project ${param.project} --description=\"${param.description}\""
   }
   if(param.restore=="true") {
      println "Requesting gcloud to restore from latest backup"
      sh "Yes Y | gcloud sql backups restore \$(gcloud sql backups list --instance=${param.instance} --project ${param.project} | grep \"SUCCESSFUL\" | awk 'NR==1{print \$1}') --restore-instance=${param.project} --backup-instance=${param.instance} "
   }
}
