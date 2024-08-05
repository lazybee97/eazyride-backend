package com.eazyride.service

import com.eazyride.auth.GoogleAuth
import com.eazyride.entity.User
import com.eazyride.model.*
import com.eazyride.repository.UserRepository
import com.eazyride.repository.AddressRepository
import jakarta.inject.Singleton
import mu.KotlinLogging
import mu.KLogger

/**
 * A service layer handles User related business logic.
 */
@Singleton
class UserService(
    val userRepository: UserRepository,
    val addressRepository: AddressRepository,
    val googleAuth: GoogleAuth,
) {
    private val logger: KLogger = KotlinLogging.logger {}

    fun createUser(request: CreateUserRequest): CreateUserResponse {
        logger.info("Creating user with request: $request")
        val address = addressRepository.save(request.address)
        val userWithAddress = request.user.copy(address = address)
        val otp = generateOTP()
        val userWithOTP = userWithAddress.copy(otp = otp)
        logger.info("User with OTP: $userWithOTP")

        val user = userRepository.save(userWithOTP)

        val success = sendOTP(user)

        val response = CreateUserResponse(user = user.copy(otp = null), success = success)
        return response
    }

    fun createOAuthUser(request: CreateOAuthUserRequest): LoginUserResponse {
        logger.info("Creating OAuth user with request: $request")
        val googleIdToken = googleAuth.verifyToken(request.idToken) ?: return LoginUserResponse()

        val payload = googleIdToken.payload

        val existingUser = userRepository.findByOAuthId(payload.subject)

        val user = existingUser
            ?: userRepository.save(
                User(
                    name = payload["name"] as String,
                    email = payload.email,
                )
            )

        val accessToken = createAndPersistAccessToken(user)

        return LoginUserResponse(
            user = user,
            success = true,
            accessToken = accessToken
        )
    }

    fun initiateLogin(request: InitiateLoginRequest): InitiateLoginResponse {
        logger.info("Initiating login for user with request: $request")
        val user = userRepository.findByPhoneNumber(request.phoneNumber)
        if (user == null) {
            logger.error("User not found for phone number: ${request.phoneNumber}")
            return InitiateLoginResponse(success = false)
        }

        val otp = generateOTP()
        userRepository.update(user.copy(otp = otp))

        val success = sendOTP(user)
        return InitiateLoginResponse(success = success)
    }

    fun loginUser(request: LoginUserRequest): LoginUserResponse {
        logger.info("Logging in user with request: $request")
        val user = userRepository.findByPhoneNumber(request.phoneNumber)
        if (user == null) {
            logger.error("User not found for phone number: ${request.phoneNumber}")
            return LoginUserResponse()
        }
        val success = user.otp == request.otp
        val accessToken = if (success) {
            createAndPersistAccessToken(user)
        } else {
            null
        }

        return LoginUserResponse(
            user = user.copy(otp = null, accessToken = null),
            success = success,
            accessToken = accessToken
        )
    }

    fun updateUser(request: UpdateUserRequest): CreateUserResponse {
        logger.info("Updating user with request: $request")
        if (request.user.id == null) {
            logger.error("Id is mandatory: ${request.user}")
            return CreateUserResponse()
        }
        if (!checkUserAccess(request.user.id!!, request.accessToken)) {
            logger.error("Access denied for user: ${request.user.id}")
            return CreateUserResponse()
        }
        val address = addressRepository.save(request.address)
        val userWithAddress = request.user.copy(address = address)
        val updatedUser = userRepository.update(userWithAddress)
        return CreateUserResponse(user = updatedUser.copy(otp = null, accessToken = null), success = true)
    }

    private fun generateOTP(): String {
        // TODO[P0]: generate random OTP
        return "1234"
    }

    private fun sendOTP(user: User): Boolean {
        // TODO[P1]: integrate comms service to send OTP
        val phoneNumber = user.phoneNumber
        val otp = user.otp
        logger.debug("Sending OTP $otp for user: ${user.id} to $phoneNumber")
        return true
    }

    private fun createAndPersistAccessToken(user: User): String {
        // TODO[P0]: generate random access token = JWT
        val newAccessToken = "1234567890"
        userRepository.update(user.copy(accessToken = newAccessToken))
        return newAccessToken
    }

    private fun checkUserAccess(userId: Long, accessToken: String): Boolean {
        val user = userRepository.findById(userId)
        return user.isPresent && accessToken == user.get().accessToken
    }
}