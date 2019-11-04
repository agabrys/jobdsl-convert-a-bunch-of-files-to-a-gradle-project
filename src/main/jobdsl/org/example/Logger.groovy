package org.example

public class Logger {

    private def out

    public Logger(def out) {
        this.out = out
    }

    public void info(def message) {
        out.println("\u001B[30m${addPrefix(message, ' [INFO]  ')}\u001B[0m".toString())
    }

    public void warn(def message) {
        out.println("\u001B[33m${addPrefix(message, ' [WARN]  ')}\u001B[0m".toString())
    }

    public void error(def message) {
        out.println("\u001B[31m${addPrefix(message, ' [ERROR] ')}\u001B[0m".toString())
    }

    private def addPrefix(def message, String prefix) {
        return "${message}".split('\n').collect { "${prefix}${it}" }.join('\n')
    }
}
