package com.picpay.desafio.android.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.picpay.desafio.android.data.models.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserEntity>)

    @Query("select * from user")
    suspend fun getUsers(): List<UserEntity>

    @Query("select count(id) from user")
    suspend fun getCount(): Int
}