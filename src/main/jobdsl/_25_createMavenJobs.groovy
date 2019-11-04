import org.example.DataStorage
import org.example.Logger
import org.example.dsl.ViewBuilder

def xml = new DataStorage(this).readAsXml('maven-jobs.xml')

def logger = new Logger(out)
xml.job.each { job ->
    logger.info("Creating Maven job for ${job.displayName.text()}")
    mavenJob(job.name.text()) {
        displayName(job.displayName.text())
        description("""\
            <p>
                Builds ${job.displayName.text()} cloned from ${job.gitUrl.text()}
            </p>
        """.stripIndent())

        scm {
            git {
                remote {
                    url job.gitUrl.text()
                }
                branch '*/$BRANCH'
                extensions {
                    localBranch('$BRANCH')
                }
            }
        }

        wrappers {
            mavenRelease {
                releaseGoals('release:prepare release:perform -Dresume=false')
                dryRunGoals('-Dresume=false -DdryRun=true release:prepare')
                numberOfReleaseBuildsToKeep(1)
            }
        }

        goals('clean package')
        localRepository(LocalRepositoryLocation.LOCAL_TO_WORKSPACE)

        configure { project ->
            project / 'properties' << 'hudson.model.ParametersDefinitionProperty' {
                parameterDefinitions {
                    'hudson.model.StringParameterDefinition' {
                        delegate.name('BRANCH')
                        delegate.defaultValue('develop')
                        delegate.description('Branch which will be built.')
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
}

if (!xml.job.isEmpty()) {
    new ViewBuilder(this).name('projects').includes('^project-\\d+$').build()
}
