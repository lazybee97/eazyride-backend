package com.eazyride.entity

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column

@Serdeable
@MappedEntity
data class CarType(
    @field:Id
    @field:GeneratedValue(GeneratedValue.Type.AUTO)
    var id: Long? = null,
    @Column(unique = true)
    var carType: String,
    var basePricePerKm: Double,
    var maxPricePerKm: Double,
    var minPricePerKm: Double,
    var basePricePerHour: Double = 0.0,
)
