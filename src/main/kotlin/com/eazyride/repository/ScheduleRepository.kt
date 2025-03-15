package com.eazyride.repository

import com.eazyride.entity.Schedule
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository
import com.eazyride.entity.CarType
import io.micronaut.data.repository.jpa.JpaSpecificationExecutor
import java.util.UUID

@Repository
interface ScheduleRepository : PageableRepository<Schedule, UUID>, JpaSpecificationExecutor<Schedule> {
    fun findByCarId(carId: UUID): List<Schedule>

    @io.micronaut.data.annotation.Query("""
        SELECT DISTINCT s.car_id 
        FROM schedule s
        WHERE car_type = :carType 
        AND NOT EXISTS (
            SELECT 1 
            FROM schedule 
            WHERE car_id = s.car_id
            AND block_start_ts < :endTime 
            AND block_end_ts > :startTime
        )
    """)
    fun findAvailableCarIds(carType: CarType, startTime: java.time.LocalDateTime, endTime: java.time.LocalDateTime): List<UUID>
    
}
