package com.example.musicloversappneofonie.network

import com.example.musicloversappneofonie.models.Album
import com.example.musicloversappneofonie.models.Artist
import com.example.musicloversappneofonie.models.DetailedAlbum
import com.example.musicloversappneofonie.responses.AlbumsResponse
import com.example.musicloversappneofonie.responses.ReleasesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val API_KEY = "IrsroYIhcHxzBEckNvFyOLVFmYNvRCLsxaDgNjLu"

interface AlbumAPI {

    @GET("database/search?token=$API_KEY")
    suspend fun getAlbums(
        @Query("query") query: String?,
        @Query("format") format: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int
    ) : AlbumsResponse

    @GET("masters/{id}?token=$API_KEY")
    suspend fun getAlbumById(
        @Path("id") id: Int
    ) : DetailedAlbum

    @GET("releases/{id}?token=$API_KEY")
    suspend fun getReleaseById(
        @Path("id") id: Int
    ) : DetailedAlbum

    @GET("artists/{id}?token=$API_KEY")
    suspend fun getArtistById(
        @Path("id") id: Int
    ) : Artist

    @GET("artists/{id}/releases?token=$API_KEY")
    suspend fun getReleasesByArtistId(
        @Path("id") id: Int
    ) : ReleasesResponse

}