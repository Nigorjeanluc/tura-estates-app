package com.example.turaestates.auth.signin.domain.model

data class Data(
    val address: String,
    val coverImg: String,
    val createdAt: String,
    val dob: String,
    val email: String,
    val facebookId: Any,
    val fullname: String,
    val gender: String,
    val id: Int,
    val isVerified: Boolean,
    val phoneNumber: String,
    val profileImg: String,
    val role: String,
    val updatedAt: String,
    val userSetting: UserSetting,
    val username: String
)