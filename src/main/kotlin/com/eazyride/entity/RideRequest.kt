package com.eazyride.entity

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.Relation
import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank

@Serdeable
@MappedEntity
data class RideRequest(
    @field:Id
    @field:GeneratedValue(GeneratedValue.Type.AUTO)
    var id: Long? = null,
    @NotBlank val userId: Long,
    @NotBlank val pickupLoc: String,
    @NotBlank val dropLoc: String,
    @NotBlank val startTime: String,
    @NotBlank val endTime: String,
    @NotBlank val rideType: String,
    @NotBlank val carType: String,
    @NotBlank var status: String,
    @Relation(value = Relation.Kind.ONE_TO_MANY)
    var bids: List<Bid> = emptyList(),
//    val matchedDrivers: List<Long> = emptyList()
)