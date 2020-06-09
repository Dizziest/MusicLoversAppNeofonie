package com.example.musicloversappneofonie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicloversappneofonie.models.Album

class AlbumAdapter : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    private val albums by lazy { mutableListOf<Album>()}

    fun setAlbums(albums: List<Album>) {
        if (albums.isNotEmpty()){
            this.albums.clear()
        }
        this.albums.addAll(albums)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate()
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class AlbumViewHolder {

    }
}