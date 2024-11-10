package com.example.demo.models

import java.time.LocalDateTime
import java.util.UUID

data class Ticket(
    val id: UUID,
    val vehicleNo: String,
    val startTime: LocalDateTime,
    var endTime: LocalDateTime ?= null,
    var status: TicketStatus,
    val vehicleType: VehicleType,
    val parkingSlot: Int
)

enum class TicketStatus {
    VEHICLE_PARKED,
    COMPLETED
}