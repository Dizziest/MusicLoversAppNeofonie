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
    var pages = 0

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
        disposable.add(repository.getAlbums(page, query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({response -> run {
                mAlbums.value = response.results
                pages = response.pagination.pages
                checkLastQuery(pages, pageNumber)
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
                    checkLastQuery(pages, pageNumber)
                    mAlbums.value?.let { currentAlbums?.addAll(it) }
                    mAlbums.postValue(currentAlbums)
                    pageNumber++
                }}, { t -> mError.value = t}))
        }
    }

    private fun checkLastQuery(pages: Int, page: Int){
        mIsQueryExhausted.value = page >= pages
    }
}