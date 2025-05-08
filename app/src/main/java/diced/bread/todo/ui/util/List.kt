package diced.bread.todo.ui.util

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import diced.bread.todo.model.database.TodoItem

@Composable
fun TaskList(itemList: List<TodoItem>, updateCheck: (TodoItem) -> Unit, modifier: Modifier = Modifier){
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        items(
            itemList,
            itemContent = { i -> TodoCard(i, updateCheck) }
        )
    }
}

@Composable
private fun TodoCard(item: TodoItem,  updateCheck: (TodoItem) -> Unit ,modifier: Modifier = Modifier){
    Row(
        modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .border(1.dp, MaterialTheme.colorScheme.secondary)
            .padding(15.dp, 1.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(item.title, color = MaterialTheme.colorScheme.secondary)
        Checkbox(
            item.complete,
            { updateCheck(item) })
//            { todoList.update(TodoItem(item.uid, item.title, !item.complete)) })
    }
}