package com.eazyride.service

import com.eazyride.entity.Distance
import com.eazyride.repository.DistanceRepository
import com.google.maps.DistanceMatrixApi
import com.google.maps.GeoApiContext
import com.google.maps.model.TrafficModel
import com.google.maps.model.TravelMode
import jakarta.inject.Singleton


@Singleton
class DistanceService(
    private val distanceRepository: DistanceRepository,
    private val apiKey: String,
) {
    private val context: GeoApiContext = GeoApiContext.Builder().apiKey(apiKey).build()

    fun getDistance(origin: String, destination: String): Distance {
        val existingDistance =
            distanceRepository.findByOriginAndDestination(origin, destination)?.firstOrNull()
        if (existingDistance != null) {
            return existingDistance
        }
        val calculatedDistance = calculateDistanceAndDuration(origin, destination)
        val distance = Distance(
            origin = origin,
            destination = destination,
            distanceInKM = calculatedDistance.first / 1000,
            durationInMinutes = calculatedDistance.second / 60,
        )
        return distanceRepository.save(distance)
    }

    private fun calculateDistanceAndDuration(origin: String, destination: String): Pair<Long, Long> {
        val distanceMatrix =
            DistanceMatrixApi
                .newRequest(context)
                .origins(origin)
                .destinations(destination)
                .trafficModel(TrafficModel.BEST_GUESS)
                .mode(TravelMode.DRIVING)
                .await()
        val entry = distanceMatrix.rows[0].elements[0]
        return Pair(entry.distance.inMeters, entry.durationInTraffic.inSeconds)
    }

}
