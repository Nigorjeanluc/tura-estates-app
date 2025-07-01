package com.example.turaestates.properties.domain.model

data class Data(
    val AC: Boolean,
    val YTUrl: String,
    val appliances: List<String>,
    val bathrooms: Int,
    val bedrooms: Int,
    val categoryId: Int,
    val createdAt: String,
    val details: String,
    val hasParking: Boolean,
    val hasPool: Boolean,
    val id: Int,
    val imageUrls: List<String>,
    val isForRent: Boolean,
    val isForSale: Boolean,
    val isSold: Boolean,
    val placeId: Int,
    val price: String,
    val shares: List<Share>,
    val size: Int,
    val slug: String,
    val title: String,
    val updatedAt: String,
    val userId: Int,
    val yearBuilt: String
)