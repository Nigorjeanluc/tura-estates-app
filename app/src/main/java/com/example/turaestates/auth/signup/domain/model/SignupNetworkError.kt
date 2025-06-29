package com.example.turaestates.auth.signup.domain.model

sealed class SignupNetworkError {
    data class ValidationError(
        val messages: List<String>,
        val error: String,
        val statusCode: Int
    ) : SignupNetworkError()

    data class GeneralError(
        val type: ApiError,
        val t: Throwable? = null
    ) : SignupNetworkError()
}

enum class ApiError(val message: String) {
    NetworkError("Network Error"),
    UnknownResponse("Unknown Response"),
    UnknownError("Unknown Error"),
    Unauthorized("Unauthorized"),
}