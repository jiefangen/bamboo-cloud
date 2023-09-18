package org.panda.support.config.controller;

import io.swagger.annotations.Api;
import org.panda.bamboo.core.context.ApplicationContextBean;
import org.panda.support.config.common.nacos.CommonConfigProperties;
import org.panda.tech.core.web.restful.RestfulResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Nacos配置中心
 *
 * @author fangen
 **/
@Api(tags = "Nacos配置中心")
@RestController
@RequestMapping(value = "/config")
public class NacosConfigController {

    @Autowired
    private CommonConfigProperties configProperties;

    @GetMapping("/realtimeGet")
    public RestfulResult realtimeGet() {
        Environment environment = ApplicationContextBean.applicationContext.getEnvironment();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("environment", environment.getProperty("nacos.common.config.useLocalCache"));
        resultMap.put("configProperties", configProperties.getUseLocalCache());
        return RestfulResult.success(resultMap);
    }
}
