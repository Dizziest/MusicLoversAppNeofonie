package com.example.musicloversappneofonie.dependency_injection

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicloversappneofonie.adapters.AlbumAdapter
import com.example.musicloversappneofonie.adapters.OnAlbumListener
import com.example.musicloversappneofonie.repositories.AlbumRepository
import com.example.musicloversappneofonie.viewmodels.AlbumListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule = module {

    factory { AlbumRepository(get()) }

    factory<RecyclerView.LayoutManager> { GridLayoutManager(androidContext(), 2) }

    viewModel { AlbumListViewModel(get()) }


}