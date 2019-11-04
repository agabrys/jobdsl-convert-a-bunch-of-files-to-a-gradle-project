package org.example.dsl

import org.example.Logger

public class ViewBuilder {

    private def context
    private String name
    private Set<String> jobNames = []
    private String includes = '^$'

    public ViewBuilder(def context) {
        this.context = context
    }

    public ViewBuilder name(def name) {
        this.name = name as String
        return this
    }

    public ViewBuilder jobs(Object... names) {
        jobNames.addAll(names)
        return this
    }

    public ViewBuilder includes(def includes) {
        this.includes = includes as String
        return this
    }

    public void build() {
        new Logger(context.out).info("Creating view: ${name}")
        
        context.listView(name) {
            jobs {
                names(this.jobNames.toArray(new String[0]))
                regex(this.includes)
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
}
