package com.picpay.desafio.android.data.source

import com.picpay.desafio.android.data.models.response.UserResponse
import com.picpay.desafio.android.data.api.UserService

class UserRemoteDataSource(private val apiService: UserService) {

    suspend fun getUsers(): List<UserResponse> = apiService.getUsers()

}