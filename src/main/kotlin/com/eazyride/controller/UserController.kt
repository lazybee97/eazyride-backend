package com.eazyride.controller

import com.eazyride.model.CreateOAuthUserRequest
import com.eazyride.model.CreateUserRequest
import com.eazyride.model.InitiateLoginRequest
import com.eazyride.model.LoginUserRequest
import com.eazyride.service.UserService
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import jakarta.inject.Inject
import mu.KLogger
import mu.KotlinLogging

@Validated
@Controller("/user")
class UserController(
    @Inject private val userService: UserService
) {
    private val logger: KLogger = KotlinLogging.logger {}

    @Post(uri="/createUser", produces=[MediaType.APPLICATION_JSON])
    fun createUser(@Body request: CreateUserRequest): HttpResponse<*> {
        return try {
            val response =  userService.createUser(request)
            return  HttpResponse.ok(response)
        } catch (e: Exception) {
            logger.error(e) { "An error occurred while creating user" }
            HttpResponse.serverError("An error occurred while creating user")
        }
    }

    @Post(uri="/createOAuthUser", produces=[MediaType.APPLICATION_JSON])
    fun createOAuthUser(@Body request: CreateOAuthUserRequest): HttpResponse<*> {
        return try {
            val response =  userService.createOAuthUser(request)
            return  HttpResponse.ok(response)
        } catch (e: Exception) {
            logger.error(e) { "An error occurred while creating OAuth user" }
            HttpResponse.serverError("An error occurred while creating OAuth user")
        }
    }

    @Post(uri="/initiateLogin", produces=[MediaType.APPLICATION_JSON])
    fun initiateLogin(@Body request: InitiateLoginRequest): HttpResponse<*> {
        return try {
            val response =  userService.initiateLogin(request)
            return  HttpResponse.ok(response)
        } catch (e: Exception) {
            logger.error(e) { "An error occurred while initiating login" }
            HttpResponse.serverError("An error occurred while initiating login")
        }
    }

    @Post(uri="/loginUser", produces=[MediaType.APPLICATION_JSON])
    fun loginUser(@Body request: LoginUserRequest): HttpResponse<*> {
        return try {
            val response =  userService.loginUser(request)
            return  HttpResponse.ok(response)
        } catch (e: Exception) {
            logger.error(e) { "An error occurred while logging in user" }
            HttpResponse.serverError("An error occurred while logging in user")
        }
    }
}