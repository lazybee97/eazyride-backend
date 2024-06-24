package com.eazyride.repository

import com.eazyride.entity.RideType
import io.micronaut.data.annotation.Id
import io.micronaut.data.exceptions.DataAccessException
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository
import jakarta.transaction.Transactional
import jakarta.validation.constraints.NotBlank

@JdbcRepository(dialect = Dialect.POSTGRES) //<1>
abstract class RideTypeRepository : PageableRepository<RideType, Long> {

    abstract fun save(rideType: RideType): RideType

    abstract fun get(id: Long): RideType?

    abstract fun findByRideType(rideType: String): RideType?

    abstract fun get(rideType: String): RideType?

}