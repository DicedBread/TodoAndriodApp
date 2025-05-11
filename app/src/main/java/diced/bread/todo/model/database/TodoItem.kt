package diced.bread.todo.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoItem(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "Title") val title: String,
    @ColumnInfo(name = "Complete") val complete: Boolean = false
)