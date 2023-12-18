package com.zhelezny.frog.di

import com.zhelezny.frog.presentation.PlayerSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<PlayerSearchViewModel> {
        PlayerSearchViewModel(joinGameUseCase = get())
    }
}