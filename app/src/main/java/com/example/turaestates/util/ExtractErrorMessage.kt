package com.example.turaestates.util

import org.json.JSONObject

fun ExtractErrorMessage(errorBody: String?): String {
    return try {
        val json = JSONObject(errorBody ?: "")
        json.getString("message") ?: "Unknown error"
    } catch (e: Exception) {
        "Unknown error"
    }
}