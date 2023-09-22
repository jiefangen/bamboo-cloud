package org.panda.support.cloud.core;

import org.panda.tech.core.CoreModule;
import org.springframework.context.annotation.ComponentScan;

/**
 * 注册微服务核心模块组件自动扫描机制
 *
 * @author fangen
 **/
@ComponentScan(basePackageClasses = CloudCoreModule.class)
@ComponentScan(basePackageClasses = CoreModule.class)
public class CloudCoreModule {
}
