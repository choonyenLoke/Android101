package com.example.advancelistingapp.Model


import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("Actors") val actors: String,
    @SerializedName("Director") val director: String,
    @SerializedName("Genre") val genre: String,
    val imdbID: String,
    val imdbRating: String,
    val imdbVotes: String,
    @SerializedName("Language") val language: String,
    @SerializedName("Plot") val plot: String,
    @SerializedName("Poster") val poster: String,
    @SerializedName("Released") val released: String,
    @SerializedName("Runtime") val runtime: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Type") val type: String
)