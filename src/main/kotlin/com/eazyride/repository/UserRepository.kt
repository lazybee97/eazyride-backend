package com.eazyride.repository

import com.eazyride.entity.User
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@JdbcRepository(dialect = Dialect.POSTGRES) //<1>
abstract class UserRepository : PageableRepository<User, Long> {

    abstract fun save(user: User): User

    abstract fun get(id: Long): User?

    abstract fun findByPhoneNumber(phoneNumber: String): User?

    abstract fun update(user: User): User
}