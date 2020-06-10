package com.example.musicloversappneofonie.network

import com.example.musicloversappneofonie.models.Album
import com.example.musicloversappneofonie.models.Artist
import com.example.musicloversappneofonie.models.DetailedAlbum
import com.example.musicloversappneofonie.responses.AlbumsResponse
import com.example.musicloversappneofonie.responses.ReleasesResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val API_KEY = "IrsroYIhcHxzBEckNvFyOLVFmYNvRCLsxaDgNjLu"

interface AlbumAPI {

    @GET("database/search?token=$API_KEY")
    fun getAlbums(
        @Query("query") query: String?,
        @Query("format") format: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int
    ) : Single<AlbumsResponse>

    @GET("masters/{id}?token=$API_KEY")
    fun getAlbumById(
        @Path("id") id: Int
    ) : Single<DetailedAlbum>

    @GET("releases/{id}?token=$API_KEY")
    fun getReleaseById(
        @Path("id") id: Int
    ) : Single<DetailedAlbum>

    @GET("artists/{id}?token=$API_KEY")
    fun getArtistById(
        @Path("id") id: Int
    ) : Single<Artist>

    @GET("artists/{id}/releases?token=$API_KEY")
    fun getReleasesByArtistId(
        @Path("id") id: Int
    ) : Single<ReleasesResponse>

}