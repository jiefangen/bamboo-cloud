package org.panda.support.cloud.example.service.restlb;

import org.panda.bamboo.common.constant.Commons;
import org.panda.bamboo.common.constant.basic.Strings;
import org.panda.tech.core.web.restful.RestfulResult;
import org.panda.tech.core.web.util.NetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Home服务restLb方式调用
 *
 * @author fangen
 **/
@Service
public class HomeRestLbService {

    private final RestTemplate restTemplateLb;

    @Autowired
    public HomeRestLbService(RestTemplate restTemplateLb) {
        this.restTemplateLb = restTemplateLb;
    }

    public Object getServiceHome(String service) {
        String url = NetUtil.PROTOCOL_HTTP + service + "/home?greetings=restTemplateLb";
        RestfulResult<String> restfulResult =  restTemplateLb.getForObject(url, RestfulResult.class);
        if (restfulResult != null && restfulResult.getCode() == Commons.RESULT_SUCCESS_CODE) {
            return restfulResult.getData();
        }
        return Strings.STR_NULL;
    }

    public RestfulResult<Map<String, Object>> getServiceIndex(String service) {
        String url = NetUtil.PROTOCOL_HTTP + service + "/home/index";
        RestfulResult<Map<String, Object>> restfulResult =  restTemplateLb.getForObject(url, RestfulResult.class);
        return restfulResult;
    }
}
