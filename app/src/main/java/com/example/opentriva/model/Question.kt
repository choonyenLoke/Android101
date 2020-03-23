package com.example.opentriva.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Question(
    val category: String,
    @Json(name ="correct_answer")
    val correctAnswer: String,
    val difficulty: String,
    @Json(name = "incorrect_answers")
    val incorrectAnswers: List<String>,
    val question: String,
    val type: String
)