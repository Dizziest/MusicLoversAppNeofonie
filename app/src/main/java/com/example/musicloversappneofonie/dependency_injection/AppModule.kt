package com.example.musicloversappneofonie.dependency_injection

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicloversappneofonie.adapters.*
import com.example.musicloversappneofonie.repositories.AlbumRepository
import com.example.musicloversappneofonie.viewmodels.AlbumListViewModel
import com.example.musicloversappneofonie.viewmodels.AlbumViewModel
import com.example.musicloversappneofonie.viewmodels.ArtistViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    factory { AlbumRepository(get()) }

    factory<RecyclerView.LayoutManager> { GridLayoutManager(androidContext(), 2) }
    factory { LinearLayoutManager(androidContext()) }

    factory(named("horizontal_manager")) { LinearLayoutManager(androidContext(), LinearLayoutManager.HORIZONTAL, false) }
    factory { TrackAdapter() }
    factory { ChipAdapter() }

    viewModel { AlbumListViewModel(get()) }
    viewModel { AlbumViewModel(get()) }
    viewModel { ArtistViewModel(get()) }


}