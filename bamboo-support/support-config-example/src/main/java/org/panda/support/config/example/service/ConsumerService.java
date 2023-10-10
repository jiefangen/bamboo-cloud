package org.panda.support.config.example.service;

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

    private final RestTemplate restTemplateLb;

    @Autowired
    public ConsumerService(RestTemplate restTemplateLb) {
        this.restTemplateLb = restTemplateLb;
    }

    public Object getServiceHome(String service) {
        String url = NetUtil.PROTOCOL_HTTP + service + "/home";
        RestfulResult restfulResult =  restTemplateLb.getForObject(url, RestfulResult.class);
        if (restfulResult != null && restfulResult.getCode() == Commons.RESULT_SUCCESS_CODE) {
            return restfulResult.getData();
        }
        return Strings.STR_NULL;
    }

    public Object getServiceIndex(String service) {
        String url = NetUtil.PROTOCOL_HTTP + service + "/home/index";
        RestfulResult restfulResult =  restTemplateLb.getForObject(url, RestfulResult.class);
        if (restfulResult != null && restfulResult.getCode() == Commons.RESULT_SUCCESS_CODE) {
            return restfulResult.getData();
        }
        return Strings.STR_NULL;
    }
}
