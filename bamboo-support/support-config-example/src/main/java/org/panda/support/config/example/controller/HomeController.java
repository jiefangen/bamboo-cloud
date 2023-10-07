package org.panda.support.config.example.controller;

import io.swagger.annotations.Api;
import org.panda.tech.core.web.restful.RestfulResult;
import org.panda.tech.core.web.util.NetUtil;
import org.panda.tech.core.web.util.WebHttpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "微服务启动语")
@Controller
@RequestMapping(value = "/home")
public class HomeController {

    @Value("${spring.application.name}")
    private String name;
    @Value("${spring.profiles.active}")
    private String env;
    @Value("${server.port}")
    private String port;

    @GetMapping
    @ResponseBody
    public RestfulResult<String> home() {
        return RestfulResult.success("The " + name + " microservice");
    }

    @GetMapping(value = "/index")
    @ResponseBody
    public RestfulResult<Map<String, Object>> index(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("service", name);
        resultMap.put("env", env);
        resultMap.put("port", port);
        resultMap.put("localIp", NetUtil.getLocalIp());
        resultMap.put("remoteAddress", WebHttpUtil.getRemoteAddress(request));
        return RestfulResult.success(resultMap);
    }

}
