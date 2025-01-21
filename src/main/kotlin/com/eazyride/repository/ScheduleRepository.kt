package com.eazyride.repository

import com.eazyride.entity.Schedule
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.UUID

@Repository
interface ScheduleRepository : CrudRepository<Schedule, UUID> {
    fun findByCarId(carId: UUID): List<Schedule>

    @io.micronaut.data.annotation.Query("""
        SELECT DISTINCT s.car_id 
        FROM schedule s
        WHERE NOT EXISTS (
            SELECT 1 
            FROM schedule 
            WHERE car_id = s.car_id
            AND car_type = :car_type
            AND block_start_ts < :endTime 
            AND block_end_ts > :startTime
        )
    """)
    fun findAvailableCarIds(car_type: CarType, startTime: java.time.LocalDateTime, endTime: java.time.LocalDateTime): List<UUID>
    
}
