package org.panda.ms.cas.server.core.cas.service;

import javax.servlet.http.HttpServletRequest;

/**
 * CAS服务管理器
 */
public interface CasServiceManager {

    String getAppName(String service);

    String getService(String appName);

    String getUri(HttpServletRequest request, String service);

    String getLoginProcessUrl(HttpServletRequest request, String service, String scope);

    String getLogoutProcessUrl(String appName, String contextUri, String serviceNot);

}
