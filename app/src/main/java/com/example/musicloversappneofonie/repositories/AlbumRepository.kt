package com.example.musicloversappneofonie.repositories

import com.example.musicloversappneofonie.models.Album
import com.example.musicloversappneofonie.models.Artist
import com.example.musicloversappneofonie.models.DetailedAlbum
import com.example.musicloversappneofonie.network.AlbumAPI

class AlbumRepository(private val api: AlbumAPI) {

    suspend fun getAlbums(page: Int, query: String?) : List<Album> {
        return api.getAlbums(query,"album", 20, page).results
    }

    suspend fun getAlbumById(id: Int) : DetailedAlbum {
        return api.getAlbumById(id)
    }

    suspend fun getReleaseById(id: Int) : DetailedAlbum {
        return api.getReleaseById(id)
    }

    suspend fun getArtistById(id: Int) : Artist{
        return api.getArtistById(id)
    }

    suspend fun getReleasesByArtistId(id: Int): List<Album> {
        return api.getReleasesByArtistId(id).releases
    }
}