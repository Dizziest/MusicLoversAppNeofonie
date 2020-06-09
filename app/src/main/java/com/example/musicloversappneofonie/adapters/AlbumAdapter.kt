package com.example.musicloversappneofonie.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.musicloversappneofonie.R
import com.example.musicloversappneofonie.models.Album
import kotlinx.android.synthetic.main.item_album.view.*

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
            .inflate(R.layout.item_album, parent, false)

        return AlbumViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albums[position]
        holder.bind(album)
    }

    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(album: Album){
            with(itemView){
                if (album.thumb.takeLast(10) == "spacer.gif" || album.thumb.isNullOrEmpty()){
                    Glide.with(this)
                        .load("https://upload.wikimedia.org/wikipedia/commons/b/b9/No_Cover.jpg")
                        .into(image_view_album)
                } else {
                    Glide.with(this)
                        .load(album.thumb)
                        .into(image_view_album)
                }


                text_album_title.text = album.title + " " + "(" + album.year + ")"
            }
        }

    }
}