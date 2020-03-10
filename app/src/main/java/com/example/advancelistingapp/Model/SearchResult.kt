package com.example.advancelistingapp.Model

import com.example.advancelistingapp.Model.Search
import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("Search") val search: List<Search>
)