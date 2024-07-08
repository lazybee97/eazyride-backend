package com.eazyride.entity

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank

@Serdeable
@MappedEntity
data class Bid(
    @field:Id
    @field:GeneratedValue(GeneratedValue.Type.AUTO)
    var id: Long? = null,
    @NotBlank val requestId: Long,
    @NotBlank val driverId: Long,
    @NotBlank val carId: Long,
    @NotBlank val pricePerKm: Double,
    @NotBlank var status: String,
    @NotBlank val bidTime: String,
    var totalPrice: Double,
)