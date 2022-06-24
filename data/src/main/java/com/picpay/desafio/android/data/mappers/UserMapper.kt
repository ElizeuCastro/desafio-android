package com.picpay.desafio.android.data.mappers

import com.picpay.desafio.android.data.models.entity.UserEntity
import com.picpay.desafio.android.data.models.response.UserResponse
import com.picpay.desafio.android.domain.models.UserModel

class UserMapper {

    fun toUserModel(users: List<UserEntity>): List<UserModel> =
        users.map { userEntity ->
            UserModel(
                id = userEntity.id,
                name = userEntity.name,
                img = userEntity.img,
                username = userEntity.username
            )
        }

    fun toUserEntity(users: List<UserResponse>): List<UserEntity> =
        users.map { userResponse ->
            UserEntity(
                id = userResponse.id,
                name = userResponse.name,
                img = userResponse.img,
                username = userResponse.username
            )
        }
}


