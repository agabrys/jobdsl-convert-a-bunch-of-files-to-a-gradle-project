package org.example

public class TestClassHelper implements ClassHelper {

    @Override
    public Class<?> loadClass(def className) {
        return Class.forName(className as String)
    }
}
