package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.mappers.UserMapper
import com.picpay.desafio.android.data.source.UserLocalDataSource
import com.picpay.desafio.android.data.source.UserRemoteDataSource
import com.picpay.desafio.android.domain.models.UserModel
import com.picpay.desafio.android.domain.repositories.UserRepository
import com.picpay.desafio.android.domain.utils.Error
import com.picpay.desafio.android.domain.utils.StateResult
import com.picpay.desafio.android.domain.utils.Success

class UserRepositoryImpl(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userMapper: UserMapper
) : UserRepository {

    override suspend fun getUsers(): StateResult<List<UserModel>> = try {
        refresh()
        val users = userLocalDataSource.getUsers()
        Success(userMapper.toUserModel(users))
    } catch (e: Exception) {
        Error(e.message)
    }

    private suspend fun refresh() {
        try {
            val users = userRemoteDataSource.getUsers()
            if (users.isNotEmpty()) {
                userLocalDataSource.insertAll(userMapper.toUserEntity(users))
            }
        } catch (e: Exception) {
            if (userLocalDataSource.getCount() == 0) {
                throw e
            }
        }
    }
}

