package com.picpay.desafio.android

import com.picpay.desafio.android.data.mappers.UserMapper
import com.picpay.desafio.android.data.models.entity.UserEntity
import com.picpay.desafio.android.data.models.response.UserResponse
import com.picpay.desafio.android.data.repository.UserRepositoryImpl
import com.picpay.desafio.android.data.source.UserLocalDataSource
import com.picpay.desafio.android.data.source.UserRemoteDataSource
import com.picpay.desafio.android.domain.repositories.UserRepository
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
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserRepositoryTest {

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @MockK(relaxed = true)
    private lateinit var userLocalDataSource: UserLocalDataSource

    @MockK(relaxed = true)
    private lateinit var userRemoteDataSource: UserRemoteDataSource

    private val userMapper = UserMapper()

    private lateinit var userRepository: UserRepository

    @Before
    fun before() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        userRepository = UserRepositoryImpl(userLocalDataSource, userRemoteDataSource, userMapper)

    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `retrieve user from remote server`() = runBlockingTest {
        val mockedRemoteUsers = provideRemoteMockedUsers()
        val mockedLocalUsers = provideLocalMockedUsers()
        coEvery { userRemoteDataSource.getUsers() } returns mockedRemoteUsers
        coEvery { userLocalDataSource.getUsers() } returns mockedLocalUsers

        val result = userRepository.getUsers()

        coVerify { userLocalDataSource.insertAll(userMapper.responseToUserEntity(mockedRemoteUsers)) }
        coVerify { userLocalDataSource.getUsers() }
        Assert.assertEquals(Success::class.java, result::class.java)
        Assert.assertEquals((result as Success).data, userMapper.entityToUserModel(mockedLocalUsers))

    }

    @Test
    fun `retrieve user from local when remote server got an error`() = runBlockingTest {
        val mockedRemoteUsers = provideRemoteMockedUsers()
        val mockedLocalUsers = provideLocalMockedUsers()
        coEvery { userRemoteDataSource.getUsers() } throws Exception()
        coEvery { userLocalDataSource.getUsers() } returns mockedLocalUsers
        coEvery { userLocalDataSource.getCount() } returns 1

        val result = userRepository.getUsers()

        coVerify(exactly = 0) { userLocalDataSource.insertAll(userMapper.responseToUserEntity(mockedRemoteUsers)) }
        coVerify(exactly = 1) { userLocalDataSource.getUsers() }
        coVerify(exactly = 1) { userLocalDataSource.getCount() }
        Assert.assertEquals(Success::class.java, result::class.java)
        Assert.assertEquals((result as Success).data, userMapper.entityToUserModel(mockedLocalUsers))

    }

    @Test
    fun `throws error user from emote server got an error and local users is empty`() = runBlockingTest {
        val mockedRemoteUsers = provideRemoteMockedUsers()
        val mockedLocalUsers = provideLocalMockedUsers()
        coEvery { userRemoteDataSource.getUsers() } throws Exception()
        coEvery { userLocalDataSource.getUsers() } returns mockedLocalUsers
        coEvery { userLocalDataSource.getCount() } returns 0

        val result = userRepository.getUsers()

        coVerify(exactly = 0) { userLocalDataSource.insertAll(userMapper.responseToUserEntity(mockedRemoteUsers)) }
        coVerify(exactly = 0) { userLocalDataSource.getUsers() }
        coVerify(exactly = 1) { userLocalDataSource.getCount() }
        Assert.assertEquals(Error::class.java, result::class.java)

    }

    private fun provideRemoteMockedUsers() = listOf(
        UserResponse(
            id = 1,
            name = "Elizeu",
            username = "esquiter",
            img = "http://image.png"
        )
    )

    private fun provideLocalMockedUsers() = listOf(
        UserEntity(
            id = 1,
            name = "Elizeu",
            username = "esquiter",
            img = "http://image.png"
        )
    )


}