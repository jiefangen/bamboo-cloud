package org.panda.ms.auth.core.cas.repo;

import org.panda.ms.auth.core.cas.entity.TicketGrantingTicket;

import java.util.Optional;

public interface TicketGrantingTicketRepo {

    void save(TicketGrantingTicket unity);

    Optional<TicketGrantingTicket> findById(String id);

    void delete(TicketGrantingTicket unity);

}
