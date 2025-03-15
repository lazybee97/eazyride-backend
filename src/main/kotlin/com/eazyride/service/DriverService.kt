package com.eazyride.service

import com.eazyride.entity.Ride
import com.eazyride.repository.RideRepository
import jakarta.inject.Singleton
import java.util.*

@Singleton
class DriverService(
    private val rideRepository: RideRepository
) {
    fun getDriverRides(driverId: Long, status: String?): List<Ride> {
        return rideRepository.findWithFilters(driverId = driverId, status = status)
    }
} 