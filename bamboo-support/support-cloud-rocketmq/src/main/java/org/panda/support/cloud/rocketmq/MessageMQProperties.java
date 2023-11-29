package org.panda.support.cloud.rocketmq;

import org.apache.commons.lang3.StringUtils;
import org.panda.bamboo.core.context.ApplicationContextBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@ConfigurationProperties("bamboo.message.mq")
public class MessageMQProperties {
    /**
     * 服务地址
     */
    private String nameServer;
    /**
     * 生产者组
     */
    private String producerGroup;

    public String getNameServer() {
        if (StringUtils.isEmpty(nameServer)) {
            // 尝试使用原生配置中获取
            ApplicationContext applicationContext = ApplicationContextBean.getApplicationContext();
            if (applicationContext != null) {
                Environment environment = applicationContext.getEnvironment();
                String nameServer = environment.getProperty("spring.cloud.stream.rocketmq.binder.nameServer");
                if (StringUtils.isEmpty(nameServer)) {
                    nameServer = environment.getProperty("spring.cloud.stream.rocketmq.binder.name-server");
                }
                setNameServer(nameServer);
            }
        }
        return nameServer;
    }

    public void setNameServer(String nameServer) {
        this.nameServer = nameServer;
    }

    public String getProducerGroup() {
        return producerGroup;
    }

    public void setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
    }

}
