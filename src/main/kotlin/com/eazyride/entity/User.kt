package com.eazyride.entity

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank

@Serdeable
@MappedEntity
data class User(
    @field:Id
    @field:GeneratedValue(GeneratedValue.Type.AUTO)
    var id: Long? = null,
    @NotBlank val name: String,
    @NotBlank val phoneNumber: String,
    val email: String? = null,
    var address: Address? = null,  // TODO[P1]: foreign key constraint
    val dob: String? = null,
    val preferredLanguage: String? = null,
    var otp: String? = null,
    val accessToken: String? = null
)