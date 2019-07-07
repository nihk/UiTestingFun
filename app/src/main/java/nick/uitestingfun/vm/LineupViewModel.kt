package nick.uitestingfun.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import nick.uitestingfun.data.LineupItem
import nick.uitestingfun.util.Resource
import nick.uitestingfun.util.ServiceLocator

class LineupViewModel : ViewModel() {

    private val repository = ServiceLocator.lineupRepository

    fun fetchItems(): LiveData<Resource<List<LineupItem>>> {
        return repository.fetchItems()
    }
}