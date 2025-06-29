package com.example.turaestates.auth.signup.data.mapper

import com.example.turaestates.auth.signup.domain.model.ApiError
import com.example.turaestates.auth.signup.domain.model.SignupNetworkError
import retrofit2.HttpException
import java.io.IOException
import org.json.JSONObject
import org.json.JSONArray

fun Throwable.toSignupNetworkError(): SignupNetworkError {
    if (this is HttpException) {
        val errorBody = this.response()?.errorBody()?.string()
        if (!errorBody.isNullOrBlank()) {
            // Try parsing as validation error with message array
            try {
                val json = JSONObject(errorBody)
                if (json.has("message")) {
                    val msgField = json.get("message")
                    if (msgField is JSONArray) {
                        val messages = mutableListOf<String>()
                        for (i in 0 until msgField.length()) {
                            messages.add(msgField.getString(i))
                        }
                        // Return validation error with messages list
                        return SignupNetworkError.ValidationError(
                            messages = messages,
                            error = json.optString("error", "Bad Request"),
                            statusCode = json.optInt("statusCode", this.code())
                        )
                    } else if (msgField is String) {
                        // If message is string (rare), wrap in list
                        return SignupNetworkError.ValidationError(
                            messages = listOf(msgField),
                            error = json.optString("error", "Bad Request"),
                            statusCode = json.optInt("statusCode", this.code())
                        )
                    }
                }
            } catch (e: Exception) {
                // JSON parsing failed, ignore and fallback below
            }

            // Fallback: check message text for Unauthorized
            val message = extractErrorMessage(errorBody)
            return when {
                message.contains("Unauthorized", ignoreCase = true) -> SignupNetworkError.GeneralError(ApiError.Unauthorized, this)
                else -> SignupNetworkError.GeneralError(ApiError.UnknownResponse, this)
            }
        }

        return SignupNetworkError.GeneralError(ApiError.UnknownResponse, this)
    }

    return when (this) {
        is IOException -> SignupNetworkError.GeneralError(ApiError.NetworkError, this)
        else -> SignupNetworkError.GeneralError(ApiError.UnknownError, this)
    }
}

// Helper: extract string message from errorBody JSON (single string message)
fun extractErrorMessage(errorBody: String?): String {
    return try {
        JSONObject(errorBody ?: "").getString("message")
    } catch (e: Exception) {
        "Unknown error"
    }
}
