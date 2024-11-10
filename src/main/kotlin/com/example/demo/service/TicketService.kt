package com.example.demo.service

import com.example.demo.models.ParkingSpot
import com.example.demo.models.Ticket
import com.example.demo.models.TicketStatus
import com.example.demo.repository.TicketCache
import com.example.demo.repository.TicketRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class TicketService(
    private val ticketRepository: TicketRepository,
    private val ticketCache: TicketCache
) {

    suspend fun generateTicket(vehicleNo: String, spot: ParkingSpot): Ticket {
        val ticket = Ticket(
            id = UUID.randomUUID(),
            vehicleNo = vehicleNo,
            startTime = LocalDateTime.now(),
            vehicleType = spot.type,
            parkingSlot = spot.id,
            status = TicketStatus.VEHICLE_PARKED
        )
        ticketRepository.saveTicket(ticket)
        ticketCache.updateCache(vehicleNo, ticket.id, TicketCache.TicketCacheAction.ADD)
        return ticket
    }

    suspend fun completeTicket(vehicleNo: String): Ticket {
        val tickedId = ticketCache.findTicketIdByVehicleNo(vehicleNo)
        val ticket = ticketRepository.findByTicketId(tickedId)
        ticket.endTime = LocalDateTime.now()
        ticket.status = TicketStatus.COMPLETED
        ticketRepository.saveTicket(ticket)
        ticketCache.updateCache(vehicleNo, ticket.id, TicketCache.TicketCacheAction.REMOVE)
        println(ticket)
        return ticket
    }
}