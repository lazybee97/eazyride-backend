package com.eazyride.dto

import com.eazyride.enums.RideType
import io.micronaut.core.annotation.Introspected
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

@Introspected
data class PriceEstimateDto(
    @NotBlank
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    val distance: Long,
    @NotBlank
    val rideType: RideType,
    val carType: String?,
)
