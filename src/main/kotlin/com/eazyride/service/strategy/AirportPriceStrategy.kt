package com.eazyride.service.strategy

import com.eazyride.dto.CalculatePriceDto

/**
 * Airport cab: 1.09 * max(approx_loc_based_kms, 40) * base_price_per_km
 */
class AirportPriceStrategy : PriceStrategy {
    override fun calculatePrice(calculatePriceDto: CalculatePriceDto): Double {
        val distancePrice =
            (
                calculatePriceDto.rideType.minKmsPerDay
                    .coerceAtLeast(calculatePriceDto.distance.toInt())
            ).times(calculatePriceDto.carType.basePricePerKm)

        return calculatePriceDto.rideType.ezyCommission.times(distancePrice)
    }

    override fun isRideTypeSupported(rideType: RideType): Boolean {
        return rideType == RideType.AIRPORT
    }
}
