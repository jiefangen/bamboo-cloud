package org.panda.support.discovery.example.service;

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

    public Object getServiceIndex(String service) {
        String url = NetUtil.PROTOCOL_HTTP + service + "/spt-config/home/index";
        return restTemplate.getForObject(url, Object.class);
    }
}
