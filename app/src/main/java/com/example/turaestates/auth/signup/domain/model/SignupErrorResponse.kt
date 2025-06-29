package com.example.turaestates.auth.signup.domain.model

data class SignupErrorResponse(
    val message: List<String>,
    val error: String,
    val statusCode: Int
)