package com.eazyride.model

import io.micronaut.core.annotation.Introspected
import java.time.LocalDateTime
import jakarta.validation.constraints.NotBlank

@Introspected
data class PriceEstimateRequest(
    @NotBlank
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    val distance: Int,
    @NotBlank
    val type: String,
    val category: String?
)
