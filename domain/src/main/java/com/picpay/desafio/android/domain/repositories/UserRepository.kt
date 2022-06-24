package com.picpay.desafio.android.domain.repositories

import com.picpay.desafio.android.domain.models.UserModel
import com.picpay.desafio.android.domain.utils.Result

interface UserRepository {
    suspend fun getUsers(): Result<List<UserModel>>
}