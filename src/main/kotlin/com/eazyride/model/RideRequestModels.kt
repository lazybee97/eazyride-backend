package com.eazyride.model

import com.google.auth.oauth2.AccessToken
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


data class RideRequestResponse(
    @NotBlank
    val rideRequestId: Int
)