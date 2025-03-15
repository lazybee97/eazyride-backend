package com.eazyride.repository

import com.eazyride.entity.RideRequest
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository
import io.micronaut.data.repository.jpa.JpaSpecificationExecutor
import jakarta.persistence.criteria.Predicate
import java.time.LocalDateTime

@JdbcRepository(dialect = Dialect.POSTGRES) //<1>
abstract class RideRequestRepository : PageableRepository<RideRequest, Long>, JpaSpecificationExecutor<RideRequest> {

    abstract fun save(rideRequest: RideRequest): RideRequest

    abstract fun get(id: Long): RideRequest?

    abstract fun findByUserId(userId: Long, status: String? = null): List<RideRequest>

    fun findWithFilters(userId: Long? = null,
     pickupLoc: String? = null,
     status: String? = null, 
     carType: String? = null
     ): List<RideRequest> {
        return findAll { root, query, cb ->
            val predicates = mutableListOf<Predicate>()
            
            userId?.let { predicates.add(cb.equal(root.get<Long>("userId"), it)) }
            pickupLoc?.let { predicates.add(cb.equal(root.get<String>("pickupLoc"), it)) }
            status?.let { predicates.add(cb.equal(root.get<String>("status"), it)) }    
            carType?.let { predicates.add(cb.equal(root.get<String>("carType"), it)) }  
            
            cb.and(*predicates.toTypedArray())
        }
    }

    fun findByStartTimeAfter(pickupLoc: String, status: String, carType: String, startTime: LocalDateTime): List<RideRequest> {
        return findAll { root, query, cb ->
            val predicates = mutableListOf<Predicate>()
            predicates.add(cb.equal(root.get<String>("pickupLoc"), pickupLoc))
            predicates.add(cb.equal(root.get<String>("status"), status))    
            predicates.add(cb.equal(root.get<String>("carType"), carType))  
            predicates.add(cb.greaterThan(root.get("startTime"), startTime))
            cb.and(*predicates.toTypedArray())
        }
    }

}