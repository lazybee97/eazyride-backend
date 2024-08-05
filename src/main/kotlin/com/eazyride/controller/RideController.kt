package com.eazyride.controller

import com.eazyride.model.GetRidesRequest
import com.eazyride.model.GetRideRequestsRequest
import com.eazyride.model.AcceptBidForRideRequest
import com.eazyride.service.RideService
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import jakarta.inject.Inject
import mu.KLogger
import mu.KotlinLogging

@Validated
@Controller("/ride")
class RideController(
    @Inject private val rideService: RideService
) {
    private val logger: KLogger = KotlinLogging.logger {}

    @Get(uri = "/getRides", produces = [MediaType.APPLICATION_JSON])
    fun getRides(
        @Header("Authorization") authorization: String,
        @QueryValue("userId") userId: Long,
        @QueryValue("status") status: String? = null): HttpResponse<*> {
        return try {
            val request = GetRidesRequest(userId, authorization, status)
            val response = rideService.getRides(request)
            HttpResponse.ok(response)
        } catch (e: Exception) {
            logger.error(e) { "An error occurred while getting rides" }
            HttpResponse.serverError("An error occurred while getting rides")
        }
    }

    @Get(uri = "/getRideRequests", produces = [MediaType.APPLICATION_JSON])
    fun getRideRequests(
        @Header("Authorization") authorization: String,
        @QueryValue("userId") userId: Long,
        @QueryValue("status") status: String? = null
    ): HttpResponse<*> {
        return try {
            val request = GetRideRequestsRequest(userId, authorization, status)
            val response = rideService.getRideRequests(request)
            HttpResponse.ok(response)
        } catch (e: Exception) {
            logger.error(e) { "An error occurred while getting ride requests" }
            HttpResponse.serverError("An error occurred while getting ride requests")
        }
    }

    @Post(uri = "/acceptBid", produces = [MediaType.APPLICATION_JSON])
    fun acceptBidForRideRequest(
        @Header("Authorization") authorization: String,
        @Body request: AcceptBidForRideRequest
    ): HttpResponse<*> {
        return try {
            request.accessToken = authorization
            val response = rideService.acceptBidForRideRequest(request)
            HttpResponse.ok(response)
        } catch (e: Exception) {
            logger.error(e) { "An error occurred while accepting bid for ride request" }
            HttpResponse.serverError("An error occurred while accepting bid for ride request")
        }
    }

}