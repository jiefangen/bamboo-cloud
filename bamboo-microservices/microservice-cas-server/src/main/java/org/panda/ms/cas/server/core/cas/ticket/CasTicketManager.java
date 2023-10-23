package org.panda.ms.cas.server.core.cas.ticket;

import org.jasig.cas.client.validation.Assertion;
import org.panda.ms.cas.server.core.cas.entity.AppTicket;
import org.panda.tech.security.user.UserSpecificDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * CAS票据管理器
 */
public interface CasTicketManager {

    String TICKET_GRANTING_TICKET_PREFIX = "TGT-";
    String SERVICE_TICKET_PREFIX = "ST-";

    void createTicketGrantingTicket(HttpServletRequest request, HttpServletResponse response);

    boolean checkTicketGrantingTicket(HttpServletRequest request);

    String getAppTicketId(HttpServletRequest request, String appName, String contextUri, String scope);

    UserSpecificDetails<?> getUserDetailsInTicketGrantingTicket(HttpServletRequest request);

    List<AppTicket> deleteAppTickets(HttpServletRequest request, String excludedApp);

    List<AppTicket> deleteTicketGrantingTicket(HttpServletRequest request, HttpServletResponse response);

    Assertion validateAppTicket(String appName, String appTicketId);
}
