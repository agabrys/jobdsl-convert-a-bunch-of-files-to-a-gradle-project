package org.example

import static org.mockito.Mockito.spy
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.verifyNoMoreInteractions

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

public class LoggerTest {

    private def out
    private Logger logger

    @BeforeEach
    public void setup() {
        out = spy(DevNullConsole)
        logger = new Logger(out)
    }

    @Test
    public void info() {
        logger.info("info message")
        verify(out).println("\u001B[30m [INFO]  info message\u001B[0m")
        verifyNoMoreInteractions(out)
    }

    @Test
    public void warn() {
        logger.warn("warning")
        verify(out).println("\u001B[33m [WARN]  warning\u001B[0m")
        verifyNoMoreInteractions(out)
    }

    @Test
    public void error() {
        logger.error("--exception--")
        verify(out).println("\u001B[31m [ERROR] --exception--\u001B[0m")
        verifyNoMoreInteractions(out)
    }

    @Test
    public void logMultiline() {
        logger.info("line1\nline2\nline3")
        verify(out).println("\u001B[30m [INFO]  line1\n [INFO]  line2\n [INFO]  line3\u001B[0m")
    }

    @Test
    public void logNull() {
        logger.info(null)
        verify(out).println("\u001B[30m [INFO]  null\u001B[0m")
    }

    @Test
    public void logPrimitive() {
        logger.info(1)
        verify(out).println("\u001B[30m [INFO]  1\u001B[0m")
    }

    static class DevNullConsole {

        def println(def message) {
            // do nothing
        }
    }
}
