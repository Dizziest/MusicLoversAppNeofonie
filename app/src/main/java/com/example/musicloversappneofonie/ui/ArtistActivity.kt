package com.example.musicloversappneofonie.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.musicloversappneofonie.R
import com.example.musicloversappneofonie.adapters.ArtistsAlbumAdapter
import com.example.musicloversappneofonie.adapters.OnAlbumListener
import com.example.musicloversappneofonie.models.Album
import com.example.musicloversappneofonie.models.Artist
import com.example.musicloversappneofonie.viewmodels.ArtistViewModel
import kotlinx.android.synthetic.main.activity_album.*
import kotlinx.android.synthetic.main.activity_artist.*
import kotlinx.android.synthetic.main.activity_artist.arrow_button
import kotlinx.android.synthetic.main.activity_artist.card_view
import kotlinx.android.synthetic.main.activity_artist.expandable_view
import kotlinx.android.synthetic.main.activity_artist.image_view_artist
import kotlinx.android.synthetic.main.activity_artist.recycler_view
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ArtistActivity : AppCompatActivity(), View.OnClickListener, OnAlbumListener {

    private val layoutManager: LinearLayoutManager by inject()
    private val viewModel: ArtistViewModel by viewModel()
    private val adapter = ArtistsAlbumAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist)
        subscribeObservers()
        getIncomingIntent()
        initRecyclerView()
    }

    private fun subscribeObservers(){
        observeArtist()
        observeReleases()
        observeError()
    }

    private fun observeArtist(){
        viewModel.getArtistLiveData().observe(this){
            showProgressBar(true)
            showArtist(it)
        }
    }

    private fun observeReleases(){
        viewModel.getReleasesLiveData().observe(this){
            showReleases(it)
        }
    }

    private fun observeError(){
        viewModel.getErrorLiveData().observe(this){throwable ->
            throwable.message?.let { showErrorMessage(it) }
        }
    }

    private fun initRecyclerView(){
        recycler_view.adapter = adapter
        recycler_view.layoutManager = layoutManager
    }

    private fun getIncomingIntent(){
        val id = intent.extras?.getInt("artist_id")
        id?.let {
            viewModel.getArtistById(id)
            viewModel.getReleasesByArtistId(id)
        }
    }

    private fun showErrorMessage(message: String){
        Toast.makeText(
            this,
            "Error : $message",
            Toast.LENGTH_SHORT
        ).show()

    }

    private fun showProgressBar(isVisible: Boolean){
        if (isVisible){
            artist_progress_bar.visibility = View.VISIBLE
        } else {
            artist_progress_bar.visibility = View.INVISIBLE
        }
    }

    private fun showArtist(artist: Artist) {

        if (artist.images == null || artist.images[0].uri == "" || artist.images[0].uri == null){
            Glide.with(this)
                .load("https://s3.amazonaws.com/wll-community-production/images/no-avatar.png")
                .into(image_view_artist)
        } else {
            Glide.with(this)
                .load(artist.images[0].uri)
                .into(image_view_artist)
        }

        text_artist_name.text = artist.name
        arrow_button.setOnClickListener(this)

        showProgressBar(false)
    }

    private fun showReleases(releases: List<Album>){
        adapter.setReleases(releases)
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
        }
    }

    override fun onAlbumClick(position: Int) {
        val intent = Intent(this, AlbumActivity::class.java)
        intent.putExtra("id", adapter.getSelectedAlbum(position)?.id)
        intent.putExtra("type", adapter.getSelectedAlbum(position)?.type)
        startActivity(intent)
    }
}
