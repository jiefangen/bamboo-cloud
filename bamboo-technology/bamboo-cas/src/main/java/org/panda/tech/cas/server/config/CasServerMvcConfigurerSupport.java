package org.panda.tech.cas.server.config;

import org.panda.tech.core.webmvc.view.config.WebViewMvcConfigurerSupport;
import org.sitemesh.builder.SiteMeshFilterBuilder;

/**
 * Cas服务端MVC配置器支持
 */
public class CasServerMvcConfigurerSupport extends WebViewMvcConfigurerSupport {

    @Override
    protected void buildSiteMeshFilter(SiteMeshFilterBuilder builder) {
        builder.addExcludedPath("/serviceValidate");
        builder.addExcludedPath("/serviceLogoutUrls");
    }
}
