package com.eazyride.service

import com.eazyride.entity.RideType
import com.eazyride.model.PriceEstimateRequest
import com.eazyride.model.PriceEstimateResponse
import com.eazyride.repository.RideTypeRepository
import jakarta.inject.Singleton
import java.time.temporal.ChronoUnit

/**
 * A service layer handles Price Estimate related business logic.
 */
@Singleton
class PriceEstimateService(
    private val rideTypeRepository: RideTypeRepository,
) {
    /**
     * Calculate the price estimate based on the request.
     *
     * @param request The request object containing the startDateTime, endDateTime, distance, type, and category.
     * @return The response object containing the list of estimates.
     */
    fun getPriceEstimate(request: PriceEstimateRequest): PriceEstimateResponse {
        val estimates = mutableListOf<PriceEstimateResponse.PriceEstimate>()
        val rideType = request.rideType ?: "default"
        val rideTypeEntity = rideTypeRepository.findByRideType(rideType)

        val basePricePerKm = rideTypeEntity?.basePricePerKm ?: 10.0
        val numberOfDays =
            ChronoUnit.DAYS.between(
                request.startDateTime.toLocalDate(),
                request.endDateTime.toLocalDate(),
            )

        val price = (250 * numberOfDays).coerceAtLeast(request.distance) *basePricePerKm
        estimates.add(PriceEstimateResponse.PriceEstimate(request.carType ?: "default", price))
        return PriceEstimateResponse(estimates)
    }

    /**
     * create a function to save ride type
     */
    fun saveRideType() {
        // save ride type using the rideTypeRepository
        val r = RideType(rideType = "Round Trip", basePricePerKm = 13.0, driverBata = 500.0, maxKmsPerDay = 200)
        rideTypeRepository.save(r)
    }
}
