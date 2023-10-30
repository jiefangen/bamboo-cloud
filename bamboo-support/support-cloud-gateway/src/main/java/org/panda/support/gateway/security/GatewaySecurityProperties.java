package org.panda.support.gateway.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Gateway安全配置规则
 *
 * @author fangen
 **/
@Configuration
@ConfigurationProperties("bamboo.gateway.security")
public class GatewaySecurityProperties {

    private List<String> ignoringPatterns;

    public List<String> getIgnoringPatterns() {
        return this.ignoringPatterns;
    }

    public void setIgnoringPatterns(List<String> ignoringPatterns) {
        this.ignoringPatterns = ignoringPatterns;
    }
}
