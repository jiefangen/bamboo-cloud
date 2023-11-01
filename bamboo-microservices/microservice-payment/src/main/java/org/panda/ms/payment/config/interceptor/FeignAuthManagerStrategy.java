package org.panda.ms.payment.config.interceptor;

import org.panda.bamboo.common.constant.Commons;
import org.panda.ms.payment.service.feign.AuthServerClient;
import org.panda.support.cloud.core.security.AuthManagerStrategy;
import org.panda.tech.core.web.restful.RestfulResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Feign方式认证授权验证策略
 *
 * @author fangen
 **/
@Component
public class FeignAuthManagerStrategy implements AuthManagerStrategy {

    @Autowired
    private AuthServerClient authServerClient;

    @Override
    public boolean verification(String token, String service) {
        RestfulResult verifyResult = authServerClient.validate(service);
        if (Commons.RESULT_SUCCESS_CODE == verifyResult.getCode()
                && Commons.RESULT_SUCCESS.equals(verifyResult.getMessage())) {
            return true;
        }
        return false;
    }
}
