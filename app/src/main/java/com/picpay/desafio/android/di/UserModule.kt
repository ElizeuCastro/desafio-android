package com.picpay.desafio.android.di

import androidx.lifecycle.SavedStateHandle
import com.picpay.desafio.android.users.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object UserModule {

    val modules = module {
        viewModel { (savedStateHandle: SavedStateHandle) -> UserViewModel(get(), savedStateHandle) }
    }
}