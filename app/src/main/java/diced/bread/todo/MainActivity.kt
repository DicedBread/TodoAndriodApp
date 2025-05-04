package diced.bread.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import diced.bread.myapplication.ui.TodoAppUi.TodoAppUi
import diced.bread.todo.model.database.TodoDatabase
import diced.bread.todo.ui.theme.TodoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = TodoDatabase.getDatabase(this)


        enableEdgeToEdge()
        setContent {
            TodoTheme {
                TodoAppUi(TodoViewModel(db.todoDao())).ToDoListApp()
            }
        }
    }
}

