package com.example.musicloversappneofonie.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicloversappneofonie.models.DetailedAlbum
import com.example.musicloversappneofonie.repositories.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumViewModel(private val repository: AlbumRepository) : ViewModel() {

    private val mAlbum = MutableLiveData<DetailedAlbum>()
    private val mError = MutableLiveData<Throwable>()
    private val mIsLoading = MutableLiveData<Boolean>()

    fun getAlbumLiveData(): LiveData<DetailedAlbum> {
        return mAlbum
    }

    fun getErrorLiveData(): LiveData<Throwable> {
        return mError
    }

    fun isLoading() : LiveData<Boolean> {
        return mIsLoading
    }

    fun getAlbumById(id: Int){
        viewModelScope.launch {
            mIsLoading.value = true
            val result = withContext(Dispatchers.IO){
                runCatching { repository.getAlbumById(id) }
            }
            result.onSuccess { mAlbum.value = it }
            result.onFailure { mError.value = it }
            mIsLoading.value = false
        }
    }

    fun getReleaseById(id: Int){
        mIsLoading.value = true
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                runCatching { repository.getReleaseById(id) }
            }
            result.onSuccess { mAlbum.value = it }
            result.onFailure { mError.value = it }
            mIsLoading.value = false
        }
    }

    fun getReleaseIfCantGetAlbum(id: Int, master_id: Int, resource_url: String) {
        if (master_id == 0 || master_id == null || master_id != id){
            val url = resource_url.substring(33)
            getReleaseById(url.toInt())
        } else {
            getAlbumById(id)
        }
    }
}