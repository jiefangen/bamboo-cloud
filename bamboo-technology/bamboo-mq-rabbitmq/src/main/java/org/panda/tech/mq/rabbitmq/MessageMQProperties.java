package org.panda.tech.mq.rabbitmq;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties("bamboo.message.rabbitmq")
public class MessageMQProperties {
    /**
     * URI连接方式
     * 简例：amqp://userName:password@hostName:portNumber/virtualHost
     */
    private String uri;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 主机地址
     */
    private String host;
    /**
     * 端口号
     */
    private int port;
    /**
     * 虚拟主机
     */
    private String vhost;
    /**
     * 自定义连接名称
     * 便于不通服务连接区分
     */
    private String connectionName;

    /**
     * 队列名称集
     * 默认直连交换机转发队列
     */
    private List<String> queueNames;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getVhost() {
        return vhost;
    }

    public void setVhost(String vhost) {
        this.vhost = vhost;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public List<String> getQueueNames() {
        return queueNames;
    }

    public void setQueueNames(List<String> queueNames) {
        this.queueNames = queueNames;
    }
}
