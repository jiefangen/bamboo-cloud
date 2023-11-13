package org.panda.support.cloud.example.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * 使用TCC模式往tcc服务插入一条记录
 *
 * @author fangen
 **/
@FeignClient(value = "spt-example")
public interface RmFeignService {

    /**
     * 往tcc服务插入一条记录
     */
    @PostMapping(value = "/rm/tcc/insert")
    String insertTCC(Map<String, String> params);

    /**
     * 调用at服务插入记录
     */
    @PostMapping(value = "/rm/at/insert")
    String insertAT(Map<String, String> params);

}
