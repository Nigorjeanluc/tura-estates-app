package com.example.turaestates.properties.domain.model

data class Meta(
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean,
    val limit: Int,
    val page: Int,
    val total: Int,
    val totalPages: Int
)