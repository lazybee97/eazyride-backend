package com.eazyride.service

import com.eazyride.model.PriceEstimateRequest
import com.eazyride.model.PriceEstimateResponse
import jakarta.inject.Singleton

/**
 * A service layer handles Price Estimate related business logic.
 */
@Singleton
class PriceEstimateService {
    /**
     * Calculate the price estimate based on the request.
        *
        * @param request The request object containing the startDateTime, endDateTime, distance, type, and category.
        * @return The response object containing the list of estimates.
        */
        fun getPriceEstimate(request: PriceEstimateRequest): PriceEstimateResponse {
            val estimates = mutableListOf<PriceEstimateResponse.PriceEstimate>()
            val price = when (request.type) {
                "standard" -> 1.0
                "premium" -> 1.5
                else -> 1.0
            }
            val category = request.category ?: "default"
            estimates.add(PriceEstimateResponse.PriceEstimate(category, request.distance * price))
            return PriceEstimateResponse(estimates)
        }
}