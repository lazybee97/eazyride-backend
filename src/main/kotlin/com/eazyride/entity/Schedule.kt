package com.eazyride.entity

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable
import java.time.LocalDateTime
import java.util.UUID

@Serdeable
@MappedEntity
data class Schedule(
    @field:Id
    @field:GeneratedValue
    val id: UUID? = null,
    val rideId: UUID,
    val isManual: Boolean,
    val carId: UUID,
    val blockStartTs: LocalDateTime,
    val blockEndTs: LocalDateTime,
)
