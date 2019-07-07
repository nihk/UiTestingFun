package nick.uitestingfun.data

import androidx.lifecycle.LiveData
import io.reactivex.Completable
import io.reactivex.Single
import nick.uitestingfun.util.NetworkBoundResource
import nick.uitestingfun.util.Resource

class LineupRepository (
    val dao: LineupItemDao,
    val service: AggregateService
) {

    fun fetchItems(): LiveData<Resource<List<LineupItem>>> {
        return object : NetworkBoundResource<List<LineupItem>, List<LineupItem>>() {

            override fun loadFromDb(): LiveData<List<LineupItem>> {
                return dao.queryAll()
            }

            override fun createCall(): Single<List<LineupItem>> {
                return service.fetchItems(orderLineupId = "2.641")
            }

            override fun saveCallResult(item: List<LineupItem>): Completable {
                return Completable.fromAction { dao.insertAll(item) }
            }
        }.liveData
    }
}