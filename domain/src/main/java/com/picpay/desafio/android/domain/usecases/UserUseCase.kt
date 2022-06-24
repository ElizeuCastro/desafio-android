package com.picpay.desafio.android.domain.usecases

import com.picpay.desafio.android.domain.models.UserModel
import com.picpay.desafio.android.domain.utils.Result

interface UserUseCase {
    suspend fun getUsers(): Result<List<UserModel>>
}