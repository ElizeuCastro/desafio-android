package com.picpay.desafio.android.domain.repositories

import com.picpay.desafio.android.domain.models.UserModel
import com.picpay.desafio.android.domain.utils.StateResult

interface UserRepository {
    suspend fun getUsers(): StateResult<List<UserModel>>
}