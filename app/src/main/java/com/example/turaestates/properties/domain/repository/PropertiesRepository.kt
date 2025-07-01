package com.example.turaestates.properties.domain.repository

import arrow.core.Either
import com.example.turaestates.properties.domain.model.PropertiesNetworkError
import com.example.turaestates.properties.domain.model.PropertiesResponse

interface PropertiesRepository {
    suspend fun getProperties(
        page: Int,
        limit: Int,
        query: String?
    ): Either<PropertiesNetworkError, PropertiesResponse>
}