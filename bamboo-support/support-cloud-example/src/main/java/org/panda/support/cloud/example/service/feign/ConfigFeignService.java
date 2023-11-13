package org.panda.support.cloud.example.service.feign;

import org.panda.tech.core.web.restful.RestfulResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * config服务openFeign方式调用
 *
 * @author fangen
 **/
@Component
@FeignClient(value = "spt-config")
public interface ConfigFeignService {

    @GetMapping(value = "/home")
    RestfulResult<String> getHome(@RequestParam("greetings") String greetings);

    @GetMapping(value = "/home/index")
    String getHomeIndex();

}
