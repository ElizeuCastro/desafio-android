package com.picpay.desafio.android.data.di

import com.picpay.desafio.android.data.api.ApiClient
import com.picpay.desafio.android.data.db.AppDataBase
import com.picpay.desafio.android.data.mappers.UserMapper
import com.picpay.desafio.android.data.repository.UserRepositoryImpl
import com.picpay.desafio.android.data.source.UserLocalDataSource
import com.picpay.desafio.android.data.source.UserRemoteDataSource
import com.picpay.desafio.android.domain.repositories.UserRepository
import org.koin.dsl.module

object DataModule {

    val modules = module {
        single { ApiClient.userService }
        single { AppDataBase.getInstance(get()).userDao }
        factory { UserMapper() }
        factory { UserLocalDataSource(get()) }
        factory { UserRemoteDataSource(get()) }
        factory<UserRepository> { UserRepositoryImpl(get(), get(), get()) }
    }
}