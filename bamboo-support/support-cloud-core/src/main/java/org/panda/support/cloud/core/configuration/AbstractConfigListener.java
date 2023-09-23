package org.panda.support.cloud.core.configuration;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.nacos.api.config.listener.Listener;
import org.apache.commons.lang3.StringUtils;
import org.panda.bamboo.core.beans.ContextInitializedBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.Executor;

/**
 * 抽象配置监听器
 *
 * @author fangen
 **/
public abstract class AbstractConfigListener implements ConfigListener, ContextInitializedBean {

    @Autowired
    private NacosConfigManager nacosConfigManager;
    @Autowired
    private NacosConfigProperties nacosConfigProperties;
    /**
     * 线程执行器
     */
    private Executor taskExecutor;

    @Autowired
    public void setExecutor(Executor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    @Override
    public void execute(String content, ApplicationContext context) {
        this.process(content, context);
    }

    @Override
    public void afterInitialized(ApplicationContext context) throws Exception {
        String group = this.getGroup();
        if (StringUtils.isBlank(group)) {
            group = nacosConfigProperties.getGroup();
        }
        nacosConfigManager.getConfigService().addListener(getDataId(), group, new Listener() {
            @Override
            public Executor getExecutor() {
                return taskExecutor;
            }

            @Override
            public void receiveConfigInfo(String s) {
                execute(s, context);
            }
        });
    }

    /**
     * 获取配置文件ID
     */
    protected abstract String getDataId();

    /**
     * 具体业务处理
     *
     * @param content 配置内容
     * @param context 应用上下文
     */
    protected abstract void process(String content, ApplicationContext context);

}
