package com.eazyride.repository

import com.eazyride.entity.Ride
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@JdbcRepository(dialect = Dialect.POSTGRES) //<1>
abstract class RideRepository : PageableRepository<Ride, Long> {

    abstract fun save(ride: Ride): Ride

    abstract fun get(id: Long): Ride?

    abstract fun findByUserId(userId: Long, status: String? = null): List<Ride>
}