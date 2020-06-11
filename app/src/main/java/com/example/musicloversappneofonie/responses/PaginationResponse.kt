package com.example.musicloversappneofonie.responses

import com.google.gson.annotations.SerializedName

data class PaginationResponse(
    @SerializedName("pages")
    val pages: Int
)