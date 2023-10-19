package org.panda.support.cas.server.authentication;

import org.panda.bamboo.common.util.LogUtil;
import org.panda.support.cas.core.CasConstants;
import org.panda.support.cas.server.service.CasServiceManager;
import org.panda.support.cas.server.ticket.CasTicketManager;
import org.panda.tech.core.web.util.NetUtil;
import org.panda.tech.core.web.util.WebHttpUtil;
import org.panda.tech.core.webmvc.view.util.WebViewUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * CAS鉴权成功处理器
 */
public class CasAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private CasServiceManager serviceManager;
    @Autowired
    private CasTicketManager ticketManager;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        try {
            this.ticketManager.createTicketGrantingTicket(request, response);
            CasUserSpecificDetailsAuthenticationToken token = (CasUserSpecificDetailsAuthenticationToken) authentication;
            String service = token.getService();
            String scope = token.getScope();
            String targetUrl = this.serviceManager.getLoginProcessUrl(request, service, scope);
            Map<String, Object> parameters = WebHttpUtil.getRequestParameterMap(request, "username",
                    "password", CasConstants.PARAMETER_SERVICE, CasConstants.PARAMETER_SCOPE);
            targetUrl = NetUtil.mergeParams(targetUrl, parameters, StandardCharsets.UTF_8.name());
            // 此处一定是表单提交鉴权成功，无需AjaxRedirectStrategy
            WebViewUtil.redirect(request, response, targetUrl);
        } catch (Exception e) {
            LogUtil.error(getClass(), e);
        }
    }

}
