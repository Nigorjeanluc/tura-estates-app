package com.example.turaestates.properties.domain.model

sealed class PropertiesNetworkError {
    data class ValidationError(
        val messages: List<String>,
        val error: String,
        val statusCode: Int
    ) : PropertiesNetworkError()

    data class GeneralError(
        val type: ApiError,
        val t: Throwable? = null
    ) : PropertiesNetworkError()
}

enum class ApiError(val message: String) {
    Unauthorized("Unauthorized"),
    NotFound("Not Found"),
    Forbidden("Forbidden"),
    NetworkError("Network Error"),
    InternalServerError("Internal Server Error"),
    Conflict("Conflict"),
    BadRequest("Bad Request"),
    UnknownResponse("Unknown Response"),
    UnknownError("Unknown Error")
}
