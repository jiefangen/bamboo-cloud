package org.panda.ms.cas.server.core.cas.authentication;

import org.panda.bamboo.common.constant.basic.Strings;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.ms.cas.server.core.cas.service.CasServiceManager;
import org.panda.ms.cas.server.core.cas.ticket.CasTicketManager;
import org.panda.tech.core.web.restful.RestfulResult;
import org.panda.tech.core.web.util.WebHttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            String appName = this.serviceManager.getAppName(service);
            // 获取应用票据
            String appTicketId = this.ticketManager.getAppTicketId(request, appName, Strings.STR_NULL, scope);
            WebHttpUtil.buildJsonResponse(response, RestfulResult.success(appTicketId));
        } catch (Exception e) {
            LogUtil.error(getClass(), e);
        }
    }

}
