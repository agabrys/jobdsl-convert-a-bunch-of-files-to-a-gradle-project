package org.example

import jenkins.model.Jenkins

public class JenkinsClassHelper implements ClassHelper {

    @Override
    public Class<?> loadClass(def className) {
        return Class.forName(className as String, true, Jenkins.get().pluginManager.uberClassLoader)
    }
}
