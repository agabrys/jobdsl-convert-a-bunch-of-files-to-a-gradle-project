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

views.each { view ->
    out.println "  Creating view: ${view.name}"
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
