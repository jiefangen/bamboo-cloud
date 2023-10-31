package org.panda.support.gateway.service.feign;

import org.panda.tech.core.web.restful.RestfulResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
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
    RestfulResult validate(@RequestParam(value = "service", required = false) String service);

}
