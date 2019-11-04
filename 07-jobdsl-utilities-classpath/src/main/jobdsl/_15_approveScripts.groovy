import org.example.Logger
import org.example.security.ScriptSecurityManager

def logger = new Logger(out)
def scriptSecurityManager = new ScriptSecurityManager()

logger.info('Approved scripts:')
def directory = "${WORKSPACE}/approved-scripts"
new File(directory).eachFileRecurse {
    logger.info("  ${it.absolutePath.substring(directory.length() + 1)} (${scriptSecurityManager.approve('groovy', it.text)})")
}
