package com.eazyride.controller

import com.eazyride.model.PriceEstimateRequest
import com.eazyride.model.PriceEstimateResponse
import com.eazyride.service.PriceEstimateService
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import io.micronaut.validation.Validated
import jakarta.inject.Inject
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

@Validated
@Controller("/priceEstimate")
@ExecuteOn(TaskExecutors.BLOCKING)
class PriceEstimateController(
    @Inject private val priceEstimateService: PriceEstimateService
) {

    /**
     * This method returns a price estimate for a ride
     */
    @Get(uri="/", produces=[MediaType.APPLICATION_JSON])
    fun getPriceEstimate(
        startDateTime: LocalDateTime,
        endDateTime: LocalDateTime,
        distance: Long,
        @NotBlank
        rideType: String,
        carType: String?
    ): HttpResponse<*> {


        val payload = PriceEstimateRequest(
            startDateTime = startDateTime,
            endDateTime = endDateTime,
            distance = distance,
            rideType = rideType,
            carType = carType
        )

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

    @Post(uri = "/saveRideType", produces = [MediaType.APPLICATION_JSON])
    fun saveRideType(): HttpResponse<*> {
        priceEstimateService.saveRideType()
        return HttpResponse.ok("Ride Type saved successfully")
    }
}