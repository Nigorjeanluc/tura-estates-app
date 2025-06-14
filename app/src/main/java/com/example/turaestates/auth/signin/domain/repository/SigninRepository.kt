package com.example.turaestates.auth.signin.domain.repository

import arrow.core.Either
import com.example.turaestates.auth.signin.domain.model.SigninNetworkError
import com.example.turaestates.auth.signin.domain.model.SigninResponse

interface SigninRepository {
    
    suspend fun signin(username: String, password: String): Either<SigninNetworkError, SigninResponse>
}