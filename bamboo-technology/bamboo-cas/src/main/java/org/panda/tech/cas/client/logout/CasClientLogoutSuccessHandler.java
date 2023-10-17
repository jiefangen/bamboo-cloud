package org.panda.tech.cas.client.logout;

import org.apache.commons.lang3.StringUtils;
import org.panda.tech.cas.client.web.CasClientSecurityUrlProvider;
import org.panda.tech.cas.core.authentication.logout.CasLogoutSuccessHandler;
import org.panda.tech.cas.core.util.CasUtil;
import org.panda.tech.core.web.util.NetUtil;
import org.panda.tech.core.web.util.WebHttpUtil;
import org.panda.tech.security.web.SecurityUrlProvider;
import org.springframework.security.cas.ServiceProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * CAS客户端登出成功处理器
 */
public class CasClientLogoutSuccessHandler extends CasLogoutSuccessHandler {

    private String serviceParameterName = ServiceProperties.DEFAULT_CAS_SERVICE_PARAMETER;

    public CasClientLogoutSuccessHandler(SecurityUrlProvider urlProvider) {
        super(urlProvider);
        if (urlProvider instanceof CasClientSecurityUrlProvider) {
            this.serviceParameterName = ((CasClientSecurityUrlProvider) urlProvider).getServiceParameterName();
        }
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        String targetUrlParameter = getTargetUrlParameter();
        String targetUrl = request.getParameter(targetUrlParameter);
        if (StringUtils.isNotBlank(targetUrl)) {
            targetUrl = URLEncoder.encode(targetUrl, StandardCharsets.UTF_8);
            targetUrl = NetUtil.mergeParam(getDefaultTargetUrl(), targetUrlParameter, targetUrl);
            return targetUrl;
        }
        targetUrl = super.determineTargetUrl(request, response);
        targetUrl = CasUtil.appendService(targetUrl, this.serviceParameterName, WebHttpUtil.getContextUrl(request));
        return targetUrl;
    }

}
