import jenkins.model.Jenkins

Jenkins.get().setSystemMessage(readFileFromWorkspace('data/systemMessage.html'))
Jenkins.get().save()
