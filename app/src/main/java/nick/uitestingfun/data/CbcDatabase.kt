package nick.uitestingfun.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LineupItem::class], version = 1, exportSchema = false)
abstract class CbcDatabase : RoomDatabase() {

    abstract fun lineupItemDao(): LineupItemDao
}