package com.eazyride

import io.micronaut.runtime.Micronaut.build
import io.swagger.v3.oas.annotations.*
import io.swagger.v3.oas.annotations.info.*

@OpenAPIDefinition(
    info = Info(
            title = "eazyride",
            version = "0.0"
    )
)
object Api {
}
fun main(args: Array<String>) {
    build()
        .args(*args)
        .packages("com.eazyride")
        .start()
}

