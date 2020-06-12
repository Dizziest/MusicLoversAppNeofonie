package com.example.musicloversappneofonie.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicloversappneofonie.models.Album
import com.example.musicloversappneofonie.models.Artist
import com.example.musicloversappneofonie.repositories.AlbumRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArtistViewModel(private val repository: AlbumRepository) : ViewModel() {

    private val disposable = CompositeDisposable()
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

    fun isLoading() : LiveData<Boolean>{
        return mIsLoading
    }

    fun getArtistById(id: Int){
        mIsLoading.value = true
        disposable.add(repository.getArtistById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({response -> run{
                mArtist.value = response
                mIsLoading.value = false
            }}, {t -> mError.value = t}))
    }

    fun getReleasesByArtistId(id: Int){
        mIsLoading.value = true
        disposable.add(repository.getReleasesByArtistId(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({response -> run{
                mReleases.value = response.releases
                mIsLoading.value = false
            }}, {t -> mError.value = t}))
    }
}