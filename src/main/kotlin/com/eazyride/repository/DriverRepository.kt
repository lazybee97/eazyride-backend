package com.eazyride.repository

import com.eazyride.entity.Driver
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository
import io.micronaut.data.repository.jpa.JpaSpecificationExecutor
import jakarta.persistence.criteria.Predicate

@JdbcRepository(dialect = Dialect.POSTGRES) // <1>
abstract class DriverRepository :
    PageableRepository<Driver, Long>,
    JpaSpecificationExecutor<Driver> {
    abstract fun save(driver: Driver): Driver

    abstract fun get(id: Long): Driver?

    abstract fun findByPhoneNumber(phoneNumber: String): Driver?

    fun findWithFilters(
        id: Long? = null,
        phoneNumber: String? = null,
    ): List<Driver> =
        findAll { root, query, cb ->
            val predicates = mutableListOf<Predicate>()

            id?.let { predicates.add(cb.equal(root.get<Long>("id"), it)) }
            phoneNumber?.let { predicates.add(cb.equal(root.get<String>("phoneNumber"), it)) }

            cb.and(*predicates.toTypedArray())
        }
}
