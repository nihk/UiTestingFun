package nick.uitestingfun.util

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import nick.uitestingfun.App
import nick.uitestingfun.data.AggregateService
import nick.uitestingfun.data.CbcDatabase
import nick.uitestingfun.data.LineupRepository
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object ServiceLocator {

    val application: Application = App.get()

    val aggregateService: AggregateService = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl("https://www.cbc.ca/aggregate_api/v1/")
        .build()
        .create(AggregateService::class.java)

    val cbcDatabase: CbcDatabase = Room.databaseBuilder(
        application,
        CbcDatabase::class.java,
        "cbc_database.db"
    ).build()

    var lineupRepository: LineupRepository = LineupRepository(
        dao = cbcDatabase.lineupItemDao(),
        service = aggregateService
    )
        @VisibleForTesting(otherwise = VisibleForTesting.NONE) set
}