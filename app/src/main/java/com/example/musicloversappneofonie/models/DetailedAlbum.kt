package com.example.musicloversappneofonie.models

import com.google.gson.annotations.SerializedName

data class DetailedAlbum(
    @SerializedName("id")
    val id: Int,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("year")
    val year: Int,
    @SerializedName("tracklist")
    val tracklist: List<Track>,
    @SerializedName("artists")
    val artists: List<Artist>,
    @SerializedName("title")
    val title: String,
    @SerializedName("images")
    val images: List<Images>

)