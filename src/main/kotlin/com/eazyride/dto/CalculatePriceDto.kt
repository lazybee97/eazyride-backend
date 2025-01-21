package com.eazyride.dto

import com.eazyride.entity.CarType
import com.eazyride.entity.RideType
import io.micronaut.core.annotation.Introspected
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

@Introspected
class CalculatePriceDto(
    @NotBlank
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    val distance: Long,
    @NotBlank
    val rideType: RideType,
) {
    lateinit var carType: CarType
}
