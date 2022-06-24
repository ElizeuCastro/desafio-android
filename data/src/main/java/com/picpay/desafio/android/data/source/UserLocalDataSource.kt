package com.picpay.desafio.android.data.source

import com.picpay.desafio.android.data.db.UserDao
import com.picpay.desafio.android.data.models.entity.UserEntity

class UserLocalDataSource(
    private val userDao: UserDao
) {

    suspend fun insertAll(users: List<UserEntity>) = userDao.insertAll(users)

    suspend fun getUsers(): List<UserEntity> = userDao.getUsers()

    suspend fun getCount(): Int = userDao.getCount()

}