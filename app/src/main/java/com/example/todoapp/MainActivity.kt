package com.example.todoapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var editTextTodo: EditText
    private lateinit var buttonAdd: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TodoAdapter

    private val todoList = mutableListOf<TodoItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupRecyclerView()
        setupClickListeners()
        loadTodos()
    }

    override fun onPause() {
        super.onPause()
        saveTodos()
    }

    override fun onStop() {
        super.onStop()
        saveTodos()
    }

    private fun initViews() {
        editTextTodo = findViewById(R.id.editTextTodo)
        buttonAdd = findViewById(R.id.buttonAdd)
        recyclerView = findViewById(R.id.recyclerView)
    }

    private fun setupRecyclerView() {
        adapter = TodoAdapter(
            todos = todoList,
            onDeleteClick = { todo -> deleteTodo(todo) },
            onToggleComplete = { todo -> toggleComplete(todo) }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupClickListeners() {
        buttonAdd.setOnClickListener {
            addTodo()
        }
    }

    private fun addTodo() {
        val todoText = editTextTodo.text.toString().trim()

        if (todoText.isEmpty()) {
            Toast.makeText(this, getString(R.string.enter_todo_prompt), Toast.LENGTH_SHORT).show()
            return
        }

        val newTodo = TodoItem(text = todoText)
        todoList.add(0, newTodo) // Add to top of list
        adapter.notifyItemInserted(0)
        recyclerView.scrollToPosition(0)
        saveTodos()

        editTextTodo.text.clear()
        Toast.makeText(this, getString(R.string.todo_added), Toast.LENGTH_SHORT).show()
    }

    private fun deleteTodo(todo: TodoItem) {
        val position = todoList.indexOf(todo)
        if (position != -1) {
            todoList.removeAt(position)
            adapter.notifyItemRemoved(position)
            saveTodos()
            Toast.makeText(this, getString(R.string.todo_deleted), Toast.LENGTH_SHORT).show()
        }
    }

    private fun toggleComplete(todo: TodoItem) {
        val position = todoList.indexOf(todo)
        if (position != -1) {
            todo.isCompleted = !todo.isCompleted
            adapter.notifyItemChanged(position)
            saveTodos()

            val statusMessage = if (todo.isCompleted) {
                getString(R.string.todo_completed)
            } else {
                getString(R.string.todo_incomplete)
            }
            Toast.makeText(this, statusMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadTodos() {
        val savedTodos = TodoPersistence.loadTodos(this)
        todoList.clear()
        todoList.addAll(savedTodos)
        adapter.notifyDataSetChanged()
    }

    private fun saveTodos() {
        TodoPersistence.saveTodos(this, todoList)
    }
}