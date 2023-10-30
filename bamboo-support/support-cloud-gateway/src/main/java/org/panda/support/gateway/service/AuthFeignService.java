package org.panda.support.gateway.service;

import org.panda.tech.core.web.restful.RestfulResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Auth认证授权服务
 *
 * @author fangen
 **/
@Component
@FeignClient(value = "ms-auth")
public interface AuthFeignService {

    @PostMapping(value = "/auth/login")
    RestfulResult<String> login(@RequestParam String username, @RequestParam String password, @RequestParam String service);

    @GetMapping(value = "/auth/access/validate")
    RestfulResult validate(@RequestParam(value = "service", required = false) String service);

}
