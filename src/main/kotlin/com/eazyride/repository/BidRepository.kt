package com.eazyride.repository

import com.eazyride.entity.Bid
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@JdbcRepository(dialect = Dialect.POSTGRES) //<1>
abstract class BidRepository : PageableRepository<Bid, Long> {

    abstract fun save(bid: Bid): Bid

    abstract fun get(id: Long): Bid?
}