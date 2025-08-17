package com.example.todoapp

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(
    private val todos: MutableList<TodoItem>,
    private val onDeleteClick: (TodoItem) -> Unit,
    private val onToggleComplete: (TodoItem) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val todoText: TextView = view.findViewById(R.id.todoText)
        val deleteButton: ImageButton = view.findViewById(R.id.deleteButton)
        val completeButton: ImageButton = view.findViewById(R.id.completeButton)
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
            holder.todoText.paintFlags = holder.todoText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.completeButton.setImageResource(R.drawable.ic_check)
            holder.completeButton.background = holder.itemView.context.getDrawable(R.drawable.button_circle_green)
        } else {
            holder.todoText.alpha = 1.0f
            holder.todoText.paintFlags = holder.todoText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.completeButton.setImageResource(R.drawable.ic_circle)
            holder.completeButton.background = holder.itemView.context.getDrawable(R.drawable.button_circle_blue)
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