package com.eazyride.model

data class PriceEstimateResponse(
    val estimates: List<PriceEstimate>
) {
    data class PriceEstimate (
        val category: String,
        val price: Double
    )
}
