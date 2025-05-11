package diced.bread.todo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import diced.bread.todo.model.database.TodoItem
import diced.bread.todo.navigation.Screen
import diced.bread.todo.ui.theme.TodoTheme
import diced.bread.todo.ui.util.AddButton
import diced.bread.todo.ui.util.TaskList
import diced.bread.todo.ui.util.TodoTopBar
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
     @Serializable
    object MainMenu
    @Serializable
    data class EditMenu(val todo: TodoItem)

    private val viewModel: TodoViewModel by viewModels() { TodoViewModel.Factory }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoTheme {
                NavStack(viewModel)
            }
        }
    }

    @Composable
    fun NavStack(viewModel: TodoViewModel){
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = MainMenu){
            composable<MainMenu>() { AppHost(viewModel) }
            composable<EditMenu>() { AddMenu() }
        }
    }

    @Composable
    fun AppHost(viewModel: TodoViewModel) {
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

    fun handleNewItem(item: TodoItem) {
        Log.e("MainActivity", "handleNewItem: $item")
        viewModel.insert(item)
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    list: List<TodoItem>,
    itemCheckToggle: (TodoItem) -> Unit,
    addNewItem: (TodoItem) -> Unit
) {
    var adding by remember { mutableStateOf(true) }
    var title by remember { mutableStateOf("") }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        topBar = { TodoTopBar() },
        floatingActionButton = {
            AddButton(onClick = { adding = !adding })
        }
    ) { innerPadding ->
        if (adding) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {

            }
            TaskList(
                list,
                updateCheck = { itemCheckToggle },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun AddMenu(){
//    TextField(title, { title = it })
//    Button(onClick = { addNewItem(TodoItem(title = title)) })
}


@Preview(showBackground = true)
@Composable
fun AppTest() {

    var list = List<TodoItem>(1) { TodoItem(0, "test") }
    TodoTheme {
        App(
            list,
            {},
            {}
        )
    }
}
