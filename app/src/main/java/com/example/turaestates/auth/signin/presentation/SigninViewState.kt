package com.example.turaestates.auth.signin.presentation

import com.example.turaestates.auth.signin.domain.model.SigninResponse

data class SigninViewState (
    val isLoading: Boolean = false,
    val signinResponse: SigninResponse? = null,
    val error: String? = null,
    val navigateToHome: Boolean = false
)