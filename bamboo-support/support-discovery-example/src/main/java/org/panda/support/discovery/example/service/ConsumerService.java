package org.panda.support.discovery.example.service;

import org.panda.bamboo.common.constant.Commons;
import org.panda.bamboo.common.constant.basic.Strings;
import org.panda.tech.core.web.restful.RestfulResult;
import org.panda.tech.core.web.util.NetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 消费者服务
 *
 * @author fangen
 **/
@Service
public class ConsumerService {

    private final RestTemplate restTemplate;

    @Autowired
    public ConsumerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Object getServiceHome(String service) {
        String url = NetUtil.PROTOCOL_HTTP + service + "/home";
        RestfulResult restfulResult =  restTemplate.getForObject(url, RestfulResult.class);
        if (restfulResult != null && restfulResult.getCode() == Commons.RESULT_SUCCESS_CODE) {
            return restfulResult.getData();
        }
        return Strings.STR_NULL;
    }

    public Object getServiceIndex(String service) {
        String url = NetUtil.PROTOCOL_HTTP + service + "/home/index";
        RestfulResult restfulResult =  restTemplate.getForObject(url, RestfulResult.class);
        if (restfulResult != null && restfulResult.getCode() == Commons.RESULT_SUCCESS_CODE) {
            return restfulResult.getData();
        }
        return Strings.STR_NULL;
    }
}
