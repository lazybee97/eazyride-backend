package com.eazyride.controller

import com.eazyride.model.AcceptBidForRideRequest
import com.eazyride.model.RideRequestRequest
import com.eazyride.service.RideRequestService
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import jakarta.inject.Inject
import mu.KLogger
import mu.KotlinLogging

@Validated
@Controller("/api/rideRequest")
class RideRequestController(
    @Inject private val rideRequestService: RideRequestService,
) {
    private val logger: KLogger = KotlinLogging.logger {}

    @Get(uri = "/user/{userId}", produces = [MediaType.APPLICATION_JSON])
    fun getRideRequests(
        @Header("Authorization") authorization: String,
        @PathVariable userId: Long,
        @QueryValue("status") status: String? = null,
    ): HttpResponse<*> =
        try {
            val response = rideRequestService.getRideRequests(userId, authorization, status)
            HttpResponse.ok(response)
        } catch (e: Exception) {
            logger.error(e) { "An error occurred while getting ride requests" }
            HttpResponse.serverError("An error occurred while getting ride requests")
        }

    @Get(uri = "/car/{carId}", produces = [MediaType.APPLICATION_JSON])
    fun getRideRequestsForCar(
        @Header("Authorization") authorization: String,
        @PathVariable carId: Long,
    ): HttpResponse<*> =
        try {
            val response = rideRequestService.getRideRequestsForCar(carId, authorization)
            HttpResponse.ok(response)
        } catch (e: Exception) {
            logger.error(e) { "An error occurred while getting ride requests for car" }
            HttpResponse.serverError("An error occurred while getting ride requests for car")
        }

    @Get(uri = "/{rideRequestId}", produces = [MediaType.APPLICATION_JSON])
    fun getRideRequest(
        @Header("Authorization") authorization: String,
        @PathVariable rideRequestId: Long,
    ): HttpResponse<*> =
        try {
            val response = rideRequestService.getRideRequest(rideRequestId, authorization)
            HttpResponse.ok(response)
        } catch (e: Exception) {
            logger.error(e) { "An error occurred while getting ride request" }
            HttpResponse.serverError("An error occurred while getting ride request")
        }

    @Post(uri = "/acceptBid", produces = [MediaType.APPLICATION_JSON])
    fun acceptBidForRideRequest(
        @Header("Authorization") authorization: String,
        @Body request: AcceptBidForRideRequest,
    ): HttpResponse<*> =
        try {
            request.accessToken = authorization
            val response = rideRequestService.acceptBidForRideRequest(request)
            HttpResponse.ok(response)
        } catch (e: Exception) {
            logger.error(e) { "An error occurred while accepting bid for ride request" }
            HttpResponse.serverError("An error occurred while accepting bid for ride request")
        }

    @Post(uri = "/", produces = [MediaType.APPLICATION_JSON])
    fun createRideRequest(
        @Header("Authorization") authorization: String,
        @Body rideRequest: RideRequestRequest,
    ): HttpResponse<*> =
        try {
            val createdRideRequest = rideRequestService.createRideRequest(rideRequest, authorization)
            HttpResponse.ok(createdRideRequest)
        } catch (e: Exception) {
            logger.error(e) { "An error occurred while creating ride request" }
            HttpResponse.serverError("An error occurred while creating ride request")
        }
}
