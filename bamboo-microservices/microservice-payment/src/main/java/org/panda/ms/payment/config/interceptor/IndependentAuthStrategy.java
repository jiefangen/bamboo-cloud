package org.panda.ms.payment.config.interceptor;

import org.panda.bamboo.common.constant.Commons;
import org.panda.ms.payment.config.interceptor.client.AuthServerClient;
import org.panda.support.cloud.core.security.AuthManagerStrategy;
import org.panda.tech.core.web.restful.RestfulResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 独立服务认证授权验证策略
 *
 * @author fangen
 **/
@Component
public class IndependentAuthStrategy implements AuthManagerStrategy {

    @Autowired
    private AuthServerClient authServerClient;

    @Override
    public boolean verification(String token, String service) {
        RestfulResult verifyResult = authServerClient.validate(token, service);
        if (Commons.RESULT_SUCCESS_CODE == verifyResult.getCode()
                && Commons.RESULT_SUCCESS.equals(verifyResult.getMessage())) {
            return true;
        }
        return false;
    }
}
