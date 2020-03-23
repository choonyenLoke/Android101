package com.example.opentriva.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Token(
    @Json(name = "response_code")
    val responseCode: Int,
    @Json(name = "response_message")
    val responseMsg: String,
    val token: String
)