package com.example.musicloversappneofonie.responses

import com.example.musicloversappneofonie.models.Album
import com.google.gson.annotations.SerializedName

data class ReleasesResponse (
    @SerializedName("releases")
    val releases: List<Album>
)