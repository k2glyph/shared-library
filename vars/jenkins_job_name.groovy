def call() {
  def jobNameParts = env.JOB_NAME.tokenize('/') as String[]
  jobNameParts.length < 2 ? env.JOB_NAME : jobNameParts[jobNameParts.length - 2]
}
