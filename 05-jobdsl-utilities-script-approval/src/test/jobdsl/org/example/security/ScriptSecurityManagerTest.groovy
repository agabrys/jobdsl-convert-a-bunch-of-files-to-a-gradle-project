package org.example.security

import static org.assertj.core.api.Assertions.assertThat
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify

import org.example.security.ScriptSecurityManager.ScriptApprover
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

public class ScriptSecurityManagerTest {

    private ScriptSecurityManager manager
    private ScriptApprover scriptApprover

    @BeforeEach
    public void setup() {
        scriptApprover = mock(ScriptApprover)
        manager = new ScriptSecurityManager(scriptApprover)
    }

    @Test
    public void verifyApproveWhenScriptDefinedInText() {
        def script = '''\
            import hudson.slaves.EnvironmentVariablesNodeProperty
            import jenkins.model.Jenkins
            Jenkins.get().globalNodeProperties.get(EnvironmentVariablesNodeProperty.class).envVars['DEFAULT_PRODUCT_ID']
        '''.stripIndent()
        def expected = '22a85b9df04b2e905150af0099a1c57b836cbb9c'

        def result = manager.approve('groovy', script)

        verify(scriptApprover).approve(expected)
        assertThat(result).isEqualTo(expected)
    }

    @Test
    public void verifyApproveWhenScriptReadFromFile() {
        def script = ScriptSecurityManagerTest.class.getResource('/securityApprovalScript.txt').text
        def expected = '22a85b9df04b2e905150af0099a1c57b836cbb9c'

        def result = manager.approve('groovy', script)

        verify(scriptApprover).approve(expected)
        assertThat(result).isEqualTo(expected)
    }
}
