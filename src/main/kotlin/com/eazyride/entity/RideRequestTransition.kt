package com.eazyride.entity

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable
import java.time.LocalDateTime
import java.util.UUID

@Serdeable
@MappedEntity
data class RideRequestTransition(
    @field:Id
    @field:GeneratedValue
    val id: UUID? = null,
    val rideRequestId: Long,
    val fromStatus: String,
    val toStatus: String,
    val transitionTime: LocalDateTime,
    val auditUserId: Long,
)
