package com.eazyride
import com.eazyride.utils.getBilledDays
import io.kotest.core.spec.style.StringSpec
import java.time.LocalDateTime

class EazyrideTest :
    StringSpec({

        "test time util" {
            assert(
                getBilledDays(
                    a = LocalDateTime.of(2021, 1, 2, 6, 0),
                    b = LocalDateTime.of(2021, 1, 2, 4, 59),
                ) == 3L,
            )
        }
    })
