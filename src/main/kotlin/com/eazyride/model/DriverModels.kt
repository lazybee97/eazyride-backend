package com.eazyride.model

import io.micronaut.core.annotation.Introspected
import io.micronaut.http.HttpStatus
import io.micronaut.serde.annotation.Serdeable

@Introspected
@Serdeable
data class GetDriverRidesResponse(
    val rides: List<Ride> = emptyList(),
    val success: Boolean = false,
    val message: String? = null,
    val status: HttpStatus = HttpStatus.OK,
) 
