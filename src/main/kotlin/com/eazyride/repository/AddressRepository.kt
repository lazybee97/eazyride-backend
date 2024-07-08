package com.eazyride.repository

import com.eazyride.entity.Address
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@JdbcRepository(dialect = Dialect.POSTGRES) //<1>
abstract class AddressRepository : PageableRepository<Address, Long> {

    abstract fun save(address: Address): Address

    abstract fun get(id: Long): Address?
}