package org.panda.ms.cas.server.core.cas.repo;

import org.panda.ms.cas.server.core.cas.entity.TicketGrantingTicket;

import java.util.Optional;

public interface TicketGrantingTicketRepo {

    void save(TicketGrantingTicket unity);

    Optional<TicketGrantingTicket> findById(String id);

    void delete(TicketGrantingTicket unity);

}
