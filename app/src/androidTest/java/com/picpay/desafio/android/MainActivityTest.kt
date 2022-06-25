package com.picpay.desafio.android

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.picpay.desafio.android.RecyclerViewMatchers.atPosition
import com.picpay.desafio.android.data.di.DataModule
import com.picpay.desafio.android.di.UserModule
import com.picpay.desafio.android.domain.models.UserModel
import com.picpay.desafio.android.domain.usecases.UserUseCase
import com.picpay.desafio.android.domain.utils.Success
import com.picpay.desafio.android.users.MainActivity
import com.picpay.desafio.android.utils.EspressoIdlingResource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
@LargeTest
@ExperimentalCoroutinesApi
class MainActivityTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @MockK(relaxed = true)
    private lateinit var mockedUserUseCase: UserUseCase

    @Before
    fun registerIdlingResource() {
        MockKAnnotations.init(this)
        stopKoin()
        startKoin {
            modules(listOf(DataModule.modules, module {
                factory(override = true) { mockedUserUseCase }
            }, UserModule.modules))
        }

        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        stopKoin()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun shouldDisplayTitle() {

        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        val expectedTitle = context.getString(R.string.title)

        onView(withText(expectedTitle)).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun shouldDisplayUserInTheList() = runBlocking {

        coEvery { mockedUserUseCase.getUsers() } returns Success(data = provideRemoteMockedUsers())

        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.recyclerView))
            .check(matches(atPosition(0, hasDescendant(withText("esquiter")))))

        activityScenario.close()
    }

    private fun provideRemoteMockedUsers() = listOf(
        UserModel(
            id = 1,
            name = "Elizeu",
            username = "esquiter",
            img = "http://image.png"
        )
    )


}