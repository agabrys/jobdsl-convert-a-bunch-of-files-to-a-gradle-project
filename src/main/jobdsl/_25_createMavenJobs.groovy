import org.example.Logger
import org.example.dsl.ViewBuilder

def jobs = [
    [
        name: 'project-1',
        displayName: 'Project 1',
        gitUrl: 'https://example.org/project-1'
    ],
    [
        name: 'project-2',
        displayName: 'Project 2',
        gitUrl: 'https://example.org/project-2'
    ],
    [
        name: 'project-3',
        displayName: 'Project 3',
        gitUrl: 'https://example.org/project-3'
    ],
    [
        name: 'project-4',
        displayName: 'Project 4',
        gitUrl: 'https://example.org/project-4'
    ],
    [
        name: 'project-5',
        displayName: 'Project 5',
        gitUrl: 'https://example.org/project-5'
    ]
]

def logger = new Logger(out)
jobs.each { job ->
    logger.info("Creating Maven job for ${job.displayName}")
    mavenJob(job.name) {
        displayName(job.displayName)
        description("""\
            <p>
                Builds ${job.displayName} cloned from ${job.gitUrl}
            </p>
        """.stripIndent())

        scm {
            git {
                remote {
                    url job.gitUrl
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

if (!jobs.isEmpty()) {
    new ViewBuilder(this).name('projects').includes('^project-\\d+$').build()
}
