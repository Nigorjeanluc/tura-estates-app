package com.example.turaestates.auth.signin.data.mapper

import com.example.turaestates.auth.signin.domain.model.ApiError
import com.example.turaestates.auth.signin.domain.model.SigninNetworkError
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toNetworkError(): SigninNetworkError {
    val error = when(this){
        is IOException -> ApiError.NetworkError
        is HttpException -> ApiError.UnknownResponse
        else -> ApiError.UnknownError
    }

    return SigninNetworkError(
        error =error,
        t = this
    )
}