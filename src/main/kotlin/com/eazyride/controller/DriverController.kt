package com.eazyride.controller

import com.eazyride.model.GetDriverRidesResponse
import com.eazyride.service.DriverService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import jakarta.inject.Inject

@Controller("/api/driver")
class DriverController @Inject constructor(
    private val driverService: DriverService
) {
    @Get("/{driverId}/rides")
    fun getDriverRides(
        @PathVariable driverId: Long,
        @QueryValue(defaultValue = "") status: String?
    ): HttpResponse<GetDriverRidesResponse> {
        val rides = driverService.getDriverRides(driverId, status)
        val response = GetDriverRidesResponse(
            rides = rides,
            success = true,
            status = HttpStatus.OK,
            message = "Successfully retrieved driver rides"
        )
        return HttpResponse.ok(response)
    }

    
} 