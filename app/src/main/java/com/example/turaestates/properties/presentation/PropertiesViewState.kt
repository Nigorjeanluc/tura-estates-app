package com.example.turaestates.properties.presentation

import com.example.turaestates.properties.domain.model.Data

data class PropertiesViewState(
    val isLoading: Boolean = false,
    val properties: List<Data> = emptyList(),
    val total: Int = 0,
    val page: Int = 1,
    val totalPages: Int = 1,
    val hasNextPage: Boolean = false,
    val hasPreviousPage: Boolean = false,
    val error: String? = null
)