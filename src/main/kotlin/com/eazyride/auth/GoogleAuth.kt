package com.eazyride.auth

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import jakarta.inject.Singleton
import mu.KLogger
import mu.KotlinLogging

@Singleton
class GoogleAuth {
    private val CLIENT_ID: String = "YOUR_CLIENT"
    private val verifier: GoogleIdTokenVerifier =
        GoogleIdTokenVerifier
            .Builder(
                NetHttpTransport(),
                GsonFactory.getDefaultInstance()
            )
            .setAudience(listOf(CLIENT_ID))
            .build()
    private val logger: KLogger = KotlinLogging.logger {}

    fun verifyToken(idTokenString: String): GoogleIdToken? {
        val idToken = verifier.verify(idTokenString)
        if (idToken != null) {
            logger.debug("idToken verified. idToken: {}", idToken)
        } else {
            logger.error("Invalid ID token: $idTokenString")
        }
        return idToken
    }
}