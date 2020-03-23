package com.example.opentriva.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Category(
    @Json(name = "trivia_categories")
    val triviaCategories: List<TriviaCategory>
)
