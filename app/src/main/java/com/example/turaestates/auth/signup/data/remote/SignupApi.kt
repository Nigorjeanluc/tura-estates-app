package com.example.turaestates.auth.signup.data.remote

import com.example.turaestates.auth.signup.domain.model.SignupResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface SignupApi {

    @POST("auth/signup")
    suspend fun signup(
        @Body request: SignupRequest
    ): SignupResponse
}