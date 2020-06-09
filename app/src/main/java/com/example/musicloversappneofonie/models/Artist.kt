package com.example.musicloversappneofonie.models

import com.google.gson.annotations.SerializedName

data class Artist(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("images")
    val images: List<Images>
)