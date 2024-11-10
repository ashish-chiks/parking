package com.example.demo.controller

import com.example.demo.contract.CheckinRequest
import com.example.demo.contract.CheckoutRequest
import com.example.demo.service.ParkingService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/parking")
class ParkingController(
    private val parkingService: ParkingService
) {

    @PostMapping("/checkin")
    suspend fun checkin(
        @RequestBody checkinRequest: CheckinRequest
    ): ResponseEntity<*> {
        return try {
            ResponseEntity.status(HttpStatus.CREATED).body(parkingService.checkin(checkinRequest))
        } catch (ex: Exception) {
            ResponseEntity.internalServerError().body(ex.message)
        }
    }

    @PostMapping("/checkout")
    suspend fun checkout(
        @RequestBody checkoutRequest: CheckoutRequest
    ): ResponseEntity<*> {
        return try {
            ResponseEntity.status(HttpStatus.OK).body(parkingService.checkout(checkoutRequest))
        } catch (ex: Exception) {
            ResponseEntity.internalServerError().body(ex.message)
        }
    }

}