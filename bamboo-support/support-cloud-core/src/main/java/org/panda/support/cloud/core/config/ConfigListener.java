package org.panda.support.cloud.core.config;

import org.springframework.context.ApplicationContext;

/**
 * 配置中心配置监听器
 */
public interface ConfigListener {
    /**
     * 获取配置文件ConfigId
     *
     * @return 配置文件ID
     */
    String getConfigId();

    /**
     * 业务执行器
     *
     * @param content 变动后的配置内容
     * @param context 应用上下文信息
     */
    void execute(String content, ApplicationContext context);

}
