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

    fun getArtistLiveData() : LiveData<Artist> {
        return mArtist
    }

    fun getReleasesLiveData() : LiveData<List<Album>> {
        return mReleases
    }

    fun getErrorLiveData() : LiveData<Throwable> {
        return mError
    }

    fun getArtistById(id: Int){
        disposable.add(repository.getArtistById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({response -> mArtist.value = response}, {t -> mError.value = t}))
    }

    fun getReleasesByArtistId(id: Int){
        disposable.add(repository.getReleasesByArtistId(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({response -> mReleases.value = response.releases}, {t -> mError.value = t}))
    }
}