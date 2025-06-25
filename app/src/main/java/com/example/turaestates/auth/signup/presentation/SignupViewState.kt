package com.example.turaestates.auth.signup.presentation

import com.example.turaestates.auth.signup.domain.model.SignupResponse

data class SignupViewState (
    val isLoading: Boolean = false,
    val signupResponse: SignupResponse? = null,
    val error: String? = null,
    val navigateToHome: Boolean = false
)