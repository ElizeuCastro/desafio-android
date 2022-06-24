package com.picpay.desafio.android.users

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.picpay.desafio.android.domain.models.UserModel
import com.picpay.desafio.android.domain.usecases.UserUseCase
import com.picpay.desafio.android.domain.utils.Error
import com.picpay.desafio.android.domain.utils.Success
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
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
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    @MockK
    private lateinit var userUseCase: UserUseCase

    private lateinit var userViewModel: UserViewModel

    @Before
    fun before() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `show contact list with success`() = runBlockingTest {
        val mockedUsers = provideMockedUsers()
        coEvery { userUseCase.getUsers() } returns Success(mockedUsers)

        userViewModel = UserViewModel(userUseCase, SavedStateHandle())

        coVerify(exactly = 1) { userUseCase.getUsers() }
        assertEquals(false, userViewModel.error.getOrAwaitValue())
        assertEquals(false, userViewModel.isLoading.getOrAwaitValue())
        assertEquals(mockedUsers, userViewModel.users.getOrAwaitValue())

    }

    @Test
    fun `show message error when occur any service error`() = runBlockingTest {

        coEvery { userUseCase.getUsers() } returns Error("error")

        userViewModel = UserViewModel(userUseCase, SavedStateHandle())

        coVerify(exactly = 1) { userUseCase.getUsers() }
        assertEquals(true, userViewModel.error.getOrAwaitValue())
        assertEquals(false, userViewModel.isLoading.getOrAwaitValue())
        assertEquals(emptyList<UserModel>(), userViewModel.users.getOrAwaitValue())

    }

    @Test
    fun `show contact list from saved state handle`() = runBlockingTest {
        val mockedUsers = provideMockedUsers()
        val savedStateHandle = SavedStateHandle()
        savedStateHandle[UserViewModel.SAVED_USERS] = mockedUsers

        userViewModel = UserViewModel(userUseCase, savedStateHandle)

        coVerify(exactly = 0) { userUseCase.getUsers() }
        assertEquals(false, userViewModel.error.getOrAwaitValue())
        assertEquals(false, userViewModel.isLoading.getOrAwaitValue())
        assertEquals(mockedUsers, userViewModel.users.getOrAwaitValue())

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