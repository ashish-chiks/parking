package com.example.demo.models

data class ParkingSpot (
    val id: Int,
    var status: ParkingSpotStatus,
    val type: VehicleType
)

enum class ParkingSpotStatus {
    AVAILABLE,
    OCCUPIED,
    OUT_OF_SERVICE
}