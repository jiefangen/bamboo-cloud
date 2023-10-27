package org.panda.ms.cas.server.core.cas.service;

import org.apache.commons.lang3.StringUtils;
import org.panda.bamboo.common.constant.basic.Strings;
import org.panda.tech.core.exception.business.BusinessException;
import org.panda.ms.cas.server.core.cas.ticket.CasTicketManager;
import org.panda.tech.security.cas.CasConstants;
import org.panda.tech.security.cas.util.CasUtil;
import org.panda.tech.core.config.CommonProperties;
import org.panda.tech.core.config.app.AppConfiguration;
import org.panda.tech.core.web.config.meta.ApiMetaProperties;
import org.panda.tech.core.web.util.NetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * CAS服务管理器实现
 */
@Component
public class CasServiceManagerImpl implements CasServiceManager {

    private static final String ENCODED_WELL = URLEncoder.encode(Strings.WELL, StandardCharsets.UTF_8);

    @Autowired
    private CommonProperties commonProperties;
    @Autowired
    private CasTicketManager ticketManager;
    @Autowired
    private ApiMetaProperties apiMetaProperties;

    private String artifactParameter = CasConstants.PARAMETER_ARTIFACT;

    public void setArtifactParameter(String artifactParameter) {
        this.artifactParameter = artifactParameter;
    }

    @Override
    public String getAppName(String service) {
        return this.commonProperties.findAppName(service, false);
    }

    @Override
    public String getService(String appName) {
        AppConfiguration app = this.commonProperties.getApp(appName);
        return getService(app);
    }

    private String getService(AppConfiguration app) {
        if (app != null) {
            String contextUri = app.getContextUri(false);
            return NetUtil.concatUri(contextUri, app.getLoginedPath());
        }
        return null;
    }

    private AppConfiguration loadAppConfiguration(String appName) {
        AppConfiguration configuration = this.commonProperties.getApp(appName);
        if (configuration == null) {
            throw new BusinessException(CasServerExceptionCodes.INVALID_SERVICE);
        }
        return configuration;
    }

    @Override
    public String getUri(HttpServletRequest request, String service) {
        String appName = getAppName(service);
        return loadAppConfiguration(appName).getDirectUri();
    }

    @Override
    public String getLoginProcessUrl(HttpServletRequest request, String service, String scope) {
        String appName = getAppName(service);
        AppConfiguration app = loadAppConfiguration(appName);
        String contextUri;
        String loginUrl;
        if (service.startsWith(CasUtil.getServicePrefixByAppName(appName))) {
            service = service.substring(appName.length() + 2);
            String contextPath = app.getContextPath();
            contextUri = NetUtil.getContextUri(service, contextPath, true);
            if (contextUri == null) {
                throw new BusinessException(CasServerExceptionCodes.INVALID_SERVICE);
            }
            loginUrl = NetUtil.concatUri(contextUri, app.getLoginPath());
        } else {
            contextUri = app.getContextUri(false);
            loginUrl = app.getLoginProcessUrl();
        }
        int index = loginUrl.indexOf(Strings.QUESTION);
        if (index < 0) {
            loginUrl += Strings.QUESTION;
        } else {
            loginUrl += Strings.AND;
        }
        loginUrl += this.artifactParameter + Strings.EQUAL + this.ticketManager.getAppTicketId(request, appName,
                contextUri, scope);
        String redirectParameter = this.apiMetaProperties.getRedirectTargetUrlParameter();
        if (StringUtils.isBlank(request.getParameter(redirectParameter))) {
            if (service.length() > contextUri.length()) {
                String redirectUrl = service.substring(contextUri.length());
                if (redirectUrl.contains(Strings.WELL)) {
                    redirectUrl = redirectUrl.replace(Strings.WELL, ENCODED_WELL);
                }
                if (!redirectUrl.startsWith(Strings.SLASH)) { // 确保以斜杠开头
                    redirectUrl = Strings.SLASH + redirectUrl;
                }
                loginUrl += Strings.AND + redirectParameter + Strings.EQUAL + redirectUrl;
            }
        }
        return loginUrl;
    }

    @Override
    public String getLogoutProcessUrl(String appName, String contextUri, String serviceNot) {
        AppConfiguration app = loadAppConfiguration(appName);
        if (serviceNot != null) {
            String service = getService(app);
            if (service.equals(serviceNot)) {
                return null;
            }
            if (Strings.ASTERISK.equals(service) && serviceNot.startsWith(CasUtil.getServicePrefixByAppName(appName))) {
                return null;
            }
        }
        String url = app.getLogoutProcessUrl();
        if (StringUtils.isBlank(url)) {
            url = NetUtil.concatUri(contextUri, app.getLogoutPath());
        }
        return url;
    }

}
