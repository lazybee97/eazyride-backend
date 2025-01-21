package com.eazyride.controller

import com.eazyride.dto.PriceEstimateDto
import com.eazyride.enums.RideType
import com.eazyride.service.PriceEstimateService
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
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
    @Inject private val priceEstimateService: PriceEstimateService,
) {
    /**
     * This method returns a price estimate for a ride
     */
    @Get(uri = "/", produces = [MediaType.APPLICATION_JSON])
    fun getPriceEstimate(
        @QueryValue request: PriceEstimateRequest
    ): HttpResponse<*> {
        val payload = PriceEstimateDto(
            startDateTime = request.startDateTime,
            endDateTime = request.endDateTime,
            distance = request.distance,
            rideType = RideType.valueOf(request.rideType),
            carType = request.carType
        )

        return try {
            val response = priceEstimateService.getPriceEstimate(payload)
            return HttpResponse.ok(response)
        } catch (e: Exception) {
            HttpResponse.serverError("An error occurred while getting price estimate")
        }
    }
}
