package com.eazyride.repository

import com.eazyride.entity.Ride
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository
import jakarta.persistence.criteria.Predicate

@JdbcRepository(dialect = Dialect.POSTGRES) //<1>
abstract class RideRepository : PageableRepository<Ride, Long>, JpaSpecificationExecutor<Ride> {

    abstract fun save(ride: Ride): Ride

    abstract fun get(id: Long): Ride?

    abstract fun findByUserId(userId: Long, status: String? = null): List<Ride>

    fun findWithFilters(userId: Long? = null, driverId: Long? = null, status: String? = null): List<Ride> {
        return findAll { root, query, cb ->
            val predicates = mutableListOf<Predicate>()
            
            userId?.let { predicates.add(cb.equal(root.get<Long>("userId"), it)) }
            driverId?.let { predicates.add(cb.equal(root.get<Long>("driverId"), it)) }
            status?.let { predicates.add(cb.equal(root.get<String>("status"), it)) }
            
            cb.and(*predicates.toTypedArray())
        }
    }
}