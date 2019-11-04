package org.example.builder;

import static org.assertj.core.api.Assertions.assertThat

import hudson.markup.RawHtmlMarkupFormatter
import org.example.TestClassHelper
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource

public class RawHtmlMarkupFormatterBuilderTest {

    private RawHtmlMarkupFormatterBuilder builder

    @BeforeEach
    public void setup() {
        builder = new RawHtmlMarkupFormatterBuilder(new TestClassHelper())
    }

    @Test
    public void verifyBuildWithoutConfiguration() {
        RawHtmlMarkupFormatter result = builder.build()

        assertThat(result.disableSyntaxHighlighting).isFalse()
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    public void verifyBuildWithConfiguration(boolean disableSyntaxHighlighting) {
        RawHtmlMarkupFormatter result = builder.disableSyntaxHighlighting(disableSyntaxHighlighting).build()

        assertThat(result.disableSyntaxHighlighting).isEqualTo(disableSyntaxHighlighting)
    }
}
