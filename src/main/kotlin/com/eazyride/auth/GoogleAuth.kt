package com.eazyride.auth

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import jakarta.inject.Singleton

@Singleton
class GoogleAuth {
    private val CLIENT_ID: String = "YOUR_CLIENT"
    private var verifier: GoogleIdTokenVerifier = GoogleIdTokenVerifier.Builder(
        NetHttpTransport(),
        GsonFactory.getDefaultInstance()
    ).setAudience(listOf(CLIENT_ID)).build()

    fun verifyToken(idTokenString: String): GoogleIdToken? {
        val idToken = verifier.verify(idTokenString)
        if (idToken != null) {
            val payload: Payload = idToken.payload
            // Print user identifier
            val userId: String = payload.subject
            println("User ID: $userId")
        } else {
            println("Invalid ID token: $idTokenString")
        }
        return idToken
    }
}