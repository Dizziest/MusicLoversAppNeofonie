package com.example.musicloversappneofonie.dependency_injection

import com.example.musicloversappneofonie.repositories.AlbumRepository
import com.example.musicloversappneofonie.viewmodels.AlbumListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule = module {

    factory { AlbumRepository(get()) }

    viewModel { AlbumListViewModel(get()) }
}