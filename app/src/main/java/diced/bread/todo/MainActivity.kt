package diced.bread.todo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import diced.bread.todo.model.database.TodoItem
import diced.bread.todo.ui.util.TodoTopBar
import diced.bread.todo.ui.theme.TodoTheme
import diced.bread.todo.ui.util.AddButton
import diced.bread.todo.ui.util.TaskList
import kotlin.text.insert
import kotlin.toString

class MainActivity : ComponentActivity() {
    private val viewModel: TodoViewModel by viewModels() { TodoViewModel.Factory }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            TodoTheme {
                var adding = remember { mutableStateOf(false) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { TodoTopBar() },
                    bottomBar = {
                        if (!adding.value) {
                            BottomAppBar(containerColor = MaterialTheme.colorScheme.primary) {
                                TextField(value = "", label = { Text("title") }, onValueChange = {})
                            }
                        }
                    },
                    floatingActionButton = {
                        AddButton(onClick = {
                            adding.value = true;
                        })
                    }
                ) { innerPadding ->
                    val list = viewModel.allTasks.observeAsState(listOf())

                    if (adding.value) {
                        TaskList(
                            list.value,
                            updateCheck = { item ->
                                viewModel.update(
                                    TodoItem(
                                        item.uid,
                                        item.title,
                                        !item.complete
                                    )
                                )
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else {

                    }
                }
            }
        }
    }
}

fun App(

){

}

@Preview(showBackground = true)
@Composable
fun AppTest() {
    TodoTheme {
        var adding = remember { mutableStateOf(false) }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TodoTopBar() },
            bottomBar = {
                if (!adding.value) {
                    BottomAppBar(containerColor = MaterialTheme.colorScheme.primary) {
                        TextField(value = "", label = { Text("title") }, onValueChange = {})
                    }
                }
            },
            floatingActionButton = {
                AddButton(onClick = {
                    adding.value = true;
                })
            }
        ) { innerPadding ->
            val list = remember { mutableStateListOf(TodoItem(0, "test"), TodoItem(1, "test2", true)) }

            if (!adding.value) {
                TaskList(
                    list,
                    updateCheck = { },
                    modifier = Modifier.padding(innerPadding)
                )
            } else {

            }
        }
    }
}
