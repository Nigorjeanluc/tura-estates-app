package com.example.turaestates.auth.signup.presentation

import android.util.Log
import androidx.lifecycle.*
import arrow.core.Either
import com.example.turaestates.auth.signup.data.remote.SignupRequest
import com.example.turaestates.auth.signup.domain.model.ApiError
import com.example.turaestates.auth.signup.domain.model.SignupNetworkError
import com.example.turaestates.auth.signup.domain.model.SignupResponse
import com.example.turaestates.auth.signup.domain.repository.SignupRepository
import com.example.turaestates.util.Event
import com.example.turaestates.util.EventBus.sendEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupRepository: SignupRepository
) : ViewModel() {

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _form = MutableLiveData(SignupRequest())
    val form: LiveData<SignupRequest> = _form

    private val _state = MutableLiveData(SignupViewState())
    val state: LiveData<SignupViewState> = _state

    // Mutable state holding current signup error
    private val _signupError = MutableLiveData<SignupNetworkError?>(null)
    val signupError: LiveData<SignupNetworkError?> = _signupError

    // Call this to update errors when signup fails
    fun setSignupError(error: SignupNetworkError?) {
        _signupError.value = error
    }

    // Clear error when needed
    fun clearError() {
        _signupError.value = null
    }

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun updateForm(update: SignupRequest.() -> SignupRequest) {
        val current = _form.value ?: SignupRequest()
        _form.value = current.update()
    }

    fun signup() {
        viewModelScope.launch {
            _state.value = _state.value?.copy(isLoading = true, error = null)

            val currentForm = (_form.value ?: SignupRequest()).copy(
                email = _email.value ?: "",
                password = _password.value ?: ""
            )

            val result: Either<SignupNetworkError, SignupResponse> = signupRepository.signup(currentForm)

            when (result) {
                is Either.Right -> {
                    val response = result.value
                    _state.value = _state.value?.copy(signupResponse = response, navigateToHome = true)
                    clearError()
                }
                is Either.Left -> {
                    val failure = result.value
                    setSignupError(failure)  // Expose the error for UI

                    val userMessage = when (failure) {
                        is SignupNetworkError.ValidationError -> failure.messages.joinToString("\n")
                        is SignupNetworkError.GeneralError -> when (failure.type) {
                            ApiError.Unauthorized -> "Invalid signup details"
                            else -> failure.type.message
                        }
                    }

                    _state.value = _state.value?.copy(error = userMessage)
                    sendEvent(Event.Toast(userMessage))
                }
            }

            _state.value = _state.value?.copy(isLoading = false)
        }
    }

    fun onNavigated() {
        _state.value = _state.value?.copy(navigateToHome = false)
    }

    override fun onCleared() {
        super.onCleared()
        // Remove the observer cleanup since we're not using observers anymore
    }
}