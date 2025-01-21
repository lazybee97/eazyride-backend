package com.eazyride.repository

import com.eazyride.entity.RideType
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@JdbcRepository(dialect = Dialect.POSTGRES) // <1>
abstract class RideTypeRepository : PageableRepository<RideType, Long> {
    abstract fun save(rideType: RideType): RideType

    abstract fun get(id: Long): RideType?

    abstract fun findByRideType(rideType: com.eazyride.enums.RideType): RideType?

    abstract fun get(rideType: String): RideType?
}
