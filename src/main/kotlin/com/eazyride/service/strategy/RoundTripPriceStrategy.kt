package com.eazyride.service.strategy

import com.eazyride.dto.CalculatePriceDto
import com.eazyride.utils.getBilledDays

/**
 * Round trip : 1.09 * (Max(approx_loc_based_kms, 250*num_days) * base_price_per_km) + driver_bata*num_days
 */
class RoundTripPriceStrategy : PriceStrategy {
    override fun calculatePrice(calculatePriceDto: CalculatePriceDto): Double {
        val numberOfDays = getBilledDays(calculatePriceDto.startDateTime, calculatePriceDto.endDateTime)

        val distancePrice =
            (
                (numberOfDays.times(calculatePriceDto.rideType.minKmsPerDay))
                    .coerceAtLeast(calculatePriceDto.distance)
            ).times(calculatePriceDto.carType.basePricePerKm)
        val driverPrice = calculatePriceDto.rideType.driverBata * numberOfDays

        return calculatePriceDto.rideType.ezyCommission.times(distancePrice + driverPrice)
    }

    override fun isRideTypeSupported(rideType: RideType): Boolean {
        return rideType == RideType.ROUND_TRIP
    }
}
