package org.panda.support.cloud.core;

import org.panda.tech.core.CoreModule;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 注册微服务核心模块组件自动扫描机制
 *
 * @author fangen
 **/
@Configuration
@ComponentScan(basePackageClasses = CoreModule.class)
public class CloudCoreModule {
}
