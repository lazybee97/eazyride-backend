package com.eazyride.service

import com.eazyride.entity.Address
import com.eazyride.entity.User
import com.eazyride.model.CreateUserRequest
import com.eazyride.model.LoginUserRequest
import com.eazyride.model.UpdateUserRequest
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest

@MicronautTest
class UserServiceTest(private val userService: UserService) : StringSpec({

    fun getUserObject(): User {
        return User(
            id = null,
            phoneNumber = "1234567890",
            name = "John Doe",
            email = "John.Doe@gmail.com"
        )
    }

    fun getAddressObject(): Address {
        return Address(
            id = null,
            addressLine1 = "123 Main St",
            city = "Springfield",
            state = "IL",
            country = "USA",
            pinCode = "62701",
        )
    }

    fun getCreateUserRequest(): CreateUserRequest {
        return CreateUserRequest(
            user = getUserObject(),
            address = getAddressObject(),
        )
    }

    fun createUser(): User {
        val request = getCreateUserRequest()
        val response = userService.createUser(request)
        response.success shouldBe true

        val userInRequest = request.user
        val userInResponse = response.user!!
        val userInDbOpt = userService.userRepository.findById(userInResponse.id!!)
        userInDbOpt.isPresent shouldBe true
        val userInDb = userInDbOpt.get()

        // id & otp are generated
        userInDb shouldBe userInRequest.copy(id = userInDb.id, otp = userInDb.otp)

        // otp is not returned in response
        // address is returned in response, but not present in user entry in db directly
        userInResponse shouldBe userInDb.copy(otp = null, address = userInResponse.address)

        // address is saved in db
        val addressInDbOpt = userService.addressRepository.findById(userInResponse.address!!.id!!)
        addressInDbOpt.isPresent shouldBe true
        val addressInDb = addressInDbOpt.get()
        addressInDb shouldBe userInResponse.address!!.copy(id = addressInDb.id)

        return userInResponse
    }

    "create user - success" {
        createUser()
    }

    "log in user - unknown user" {
        val loginRequest = LoginUserRequest(
            phoneNumber = "test",
            otp = "wrong"
        )
        val loginResponse = userService.loginUser(loginRequest)
        loginResponse.success shouldBe false
        loginResponse.user shouldBe null
        loginResponse.accessToken shouldBe null
    }

    "log in user - otp test" {
        val userInResponse = createUser()

        val loginRequest = LoginUserRequest(
            phoneNumber = userInResponse.phoneNumber!!,
            otp = "wrong"
        )
        val loginResponse = userService.loginUser(loginRequest)
        loginResponse.success shouldBe false
        loginResponse.user shouldBe userInResponse.copy(otp = null, address = null)
        loginResponse.accessToken shouldBe null

        val loginRequest2 = loginRequest.copy(
            otp = "1234"
        )
        val loginResponse2 = userService.loginUser(loginRequest2)
        loginResponse2.success shouldBe true
        loginResponse2.user shouldBe userInResponse.copy(otp = null, address = null)
        loginResponse2.accessToken shouldNotBe null
    }

    "update user - unknown user" {
        val updateRequest =
            UpdateUserRequest(
                user = getUserObject(),
                address = getAddressObject(),
                accessToken = null.toString(),
            )
        val updateResponse = userService.updateUser(updateRequest)
        updateResponse.success shouldBe false
        updateResponse.user shouldBe null

        val updateRequest2 =
            updateRequest.copy(
                user = getUserObject().copy(id = 12345)
            )
        val updateResponse2 = userService.updateUser(updateRequest2)
        updateResponse2.success shouldBe false
        updateResponse2.user shouldBe null
    }

    "update user - access denied" {
        val userInResponse = createUser()

        val updateRequest =
            UpdateUserRequest(
                user = userInResponse.copy(name = "Jane Doe"),
                address = getAddressObject().copy(addressLine1 = "456 Main St"),
                accessToken = null.toString(),
            )
        val updateResponse = userService.updateUser(updateRequest)
        updateResponse.success shouldBe false
        updateResponse.user shouldBe null
    }

    "update user - success" {
        val userInResponse = createUser()

        val loginRequest = LoginUserRequest(
            phoneNumber = userInResponse.phoneNumber!!,
            otp = "1234"
        )
        val loginResponse = userService.loginUser(loginRequest)
        loginResponse.success shouldBe true
        loginResponse.user shouldBe userInResponse.copy(otp = null, address = null)
        loginResponse.accessToken shouldNotBe null

        val updateRequest =
            UpdateUserRequest(
                user = userInResponse.copy(name = "Jane Doe"),
                address = getAddressObject().copy(addressLine1 = "456 Main St"),
                accessToken = loginResponse.accessToken!!
            )
        val updateResponse = userService.updateUser(updateRequest)
        updateResponse.success shouldBe true
        updateResponse.user?.copy(
            address = updateResponse.user?.address?.copy(id = null)
        ) shouldBe
            userInResponse.copy(
                name = "Jane Doe",
                otp = null,
                address = userInResponse.address?.copy(addressLine1 = "456 Main St", id = null)
            )
    }
})