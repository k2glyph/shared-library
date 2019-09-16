def call(String savedfile = null) {
   def filedata = null
   def filename = null
   // Get file using input step, will put it in build directory
   // the filename will not be included in the upload data, so optionally allow it to be specified
   if (savedfile == null) {
   def inputFile = input message: 'Upload file', parameters: [file(name: 'library_data_upload'), string(name: 'filename', defaultValue: 'uploaded-file-data')]
   filedata = inputFile['library_data_upload']
   filename = inputFile['filename']
   } else {
   def inputFile = input message: 'Upload file', parameters: [file(name: 'library_data_upload')]
   filedata = inputFile
   filename = savedfile
   }
   // Read contents and write to workspace
   writeFile(file: filename, encoding: 'Base64', text: filedata.read().getBytes().encodeBase64().toString())
   // Remove the file from the master to avoid stuff like secret leakage
   filedata.delete()
   return filename
}
