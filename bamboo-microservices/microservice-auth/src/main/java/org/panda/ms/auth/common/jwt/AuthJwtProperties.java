package org.panda.ms.auth.common.jwt;

import org.panda.tech.core.config.app.AppConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Auth Jwt参数
 **/
@Configuration
@ConfigurationProperties("bamboo.web.jwt")
public class AuthJwtProperties {
    /**
     * 加密静态KEY
     */
    protected static final long STATIC_KEY = 22269618L;

    @Value(AppConstants.EL_SPRING_APP_NAME)
    private String name;

    /**
     * 加密名称
     */
    private String encryptionName;

    public String getEncryptionName() {
        if (encryptionName == null) {
            this.encryptionName = name;
        }
        return encryptionName;
    }

    public void setEncryptionName(String encryptionName) {
        this.encryptionName = encryptionName;
    }
}
