package com.example.greatweek.app.presentation.viewmodel

import androidx.lifecycle.*
import com.example.greatweek.domain.model.WeekDay
import com.example.greatweek.domain.usecase.goal.CompleteGoalUseCase
import com.example.greatweek.domain.usecase.goal.DropGoalToWeekUseCase
import com.example.greatweek.domain.usecase.goal.GetWeekUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ScheduleViewModel(
    private val getWeekUseCase: GetWeekUseCase,
    private val completeGoalUseCase: CompleteGoalUseCase,
    private val dropGoalToWeekUseCase: DropGoalToWeekUseCase
): ViewModel() {

    val week: LiveData<List<WeekDay>> = getWeekUseCase.execute().asLiveData()

    fun completeGoal(goalId: Int) = viewModelScope.launch {
        completeGoalUseCase.execute(goalId = goalId)
    }

    fun dropGoal(goalId: Int, weekDay: Int, isCommitment: Boolean) = viewModelScope.launch {
        dropGoalToWeekUseCase.execute(goalId, weekDay, isCommitment)
    }
}