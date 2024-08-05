package com.eazyride.repository

import com.eazyride.entity.Distance
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@JdbcRepository(dialect = Dialect.POSTGRES) //<1>
abstract class DistanceRepository : PageableRepository<Distance, Long> {

    abstract fun save(bid: Distance): Distance

    abstract fun get(id: Long): Distance?

    abstract fun findByOriginAndDestination(origin: String?, destination: String?): List<Distance?>?
}