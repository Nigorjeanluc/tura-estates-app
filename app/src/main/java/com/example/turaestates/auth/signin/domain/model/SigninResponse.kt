package com.example.turaestates.auth.signin.domain.model

data class SigninResponse(
    val `data`: Data,
    val message: String,
    val token: String
)