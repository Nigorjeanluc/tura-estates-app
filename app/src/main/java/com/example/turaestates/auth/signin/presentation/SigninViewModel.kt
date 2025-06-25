package com.example.turaestates.auth.signin.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.example.turaestates.auth.signin.domain.model.ApiError
import com.example.turaestates.auth.signin.domain.model.SigninNetworkError
import com.example.turaestates.auth.signin.domain.model.SigninResponse
import com.example.turaestates.auth.signin.domain.repository.SigninRepository
import com.example.turaestates.util.Event
import com.example.turaestates.util.sendEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SigninViewModel @Inject constructor(
    private val signinRepository: SigninRepository
): ViewModel() {
    private val _state = MutableStateFlow(SigninViewState())
    val state = _state.asStateFlow()

    fun signin(username: String, password: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true, error = null)
            }

            val result: Either<SigninNetworkError, SigninResponse> = signinRepository.signin(username, password)

            when (result) {
                is Either.Right -> {
                    val response = result.value

                    _state.update {
                        it.copy(signinResponse = response, navigateToHome = true)
                    }
                }

                is Either.Left -> {
                    val failure = result.value
                    val userMessage = when (failure.error) {
                        ApiError.Unauthorized -> "Invalid username or password"
                        else -> failure.error.message
                    }

                    _state.update {
                        it.copy(error = userMessage)
                    }
                    sendEvent(Event.Toast(userMessage))
                }
            }

            _state.update {
                it.copy(isLoading = false)
            }
        }
    }

    fun onNavigated() {
        _state.update { it.copy(navigateToHome = false) }
    }
}