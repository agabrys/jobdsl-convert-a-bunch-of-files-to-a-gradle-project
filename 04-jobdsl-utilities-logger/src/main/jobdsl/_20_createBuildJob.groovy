pipelineJob('build') {
    displayName('Build')
    description('''\
        <p>
            Builds a given branch of the product (commit id is determined as a head of this branch).
        </p>
    '''.stripIndent())

    definition {
        cps {
            script(readFileFromWorkspace('jenkinsfile/build.jenkinsfile'))
            sandbox()
        }
    }

    configure { project ->
        project / 'properties' << 'hudson.model.ParametersDefinitionProperty' {
            parameterDefinitions {
                'com.cwctravel.hudson.plugins.extended__choice__parameter.ExtendedChoiceParameterDefinition' {
                    delegate.name('PRODUCT_ID')
                    delegate.description('ID of the git project for which a new build will be triggered.')
                    delegate.quoteValue(false)
                    delegate.saveJSONParameterToFile(false)
                    delegate.visibleItemCount(10)
                    delegate.type('PT_SINGLE_SELECT')
                    delegate.groovyScript('''\
                        import hudson.slaves.EnvironmentVariablesNodeProperty
                        import jenkins.model.Jenkins
                        Jenkins.get().globalNodeProperties.get(EnvironmentVariablesNodeProperty.class).envVars['PRODUCT_IDS']
                    '''.stripIndent())
                    delegate.defaultGroovyScript('''\
                        import hudson.slaves.EnvironmentVariablesNodeProperty
                        import jenkins.model.Jenkins
                        Jenkins.get().globalNodeProperties.get(EnvironmentVariablesNodeProperty.class).envVars['DEFAULT_PRODUCT_ID']
                    '''.stripIndent())
                    delegate.multiSelectDelimiter(',')
                }

                'hudson.plugins.validating__string__parameter.ValidatingStringParameterDefinition' {
                    delegate.name('BRANCH')
                    delegate.description('Branch which will be built.')
                    delegate.regex('.+')
                    delegate.failedValidationMessage('The parameter is required!')
                }

                'com.cwctravel.hudson.plugins.extended__choice__parameter.ExtendedChoiceParameterDefinition' {
                    delegate.name('PRIORITY')
                    delegate.description('Job priority (number from 1 to 5, where 1 is the highest).')
                    delegate.quoteValue(false)
                    delegate.saveJSONParameterToFile(false)
                    delegate.visibleItemCount(5)
                    delegate.type('PT_RADIO')
                    delegate.value('1,2,3,4,5')
                    delegate.defaultValue(3)
                    delegate.multiSelectDelimiter(',')
                }
            }
        }
    }
}
