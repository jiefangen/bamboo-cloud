package org.panda.support.cas.server.repo;

import org.panda.support.cas.server.entity.AppTicket;

import java.util.List;
import java.util.Optional;

/**
 * 应用票据仓库
 */
public interface AppTicketRepo {

    void save(AppTicket unity);

    Optional<AppTicket> findById(String id);

    AppTicket findByTicketGrantingTicketIdAndApp(String ticketGrantingTicketId, String app);

    List<AppTicket> deleteByTicketGrantingTicketIdAndAppNot(String ticketGrantingTicketId, String appNot);

}
