package com.picpay.desafio.android.domain.usecases

import com.picpay.desafio.android.domain.models.UserModel
import com.picpay.desafio.android.domain.repositories.UserRepository
import com.picpay.desafio.android.domain.utils.Result

class UserUseCaseImpl(
    private val userRepository: UserRepository
) : UserUseCase {

    override suspend fun getUsers(): Result<List<UserModel>> = userRepository.getUsers()

}