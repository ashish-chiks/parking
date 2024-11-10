package com.example.demo.repository

import com.example.demo.models.Ticket
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class TicketRepository {

    private val tickets = mutableMapOf<UUID, Ticket>()

    suspend fun saveTicket(ticket: Ticket): Ticket {
        tickets[ticket.id] = ticket
        return ticket
    }

    suspend fun findByTicketId(id: UUID): Ticket {
        return tickets[id]
            ?: throw Exception("Ticket with id $id not found")
    }
}