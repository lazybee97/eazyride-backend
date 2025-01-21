package com.eazyride.entity

import com.eazyride.enums.RideType
import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable

@Serdeable
@MappedEntity
data class RideType(
    @field:Id
    @field:GeneratedValue(GeneratedValue.Type.AUTO)
    val id: Long? = null,
    val rideType: RideType,
    val driverBata: Double,
    val ezyCommission: Double,
    // TODO: see if we can add a unit and just limit to minKms and maxKms
    val minKmsPerDay: Int = 10,
    val maxKmsPerDay: Int = 600,
    val minKmsPerHour: Int = 10,
    val minHoursBeforeAccept: Int = 20,
)
