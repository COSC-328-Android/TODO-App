package com.example.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(
    private val todos: MutableList<TodoItem>,
    private val onDeleteClick: (TodoItem) -> Unit,
    private val onToggleComplete: (TodoItem) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val todoText: TextView = view.findViewById(R.id.todoText)
        val deleteButton: Button = view.findViewById(R.id.deleteButton)
        val completeButton: Button = view.findViewById(R.id.completeButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todos[position]

        holder.todoText.text = todo.text

        // Update appearance based on completion status
        if (todo.isCompleted) {
            holder.todoText.alpha = 0.6f
            holder.completeButton.text = holder.itemView.context.getString(R.string.complete_symbol)
            holder.completeButton.setBackgroundColor(0xFF4CAF50.toInt()) // Green
        } else {
            holder.todoText.alpha = 1.0f
            holder.completeButton.text = holder.itemView.context.getString(R.string.incomplete_symbol)
            holder.completeButton.setBackgroundColor(0xFF2196F3.toInt()) // Blue
        }

        holder.deleteButton.setOnClickListener {
            onDeleteClick(todo)
        }

        holder.completeButton.setOnClickListener {
            onToggleComplete(todo)
        }
    }

    override fun getItemCount() = todos.size
}