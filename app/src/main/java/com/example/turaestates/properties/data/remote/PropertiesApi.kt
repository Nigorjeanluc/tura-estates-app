package com.example.turaestates.properties.data.remote

import com.example.turaestates.properties.domain.model.PropertiesResponse
import retrofit2.http.GET

interface PropertiesApi {

    @GET("properties")
    suspend fun getProperties(): PropertiesResponse
}