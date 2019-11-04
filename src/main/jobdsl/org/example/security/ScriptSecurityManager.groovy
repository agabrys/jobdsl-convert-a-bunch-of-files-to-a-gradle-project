package org.example.security

import org.apache.commons.codec.digest.DigestUtils
import org.jenkinsci.plugins.scriptsecurity.scripts.ScriptApproval

public class ScriptSecurityManager {

    private ScriptApprover scriptApprover

    public ScriptSecurityManager(ScriptApprover scriptApprover = new ScriptSeurityPluginApprover()) {
        this.scriptApprover = scriptApprover
    }

    public String approve(final String language, final String script) {
        def hash = calculateHash(language, script)
        scriptApprover.approve(hash)
        return hash
    }

    private String calculateHash(final String language, final String script) {
        def text = "${language}:${script.replace('\r\n', '\n').replace('\r', '\n')}" as String
        return DigestUtils.sha1Hex(text)
    }

    public interface ScriptApprover {

        void approve(String hash)
    }

    public static class ScriptSeurityPluginApprover implements ScriptApprover {

        public void approve(String hash) {
            ScriptApproval.get().approveScript(hash)
        }
    }
}
