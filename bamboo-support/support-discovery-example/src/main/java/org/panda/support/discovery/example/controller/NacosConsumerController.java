package org.panda.support.discovery.example.controller;

import io.swagger.annotations.Api;
import org.panda.support.discovery.example.service.ConsumerService;
import org.panda.tech.core.web.restful.RestfulResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Nacos消费者服务
 *
 * @author fangen
 **/
@Api(tags = "Nacos消费者服务")
@RestController
@RequestMapping(value = "/consumer")
public class NacosConsumerController {

    @Autowired
    private ConsumerService consumerService;

    @GetMapping("/getSptConfigHome")
    public RestfulResult getSptConfigHome() {
        Object result = consumerService.getServiceHome("spt-config");
        return RestfulResult.success(result);
    }

    @GetMapping("/getSptConfigIndex")
    public RestfulResult getSptConfigIndex() {
        Object result = consumerService.getServiceIndex("spt-config");
        return RestfulResult.success(result);
    }
}
