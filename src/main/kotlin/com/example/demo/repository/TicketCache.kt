package com.example.demo.repository

import org.springframework.stereotype.Component
import java.util.UUID

@Component
class TicketCache {

    private val vehicleNoToTicketIdMap = mutableMapOf<String, UUID>()

    enum class TicketCacheAction {
        ADD,
        REMOVE
    }

    suspend fun findTicketIdByVehicleNo(vehicleNo: String): UUID {
        return vehicleNoToTicketIdMap[vehicleNo]
            ?: throw Exception("No entry found in cache")
    }

    suspend fun updateCache(vehicleNo: String, ticketId: UUID, action: TicketCacheAction) {
        when (action) {
            TicketCacheAction.ADD -> {
                vehicleNoToTicketIdMap[vehicleNo] = ticketId
                println("ticket cache updated, ${vehicleNoToTicketIdMap[vehicleNo]}")
            }
            TicketCacheAction.REMOVE -> { vehicleNoToTicketIdMap.remove(vehicleNo) }
        }
    }
}