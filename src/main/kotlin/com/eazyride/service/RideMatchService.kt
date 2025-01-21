package com.eazyride.service

import com.eazyride.entity.RideRequest
import com.eazyride.repository.CarRepository
import com.eazyride.repository.ScheduleRepository
import jakarta.inject.Singleton

@Singleton
class RideMatchService(
    private val carRepository: CarRepository,
    private val scheduleRepository: ScheduleRepository,
) {
    fun fetchApplicableDriverIds(rideRequest: RideRequest): List<Long> {

        // Filter out cars that have overlapping schedules
        val availableCars = scheduleRepository.findAvailableCarIds(rideRequest.startDateTime, rideRequest.endDateTime)

        //TODO: Filter out any license expiries

        // Collect the driverId from the filtered cars
        // add the message context
        return availableCars.map { it.driverId }
    }
}
