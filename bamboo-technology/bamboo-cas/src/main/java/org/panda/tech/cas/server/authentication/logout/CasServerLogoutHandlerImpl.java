package org.panda.tech.cas.server.authentication.logout;

import org.panda.tech.cas.core.CasConstants;
import org.panda.tech.cas.server.entity.AppTicket;
import org.panda.tech.cas.server.service.CasServiceManager;
import org.panda.tech.cas.server.ticket.CasTicketManager;
import org.panda.tech.core.config.app.AppConstants;
import org.panda.tech.core.util.URLConnUtil;
import org.panda.tech.core.web.util.WebHttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * CAS服务端登出处理器实现
 */
@Component
public class CasServerLogoutHandlerImpl implements CasServerLogoutHandler {

    @Autowired
    private CasTicketManager ticketManager;
    @Autowired
    private CasServiceManager serviceManager;
    @Value(AppConstants.EL_SPRING_APP_NAME)
    private String appName;
    @Autowired
    private ExecutorService executorService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Collection<AppTicket> appTickets = this.ticketManager.deleteTicketGrantingTicket(request, response);
        if (appTickets.size() > 0) {
            String logoutService = WebHttpUtil.getParameterOrAttribute(request, CasConstants.PARAMETER_SERVICE);
            for (AppTicket appTicket : appTickets) {
                // 排除当前CAS服务端应用，之所以需要在此排除是考虑到一个应用同时作为CAS服务端和客户端的场景
                if (!appTicket.getAppName().equals(this.appName)) {
                    this.executorService.submit(() -> {
                        noticeAppLogout(appTicket, logoutService);
                    });
                }
            }
        }
    }

    private void noticeAppLogout(AppTicket appTicket, String excludedService) {
        String logoutProcessUrl = this.serviceManager.getLogoutProcessUrl(appTicket.getAppName(),
                appTicket.getContextUri(), excludedService);
        if (logoutProcessUrl != null) {
            Map<String, Object> params = new HashMap<>();
            params.put("logoutRequest", "<SessionIndex>" + appTicket.getId() + "</SessionIndex>");
            URLConnUtil.requestByGet(logoutProcessUrl, params, StandardCharsets.UTF_8.name());
        }
    }

    @Override
    public void logoutClients(HttpServletRequest request) {
        // 排除当前CAS服务端应用，之所以需要在此排除是考虑到一个应用同时作为CAS服务端和客户端的场景
        List<AppTicket> appTickets = this.ticketManager.deleteAppTickets(request, this.appName);
        for (AppTicket appTicket : appTickets) {
            noticeAppLogout(appTicket, null);
        }
    }

}
