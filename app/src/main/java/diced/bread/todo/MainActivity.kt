package diced.bread.todo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import diced.bread.todo.model.database.TodoItem
import diced.bread.todo.ui.util.TodoTopBar
import diced.bread.todo.ui.theme.TodoTheme
import diced.bread.todo.ui.util.AddButton
import diced.bread.todo.ui.util.TaskList
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    private val viewModel: TodoViewModel by viewModels() { TodoViewModel.Factory }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            TodoTheme {
                AppHost(viewModel)
            }
        }
    }

    @Composable
    fun AppHost(viewModel: TodoViewModel){
        val state by viewModel.stateFlow.collectAsState()
        val list by state.list.collectAsState(initial = emptyList())
        App(
            list,
            this::handleItemToggle,
            this::handleNewItem
        )
    }

    fun handleItemToggle(item: TodoItem) {
        Log.e("MainActivity", "handleItemToggle: $item")
        viewModel.update(
            TodoItem(
                item.uid,
                item.title,
                !item.complete
            )
        )
    }

    fun handleNewItem(item: TodoItem){
        Log.e("MainActivity", "handleNewItem: $item")
        viewModel.insert(item)
    }
}

@Composable
fun App(
    list: List<TodoItem>,
    itemCheckToggle: (TodoItem) -> Unit,
    addNewItem: (TodoItem) -> Unit
) {
    var adding = remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TodoTopBar() },
//        bottomBar = {
//            if (!adding.value) {
//                BottomAppBar(containerColor = MaterialTheme.colorScheme.primary) {
//                    TextField(value = "", label = { Text("title") }, onValueChange = {})
//                }
//            }
//        },
        floatingActionButton = {
            AddButton(onClick = {addNewItem(TodoItem(0, "test"))})
        }
    ) { innerPadding ->
        TaskList(
            list,
            updateCheck = { itemCheckToggle },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppTest() {

    var list = List<TodoItem>(1) { TodoItem(0, "test") }
    TodoTheme {
//        App(
//            list,
//            {  }
//        )
    }
}
