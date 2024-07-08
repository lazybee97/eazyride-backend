package com.eazyride.entity

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank

@Serdeable
@MappedEntity
data class Address(
    @field:Id
    @field:GeneratedValue(GeneratedValue.Type.AUTO)
    var id: Long? = null,
    @NotBlank val addressLine1: String,
    var addressLine2: String? = null,
    @NotBlank val city: String,
    @NotBlank val state: String,
    @NotBlank val country: String,
    @NotBlank val pinCode: String
)