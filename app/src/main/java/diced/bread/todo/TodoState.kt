package diced.bread.todo

import diced.bread.todo.model.database.TodoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class TodoState(
    val list: Flow<List<TodoItem>>,
    val i: Int = 0
)