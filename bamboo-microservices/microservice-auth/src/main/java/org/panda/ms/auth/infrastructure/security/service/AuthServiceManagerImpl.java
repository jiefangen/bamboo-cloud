package org.panda.ms.auth.infrastructure.security.service;

import org.apache.commons.lang3.StringUtils;
import org.panda.bamboo.common.constant.basic.Strings;
import org.panda.tech.core.exception.business.BusinessException;
import org.panda.tech.core.config.CommonProperties;
import org.panda.tech.core.config.app.AppConfiguration;
import org.panda.tech.core.web.util.NetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Auth服务管理器实现
 */
@Component
public class AuthServiceManagerImpl implements AuthServiceManager {

    @Autowired
    private CommonProperties commonProperties;
//    @Autowired
//    private CasTicketManager ticketManager;


    @Override
    public String getAppName(String url) {
        if (StringUtils.isNotEmpty(url)) {
            return getAppNameByUrl(url);
        }
        return null;
    }

    private String getAppNameByUrl(String url) {
        int protocolIndex = url.indexOf("://");
        if (protocolIndex >= 0) { // 带有协议和域名的url
            url = url.substring(protocolIndex + 3);
            int slashIndex = url.indexOf(Strings.SLASH);
            url = url.substring(slashIndex + 1);
        }
        if (url.startsWith(Strings.SLASH)) { // 截取后确保不以/开头
            url = url.substring(1);
        }
        return url.substring(0, url.indexOf(Strings.SLASH));
    }

    @Override
    public List<String> getServices(String appName) {
        AppConfiguration app = this.commonProperties.getApp(appName);
        return null;
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
            throw new BusinessException(AuthServerExceptionCodes.INVALID_SERVICE);
        }
        return configuration;
    }

    @Override
    public String getUri(HttpServletRequest request, String service) {
        String appName = getAppName(service);
        return loadAppConfiguration(appName).getDirectUri();
    }

}
