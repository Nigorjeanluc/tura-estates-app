package com.example.turaestates.properties.data.repository

import arrow.core.Either
import com.example.turaestates.properties.data.mapper.toPropertiesNetworkError
import com.example.turaestates.properties.data.remote.PropertiesApi
import com.example.turaestates.properties.domain.model.PropertiesNetworkError
import com.example.turaestates.properties.domain.model.PropertiesResponse
import com.example.turaestates.properties.domain.repository.PropertiesRepository
import javax.inject.Inject

class PropertiesRepositoryImpl @Inject constructor(
    private val propertiesApi: PropertiesApi
): PropertiesRepository {

    override suspend fun getProperties(page: Int, limit: Int, query: String?): Either<PropertiesNetworkError, PropertiesResponse> {
        return Either.catch {
            propertiesApi.getProperties()
        }.mapLeft {
            it.toPropertiesNetworkError()
        }
    }
}