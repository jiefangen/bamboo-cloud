package org.panda.support.cas.server.repo;

import org.panda.support.cas.server.entity.TicketGrantingTicket;

import java.util.Optional;

public interface TicketGrantingTicketRepo {

    void save(TicketGrantingTicket unity);

    Optional<TicketGrantingTicket> findById(String id);

    void delete(TicketGrantingTicket unity);

}
