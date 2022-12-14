package com.example.greatweek.domain.usecase.goal

import com.example.greatweek.domain.model.Goal
import com.example.greatweek.domain.repository.GoalRepository

class DropGoalToWeekUseCase(private val goalRepository: GoalRepository) {
    suspend fun execute(goalId: Int, weekDay: Int, isCommitment: Boolean) {
        val goal = goalRepository.getGoal(goalId = goalId)
        goalRepository.editGoal(
            Goal(
                id = goalId,
                title = goal.title,
                description = goal.description,
                role = goal.role,
                weekday = weekDay,
                commitment = isCommitment
            )
        )
    }
}