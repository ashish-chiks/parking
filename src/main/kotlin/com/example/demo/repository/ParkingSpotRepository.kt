package com.example.demo.repository

import com.example.demo.models.ParkingSpot
import com.example.demo.models.ParkingSpotStatus
import com.example.demo.models.VehicleType
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository

@Repository
class ParkingSpotRepository(
    @Value("\${parking.spots.large}") largeVehicleParkingSpots: Int,
    @Value("\${parking.spots.medium}") mediumVehicleParkingSpots: Int,
    @Value("\${parking.spots.small}") smallVehicleParkingSpots: Int,
) {

    private val parkingSpots: MutableList<ParkingSpot> = mutableListOf()

    init {
        for (i in 1..smallVehicleParkingSpots) {
            parkingSpots.add(
                ParkingSpot(
                    id = i,
                    status = ParkingSpotStatus.AVAILABLE,
                    type = VehicleType.SMALL
                )
            )
        }

        for (i in 1..largeVehicleParkingSpots) {
            parkingSpots.add(
                ParkingSpot(
                    id = smallVehicleParkingSpots + i,
                    status = ParkingSpotStatus.AVAILABLE,
                    type = VehicleType.LARGE
                )
            )
        }

        for (i in 1..mediumVehicleParkingSpots) {
            parkingSpots.add(
                ParkingSpot(
                    id = largeVehicleParkingSpots + smallVehicleParkingSpots + i,
                    status = ParkingSpotStatus.AVAILABLE,
                    type = VehicleType.MEDIUM
                )
            )
        }
    }

    suspend fun findByVehicleTypeAndStatus(vehicleType: VehicleType, status: ParkingSpotStatus): ParkingSpot {
        for (parkingSpot in parkingSpots) {
            if (parkingSpot.type == vehicleType && parkingSpot.status == status) {
                return parkingSpot
            }
        }
        throw Exception("No parking spot found for $vehicleType and status $status")
    }

    suspend fun updateStatusById(parkingSpotId: Int, status: ParkingSpotStatus): ParkingSpot {
        parkingSpots[parkingSpotId-1].status = status
        for (parkingSpot in parkingSpots) {
            println(parkingSpot)
        }
        return parkingSpots[parkingSpotId-1]
    }
}