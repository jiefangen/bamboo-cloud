package org.panda.ms.auth.common.jwt;

import org.panda.tech.core.webmvc.jwt.symmetric.JwtSymmetricEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Auth JWT对称加密器
 *
 * @author fangen
 **/
@Component
public class AuthJwtEncryption extends JwtSymmetricEncryption {

    @Autowired
    private AuthJwtProperties authJwtProperties;

    @Override
    public String getEncryptionName() {
        return authJwtProperties.getEncryptionName();
    }

    @Override
    protected long getStaticKey() {
        return AuthJwtProperties.STATIC_KEY;
    }

    @Override
    public String getPayload(String type) {
        // TODO 通过type查询访问应用相关信息
        return type;
    }
}
