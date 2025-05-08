package diced.bread.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import diced.bread.todo.model.database.TodoDao
import diced.bread.todo.model.database.TodoDatabase
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

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                val todoDao = TodoDatabase.getDatabase(application).todoDao()
                return TodoViewModel(todoDao) as T
            }
        }
    }
}