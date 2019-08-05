package nick.uitestingfun.data

import androidx.lifecycle.LiveData
import nick.uitestingfun.util.Resource

interface LineupRepository {

    fun fetchItems(): LiveData<Resource<List<LineupItem>>>
}