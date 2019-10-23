def call(Map param) {
  def file=param.file
  def token=param.token
  def channel=param.channel
  
  sh "curl -F file=@${file} -F channels=\"${channel}\" -H \"Authorization: Bearer ${token}\" https://slack.com/api/files.upload"
}
