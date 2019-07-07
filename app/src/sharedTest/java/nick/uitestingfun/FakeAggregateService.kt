package nick.uitestingfun

import io.reactivex.Single
import nick.uitestingfun.data.AggregateService
import nick.uitestingfun.data.LineupItem

object FakeAggregateService : AggregateService {
    val lineupItems = listOf(
        LineupItem(title = "1 2 3 4 5 6 7 8 9 10 11"),
        LineupItem(title = "1 2 3 4"),
        LineupItem(title = "1 2 3 4 5 6 7 8 9 10 11 12")
    )

    override fun fetchItems(
        orderLineupId: String?,
        categoryIds: String?,
        type: String?,
        excludedCategorySet: String?,
        page: Int?,
        pageSize: Int?
    ): Single<List<LineupItem>> = Single.just(lineupItems)
}