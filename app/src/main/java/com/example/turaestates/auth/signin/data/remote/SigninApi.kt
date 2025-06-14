package com.example.turaestates.auth.signin.data.remote

import com.example.turaestates.auth.signin.domain.model.SigninResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface SigninApi {

    @POST("auth/login")
    suspend fun signin(
        @Body request: SigninRequest
    ): SigninResponse
}