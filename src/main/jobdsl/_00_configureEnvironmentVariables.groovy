import hudson.slaves.EnvironmentVariablesNodeProperty
import hudson.slaves.EnvironmentVariablesNodeProperty.Entry
import jenkins.model.Jenkins
import org.example.DataStorage
import org.example.Logger

def envVariables = new DataStorage(this).readAsProperties('environment-variables.properties')
def logger = new Logger(out)

logger.info('Jenkins environment variables:')
def envEntries = []
for (def entry in envVariables.entrySet()) {
    logger.info("  ${entry.key}=${entry.value}")
    envEntries.add(new Entry(entry.key, entry.value))
}

Jenkins.get().globalNodeProperties.replace(new EnvironmentVariablesNodeProperty(envEntries))
Jenkins.get().save()
