package com.eazyride.service

import com.eazyride.dto.CalculatePriceDto
import com.eazyride.dto.PriceEstimateDto
import com.eazyride.model.PriceEstimateResponse
import com.eazyride.repository.CarTypeRepository
import com.eazyride.repository.RideTypeRepository
import com.eazyride.service.strategy.PriceStrategyResolver
import jakarta.inject.Inject
import jakarta.inject.Singleton

/**
 * A service layer handles Price Estimate related business logic.
 */
@Singleton
class PriceEstimateService(
    @Inject private val rideTypeRepository: RideTypeRepository,
    @Inject private val carTypeRepository: CarTypeRepository,
    @Inject private val priceStrategyResolver: PriceStrategyResolver,
) {
    /**
     * Calculate the price estimate based on the request.
     *
     * @param request The request object containing the startDateTime, endDateTime, distance, type, and category.
     * @return The response object containing the list of estimates.
     */
    fun getPriceEstimate(request: PriceEstimateDto): PriceEstimateResponse {
        val estimates = mutableListOf<PriceEstimateResponse.PriceEstimate>()
        val rideType = request.rideType
        val rideTypeDetails = rideTypeRepository.findByRideType(rideType)
        val carTypeList = carTypeRepository.findAll()
        val priceStrategy = priceStrategyResolver.resolvePriceStrategy(rideType)

        var calculatePriceDto =
            CalculatePriceDto(
                startDateTime = request.startDateTime,
                endDateTime = request.endDateTime,
                distance = request.distance,
                rideType = rideTypeDetails!!,
            )

        carTypeList.stream().forEach { carType ->
            calculatePriceDto.carType = carType
            priceStrategy.calculatePrice(calculatePriceDto)
        }

        return PriceEstimateResponse(estimates)
    }
}
