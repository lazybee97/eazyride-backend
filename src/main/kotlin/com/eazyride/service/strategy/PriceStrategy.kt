package com.eazyride.service.strategy

import com.eazyride.dto.CalculatePriceDto

interface PriceStrategy {
    fun calculatePrice(calculatePriceDto: CalculatePriceDto): Double
    fun isRideTypeSupported(rideType: RideType): Boolean
}
