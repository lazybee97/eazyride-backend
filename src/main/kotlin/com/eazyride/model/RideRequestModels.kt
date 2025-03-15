package com.eazyride.model

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

@Serdeable
data class RideRequestRequest(
    @NotBlank
    val accessToken: String,
    @NotBlank
    val userId: Long,
    @NotBlank
    val rideType: String,
    @NotBlank
    val carType: String,
    @NotBlank
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    val startLoc: String,
    val endLoc: String,
)

data class RideRequest(
    @NotBlank
    val rideRequestId: Long,
    @NotBlank val userId: Long,
    @NotBlank val pickupLoc: String,
    @NotBlank val dropLoc: String,
    @NotBlank val startTime: LocalDateTime,
    @NotBlank val endTime: LocalDateTime,
    @NotBlank val rideType: String,
    @NotBlank val carType: String,
    @NotBlank var status: String,
)

@Introspected
@Serdeable
data class GetRideRequestsRequest(
    @NotBlank
    val userId: Long,
    @NotBlank
    var accessToken: String,
    val status: String? = null,
)

@Introspected
@Serdeable
data class GetRideRequestsResponse(
    val rideRequests: List<RideRequest> = emptyList(),
)

@Introspected
@Serdeable
data class GetRideRequestsForCarResponse(
    val rideRequests: List<RideRequest> = emptyList(),
)

@Introspected
@Serdeable
data class AcceptBidForRideRequest(
    @NotBlank
    val userId: Long,
    @NotBlank
    val rideRequestId: Long,
    @NotBlank
    val bidId: Long,
    var accessToken: String? = null,
)

@Introspected
@Serdeable
data class AcceptBidForRideRequestResponse(
    val success: Boolean = false,
    val ride: Ride? = null,
)

@Introspected
@Serdeable
data class CreateRideRequestResponse(
    val success: Boolean = false,
    val rideRequestId: Long? = null,
)
