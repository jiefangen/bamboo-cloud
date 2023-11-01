package org.panda.support.cloud.core.configuration;

import org.panda.bamboo.common.constant.basic.Strings;
import org.springframework.context.ApplicationContext;

/**
 * 配置中心配置监听器
 */
public interface ConfigCenterListener {
    /**
     * 获取配置文件属组
     *
     * @return 配置文件属组
     */
    default String getGroup() {
        return Strings.STR_NULL;
    }

    /**
     * 业务执行器
     *
     * @param content 变动后的配置内容
     * @param context 应用上下文信息
     */
    void execute(String content, ApplicationContext context);

}
