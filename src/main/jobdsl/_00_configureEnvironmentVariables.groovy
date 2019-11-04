import hudson.slaves.EnvironmentVariablesNodeProperty
import hudson.slaves.EnvironmentVariablesNodeProperty.Entry
import jenkins.model.Jenkins

def envVariables = [
    'PRODUCT_IDS': 'animal-service,pet-service,zoo-service',
    'DEFAULT_PRODUCT_ID': 'pet-service',
    'PRODUCT_animal-service_URL': 'https://example.org/animal-service.git',
    'PRODUCT_pet-service_URL': 'https://example.org/pet-service.git',
    'PRODUCT_zoo-service_URL': 'https://example.org/zoo-service.git'
]

out.println('  Jenkins environment variables:')
def envEntries = []
for (def entry in envVariables.entrySet()) {
    out.println("    ${entry.key}=${entry.value}")
    envEntries.add(new Entry(entry.key, entry.value))
}

Jenkins.get().globalNodeProperties.replace(new EnvironmentVariablesNodeProperty(envEntries))
Jenkins.get().save()
