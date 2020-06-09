package com.example.musicloversappneofonie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.observe
import com.example.musicloversappneofonie.viewmodels.AlbumListViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: AlbumListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        subscribeObservers()
        viewModel.getAlbums(1)
    }

    private fun subscribeObservers(){
        observeAlbums()
        observeErrors()
    }

    private fun observeAlbums(){
        viewModel.getAlbumsLiveData().observe(this) {
            println(it)
        }
    }

    private fun observeErrors(){
        viewModel.getErrorLiveData().observe(this){ throwable ->
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
}
