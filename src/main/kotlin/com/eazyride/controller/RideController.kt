package com.eazyride.controller

import com.eazyride.model.GetRidesRequest
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
    @Inject private val rideService: RideService,
) {
    private val logger: KLogger = KotlinLogging.logger {}

    @Get(uri = "/getRides", produces = [MediaType.APPLICATION_JSON])
    fun getRides(
        @Header("Authorization") authorization: String,
        @QueryValue request: GetRidesRequest,
    ): HttpResponse<*> =
        try {
            request.accessToken = authorization
            val response = rideService.getRides(request)
            HttpResponse.ok(response)
        } catch (e: Exception) {
            logger.error(e) { "An error occurred while getting rides" }
            HttpResponse.serverError("An error occurred while getting rides")
        }
}
