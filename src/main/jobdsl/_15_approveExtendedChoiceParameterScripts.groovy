import org.example.Logger
import org.jenkinsci.plugins.scriptsecurity.scripts.ScriptApproval

// Approve the following scripts used by ExtendedChoiceParameter plugin

def scriptsHashes = [
/*
import hudson.slaves.EnvironmentVariablesNodeProperty
import jenkins.model.Jenkins
Jenkins.get().globalNodeProperties.get(EnvironmentVariablesNodeProperty.class).envVars['PRODUCT_IDS']

*/
    '9775a0362ea4dad13ca42d6b6e6076aacc25d43a',
/*
import hudson.slaves.EnvironmentVariablesNodeProperty
import jenkins.model.Jenkins
Jenkins.get().globalNodeProperties.get(EnvironmentVariablesNodeProperty.class).envVars['DEFAULT_PRODUCT_ID']

*/
    '22a85b9df04b2e905150af0099a1c57b836cbb9c'
]

def logger = new Logger(out)
scriptsHashes.each {
    logger.info("Approving script by hash: ${it}")
    ScriptApproval.get().approveScript(it)
}
