package org.panda.ms.payment.config.interceptor.client;

import org.panda.tech.core.web.restful.RestfulResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Auth服务调用客户端
 *
 * @author fangen
 **/
@Component
@FeignClient(value = "ms-auth")
public interface AuthServerClient {

    @GetMapping(value = "/auth/access/validate")
    RestfulResult validate(@RequestHeader("Authorization") String authToken,
                           @RequestParam(value = "service", required = false) String service);

}
