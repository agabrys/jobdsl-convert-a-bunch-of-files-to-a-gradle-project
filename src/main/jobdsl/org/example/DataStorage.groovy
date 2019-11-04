package org.example

import groovy.util.slurpersupport.GPathResult

public class DataStorage {
    
    private String storage
    
    public DataStorage(def context) {
        this.storage = "${context.WORKSPACE}/data" as String
    }

    public String readAsText(def path) {
        return getFile(path).text
    }

    public GPathResult readAsXml(def path) {
        return new XmlSlurper().parse(getFile(path))
    }

    public Properties readAsProperties(def path) {
        def properties = new Properties()
        properties.load(new StringReader(readAsText(path)))
        return properties
    }

    public File getFile(def path) {
        return new File(storage, "${path}")
    }
}
