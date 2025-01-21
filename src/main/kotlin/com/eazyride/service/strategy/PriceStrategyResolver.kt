package com.eazyride.service.strategy

import com.eazyride.enums.RideType
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class PriceStrategyResolver
    @Inject
    constructor(
        private val priceStrategies: List<PriceStrategy>,
    ) {
        fun resolvePriceStrategy(rideType: RideType): PriceStrategy =
            priceStrategies.first { it.isRideTypeSupported(rideType) }
                ?: throw IllegalArgumentException("No price strategy found for ride type: $rideType")
    }
}
