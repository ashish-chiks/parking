package com.example.demo.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
class PingController {

    @GetMapping("/ping")
    suspend fun ping(): String {
        return "pong"
    }
}
