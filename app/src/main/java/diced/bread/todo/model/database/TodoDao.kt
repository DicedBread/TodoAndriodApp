package diced.bread.todo.model.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {
    @Insert
    suspend fun insert(item: TodoItem)

    @Update
    suspend fun update(item: TodoItem)

    @Query("SELECT * FROM TodoItem")
    fun getAll(): LiveData<List<TodoItem>>
}
