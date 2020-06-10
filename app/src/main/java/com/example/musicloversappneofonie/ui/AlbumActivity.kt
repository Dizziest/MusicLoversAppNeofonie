package com.example.musicloversappneofonie.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.musicloversappneofonie.R
import com.example.musicloversappneofonie.adapters.ChipAdapter
import com.example.musicloversappneofonie.adapters.TrackAdapter
import com.example.musicloversappneofonie.models.DetailedAlbum
import com.example.musicloversappneofonie.models.Track
import com.example.musicloversappneofonie.viewmodels.AlbumViewModel
import kotlinx.android.synthetic.main.activity_album.*
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import org.koin.android.viewmodel.ext.android.viewModel

class AlbumActivity : AppCompatActivity(), View.OnClickListener {

    private val viewModel: AlbumViewModel by viewModel()
    private val adapter: TrackAdapter by inject()
    private val chipAdapter: ChipAdapter by inject()
    private val layoutManager: LinearLayoutManager by inject()
    private val chipLayoutManager: LinearLayoutManager by inject(named("horizontal_manager"))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)
        initRecyclerView()
        getIncomingIntent()
        subscribeObservers()
    }

    private fun subscribeObservers(){
        observeAlbum()
        observeError()
    }

    private fun observeAlbum(){
        viewModel.getAlbumLiveData().observe(this){
            showAlbum(it)
            showTracks(it.tracklist)
            showChips(it.genres)
            showProgressBar(false)
        }
    }

    private fun observeError(){
        viewModel.getErrorLiveData().observe(this){throwable ->
            throwable.message?.let { showErrorMessage(it) }
        }
    }

    private fun showErrorMessage(message: String){
        Toast.makeText(
            this,
            "Error : $message",
            Toast.LENGTH_SHORT
        ).show()

    }

    private fun initRecyclerView(){
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adapter

        chip_recycler_view.layoutManager = chipLayoutManager
        chip_recycler_view.adapter = chipAdapter
    }

    private fun getIncomingIntent(){
        showProgressBar(true)
        val id = intent.extras?.getInt("id")
        println(intent.extras?.getString("type"))
        if (intent.extras?.containsKey("type")!!){
            val type = intent.extras?.getString("type")
            if (type == "release"){
                id?.let { viewModel.getReleaseById(it) }
            } else if (type == "master") {
                id?.let { viewModel.getAlbumById(it) }
            }
        } else {
            val masterId = intent.extras?.getInt("master_id")
            val resourceUrl = intent.extras?.getString("resource_url")
            if (id != null && masterId != null && resourceUrl != null) {
                viewModel.getReleaseIfCantGetAlbum(id, masterId, resourceUrl)
            }
        }

    }

    private fun showAlbum(album: DetailedAlbum){

        if (album.images == null || album.images[0].uri == "" || album.images[0].uri == null){
            Glide.with(this)
                .load("https://upload.wikimedia.org/wikipedia/commons/b/b9/No_Cover.jpg")
                .into(image_view_album)
        } else {
            Glide.with(this)
                .load(album.images[0].uri)
                .into(image_view_album)
        }


        if (album.artists[0].thumb == "" || album.artists[0].thumb == null){
            Glide.with(this)
                .load("https://s3.amazonaws.com/wll-community-production/images/no-avatar.png")
                .into(image_view_artist)
        } else {
            Glide.with(this)
                .load(album.artists[0].thumb)
                .into(image_view_artist)
        }
        text_album_title.text = album.title + " (" + album.year + ")"
        text_artist.text = album.artists[0].name

        arrow_button.setOnClickListener(this)
        card_artist.setOnClickListener(this)


    }

    private fun showTracks(tracks: List<Track>){
        adapter.setTracks(tracks)
    }

    private fun showChips(chips: List<String>){
        chipAdapter.setChips(chips)
    }

    private fun showProgressBar(isVisible: Boolean){
        if (isVisible){
            album_progress_bar.visibility = View.VISIBLE
        } else {
            album_progress_bar.visibility = View.INVISIBLE
        }
    }

    private fun onArtistCardClick(album: DetailedAlbum){
        val intent = Intent(this, ArtistActivity::class.java)
        intent.putExtra("artist_id", album.artists[0].id)
        startActivity(intent)
    }

    override fun onClick(v: View?) {

        when(v?.id){
            R.id.arrow_button -> {
                if (expandable_view.visibility == View.GONE) {
                    TransitionManager.beginDelayedTransition(card_view, AutoTransition())
                    expandable_view.visibility = View.VISIBLE
                    arrow_button.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp)
                } else {
                    TransitionManager.beginDelayedTransition(card_view, AutoTransition())
                    expandable_view.visibility = View.GONE
                    arrow_button.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp)
                }
            }

            R.id.card_artist -> {
                viewModel.getAlbumLiveData().value?.let { onArtistCardClick(it) }
            }
        }
    }
}
