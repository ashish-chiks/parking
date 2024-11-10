package com.example.demo.contract

data class CheckoutRequest (
    val vehicleNo: String
)

data class CheckoutResponse(
    val totalTime: String,
    val price: Int
)