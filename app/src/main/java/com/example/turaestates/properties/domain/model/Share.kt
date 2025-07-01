package com.example.turaestates.properties.domain.model

data class Share(
    val createdAt: String,
    val id: Int,
    val parentShareId: Any,
    val propertyId: Int,
    val recipientId: Int,
    val senderId: Int,
    val status: String
)