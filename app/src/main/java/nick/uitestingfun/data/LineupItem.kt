package nick.uitestingfun.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entities")
data class LineupItem(
    @PrimaryKey
    val title: String
)