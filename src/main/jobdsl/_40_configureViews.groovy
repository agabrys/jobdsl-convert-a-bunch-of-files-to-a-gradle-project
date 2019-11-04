import org.example.Logger

def views = [
    [
        name: 'view 1',
        jobNames: [
            'build'
        ],
        includes: '^$'
    ],
    [
        name: 'projects',
        jobNames: [],
        includes: '^project-\\d+$'
    ]
]

def logger = new Logger(out)
views.each { view ->
    logger.info("Creating view: ${view.name}")
    listView(view.name) {
        jobs {
            names(view.jobNames.toArray(new String[0]))
            regex(view.includes)
        }
        columns {
            status()
            weather()
            name()
            lastSuccess()
            lastFailure()
            lastDuration()
            buildButton()
        }
    }
}
