package com.example.opentriva.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Count(
    @Json(name = "category_id")
    val categoryId: Int,
    @Json(name = "category_question_count")
    val categoryQuestionCount: CategoryQuestionCount
)