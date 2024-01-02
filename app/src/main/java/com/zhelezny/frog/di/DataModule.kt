package com.zhelezny.frog.di

import com.zhelezny.frog.data.repository.IPlayerRepository
import com.zhelezny.frog.data.storage.PlayerStorage
import com.zhelezny.frog.data.storage.SharedPrefPlayerStorage
import com.zhelezny.frog.domain.repository.PlayerRepository
import org.koin.dsl.module

val dataModule = module {

    single<PlayerStorage> {
        SharedPrefPlayerStorage(context = get())
    }

    single<PlayerRepository> {
        IPlayerRepository(playerStorage = get())
    }
}