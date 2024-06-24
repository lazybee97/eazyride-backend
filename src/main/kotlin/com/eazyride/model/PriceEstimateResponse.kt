package com.eazyride.model

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class PriceEstimateResponse(
    val estimates: List<PriceEstimate>
) {
    @Serdeable
    data class PriceEstimate (
        val carType: String,
        val price: Double
    )
}
