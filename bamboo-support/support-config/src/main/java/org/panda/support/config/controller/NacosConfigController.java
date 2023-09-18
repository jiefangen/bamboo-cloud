package org.panda.support.config.controller;

import io.swagger.annotations.Api;
import org.panda.tech.core.web.restful.RestfulResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Nacos配置中心管理
 *
 * @author fangen
 **/
@Api(tags = "Nacos配置中心管理")
@RestController
@RequestMapping(value = "/config")
public class NacosConfigController {

    @GetMapping("/get")
    public RestfulResult get() {
        return null;
    }
}
