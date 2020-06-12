package com.example.musicloversappneofonie.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicloversappneofonie.models.Album
import com.example.musicloversappneofonie.models.Artist
import com.example.musicloversappneofonie.repositories.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArtistViewModel(private val repository: AlbumRepository) : ViewModel() {

    private val mArtist = MutableLiveData<Artist>()
    private val mReleases = MutableLiveData<List<Album>>()
    private val mError = MutableLiveData<Throwable>()
    private val mIsLoading = MutableLiveData<Boolean>()

    fun getArtistLiveData() : LiveData<Artist> {
        return mArtist
    }

    fun getReleasesLiveData() : LiveData<List<Album>> {
        return mReleases
    }

    fun getErrorLiveData() : LiveData<Throwable> {
        return mError
    }

    fun isLoading() : LiveData<Boolean> {
        return mIsLoading
    }

    fun getArtistById(id: Int){
        viewModelScope.launch {
            mIsLoading.value = true
            val result = withContext(Dispatchers.IO){
                runCatching{ repository.getArtistById(id) }
            }
            result.onSuccess { mArtist.value = it }
            result.onFailure { mError.value = it }
            mIsLoading.value = false
        }
    }

    fun getReleasesByArtistId(id: Int){
        viewModelScope.launch {
            mIsLoading.value = true
            val result = withContext(Dispatchers.IO){
                runCatching{ repository.getReleasesByArtistId(id) }
            }
            result.onSuccess { mReleases.value = it }
            result.onFailure { mError.value = it }
            mIsLoading.value = false
        }
    }
}