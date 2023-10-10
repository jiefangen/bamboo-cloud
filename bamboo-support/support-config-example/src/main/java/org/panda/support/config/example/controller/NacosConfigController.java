package org.panda.support.config.example.controller;

import io.swagger.annotations.Api;
import org.panda.bamboo.core.context.ApplicationContextBean;
import org.panda.support.config.example.common.nacos.CommonConfigProperties;
import org.panda.support.config.example.service.ConsumerService;
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
@Api(tags = "通用配置内容")
@RestController
@RequestMapping(value = "/config")
public class NacosConfigController {

    @Autowired
    private CommonConfigProperties configProperties;

    @Autowired
    private ConsumerService consumerService;

    @GetMapping("/getCommonConfig")
    public RestfulResult getCommonConfig() {
        Environment environment = ApplicationContextBean.applicationContext.getEnvironment();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("useLocalCache", environment.getProperty("nacos.common.config.appLocalCache"));
        resultMap.put("configProperties", configProperties.toString());
        return RestfulResult.success(resultMap);
    }

    @GetMapping("/getSptDiscoveryHome")
    public RestfulResult getSptDiscoveryHome() {
        Object result = consumerService.getServiceHome("spt-discovery");
        return RestfulResult.success(result);
    }

    @GetMapping("/getSptDiscoveryIndex")
    public RestfulResult getSptDiscoveryIndex() {
        Object result = consumerService.getServiceIndex("spt-discovery");
        return RestfulResult.success(result);
    }
}
