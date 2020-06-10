package com.example.musicloversappneofonie.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicloversappneofonie.R
import com.example.musicloversappneofonie.models.Track
import kotlinx.android.synthetic.main.item_track.view.*

class TrackAdapter : RecyclerView.Adapter<TrackAdapter.TrackViewHolder>() {

    private val tracks by lazy { mutableListOf<Track>() }

    fun setTracks(tracks: List<Track>) {
        if (tracks.isNotEmpty()){
            this.tracks.clear()
        }
        this.tracks.addAll(tracks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_track, parent, false)

        return TrackViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = tracks[position]
        holder.bind(track)
    }

    class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(track: Track){
            with(itemView){
                text_track_position.text = track.position + ". "
                text_track_title.text = track.title
                text_track_duration.text = track.duration
            }
        }
    }
}