import jenkins.model.Jenkins

Jenkins.get().setSystemMessage(readFileFromWorkspace('resources/systemMessage.html'))
Jenkins.get().save()
