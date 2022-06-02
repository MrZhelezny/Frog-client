package com.zhelezny.frog.di

import com.zhelezny.frog.data.repository.KtorRepositoryImpl
import com.zhelezny.frog.data.repository.UserRepositoryImpl
import com.zhelezny.frog.data.storage.SharedPrefUserStorage
import com.zhelezny.frog.data.storage.UserStorage
import com.zhelezny.frog.domain.repository.KtorRepository
import com.zhelezny.frog.domain.repository.UserRepository
import org.koin.dsl.module

val dataModule = module {

    single<UserStorage> {
        SharedPrefUserStorage(context = get())
    }

    single<UserRepository> {
        UserRepositoryImpl(userStorage = get())
    }

    single<KtorRepository> {
        KtorRepositoryImpl()
    }
}