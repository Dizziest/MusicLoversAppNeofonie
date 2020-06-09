package com.example.musicloversappneofonie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.example.musicloversappneofonie.adapters.AlbumAdapter
import com.example.musicloversappneofonie.models.Album
import com.example.musicloversappneofonie.viewmodels.AlbumListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: AlbumListViewModel by viewModel()
    private val adapter: AlbumAdapter by inject()
    private val layoutManager: RecyclerView.LayoutManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        subscribeObservers()
        initRecyclerView()

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

    private fun initRecyclerView() {
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adapter

    }

    private fun showAlbums(albums: List<Album>) {
        adapter.setAlbums(albums)
    }

    private fun showErrorMessage(message: String){
        Toast.makeText(
            this,
            "Error : $message",
            Toast.LENGTH_SHORT
        ).show()
    }
}
