package com.eazyride.service

import com.eazyride.entity.Bid
import com.eazyride.entity.Ride
import com.eazyride.entity.RideRequest
import com.eazyride.model.*
import com.eazyride.repository.BidRepository
import com.eazyride.repository.RideRepository
import com.eazyride.repository.RideRequestRepository
import com.eazyride.repository.UserRepository
import jakarta.inject.Singleton
import mu.KLogger
import mu.KotlinLogging

/**
 * A service layer handles Ride related business logic.
 */
@Singleton
class RideService(
    private val bidRepository: BidRepository,
    private val rideRepository: RideRepository,
    private val rideRequestRepository: RideRequestRepository,
    private val userRepository: UserRepository,
) {
    private val logger: KLogger = KotlinLogging.logger {}

    fun getRides(request: GetRidesRequest): GetRidesResponse {
        logger.info("Getting rides for user with id: ${request.userId}")
        if (!isUserAuthenticated(request.userId, request.accessToken)) {
            return GetRidesResponse()
        }
        val rides = rideRepository.findByUserId(request.userId, request.status)
        return GetRidesResponse(rides = rides, success = true)
    }

    /**
     *   48 hrs - full  refund
     *   24 hrs - 50% refund
     *   12 hrs - 25% refund
     */
    fun cancelRide(
        rideId: Long,
        userId: Long,
        accessToken: String,
    ): Boolean {
        logger.info("Cancelling ride with id: $rideId for user with id: $userId")
        if (!isUserAuthenticated(userId, accessToken)) {
            throw IllegalArgumentException("User not authenticated")
        }

        val ride =
            rideRepository.findById(rideId).orElse(null)
                ?: throw IllegalArgumentException("Ride not found")

        ride.status = "CANCELLED"
        rideRepository.update(ride)
        return true
    }

    private fun isUserAuthenticated(
        userId: Long,
        accessToken: String?,
    ): Boolean {
        val user = userRepository.get(userId)
        if (user == null) {
            println("User with id: $userId not found")
            return false
        }
        logger.debug("accessToken: $accessToken")
        val bearerToken = accessToken?.takeIf { it.startsWith("Bearer ") }?.substring(7)
        logger.debug("bearerToken: $bearerToken")
        return bearerToken == user.accessToken
    }

    private fun invalidateOtherBids(
        rideRequest: RideRequest,
        acceptedBid: Bid,
    ) {
        rideRequest.bids.forEach {
            if (it.id != acceptedBid.id) {
                bidRepository.update(it.copy(status = "INVALID"))
                // TODO[P0]: Send notification to driver
            }
        }
    }
}
