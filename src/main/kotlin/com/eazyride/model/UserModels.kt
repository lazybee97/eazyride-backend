package com.eazyride.model

import com.eazyride.entity.Address
import com.eazyride.entity.User
import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank

@Introspected
@Serdeable
data class CreateUserRequest(
    @NotBlank
    val user: User,
    @NotBlank
    val address: Address
)

@Introspected
@Serdeable
data class CreateOAuthUserRequest(
    @NotBlank
    val idToken: String,
)

@Introspected
@Serdeable
data class UpdateUserRequest(
    @NotBlank
    val user: User,
    @NotBlank
    val address: Address
)

@Introspected
@Serdeable
data class CreateUserResponse(
    val user: User,
    val success: Boolean = false
)

@Introspected
@Serdeable
data class LoginUserRequest(
    @NotBlank
    val phoneNumber: String,
    @NotBlank
    val otp: String
)

@Introspected
@Serdeable
data class LoginUserResponse(
    val userId: Long? = null,
    val success: Boolean = false,
    val accessToken: String? = null
)

@Introspected
@Serdeable
data class InitiateLoginRequest(
    @NotBlank
    val phoneNumber: String
)

@Introspected
@Serdeable
data class InitiateLoginResponse(
    val success: Boolean = false
)