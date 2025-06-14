package com.example.turaestates.auth.signup.domain.model

data class SignupResponse(
    val `data`: Data,
    val message: String,
    val token: String
)