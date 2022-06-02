package com.zhelezny.frog.di

import com.zhelezny.frog.domain.usecases.GameRequestUseCase
import com.zhelezny.frog.domain.usecases.GetUidFromServerUseCase
import com.zhelezny.frog.domain.usecases.GetUserUseCase
import com.zhelezny.frog.domain.usecases.SaveUserUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<SaveUserUseCase> {
        SaveUserUseCase(userRepo = get())
    }

    factory<GetUserUseCase> {
        GetUserUseCase(userRepo = get())
    }

    factory<GetUidFromServerUseCase> {
        GetUidFromServerUseCase(ktorRepo = get())
    }

    factory<GameRequestUseCase> {
        GameRequestUseCase(ktorRepository = get())
    }
}