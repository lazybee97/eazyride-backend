package com.eazyride.service.strategy

import com.eazyride.dto.CalculatePriceDto

/**
 * Oneway trip : 1.09 * max(maxapprox_loc_based_kms, base_kms)*base_price_per_km + driver_bata
 */
class OneWayPriceStrategy : PriceStrategy {
    override fun calculatePrice(calculatePriceDto: CalculatePriceDto): Double {
        val numberOfDays = 1

        val distancePrice =
            (
                (numberOfDays.times(calculatePriceDto.rideType.minKmsPerDay))
                    .coerceAtLeast(calculatePriceDto.distance.toInt())
            ).times(calculatePriceDto.carType.basePricePerKm)
        val driverPrice = calculatePriceDto.rideType.driverBata * numberOfDays

        return calculatePriceDto.rideType.ezyCommission.times(distancePrice + driverPrice)
    }

    override fun isRideTypeSupported(rideType: RideType): Boolean {
        return rideType == RideType.ONE_WAY
    }
}
