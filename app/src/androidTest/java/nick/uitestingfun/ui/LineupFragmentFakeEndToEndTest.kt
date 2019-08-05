package nick.uitestingfun.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import nick.uitestingfun.*
import nick.uitestingfun.data.CbcDatabase
import nick.uitestingfun.data.LineupRepositoryImpl
import nick.uitestingfun.util.Colors
import nick.uitestingfun.util.EspressoIdlingResource
import nick.uitestingfun.util.ServiceLocator
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LineupFragmentFakeEndToEndTest {

    @get:Rule
    var cbcDatabaseRule = InMemoryDatabaseRule(CbcDatabase::class.java)

    @get:Rule
    var idlingResourceRule = IdlingResourceRule(EspressoIdlingResource)

    @Before
    fun setUp() {
        ServiceLocator.lineupRepository = LineupRepositoryImpl(
            cbcDatabaseRule.database.lineupItemDao(),
            FakeAggregateService
        )

        launchFragmentInContainer<LineupFragment>(themeResId = R.style.AppTheme)
    }

    @Test
    fun lineupItemsHaveCorrectText() {
        onView(withId(R.id.recycler_view)).apply {
            repeat(FakeAggregateService.lineupItems.size) { i ->
                check(matches(atPosition(i, withChild(withText(FakeAggregateService.lineupItems[i].title)))))
            }
        }
    }

    @Test
    fun greaterThanTenWordsHaveGreenBackground() {
        onView(withId(R.id.recycler_view))
            .check(matches(atPosition(0, withBackgroundColor(Colors.LIGHT_GREEN))))
    }

    @Test
    fun tenOrFewerWordsHaveRedBackground() {
        onView(withId(R.id.recycler_view))
            .check(matches(atPosition(1, withBackgroundColor(Colors.LIGHT_RED))))
    }

    @Test
    fun lineupItemsHaveCorrectCount() {
        onView(withId(R.id.recycler_view))
            .check(matches(withItemCount(FakeAggregateService.lineupItems.size)))
    }

    // This works if FakeAggregateService returned a Single.error()
//    @Test
//    fun errorViewIsShownOnError() {
//        onView(withId(R.id.error_message))
//            .check(matches(isDisplayed()))
//    }
}