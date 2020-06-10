package com.example.musicloversappneofonie.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicloversappneofonie.models.DetailedAlbum
import com.example.musicloversappneofonie.repositories.AlbumRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class AlbumViewModel(private val repository: AlbumRepository) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val mAlbum = MutableLiveData<DetailedAlbum>()
    private val mError = MutableLiveData<Throwable>()

    fun getAlbumLiveData(): LiveData<DetailedAlbum> {
        return mAlbum
    }

    fun getErrorLiveData(): LiveData<Throwable> {
        return mError
    }

    fun getAlbumById(id: Int){
        disposable.add(repository.getAlbumById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({response -> mAlbum.value = response}, {t -> mError.value = t }))
    }

    fun getReleaseById(id: Int){
        disposable.add(repository.getReleaseById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({response -> mAlbum.value = response}, {t -> mError.value = t}))
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