package com.eazyride.entity

import com.eazyride.enums.RideType

data class RideTypeCancellation(
    val rideType: RideType,
    val cancellationPercentage: Double,
    val hoursBeforeRideIntervalStart: Int,
    val hoursBeforeRideIntervalEnd: Int,
    val hoursAfterRidebook: Int,
)
