package com.picpay.desafio.android.domain.usecases

import com.picpay.desafio.android.domain.models.UserModel
import com.picpay.desafio.android.domain.repositories.UserRepository
import com.picpay.desafio.android.domain.utils.StateResult

class UserUseCaseImpl(
    private val userRepository: UserRepository
) : UserUseCase {

    override suspend fun getUsers(): StateResult<List<UserModel>> = userRepository.getUsers()

}