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

    fun createRideRequest(rideReq: RideRequestRequest): RideRequestResponse {
        logger.info("Creating a new ride request for user with id: ${rideReq.userId}")
        if (!isUserAuthenticated(rideRequest.userId, rideRequest.accessToken)) {
            throw IllegalArgumentException("User not authenticated")
        }

        // Save the RideRequest object
        val rideRequest: RideRequest = rideRequestRepository.save(rideRequest)
        return rideRequest.
    }

    fun getRideRequests(request: GetRideRequestsRequest): GetRideRequestsResponse {
        logger.info("Getting ride requests for user with id: ${request.userId}")
        if (!isUserAuthenticated(request.userId, request.accessToken)) {
            return GetRideRequestsResponse()
        }
        val rideRequests = rideRequestRepository.findByUserId(request.userId, request.status)
        return GetRideRequestsResponse(rideRequests = rideRequests, success = true)
    }

    fun acceptBidForRideRequest(request: AcceptBidForRideRequest): AcceptBidForRideRequestResponse {
        logger.info("Accepting bid for ride request with id: ${request.rideRequestId}")
        if (!isUserAuthenticated(request.userId, request.accessToken)) {
            return AcceptBidForRideRequestResponse()
        }
        val rideRequest = rideRequestRepository.get(request.rideRequestId) ?: return AcceptBidForRideRequestResponse()

        val bid = rideRequest.bids.find { it.id == request.bidId } ?: return AcceptBidForRideRequestResponse()

        val ride =
            rideRepository.save(
                Ride(
                    userId = rideRequest.userId,
                    driverId = bid.driverId,
                    rideRequestId = request.rideRequestId,
                    carId = bid.carId,
                    pickupLoc = rideRequest.pickupLoc,
                    dropLoc = rideRequest.dropLoc,
                    startTime = rideRequest.startTime,
                    endTime = rideRequest.endTime,
                    rideType = rideRequest.rideType,
                    carType = "carType",
                    status = "PENDING",
                    kms = 10.0,
                    totalCost = 10.0,
                    profit = 10.0,
                    initialPayment = 10.0,
                    finalPayment = 10.0,
                    paymentStatus = "PENDING",
                ),
            )

        invalidateOtherBids(rideRequest, bid)

        return AcceptBidForRideRequestResponse(success = true, ride = ride)
    }

    fun cancelRideRequest(
        rideRequestId: Long,
        userId: Long,
        accessToken: String,
    ): Boolean {
        logger.info("Cancelling ride request with id: $rideRequestId for user with id: $userId")
        if (!isUserAuthenticated(userId, accessToken)) {
            throw IllegalArgumentException("User not authenticated")
        }

        val rideRequest =
            rideRequestRepository.findById(rideRequestId).orElse(null)
                ?: throw IllegalArgumentException("Ride request not found")

        rideRequest.status = "CANCELLED"
        rideRequestRepository.update(rideRequest)
        return true
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
