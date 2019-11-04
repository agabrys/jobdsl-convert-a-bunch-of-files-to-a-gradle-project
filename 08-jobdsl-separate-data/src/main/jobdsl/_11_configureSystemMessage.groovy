import jenkins.model.Jenkins
import org.example.DataStorage

Jenkins.get().setSystemMessage(new DataStorage(this).readAsText('systemMessage.html'))
Jenkins.get().save()
