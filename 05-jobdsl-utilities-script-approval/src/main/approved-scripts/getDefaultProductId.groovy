import hudson.slaves.EnvironmentVariablesNodeProperty
import jenkins.model.Jenkins

Jenkins.get().globalNodeProperties.get(EnvironmentVariablesNodeProperty.class).envVars['DEFAULT_PRODUCT_ID']
