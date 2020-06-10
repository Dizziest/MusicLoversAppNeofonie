package com.example.musicloversappneofonie.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicloversappneofonie.R
import com.example.musicloversappneofonie.models.Album
import com.example.musicloversappneofonie.models.Track
import kotlinx.android.synthetic.main.item_artists_album.view.*
import kotlinx.android.synthetic.main.item_track.view.*

class ArtistsAlbumAdapter(onAlbumListener: OnAlbumListener) : RecyclerView.Adapter<ArtistsAlbumAdapter.ArtistsAlbumViewHolder>() {

    private val albums by lazy { mutableListOf<Album>() }
    private val onAlbumListener = onAlbumListener

    fun setReleases(albums: List<Album>) {
        if (albums.isNotEmpty()){
            this.albums.clear()
        }
        this.albums.addAll(albums)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistsAlbumViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_artists_album, parent, false)

        return ArtistsAlbumViewHolder(itemView, onAlbumListener)
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    override fun onBindViewHolder(holder: ArtistsAlbumViewHolder, position: Int) {
        val album = albums[position]
        holder.bind(album)
    }

    fun getSelectedAlbum(position: Int): Album? {
        if (albums != null && albums.isNotEmpty()){
            return albums[position]
        }
        return null
    }

    class ArtistsAlbumViewHolder(itemView: View, onAlbumListener: OnAlbumListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val onAlbumListener = onAlbumListener
        fun bind(album: Album){
            with(itemView){
                Glide.with(this)
                    .load(album.thumb2)
                    .into(image_album)

                text_album_title.text = album.title + " (" + album.year + ")"
            }
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            onAlbumListener.onAlbumClick(adapterPosition)
        }
    }
}