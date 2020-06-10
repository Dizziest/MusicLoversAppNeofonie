package com.example.musicloversappneofonie.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicloversappneofonie.models.Album
import com.example.musicloversappneofonie.repositories.AlbumRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class AlbumListViewModel(private val repository: AlbumRepository) : ViewModel() {

    private val disposable = CompositeDisposable()
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

    fun getAlbums(page: Int, query: String?){
        mIsQueryExhausted.value = false
        pageNumber = page
        mQuery = query
        disposable.add(repository.getAlbums(page, query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({response -> run {
                mAlbums.value = response.results
                checkLastQuery(mAlbums.value?.toList())
            }}, {t -> mError.value = t}))
    }

    fun searchNextPage(){
        println(isQueryExhausted().value)
        if (!isQueryExhausted().value!!){
            var currentAlbums: MutableList<Album>? = mAlbums.value?.toMutableList()
            disposable.add(repository.getAlbums(pageNumber + 1, mQuery)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({response -> run {
                    mAlbums.value = response.results
                    checkLastQuery(mAlbums.value?.toList())
                    mAlbums.value?.let { currentAlbums?.addAll(it) }
                    mAlbums.postValue(currentAlbums)
                    pageNumber++
                }}, { t -> mError.value = t}))
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