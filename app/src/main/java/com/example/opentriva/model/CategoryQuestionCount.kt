package com.example.opentriva.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryQuestionCount(
    @Json(name = "total_easy_question_count")
    val easyCount: Int,
    @Json(name = "total_hard_question_count")
    val hardCount: Int,
    @Json(name = "total_medium_question_count")
    val mediumCount: Int,
    @Json(name = "total_question_count")
    val totalCount: Int
)