package com.zhelezny.frog.di

import com.zhelezny.frog.domain.usecases.JoinGameSessionUseCase
import com.zhelezny.frog.domain.usecases.GetUidFromServerUseCase
import com.zhelezny.frog.domain.usecases.GetUserUseCase
import com.zhelezny.frog.domain.usecases.SaveUserUseCase
import org.koin.dsl.module

val domainModule = module {

    factory {
        SaveUserUseCase(userRepo = get())
    }

    factory {
        GetUserUseCase(userRepo = get())
    }

    factory {
        GetUidFromServerUseCase(ktorRepo = get())
    }

    factory {
        JoinGameSessionUseCase(playerRepository = get())
    }
}