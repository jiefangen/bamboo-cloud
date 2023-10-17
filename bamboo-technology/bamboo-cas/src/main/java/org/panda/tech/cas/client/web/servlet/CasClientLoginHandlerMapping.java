package org.panda.tech.cas.client.web.servlet;

import org.panda.tech.core.web.mvc.cors.SingleCorsConfigurationSource;
import org.panda.tech.core.web.util.WebHttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * CAS客户端登录处理器映射
 */
public class CasClientLoginHandlerMapping implements HandlerMapping {

    private String processUrl;
    @Autowired
    private SingleCorsConfigurationSource corsConfigurationSource;

    public CasClientLoginHandlerMapping(String processUrl) {
        this.processUrl = processUrl;
    }

    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        if (WebHttpUtil.getRelativeRequestUrl(request).equals(this.processUrl)) {
            return new HandlerExecutionChain(this.corsConfigurationSource);
        }
        return null;
    }

}
