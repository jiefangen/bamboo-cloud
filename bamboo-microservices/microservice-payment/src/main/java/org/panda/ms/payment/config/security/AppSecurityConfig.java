package org.panda.ms.payment.config.security;

import org.panda.ms.payment.config.security.filter.AuthenticationFilter;
import org.panda.ms.payment.config.security.interceptor.AuthorizationInterceptor;
import org.panda.support.cloud.core.security.authority.AppSecurityMetadataSource;
import org.panda.support.cloud.core.security.authority.AuthoritiesBizExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 应用安全元数据源配置
 *
 * @author fangen
 **/
@Configuration
public class AppSecurityConfig {

    @Autowired
    private AuthoritiesBizExecutor authoritiesBizExecutor;

    @Bean
    public AppSecurityMetadataSource appSecurityMetadataSource() {
        return new AppSecurityMetadataSource(authoritiesBizExecutor);
    }

    @Bean
    public AuthenticationFilter authenticationFilter() {
        return new AuthenticationFilter();
    }

    @Bean
    public AuthorizationInterceptor authorizationInterceptor() {
        return new AuthorizationInterceptor();
    }

}
