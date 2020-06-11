package com.example.musicloversappneofonie.responses

import com.example.musicloversappneofonie.models.Album
import com.google.gson.annotations.SerializedName

data class AlbumsResponse(
    @SerializedName("results")
    val results: List<Album>,
    @SerializedName("pagination")
    val pagination: PaginationResponse
)