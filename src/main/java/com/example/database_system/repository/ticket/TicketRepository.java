package com.example.database_system.repository.ticket;

import com.example.database_system.pojo.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
}
