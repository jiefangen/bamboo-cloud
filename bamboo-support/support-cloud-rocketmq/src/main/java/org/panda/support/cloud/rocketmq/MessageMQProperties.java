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

    // 默认生产者组
    public static final String DEFAULT_PRODUCER = "default-producer";

    /**
     * 服务地址
     */
    private String nameServer;
    /**
     * 生产者组
     * 释义：用于标识属于同一个逻辑生产者组的多个生产者实例，在集群应用中应有较好区分
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
        if (StringUtils.isEmpty(producerGroup)) {
            // 未配置则使用默认生产者组
            setProducerGroup(DEFAULT_PRODUCER);
        }
        return producerGroup;
    }

    public void setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
    }

}
