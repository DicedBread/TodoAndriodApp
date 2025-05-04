package diced.bread.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import diced.bread.todo.model.database.TodoDao
import diced.bread.todo.model.database.TodoItem
import kotlinx.coroutines.launch

class TodoViewModel(private val todoDao: TodoDao) : ViewModel() {
    private val _allTasks: LiveData<List<TodoItem>> = todoDao.getAll();
    val allTasks: LiveData<List<TodoItem>> get() = _allTasks

    @Insert
    fun insert(item: TodoItem){
        viewModelScope.launch {
            todoDao.insert(item)
        }
    }

    @Update
    fun update(item: TodoItem){
        viewModelScope.launch {
            todoDao.update(item)
        }
    }
}