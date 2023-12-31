package org.panda.ms.cas.server.core.cas.controller;

import org.apache.commons.lang3.StringUtils;
import org.panda.bamboo.common.constant.basic.Strings;
import org.panda.bamboo.common.util.lang.StringUtil;
import org.panda.ms.cas.server.core.cas.service.CasServiceManager;
import org.panda.ms.cas.server.core.cas.ticket.CasTicketManager;
import org.panda.tech.core.web.config.WebConstants;
import org.panda.tech.core.web.config.meta.ApiMetaProperties;
import org.panda.tech.core.web.util.NetUtil;
import org.panda.tech.core.web.util.WebHttpUtil;
import org.panda.tech.security.cas.CasConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Cas服务端登录控制器支持
 */
public abstract class CasServerLoginControllerSupport {

    @Autowired
    protected CasServiceManager serviceManager;
    @Autowired
    private CasTicketManager ticketManager;
    @Autowired
    private RedirectStrategy redirectStrategy;
    @Autowired
    private ApiMetaProperties apiMetaProperties;

    @GetMapping(CasConstants.URL_LOGIN)
    public ModelAndView get(@RequestParam(value = "service", required = false) String service,
            @RequestParam(value = "scope", required = false) String scope, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        if (StringUtils.isBlank(service)) {
            service = getDefaultService();
            if (StringUtils.isBlank(service)) {
                return toBadServiceView(response);
            }
        }
        // 写入请求属性中，便于后续获取
        request.setAttribute(CasConstants.PARAMETER_SERVICE, service);

        String redirectParameter = this.apiMetaProperties.getRedirectTargetUrlParameter();
        if (WebHttpUtil.isAjaxRequest(request)) {
            String originalRequest = request.getHeader(WebConstants.HEADER_ORIGINAL_REQUEST);
            if (originalRequest != null) {
                response.setHeader(WebConstants.HEADER_ORIGINAL_REQUEST, originalRequest);
            }
            if (this.ticketManager.checkTicketGrantingTicket(request)) {
                String targetUrl = this.serviceManager.getLoginProcessUrl(request, service, scope);
                if (originalRequest != null) {
                    String originalUrl = originalRequest.substring(originalRequest.indexOf(Strings.SPACE) + 1);
                    targetUrl = NetUtil.mergeParam(targetUrl, redirectParameter, originalUrl);
                }
                this.redirectStrategy.sendRedirect(request, response, targetUrl);
            } else { // AJAX登录只能进行自动登录，否则报401
                StringBuffer url = request.getRequestURL();
                StringUtil.ifNotBlank(request.getQueryString(), queryString -> {
                    url.append(Strings.QUESTION).append(queryString);
                });
                response.setHeader(WebConstants.HEADER_LOGIN_URL, url.toString()); // 将当前ajax请求URL作为登录URL返回
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
            return null;
        } else {
            if (this.ticketManager.checkTicketGrantingTicket(request)) {
                String targetUrl = this.serviceManager.getLoginProcessUrl(request, service, scope);
                String redirectUrl = request.getParameter(redirectParameter);
                if (StringUtils.isNotBlank(redirectUrl)) {
                    redirectUrl = URLEncoder.encode(redirectUrl, StandardCharsets.UTF_8);
                    targetUrl = NetUtil.mergeParam(targetUrl, redirectParameter, redirectUrl);
                }
                this.redirectStrategy.sendRedirect(request, response, targetUrl);
                return null;
            }
            return new ModelAndView(CasConstants.URL_LOGIN);
        }
    }

    protected ModelAndView toBadServiceView(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Required String parameter 'service' is not present.");
        return null;
    }

    public String getDefaultService() {
        String appName = getDefaultAppName();
        if (StringUtils.isNotBlank(appName)) {
            return this.serviceManager.getService(appName);
        }
        return null;
    }

    protected String getDefaultAppName() {
        return null;
    }

}
