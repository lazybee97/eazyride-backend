package com.eazyride.entity

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

@Serdeable
@MappedEntity
data class Ride(
    @field:Id
    @field:GeneratedValue(GeneratedValue.Type.AUTO)
    var id: Long? = null,
    @NotBlank val userId: Long,
    @NotBlank val carId: Long,
    @NotBlank val driverId: Long,
    @NotBlank val rideRequestId: Long,
    @NotBlank val pickupLoc: String, // addressId?
    @NotBlank val dropLoc: String,
    @NotBlank val startTime: LocalDateTime,
    @NotBlank val endTime: LocalDateTime,
    @NotBlank val rideType: String,
    @NotBlank val carType: String,
    @NotBlank var status: String,
    @NotBlank val kms: Double,
    @NotBlank val totalCost: Double,
    @NotBlank val profit: Double,
    @NotBlank val initialPayment: Double,
    @NotBlank val finalPayment: Double,
    @NotBlank val paymentStatus: String,
)
