package org.panda.support.config.example.common.nacos;

import org.panda.bamboo.common.constant.basic.Strings;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.support.cloud.core.config.AbstractConfigListener;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 应用启动配置监听
 *
 * @author fangen
 **/
@Component
public class ApplicationConfigListener extends AbstractConfigListener {

    private static final Logger LOGGER = LogUtil.getLogger(ApplicationConfigListener.class);

    @Value("${spring.application.name}")
    private String name;
    @Value("${spring.profiles.active}")
    private String env;

    @Override
    protected String getDataId() {
        return name + Strings.MINUS + env + ".yml";
    }

    @Override
    protected void process(String content, ApplicationContext context) {
        LOGGER.warn("[Nacos Config] config[dataId={}] content: \n {}", getDataId(), content);
        // 配置变动业务通知处理
    }
}
