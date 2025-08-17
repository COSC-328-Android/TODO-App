package com.example.todoapp

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object TodoPersistence {
    private const val PREFS_NAME = "todo_prefs"
    private const val TODO_LIST_KEY = "todo_list"
    private val gson = Gson()

    fun saveTodos(context: Context, todos: List<TodoItem>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = gson.toJson(todos)
        prefs.edit().putString(TODO_LIST_KEY, json).apply()
    }

    fun loadTodos(context: Context): MutableList<TodoItem> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(TODO_LIST_KEY, null)
        
        return if (json != null) {
            val type = object : TypeToken<List<TodoItem>>() {}.type
            gson.fromJson<List<TodoItem>>(json, type).toMutableList()
        } else {
            mutableListOf()
        }
    }
}
