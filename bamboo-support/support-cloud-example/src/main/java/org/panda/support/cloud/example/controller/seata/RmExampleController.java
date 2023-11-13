package org.panda.support.cloud.example.controller.seata;

import io.swagger.annotations.Api;
import org.panda.support.cloud.example.service.AtService;
import org.panda.support.cloud.example.service.TccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 资源管理器
 *
 * @author fangen
 **/
@Api(tags = "分支事务管理案例")
@RestController
@RequestMapping(value = "/rm")
public class RmExampleController {

    @Autowired
    private TccService tccService;
    @Autowired
    private AtService atService;

    @PostMapping("/tcc/insert")
    public String insertTcc(@RequestBody Map<String, String> params) {
        return tccService.insert(params);
    }

    @PostMapping("/at/insert")
    public String insert(@RequestBody Map<String, String> params) {
        return atService.insert(params);
    }

}
