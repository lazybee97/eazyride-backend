package com.eazyride.service.strategy

import com.eazyride.dto.CalculatePriceDto
import com.eazyride.utils.getBilledHours

/**
 * Local :  1.09 * ((max(input_kms-input_hours*10, 0) * base_price_per_km) + (input_hours * base_price_per_hr))
 */
class IntraCityPriceStrategy : PriceStrategy {
    override fun calculatePrice(calculatePriceDto: CalculatePriceDto): Double {
        val numberOfHours = getBilledHours(calculatePriceDto.startDateTime, calculatePriceDto.endDateTime)

        val hourlyPrice = calculatePriceDto.carType.basePricePerHour.times(numberOfHours)
        val addDistancePrice =
            (
                calculatePriceDto.distance - numberOfHours.times(calculatePriceDto.rideType.minKmsPerHour)
            ).coerceAtLeast(0).times(calculatePriceDto.carType.basePricePerKm)

        return calculatePriceDto.rideType.ezyCommission.times(hourlyPrice + addDistancePrice)
    }

    override fun isRideTypeSupported(rideType: RideType): Boolean {
        return rideType == RideType.INTRA_CITY
    }
}
