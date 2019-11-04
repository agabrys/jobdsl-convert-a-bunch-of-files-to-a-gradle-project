package org.example.builder

import org.example.ClassHelper
import org.example.JenkinsClassHelper

public class RawHtmlMarkupFormatterBuilder {

    private ClassHelper classHelper
    private boolean disableSyntaxHighlighting

    public RawHtmlMarkupFormatterBuilder(ClassHelper classHelper = new JenkinsClassHelper()) {
        this.classHelper = classHelper
    }

    public RawHtmlMarkupFormatterBuilder disableSyntaxHighlighting(boolean disableSyntaxHighlighting) {
        this.disableSyntaxHighlighting = disableSyntaxHighlighting
        return this
    }

    public Object build() {
        return classHelper
                .loadClass('hudson.markup.RawHtmlMarkupFormatter')
                .getDeclaredConstructor(Boolean.TYPE)
                .newInstance(disableSyntaxHighlighting)
    }
}
