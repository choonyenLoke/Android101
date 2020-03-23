package com.example.opentriva.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResetToken(
    @Json(name = "response_code")
    val responseCode: Int,
    val token: String
)