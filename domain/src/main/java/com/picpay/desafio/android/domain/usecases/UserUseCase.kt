package com.picpay.desafio.android.domain.usecases

import com.picpay.desafio.android.domain.models.UserModel
import com.picpay.desafio.android.domain.utils.StateResult

interface UserUseCase {
    suspend fun getUsers(): StateResult<List<UserModel>>
}