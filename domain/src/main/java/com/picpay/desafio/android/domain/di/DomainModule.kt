package com.picpay.desafio.android.domain.di

import com.picpay.desafio.android.domain.usecases.UserUseCase
import com.picpay.desafio.android.domain.usecases.UserUseCaseImpl
import org.koin.dsl.module

object DomainModule {

    val modules = module {
        factory<UserUseCase> { UserUseCaseImpl(get()) }
    }
}