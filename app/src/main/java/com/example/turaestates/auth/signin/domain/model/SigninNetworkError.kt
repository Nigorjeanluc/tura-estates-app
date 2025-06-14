package com.example.turaestates.auth.signin.domain.model

data class SigninNetworkError (
    val error: ApiError,
    val t: Throwable? = null
)

enum class ApiError(val message: String) {
    NetworkError("Network Error"),
    UnknownResponse("Unknown Response"),
    UnknownError("Unknown Error"),
}