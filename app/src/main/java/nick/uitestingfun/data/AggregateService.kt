package nick.uitestingfun.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface AggregateService {

    companion object {
        const val DEFAULT_PAGE_SIZE = 50
    }

    @GET("items")
    fun fetchItems(
        @Query("orderLineupId") orderLineupId: String?,
        @Query("categoryIds") categoryIds: String? = null,
        @Query("type") type: String? = null,
        @Query("excludedCategorySet") excludedCategorySet: String? = "cbc-news-apps-exclude",
        @Query("page") page: Int? = 1,
        @Query("pageSize") pageSize: Int? = DEFAULT_PAGE_SIZE
    ): Single<List<LineupItem>>
}