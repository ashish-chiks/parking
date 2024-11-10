package com.example.demo.handlers

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.LocalDateTime
import kotlin.math.ceil

@Component
class PriceCalculator(
    @Value("\${parking.rps}") val ratePerSecond: Double
) {

    suspend fun calculatePrice(startTime: LocalDateTime, endTime: LocalDateTime): Pair<String, Int> {
        val duration = Duration.between(startTime, endTime)
        return Pair(formattedTime(duration), ceil(duration.toSeconds()*ratePerSecond).toInt())
    }

    suspend fun formattedTime(duration: Duration): String {
        val hours = duration.toHours()
        val minutes = duration.toMinutesPart()
        val seconds = duration.toSecondsPart()
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}