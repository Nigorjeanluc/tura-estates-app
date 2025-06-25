package com.example.turaestates.auth.signin.data.mapper

import com.example.turaestates.auth.signin.domain.model.ApiError
import com.example.turaestates.auth.signin.domain.model.SigninNetworkError
import com.example.turaestates.util.ExtractErrorMessage
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toSigninNetworkError(): SigninNetworkError {
    val error = when(this){
        is IOException -> ApiError.NetworkError
        is HttpException -> {
            val errorBody = this.response()?.errorBody()?.string()
            val message = ExtractErrorMessage(errorBody)
            if (message.contains("Unauthorized", ignoreCase = true)) {
                ApiError.Unauthorized
            } else {
                ApiError.UnknownResponse
            }
        }
        else -> ApiError.UnknownError
    }

    return SigninNetworkError(
        error =error,
        t = this
    )
}