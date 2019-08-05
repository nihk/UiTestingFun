package nick.uitestingfun.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import nick.uitestingfun.R
import nick.uitestingfun.atPosition
import nick.uitestingfun.data.LineupItem
import nick.uitestingfun.data.LineupRepository
import nick.uitestingfun.mock
import nick.uitestingfun.util.Resource
import nick.uitestingfun.util.ServiceLocator
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`

@RunWith(AndroidJUnit4::class)
class LineupFragmentMockedRepoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var resource: MutableLiveData<Resource<List<LineupItem>>>
    lateinit var lineupRepository: LineupRepository

    @Before
    fun setUp() {
        resource = MutableLiveData()
        lineupRepository = mock()
        ServiceLocator.lineupRepository = lineupRepository
    }

    @Test
    fun loadingShowsProgressBar() {
        resource.value = Resource.Loading(data = null)
        `when`(lineupRepository.fetchItems()).thenReturn(resource)

        launchFragmentInContainer<LineupFragment>(themeResId = R.style.AppTheme)

        onView(withId(R.id.progress_bar))
            .check(matches(isDisplayed()))
        onView(withId(R.id.error_message))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun error_withoutData_showsErrorMessage() {
        resource.value = Resource.Error(throwable = Exception())
        `when`(lineupRepository.fetchItems()).thenReturn(resource)

        launchFragmentInContainer<LineupFragment>(themeResId = R.style.AppTheme)

        onView(withId(R.id.error_message))
            .check(matches(isDisplayed()))
        onView(withId(R.id.progress_bar))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun error_withData_showsData() {
        val title = "hello world"
        resource.value = Resource.Error(throwable = Exception(), data = listOf(LineupItem(title = title)))
        `when`(lineupRepository.fetchItems()).thenReturn(resource)

        launchFragmentInContainer<LineupFragment>(themeResId = R.style.AppTheme)

        onView(withId(R.id.recycler_view))
            .check(matches(atPosition(0, withChild(withText(title)))))
        onView(withId(R.id.error_message))
            .check(matches(not(isDisplayed())))
        onView(withId(R.id.progress_bar))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun successShowsData() {
        val title = "hello world"
        resource.value = Resource.Success(data = listOf(LineupItem(title = title)))
        `when`(lineupRepository.fetchItems()).thenReturn(resource)

        launchFragmentInContainer<LineupFragment>(themeResId = R.style.AppTheme)

        onView(withId(R.id.recycler_view))
            .check(matches(atPosition(0, withChild(withText(title)))))
        onView(withId(R.id.error_message))
            .check(matches(not(isDisplayed())))
        onView(withId(R.id.progress_bar))
            .check(matches(not(isDisplayed())))
    }
}