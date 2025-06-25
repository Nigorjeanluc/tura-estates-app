package com.example.turaestates.auth.signup.data.mapper

import com.example.turaestates.auth.signup.domain.model.ApiError
import com.example.turaestates.auth.signup.domain.model.SignupNetworkError
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toSignupNetworkError(): SignupNetworkError {
    val error = when (this) {
        is IOException -> ApiError.NetworkError
        is HttpException -> {
            val errorBody = this.response()?.errorBody()?.string()
            val message = extractErrorMessage(errorBody)
            when {
                message.contains("Unauthorized", true) -> ApiError.Unauthorized
                else -> ApiError.UnknownResponse
            }
        }
        else -> ApiError.UnknownError
    }

    return SignupNetworkError(error, this)
}

fun extractErrorMessage(errorBody: String?): String {
    return try {
        JSONObject(errorBody ?: "").getString("message")
    } catch (e: Exception) {
        "Unknown error"
    }
}