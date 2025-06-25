package com.example.turaestates.auth.signup.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.example.turaestates.auth.signup.data.remote.SignupRequest
import com.example.turaestates.auth.signup.domain.model.ApiError
import com.example.turaestates.auth.signup.domain.model.SignupNetworkError
import com.example.turaestates.auth.signup.domain.model.SignupResponse
import com.example.turaestates.auth.signup.domain.repository.SignupRepository
import com.example.turaestates.util.Event
import com.example.turaestates.util.EventBus.sendEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupRepository: SignupRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SignupViewState())
    val state = _state.asStateFlow()

    // Hold form data between screens
    private val _form = MutableStateFlow(SignupRequest())
    val form = _form.asStateFlow()

    fun updateForm(update: SignupRequest.() -> SignupRequest) {
        _form.update { it.update() }
    }

    fun signup() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true, error = null)
            }

            val result: Either<SignupNetworkError, SignupResponse> =
                signupRepository.signup(_form.value)

            when (result) {
                is Either.Right -> {
                    val response = result.value
                    _state.update {
                        it.copy(signupResponse = response, navigateToHome = true)
                    }
                }

                is Either.Left -> {
                    val failure = result.value
                    val userMessage = when (failure.error) {
                        ApiError.Unauthorized -> "Invalid signup details"
                        else -> failure.error.message
                    }

                    _state.update { it.copy(error = userMessage) }
                    sendEvent(Event.Toast(userMessage))
                }
            }

            _state.update { it.copy(isLoading = false) }
        }
    }

    fun onNavigated() {
        _state.update { it.copy(navigateToHome = false) }
    }
}
