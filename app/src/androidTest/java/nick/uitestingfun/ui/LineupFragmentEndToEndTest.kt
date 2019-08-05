package nick.uitestingfun.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import nick.uitestingfun.*
import nick.uitestingfun.data.AggregateService
import nick.uitestingfun.data.CbcDatabase
import nick.uitestingfun.data.LineupRepositoryImpl
import nick.uitestingfun.util.EspressoIdlingResource
import nick.uitestingfun.util.ServiceLocator
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LineupFragmentEndToEndTest {

    @get:Rule
    var cbcDatabaseRule = InMemoryDatabaseRule(CbcDatabase::class.java)

    @get:Rule
    var idlingResourceRule = IdlingResourceRule(EspressoIdlingResource)

    @Before
    fun setUp() {
        ServiceLocator.lineupRepository = LineupRepositoryImpl(
            dao = cbcDatabaseRule.database.lineupItemDao(),
            service = ServiceLocator.aggregateService
        )

        launchFragmentInContainer<LineupFragment>(themeResId = R.style.AppTheme)
    }

    @Test
    fun itemsHaveCorrectBackgroundColors() {
        onView(withId(R.id.recycler_view)).apply {
            repeat(AggregateService.DEFAULT_PAGE_SIZE) { i ->
                perform(RecyclerViewActions.scrollToPosition<LineupItemViewHolder>(i))
                check(matches(atPosition(i, withLineupViewHolderBackgroundColor())))
            }
        }
    }

    @Test
    fun lineupItemsHaveCorrectCount() {
        onView(withId(R.id.recycler_view))
            .check(matches(withItemCount(AggregateService.DEFAULT_PAGE_SIZE)))
    }
}