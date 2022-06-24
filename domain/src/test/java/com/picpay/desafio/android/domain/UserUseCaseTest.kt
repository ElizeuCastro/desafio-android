package com.picpay.desafio.android.domain

import com.picpay.desafio.android.domain.models.UserModel
import com.picpay.desafio.android.domain.repositories.UserRepository
import com.picpay.desafio.android.domain.usecases.UserUseCase
import com.picpay.desafio.android.domain.usecases.UserUseCaseImpl
import com.picpay.desafio.android.domain.utils.Success
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserUseCaseTest {

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @MockK(relaxed = true)
    private lateinit var userRepository: UserRepository

    private lateinit var userUseCase: UserUseCase

    @Before
    fun before() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        userUseCase = UserUseCaseImpl(userRepository)

    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `retrieve user list`() = runBlockingTest {
        val mockedUsers = provideMockedUsers()
        coEvery { userRepository.getUsers() } returns Success(mockedUsers)

        val result  = userUseCase.getUsers()

        assertEquals(Success::class.java, result::class.java)
        assertEquals(mockedUsers, (result as Success).data)

    }

    private fun provideMockedUsers() = listOf(
        UserModel(
            id = 1,
            name = "Elizeu",
            username = "esquiter",
            img = "http://image.png"
        )
    )

}