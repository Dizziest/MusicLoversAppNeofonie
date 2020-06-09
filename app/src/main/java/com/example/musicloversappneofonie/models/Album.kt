package com.example.musicloversappneofonie.models

import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("id")
    val id: Int,
    @SerializedName("year")
    val year: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("cover_image")
    val thumb: String
)