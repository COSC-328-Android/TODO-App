package com.example.todoapp

data class TodoItem(
    val id: Long = System.currentTimeMillis(),
    val text: String,
    var isCompleted: Boolean = false
)