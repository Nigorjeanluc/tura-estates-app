package com.example.turaestates.auth.signin.data.repository

import arrow.core.Either
import com.example.turaestates.auth.signin.data.mapper.toNetworkError
import com.example.turaestates.auth.signin.data.remote.SigninApi
import com.example.turaestates.auth.signin.data.remote.SigninRequest
import com.example.turaestates.auth.signin.domain.model.SigninNetworkError
import com.example.turaestates.auth.signin.domain.model.SigninResponse
import com.example.turaestates.auth.signin.domain.repository.SigninRepository
import javax.inject.Inject

class SigninRepositoryImpl @Inject constructor(
    private val signinApi: SigninApi
): SigninRepository {

    override suspend fun signin(
        username: String,
        password: String
    ): Either<SigninNetworkError, SigninResponse> {
        return Either.catch {
            signinApi.signin(
                request = SigninRequest(
                    username = username,
                    password = password
                )
            )
        }.mapLeft {
            it.toNetworkError()
        }
    }
}