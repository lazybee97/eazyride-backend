package com.eazyride.utils

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

fun getBilledDays(
    a: LocalDateTime,
    b: LocalDateTime,
): Long =
    ChronoUnit.DAYS.between(
        a.toLocalDate(),
        b.toLocalDate(),
    ) + 1

fun getBilledHours(
    a: LocalDateTime,
    b: LocalDateTime,
): Long =
    ChronoUnit.HOURS.between(
        a,
        b,
    )
