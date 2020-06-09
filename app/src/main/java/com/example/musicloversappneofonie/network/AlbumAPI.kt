package com.example.musicloversappneofonie.network

import com.example.musicloversappneofonie.responses.AlbumsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumAPI {

    @GET("database/search?token=IrsroYIhcHxzBEckNvFyOLVFmYNvRCLsxaDgNjLu")
    suspend fun getAlbums(
        @Query("query") query: String?,
        @Query("format") format: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int
    ) : AlbumsResponse
}