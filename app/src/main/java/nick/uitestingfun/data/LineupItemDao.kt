package nick.uitestingfun.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LineupItemDao {

    @Query("SELECT * FROM entities")
    fun queryAll(): LiveData<List<LineupItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(lineupItems: List<LineupItem>)
}