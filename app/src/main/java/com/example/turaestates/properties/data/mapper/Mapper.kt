package com.example.turaestates.properties.data.mapper

import com.example.turaestates.auth.signup.data.mapper.extractErrorMessage
import com.example.turaestates.properties.domain.model.ApiError
import com.example.turaestates.properties.domain.model.PropertiesNetworkError
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toPropertiesNetworkError(): PropertiesNetworkError {
    if (this is HttpException) {
        val errorBody = this.response()?.errorBody()?.string()
        if (!errorBody.isNullOrBlank()) {
            try {
                val json = JSONObject(errorBody)
                val msgField = json.opt("message")
                val statusCode = json.optInt("statusCode", this.code())
                val error = json.optString("error", "Bad Request")

                if (msgField is JSONArray) {
                    val messages = mutableListOf<String>()
                    for (i in 0 until msgField.length()) {
                        messages.add(msgField.getString(i))
                    }
                    return PropertiesNetworkError.ValidationError(messages, error, statusCode)
                } else if (msgField is String) {
                    return PropertiesNetworkError.ValidationError(listOf(msgField), error, statusCode)
                }

            } catch (e: Exception) {
                // Fallback if JSON parsing fails
            }

            val message = extractErrorMessage(errorBody)
            return when {
                message.contains("Unauthorized", ignoreCase = true) ->
                    PropertiesNetworkError.GeneralError(ApiError.Unauthorized, this)
                message.contains("Forbidden", ignoreCase = true) ->
                    PropertiesNetworkError.GeneralError(ApiError.Forbidden, this)
                message.contains("Not Found", ignoreCase = true) ->
                    PropertiesNetworkError.GeneralError(ApiError.NotFound, this)
                else ->
                    PropertiesNetworkError.GeneralError(ApiError.UnknownResponse, this)
            }
        }

        return when (this.code()) {
            400 -> PropertiesNetworkError.GeneralError(ApiError.BadRequest, this)
            401 -> PropertiesNetworkError.GeneralError(ApiError.Unauthorized, this)
            403 -> PropertiesNetworkError.GeneralError(ApiError.Forbidden, this)
            404 -> PropertiesNetworkError.GeneralError(ApiError.NotFound, this)
            409 -> PropertiesNetworkError.GeneralError(ApiError.Conflict, this)
            500 -> PropertiesNetworkError.GeneralError(ApiError.InternalServerError, this)
            else -> PropertiesNetworkError.GeneralError(ApiError.UnknownResponse, this)
        }
    }

    return when (this) {
        is IOException -> PropertiesNetworkError.GeneralError(ApiError.NetworkError, this)
        else -> PropertiesNetworkError.GeneralError(ApiError.UnknownError, this)
    }
}
