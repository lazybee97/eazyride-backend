package com.eazyride.repository

import com.eazyride.entity.CarType
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@JdbcRepository(dialect = Dialect.POSTGRES) // <1>
abstract class CarTypeRepository : PageableRepository<CarType, Long> {
    abstract fun save(carType: CarType): CarType

    abstract fun get(id: Long): CarType?

    abstract fun findByCarType(carType: String): CarType?

    abstract fun get(carType: String): CarType?
}
