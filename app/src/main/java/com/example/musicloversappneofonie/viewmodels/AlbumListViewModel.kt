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

    private val mAlbums: MutableLiveData<List<Album>> by lazy {
        MutableLiveData<List<Album>>()
            .also{ getAlbums(1, "") }
    }
    private val mError = MutableLiveData<Throwable>()
    private val mIsQueryExhausted = MutableLiveData<Boolean>()
    private val mIsLoading = MutableLiveData<Boolean>()
    var pageNumber = 1
    var mQuery: String? = ""
    var pages = 0

    fun getAlbumsLiveData() : LiveData<List<Album>> {
        return mAlbums
    }

    fun getErrorLiveData() : LiveData<Throwable> {
        return mError
    }

    fun isQueryExhausted() : LiveData<Boolean> {
        return mIsQueryExhausted
    }

    fun isLoading() : LiveData<Boolean> {
        return mIsLoading
    }

    fun getAlbums(page: Int, query: String?){
        mIsQueryExhausted.value = false
        pageNumber = page
        mQuery = query
        viewModelScope.launch {
            mIsLoading.value = true
            val result = withContext(Dispatchers.IO){
                runCatching { repository.getAlbums(page, query) }
            }
            result.onSuccess {
                mAlbums.value = it.results
                pages = it.pagination.pages
            }
            result.onFailure { mError.value = it }
            checkLastQuery(pages, page)
            mIsLoading.value = false
        }
    }

    fun searchNextPage(){
        if (!isQueryExhausted().value!!){
            var currentAlbums: MutableList<Album>? = mAlbums.value?.toMutableList()
            viewModelScope.launch {
                mIsLoading.value = true
                val result = withContext(Dispatchers.IO) {
                    runCatching { repository.getAlbums(pageNumber + 1, mQuery) }
                }
                result.onSuccess { mAlbums.value = it.results }
                result.onFailure { mError.value = it }

                checkLastQuery(pages, pageNumber)
                mAlbums.value?.let { currentAlbums?.addAll(it) }
                mAlbums.postValue(currentAlbums)
                pageNumber++
                mIsLoading.value = false
            }
        }
    }

    private fun checkLastQuery(pages: Int, page: Int){
        mIsQueryExhausted.value = page >= pages
    }
}