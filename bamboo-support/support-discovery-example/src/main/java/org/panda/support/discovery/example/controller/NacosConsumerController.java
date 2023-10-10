package org.panda.support.discovery.example.controller;

import io.swagger.annotations.Api;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.bamboo.common.util.jackson.JsonUtil;
import org.panda.support.discovery.example.service.feign.ConfigFeignService;
import org.panda.support.discovery.example.service.restlb.HomeRestLbService;
import org.panda.tech.core.web.restful.RestfulResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消费服务示例
 *
 * @author fangen
 **/
@Api(tags = "消费服务示例")
@RestController
@RequestMapping(value = "/consumer")
public class NacosConsumerController {

    @Autowired
    private HomeRestLbService restLbService;
    @Autowired
    private ConfigFeignService configFeignService;

    @GetMapping("/getSptConfigHome")
    public RestfulResult getSptConfigHome(@RequestParam boolean feignCall) {
        if (feignCall) {
            LogUtil.warn(getClass(), "调用开始。。。");
            return configFeignService.getHome("Hello World");
        } else {
            Object result = restLbService.getServiceHome("spt-config");
            return RestfulResult.success(result);
        }
    }

    @GetMapping("/getSptConfigIndex")
    public RestfulResult getSptConfigIndex(@RequestParam boolean feignCall) {
        if (feignCall) {
            return JsonUtil.json2Bean(configFeignService.getHomeIndex(), RestfulResult.class);
        } else {
            return restLbService.getServiceIndex("spt-config");
        }
    }
}
