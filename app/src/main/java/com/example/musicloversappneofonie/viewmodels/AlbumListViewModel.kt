package com.example.musicloversappneofonie.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicloversappneofonie.models.Album
import com.example.musicloversappneofonie.repositories.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumListViewModel(private val repository: AlbumRepository) : ViewModel() {
    private val mAlbums = MutableLiveData<List<Album>>()
    private val mError = MutableLiveData<Throwable>()

    fun getAlbumsLiveData() : LiveData<List<Album>> {
        return mAlbums
    }

    fun getErrorLiveData() : LiveData<Throwable> {
        return mError
    }

    fun getAlbums(page: Int, query: String){
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                runCatching { repository.getAlbums(page, query) }
            }
            result.onSuccess { mAlbums.value = it }
            result.onFailure { mError.value = it }
        }
    }
}