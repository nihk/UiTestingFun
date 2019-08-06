package nick.uitestingfun.vm

import nick.uitestingfun.data.LineupRepository
import nick.uitestingfun.mock
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify

class LineupViewModelTest {

    lateinit var lineupViewModel: LineupViewModel
    lateinit var lineupRepository: LineupRepository

    @Before
    fun setUp() {
        lineupRepository = mock()
        lineupViewModel = LineupViewModel(lineupRepository)
    }

    @Test
    fun repositoryIsFetchedFrom() {
        lineupViewModel.fetchItems()

        verify(lineupRepository).fetchItems()
    }
}