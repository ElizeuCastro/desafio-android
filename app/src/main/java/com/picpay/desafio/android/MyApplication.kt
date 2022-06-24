package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.data.di.DataModule
import com.picpay.desafio.android.di.UserModule
import com.picpay.desafio.android.domain.di.DomainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(DataModule.modules, DomainModule.modules, UserModule.modules))
        }
    }
}