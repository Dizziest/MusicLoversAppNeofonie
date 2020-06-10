package com.example.musicloversappneofonie.models

import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("id")
    val id: Int,
    @SerializedName("master_id")
    val master_id: Int,
    @SerializedName("resource_url")
    val resource_url: String,
    @SerializedName("year")
    val year: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("cover_image")
    val thumb: String,
    @SerializedName("thumb")
    val thumb2: String,
    @SerializedName("type")
    val type: String
)