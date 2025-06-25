package com.example.turaestates.auth.signup.domain.repository

import arrow.core.Either
import com.example.turaestates.auth.signup.data.remote.SignupRequest
import com.example.turaestates.auth.signup.domain.model.SignupNetworkError
import com.example.turaestates.auth.signup.domain.model.SignupResponse

interface SignupRepository {
    suspend fun signup(
        request: SignupRequest
    ): Either<SignupNetworkError, SignupResponse>
}