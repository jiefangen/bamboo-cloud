package org.panda.support.cloud.example.controller.seata;

import io.swagger.annotations.Api;
import org.panda.support.cloud.example.service.TmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 事务管理器-全局事务
 *
 * @author fangen
 **/
@Api(tags = "全局事务管理案例")
@RestController
@RequestMapping(value = "/tm")
public class TmExampleController {

    @Autowired
    TmService service;

    /**
     * 调用TCC手工分布式事务-TM（事务管理器）
     *
     * @param name - 入参
     * @return String
     */
    @GetMapping("/tcc/insert")
    public String insertTcc(@RequestParam String name) {
        Map<String, String> params = new HashMap<>(1);
        params.put("name", name);
        return service.insertTcc(params);
    }

    /**
     * 调用AT自动事务-TM（事务管理器）
     *
     * @param name - 传入参数
     * @return String (success)
     */
    @GetMapping("/at/insert")
    public String insertAt(@RequestParam String name) {
        Map<String, String> params = new HashMap<>(1);
        params.put("name", name);
        return service.insertAt(params);
    }

}
