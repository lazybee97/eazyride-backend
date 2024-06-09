package com.eazyride.controller

import com.eazyride.model.PriceEstimateRequest
import com.eazyride.model.PriceEstimateResponse
import com.eazyride.service.PriceEstimateService
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.validation.Validated
import jakarta.inject.Inject

@Validated
@Controller("/priceEstimate")
class PriceEstimateController(
    @Inject private val priceEstimateService: PriceEstimateService
) {

    /**
     * This method returns a price estimate for a ride
     */
    @Get(uri="/", produces=[MediaType.APPLICATION_JSON])
    fun getPriceEstimate(
        @Body payload: PriceEstimateRequest
    ): HttpResponse<*> {



        return try {
            // Call the pricing service to get the price estimate
            val response =  priceEstimateService.getPriceEstimate(payload)

            return  HttpResponse.ok(response)
        } catch (e: Exception) {
            // Log the error
            // logger.error("Error getting price estimate", e)
            // Return a default price estimate
            HttpResponse.serverError("An error occurred while getting price estimate")
        }
    }
}