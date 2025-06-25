package com.example.turaestates.auth.signup.domain.model

data class SignupNetworkError (
    val error: ApiError,
    val t: Throwable? = null
)

enum class ApiError(val message: String) {
    NetworkError("Network Error"),
    UnknownResponse("Unknown Response"),
    UnknownError("Unknown Error"),
    Unauthorized("Unauthorized"),
}