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
    private val mIsQueryExhausted = MutableLiveData<Boolean>()
    var pageNumber = 1
    var mQuery: String? = ""

    fun getAlbumsLiveData() : LiveData<List<Album>> {
        return mAlbums
    }

    fun getErrorLiveData() : LiveData<Throwable> {
        return mError
    }

    fun isQueryExhausted() : LiveData<Boolean>{
        return mIsQueryExhausted
    }

    fun onViewCreated(){
        getAlbums(1, "")
    }

    fun getAlbums(page: Int, query: String?){
        mIsQueryExhausted.value = false
        pageNumber = page
        mQuery = query
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                runCatching { repository.getAlbums(page, query) }
            }
            result.onSuccess { mAlbums.value = it }
            result.onFailure { mError.value = it }
            checkLastQuery(mAlbums.value?.toList())
        }
    }

    fun searchNextPage(){
        if (!isQueryExhausted().value!!){
            var currentAlbums: MutableList<Album>? = mAlbums.value?.toMutableList()
            viewModelScope.launch {
                val result = withContext(Dispatchers.IO) {
                    runCatching { repository.getAlbums(pageNumber + 1, mQuery) }
                }
                result.onSuccess { mAlbums.value = it }
                result.onFailure { mError.value = it }

                checkLastQuery(mAlbums.value?.toList())
                mAlbums.value?.let { currentAlbums?.addAll(it) }
                mAlbums.postValue(currentAlbums)
                pageNumber++
            }
        }
    }

    // API sometimes returns 19 albums on page instead of 20, so the function tells
    // app that the query is exhausted despite the fact that there are more albums
    // to retrieve.
    private fun checkLastQuery(albums: List<Album>?){
        if (albums != null){
            if (albums.size % 20 != 0){
                mIsQueryExhausted.value = true
            }
        } else {
            mIsQueryExhausted.value = true
        }
    }
}