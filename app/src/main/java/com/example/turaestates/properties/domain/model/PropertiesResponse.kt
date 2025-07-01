package com.example.turaestates.properties.domain.model

data class PropertiesResponse(
    val `data`: List<Data>,
    val message: String,
    val meta: Meta
)