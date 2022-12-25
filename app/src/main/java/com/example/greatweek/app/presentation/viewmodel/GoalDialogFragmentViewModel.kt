package com.example.greatweek.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.greatweek.domain.model.Goal
import com.example.greatweek.domain.model.Role
import com.example.greatweek.domain.usecase.goal.AddGoalUseCase
import com.example.greatweek.domain.usecase.goal.EditGoalUseCase
import com.example.greatweek.domain.usecase.goal.GetGoalUseCase
import com.example.greatweek.domain.usecase.role.GetRoleUseCase
import com.example.greatweek.domain.usecase.role.GetRolesUseCase
import kotlinx.coroutines.flow.Flow

class GoalDialogFragmentViewModel(
    private val addGoalUseCase: AddGoalUseCase,
    private val getRoleUseCase: GetRoleUseCase,
    private val getRolesUseCase: GetRolesUseCase,
    private val editGoalUseCase: EditGoalUseCase,
    private val getGoalUseCase: GetGoalUseCase
) : ViewModel() {

    private var _id: Int = 0
    val id: Int get() = _id

    private var _title: String = ""
    val title: String get() = _title

    private var _description: String = ""
    val description: String get() = _description

    private var _roleId: Int? = null
    val roleId: Int? get() = _roleId

    private var _roleName: String = ""
    val roleName: String get() = _roleName

    private var _weekday: Int = 0
    val weekday: Int get() = _weekday

    private var _commitment: Boolean = false
    val commitment: Boolean get() = _commitment

    fun getGoal() {
        val goal = getGoalUseCase.execute(goalId = _id)
        _title = goal.title
        _description = goal.description
        _roleId = goal.roleId
        _weekday = goal.weekday
        _commitment = goal.commitment
        getRole()
    }

    fun getRole() {
        _roleName = getRoleUseCase.execute(roleId = _roleId!!).name
    }

    fun getRoles(): Flow<List<Role>> {
        return getRolesUseCase.execute()
    }

    fun editGoal() {
        editGoalUseCase.execute(
            Goal(
                id = id,
                title = title,
                description = description,
                roleId = roleId!!,
                weekday = weekday,
                commitment = commitment
            )
        )
    }

    fun addGoal() {
        addGoalUseCase.execute(
            Goal(
                title = title,
                description = description,
                roleId = roleId!!,
                weekday = weekday,
                commitment = commitment
            )
        )
    }

    fun setId(goalId: Int) {
        _id = goalId
    }

    fun setRoleId(roleId: Int) {
        _roleId = roleId
    }

    fun setWeekDay(weekDay: Int) {
        _weekday = weekDay
    }

    fun setRoleName(roleName: String) {
        _roleName = roleName
    }

    fun setGoal(title: String, description: String, commitment: Boolean) {
        _title = title
        _description = description
        _commitment = commitment
    }
}