package com.example.opentriva.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TriviaCategory(
    val id: Int,
    val name: String
)