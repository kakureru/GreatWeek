package com.example.greatweek.app.presentation.adapter

import android.content.ClipData
import android.content.ClipDescription
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.greatweek.databinding.GoalLayoutBinding
import com.example.greatweek.domain.model.Goal

class GoalAdapter(
    private val completeGoal: (goalId: Int) -> Unit,
    private val editGoal: (goalId: Int) -> Unit
    )
    : ListAdapter<Goal, GoalAdapter.GoalViewHolder>(DiffCallback) {

    inner class GoalViewHolder(private val binding: GoalLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(goal: Goal) {
            binding.goalTextView.text = goal.title
            binding.roleTextView.text = goal.role
            binding.goalCheckbox.setOnClickListener { completeGoal(goal.id) }
            binding.root.setOnClickListener { editGoal(goal.id) }
            binding.root.setOnLongClickListener {
                val itemId = ClipData.Item(goal.id.toString())
                val weekDay = goal.weekday.toString()
                val dragData = ClipData(
                    weekDay,
                    arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                    itemId)
                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(dragData, dragShadowBuilder, it, 0)
                it.visibility = View.INVISIBLE
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        return GoalViewHolder(
            GoalLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Goal>() {
            override fun areItemsTheSame(oldItem: Goal, newItem: Goal): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Goal, newItem: Goal): Boolean {
                return oldItem == newItem
            }
        }
    }
}