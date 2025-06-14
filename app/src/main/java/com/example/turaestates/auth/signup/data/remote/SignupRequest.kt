package com.example.turaestates.auth.signup.data.remote

data class SignupRequest(
    val username: String,
    val email: String,
    val fullname: String,
    val gender: String,
    val address: String,
    val dob: String, // ISO format: "1990-01-01T00:00:00.000Z"
    val phoneNumber: String,
    val profileImg: String,
    val coverImg: String,
    val password: String,
    val role: String,
    val facebookId: String,
    val isVerified: Boolean
)