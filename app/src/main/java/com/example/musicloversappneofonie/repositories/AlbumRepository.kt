package com.example.musicloversappneofonie.repositories

import com.example.musicloversappneofonie.models.Album
import com.example.musicloversappneofonie.models.Artist
import com.example.musicloversappneofonie.models.DetailedAlbum
import com.example.musicloversappneofonie.network.AlbumAPI
import com.example.musicloversappneofonie.responses.AlbumsResponse
import com.example.musicloversappneofonie.responses.ReleasesResponse
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*

class AlbumRepository(private val api: AlbumAPI) {

    fun getAlbums(page: Int, query: String?) : Single<AlbumsResponse> {
        return api.getAlbums(query,"album", 20, page)
    }

    fun getAlbumById(id: Int) : Single<DetailedAlbum> {
        return api.getAlbumById(id)
    }

    fun getReleaseById(id: Int) : Single<DetailedAlbum> {
        return api.getReleaseById(id)
    }

    fun getArtistById(id: Int) : Single<Artist>{
        return api.getArtistById(id)
    }

    fun getReleasesByArtistId(id: Int): Single<ReleasesResponse> {
        return api.getReleasesByArtistId(id)
    }
}