package com.eazyride.model

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

@Introspected
@Serdeable
data class GetRidesRequest(
    val userId: Long? = null,
    val driverId: Long? = null,
    @NotBlank
    var accessToken: String,
    val status: String? = null,
)

@Introspected
@Serdeable
data class GetRidesResponse(
    val rides: List<Ride> = emptyList(),
    val success: Boolean = false,
)

@Introspected
@Serdeable
data class Ride(
    var id: Long,
    var userId: Long,
    var carId: Long,
    var driverId: Long,
    var rideRequestId: Long,
    var pickupLoc: String, // addressId?
    var dropLoc: String,
    var startTime: LocalDateTime,
    var endTime: LocalDateTime,
    var rideType: String,
    var carType: String,
    var status: String,
    var kms: Double,
    var totalCost: Double,
    var initialPayment: Double,
    var finalPayment: Double,
    var paymentStatus: String,
)
