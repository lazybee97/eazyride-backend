package com.eazyride.model

import com.eazyride.entity.Ride
import com.eazyride.entity.RideRequest
import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank

@Introspected
@Serdeable
data class GetRidesRequest(
    val userId: Long? = null,
    val driverId: Long? = null,
    @NotBlank
    val accessToken: String,
    val status: String? = null
)

@Introspected
@Serdeable
data class GetRidesResponse(
    val rides: List<Ride> = emptyList(),
    val success: Boolean = false
)

@Introspected
@Serdeable
data class GetRideRequestsRequest(
    @NotBlank
    val userId: Long,
    @NotBlank
    val accessToken: String,
    val status: String? = null
)

@Introspected
@Serdeable
data class GetRideRequestsResponse(
    val rideRequests: List<RideRequest> = emptyList(),
    val success: Boolean = false
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
    val ride: Ride? = null
)