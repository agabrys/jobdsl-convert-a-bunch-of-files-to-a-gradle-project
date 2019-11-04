import jenkins.model.Jenkins

Jenkins.get().setMarkupFormatter(
    Class.forName(
        'hudson.markup.RawHtmlMarkupFormatter',
        true,
        Jenkins.get().pluginManager.uberClassLoader
    ).getDeclaredConstructor(Boolean.TYPE).newInstance(false)
)
Jenkins.get().save()
