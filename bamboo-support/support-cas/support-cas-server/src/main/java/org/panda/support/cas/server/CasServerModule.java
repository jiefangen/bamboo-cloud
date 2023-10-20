package org.panda.support.cas.server;

import org.panda.tech.security.SecurityModule;
import org.springframework.context.annotation.ComponentScan;

/**
 * CAS服务端模块
 */
@ComponentScan(basePackageClasses = CasServerModule.class)
@ComponentScan(basePackageClasses = SecurityModule.class)
public class CasServerModule {
}
