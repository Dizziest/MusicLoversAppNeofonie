package com.example.musicloversappneofonie.repositories

import com.example.musicloversappneofonie.models.Album
import com.example.musicloversappneofonie.network.AlbumAPI

class AlbumRepository(private val api: AlbumAPI) {

    suspend fun getAlbums(page: Int, query: String?) : List<Album> {
        return api.getAlbums(query,"album", 20, page).results
    }
}