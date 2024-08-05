package com.eazyride.repository

import com.eazyride.entity.RideRequest
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@JdbcRepository(dialect = Dialect.POSTGRES) //<1>
abstract class RideRequestRepository : PageableRepository<RideRequest, Long> {

    abstract fun save(rideRequest: RideRequest): RideRequest

    abstract fun get(id: Long): RideRequest?

    abstract fun findByUserId(userId: Long, status: String? = null): List<RideRequest>
}