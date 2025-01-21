package com.eazyride.repository

import com.eazyride.entity.Car
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.UUID

@Repository
interface CarRepository : CrudRepository<Car, UUID> {
    fun findByType(type: String): List<Car>
}
