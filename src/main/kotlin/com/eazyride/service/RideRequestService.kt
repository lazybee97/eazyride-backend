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
class RideRequestService(
    private val bidRepository: BidRepository,
    private val rideRepository: RideRepository,
    private val rideRequestRepository: RideRequestRepository,
    private val userRepository: UserRepository,
    private val scheduleRepository: ScheduleRepository,
    private val carRepository: CarRepository,
    private val driverRepository: DriverRepository,
) {
    private val logger: KLogger = KotlinLogging.logger {}

    fun createRideRequest(rideReq: RideRequestRequest): CreateRideRequestResponse {
        logger.info("Creating a new ride request for user with id: ${rideReq.userId}")
        if (!isUserAuthenticated(rideReq.userId, rideReq.accessToken)) {
            throw IllegalArgumentException("User not authenticated")
        }

        // Save the RideRequest object
        val rideReqEntity =
            RideRequest(
                userId = rideReq.userId,
                pickupLoc = rideReq.startLoc,
                dropLoc = rideReq.endLoc,
                startTime = rideReq.startDateTime,
                endTime = rideReq.endDateTime,
                rideType = rideReq.rideType,
                carType = rideReq.carType,
                status = "PENDING",
            )

        val rideRequest: RideRequest = rideRequestRepository.save(rideReqEntity)
        return rideRequest.id?.let { CreateRideRequestResponse(success = true, rideRequestId = it) }
            ?: CreateRideRequestResponse(success = false)
    }

    fun getRideRequests(
        userId: Long,
        accessToken: String,
        status: String?,
    ): GetRideRequestsResponse {
        logger.info("Getting ride requests for user with id: $userId")
        if (!isUserAuthenticated(userId, accessToken)) {
            return GetRideRequestsResponse()
        }
        val rideRequests = rideRequestRepository.findWithFilters(userId = userId, status = status)
        return GetRideRequestsResponse(
            rideRequests.map {
                com.eazyride.model.RideRequest(
                    rideRequestId = it.id ?: 0,
                    userId = it.userId,
                    pickupLoc = it.pickupLoc,
                    dropLoc = it.dropLoc,
                    startTime = it.startTime,
                    endTime = it.endTime,
                    rideType = it.rideType,
                    carType = it.carType,
                    status = it.status,
                )
            },
        )
    }

    fun getRideRequest(
        rideRequestId: Long,
        accessToken: String,
    ): GetRideRequestsResponse {
        logger.info("Getting ride request with id: $rideRequestId")
        if (!isUserAuthenticated(userId, accessToken)) {
            return GetRideRequestResponse()
        }
        val rideRequest = rideRequestRepository.findById(rideRequestId) ?: return GetRideRequestResponse()
        return rideRequest.toGetRideRequestResponse()
    }

    fun getRideRequestsForCar(
        carId: Long,
        accessToken: String,
    ): GetRideRequestsForCarResponse {
        logger.info("Getting ride requests for car with id: $carId")
       if (!isUserAuthenticated(carId, accessToken)) {
           return GetRideRequestsForDriverResponse()
       }

        // Find car details
        val car = carRepository.findById(carId)

        // Find the drivers onboarded location
        val driver = driverRepository.findById(car.driverId)
        val driverOnboardedLocation = driver?.get(0)?.onboardedLocation

        // Find the ride requests that start after 2 hours from now. TODO: make this configurable
        val startTime = LocalDateTime.now() + Duration.ofHours(2)

        val rideRequests =
            rideRequestRepository.findByStartTimeAfter(
                pickupLoc = driverOnboardedLocation,
                carType = car.type,
                status = status,
                startTime = startTime,
            )

        // Get the schedule of the car that ends after the ride request starts
        val schedule = scheduleRepository.findByCarId(car.id)

        return rideRequests.map { it.toGetRideRequestResponse() }
    }

    fun getRideRequestsForUser(
        userId: Long,
        accessToken: String,
        status: String?,
    ): GetRideRequestsForUserResponse {
        logger.info("Getting ride requests for user with id: $userId")
        if (!isUserAuthenticated(userId, accessToken)) {
            return GetRideRequestsForUserResponse()
        }
        val rideRequests = rideRequestRepository.findWithFilters(userId = userId, status = status)
        return rideRequests.map { it.toGetRideRequestResponse() }
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
