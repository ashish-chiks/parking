package com.example.demo.service

import com.example.demo.contract.CheckinRequest
import com.example.demo.contract.CheckoutRequest
import com.example.demo.contract.CheckoutResponse
import com.example.demo.handlers.PriceCalculator
import com.example.demo.models.ParkingSpot
import com.example.demo.models.ParkingSpotStatus
import com.example.demo.models.Ticket
import com.example.demo.repository.ParkingSpotRepository
import org.springframework.stereotype.Service

@Service
class ParkingService(
    private val ticketService: TicketService,
    private val parkingSpotRepository: ParkingSpotRepository,
    private val priceCalculator: PriceCalculator
) {

    suspend fun checkin(checkinRequest: CheckinRequest): Ticket {
        val spot = parkingSpotRepository.findByVehicleTypeAndStatus(checkinRequest.vehicleType, ParkingSpotStatus.AVAILABLE)
        val ticket = ticketService.generateTicket(vehicleNo = checkinRequest.vehicleNo, spot)
        updateStatus(spot.id, ParkingSpotStatus.OCCUPIED)
        return ticket
    }

    suspend fun updateStatus(spotId: Int, status: ParkingSpotStatus): ParkingSpot {
        return parkingSpotRepository.updateStatusById(spotId, status)
    }

    suspend fun checkout(checkoutRequest: CheckoutRequest): CheckoutResponse {
        val ticket = ticketService.completeTicket(checkoutRequest.vehicleNo)
        updateStatus(ticket.parkingSlot, ParkingSpotStatus.AVAILABLE)
        return getCheckoutResponse(ticket)
    }

    suspend fun getCheckoutResponse(ticket: Ticket): CheckoutResponse {
        val durationAndPrice = priceCalculator.calculatePrice(ticket.startTime, ticket.endTime!!)
        return CheckoutResponse(
            totalTime = durationAndPrice.first,
            price = durationAndPrice.second
        )
    }
}