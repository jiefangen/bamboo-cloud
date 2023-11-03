package org.panda.ms.payment.config.security.client;

import org.panda.tech.core.web.config.WebConstants;
import org.panda.tech.core.web.restful.RestfulResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/auth/login")
    RestfulResult<String> login(@RequestParam("service") String service,
                                @RequestParam("username") String username,
                                @RequestParam("password") String password);

    @PostMapping("/auth/login")
    RestfulResult<String> loginByCredentials(@RequestHeader(WebConstants.HEADER_SECRET_KEY) String secretKey,
                                             @RequestHeader(WebConstants.HEADER_AUTH_CREDENTIALS) String credentials,
                                             @RequestParam("service") String service);

    @GetMapping(value = "/auth/access/validate")
    RestfulResult validate(@RequestHeader(WebConstants.HEADER_AUTH_JWT) String authToken,
                           @RequestParam("service") String service);

}
