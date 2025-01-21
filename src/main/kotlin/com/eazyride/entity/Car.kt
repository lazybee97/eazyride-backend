package com.eazyride.entity

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable
import java.util.UUID

@Serdeable
@MappedEntity
data class Car(
    @field:Id
    @field:GeneratedValue
    val id: UUID? = null,
    val model: String,
    val make: String,
    val type: String, // e.g., Sedan, SUV, etc.
    val seats: Int,
    val pollutionCheck: Boolean,
    val rcNumber: String,
    val available: Boolean,
    val rating: Double,
    val kmsTravelled: Double,
    val fastag: Boolean,
    val vendorId: Long,
    val driverId: Long,
    val fitnessCertificate: Boolean,
    val isVerified: Boolean,
)
