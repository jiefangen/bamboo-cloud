package org.panda.support.config.example.common.nacos;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.nacos.api.config.listener.Listener;
import org.panda.bamboo.common.util.LogUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

/**
 * Nacos指定配置变动监听
 *
 * @author fangen
 **/
@Component
public class NacosListener implements InitializingBean {

    private static final Logger LOGGER = LogUtil.getLogger(NacosListener.class);

    @Value("${spring.application.name}")
    private String name;
    @Value("${spring.profiles.active}")
    private String env;

    @Autowired
    private NacosConfigManager nacosConfigManager;
    @Autowired
    private NacosConfigProperties nacosConfigProperties;

    @Override
    public void afterPropertiesSet() throws Exception {
        String listenerConfigName = name + "-" + env + ".yml";
        nacosConfigManager.getConfigService().addListener(listenerConfigName, nacosConfigProperties.getGroup(), new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String s) {
                LOGGER.warn("{} configuration update {}", listenerConfigName, s);
            }
        });
    }
}
