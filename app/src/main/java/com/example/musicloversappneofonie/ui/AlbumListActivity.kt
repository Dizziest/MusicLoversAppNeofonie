package com.example.musicloversappneofonie.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.example.musicloversappneofonie.R
import com.example.musicloversappneofonie.adapters.AlbumAdapter
import com.example.musicloversappneofonie.adapters.OnAlbumListener
import com.example.musicloversappneofonie.models.Album
import com.example.musicloversappneofonie.viewmodels.AlbumListViewModel
import kotlinx.android.synthetic.main.activity_album.*
import kotlinx.android.synthetic.main.activity_album_list.*
import kotlinx.android.synthetic.main.activity_album_list.recycler_view

import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class AlbumListActivity : AppCompatActivity(), OnAlbumListener {

    private val viewModel: AlbumListViewModel by viewModel()
    private val layoutManager: RecyclerView.LayoutManager by inject()
    private val landscapeLayoutManager: RecyclerView.LayoutManager by inject(named("landscape_manager"))
    private val tabletLayoutManager: RecyclerView.LayoutManager by inject(named("tablet_manager"))
    private val adapter = AlbumAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_list)
        subscribeObservers()
        initSearchView()
        initRecyclerView()
        showProgressBar(true)
        viewModel.getAlbums(1, "")
    }

    private fun subscribeObservers(){
        observeAlbums()
        observeErrors()
    }

    private fun observeAlbums(){
        viewModel.getAlbumsLiveData().observe(this) {
            showAlbums(it)
        }
    }

    private fun observeErrors(){
        viewModel.getErrorLiveData().observe(this){ throwable ->
            throwable.message?.let { showErrorMessage(it) }
        }
    }

    private fun getProperManager() : RecyclerView.LayoutManager{
        val isPhone = resources.getBoolean(R.bool.is_phone)
        val isLandscape = resources.configuration.orientation
        if (isPhone && isLandscape == 1){
            return layoutManager
        } else if (isPhone && isLandscape == 2) {
            return landscapeLayoutManager
        } else {
            return tabletLayoutManager
        }
    }

    private fun initRecyclerView() {
        recycler_view.layoutManager = getProperManager()
        recycler_view.adapter = adapter

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1)){
                    showProgressBar(true)
                    viewModel.searchNextPage()
                }
            }
        })
    }

    private fun initSearchView() {
        search_view.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                showProgressBar(true)
                viewModel.getAlbums(1, query)
                search_view.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun showProgressBar(isVisible: Boolean){
        if (isVisible){
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.INVISIBLE
        }
    }


    private fun showAlbums(albums: List<Album>) {
        adapter.setAlbums(albums)
        showProgressBar(false)
    }

    private fun showErrorMessage(message: String){
        Toast.makeText(
            this,
            "Error : $message",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onAlbumClick(position: Int) {
        val intent = Intent(this, AlbumActivity::class.java)
        intent.putExtra("id", adapter.getSelectedAlbum(position)?.id)
        intent.putExtra("master_id", adapter.getSelectedAlbum(position)?.master_id)
        intent.putExtra("resource_url", adapter.getSelectedAlbum(position)?.resource_url)
        startActivity(intent)
    }
}
