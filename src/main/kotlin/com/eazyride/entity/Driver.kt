package com.eazyride.entity

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable

@Serdeable
@MappedEntity
data class Driver(
    @field:Id
    val phoneNumber: String, // Primary key
    val name: String,
    val company: String,
    val dlId: String,
    val bankDetails: String,
    val vendorId: Long,
    val aadharId: String?, // Nullable if not collected
    val secondaryId: String?,
    val languages: String,
    val homeLocation: Long, // Address ID
    val deviceId: String,
    val isVerified: Boolean,
)
