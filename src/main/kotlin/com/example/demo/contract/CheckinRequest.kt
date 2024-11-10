package com.example.demo.contract

import com.example.demo.models.VehicleType

data class CheckinRequest (
    val vehicleNo: String,
    val vehicleType: VehicleType
)