package com.example.turaestates.properties.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.example.turaestates.properties.domain.model.PropertiesNetworkError
import com.example.turaestates.properties.domain.repository.PropertiesRepository
import com.example.turaestates.properties.presentation.PropertiesViewState
import com.example.turaestates.util.Event
import com.example.turaestates.util.EventBus.sendEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertiesViewModel @Inject constructor(
    private val propertiesRepository: PropertiesRepository
) : ViewModel() {

    private val _state = MutableLiveData(PropertiesViewState())
    val state: LiveData<PropertiesViewState> = _state

    fun loadProperties(page: Int = 1, limit: Int = 10, query: String? = null) {
        _state.value = _state.value?.copy(isLoading = true, error = null)

        viewModelScope.launch {
            val result = propertiesRepository.getProperties(page, limit, query)

            when (result) {
                is Either.Right -> {
                    val res = result.value
                    _state.value = _state.value?.copy(
                        properties = res.data,
                        total = res.meta.total,
                        page = page,
                        totalPages = res.meta.totalPages,
                        hasNextPage = res.meta.hasNextPage,
                        hasPreviousPage = page > 1,
                        isLoading = false,
                        error = null
                    )
                }

                is Either.Left -> {
                    val failure = result.value
                    val userMessage = when (failure) {
                        is PropertiesNetworkError.ValidationError -> failure.messages.joinToString("\n")
                        is PropertiesNetworkError.GeneralError -> failure.type.message
                    }

                    _state.value = _state.value?.copy(isLoading = false, error = userMessage)
                    sendEvent(Event.Toast(userMessage))
                }
            }
        }
    }

    fun refreshProperties() {
        val currentPage = _state.value?.page ?: 1
        loadProperties(page = currentPage)
    }
}