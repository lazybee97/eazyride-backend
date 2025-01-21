package com.eazyride.dto

import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

data class PriceEstimateRequest(
    @NotBlank
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    @NotBlank
    val distance: Long,
    @NotBlank
    val rideType: String,
    val carType: String?
)
