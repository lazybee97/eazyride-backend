package com.eazyride.entity

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank

@Serdeable
@MappedEntity
data class Distance(
    @field:Id
    @field:GeneratedValue(GeneratedValue.Type.AUTO)
    var id: Long? = null,
    @NotBlank val origin: String,
    @NotBlank val destination: String,
    val distanceInKM: Long? = null,
    val durationInMinutes: Long? = null,
)