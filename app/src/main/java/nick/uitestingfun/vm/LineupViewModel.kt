package nick.uitestingfun.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import nick.uitestingfun.data.LineupItem
import nick.uitestingfun.data.LineupRepository
import nick.uitestingfun.util.Resource
import nick.uitestingfun.util.ServiceLocator

class LineupViewModel(
    private val repository: LineupRepository = ServiceLocator.lineupRepository
) : ViewModel() {

    fun fetchItems(): LiveData<Resource<List<LineupItem>>> {
        return repository.fetchItems()
    }
}