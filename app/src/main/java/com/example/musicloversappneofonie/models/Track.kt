package com.example.musicloversappneofonie.models

import com.google.gson.annotations.SerializedName

data class Track(
    @SerializedName("position")
    val position: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("duration")
    val duration: String
)