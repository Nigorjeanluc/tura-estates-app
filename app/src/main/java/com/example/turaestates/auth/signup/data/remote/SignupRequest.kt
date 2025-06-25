package com.example.turaestates.auth.signup.data.remote

data class SignupRequest(
    val username: String = "",
    val email: String = "",
    val fullname: String = "",
    val gender: String = "",
    val address: String = "",
    val dob: String = "",
    val phoneNumber: String = "",
    val profileImg: String = "",
    val coverImg: String = "",
    val password: String = "",
    val role: String = "USER",
    val isVerified: Boolean = false,
    val facebookId: String? = null
)