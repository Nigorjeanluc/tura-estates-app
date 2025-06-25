package com.example.turaestates.auth.signup.data.repository

import arrow.core.Either
import com.example.turaestates.auth.signup.data.mapper.toSignupNetworkError
import com.example.turaestates.auth.signup.data.remote.SignupApi
import com.example.turaestates.auth.signup.data.remote.SignupRequest
import com.example.turaestates.auth.signup.domain.model.SignupNetworkError
import com.example.turaestates.auth.signup.domain.model.SignupResponse
import com.example.turaestates.auth.signup.domain.repository.SignupRepository
import javax.inject.Inject

class SignupRepositoryImpl @Inject constructor(
    private val signupApi: SignupApi
): SignupRepository {

    override suspend fun signup(request: SignupRequest): Either<SignupNetworkError, SignupResponse> {
        return Either.catch {
            signupApi.signup(request)
        }.mapLeft {
            it.toSignupNetworkError()
        }
    }
}