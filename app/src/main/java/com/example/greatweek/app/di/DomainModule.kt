package com.example.greatweek.app.di

import com.example.greatweek.domain.usecase.goal.*
import com.example.greatweek.domain.usecase.role.*
import org.koin.dsl.module

val domainModule = module {

    factory<AddRoleUseCase> {
        AddRoleUseCase(roleRepository = get())
    }

    factory<AddGoalUseCase> {
        AddGoalUseCase(goalRepository = get())
    }

    factory<CompleteGoalUseCase> {
        CompleteGoalUseCase(goalRepository = get())
    }

    factory<DeleteRoleUseCase> {
        DeleteRoleUseCase(roleRepository = get())
    }

    factory<GetWeekUseCase> {
        GetWeekUseCase(goalRepository = get())
    }

    factory<GetRolesUseCase> {
        GetRolesUseCase(roleRepository = get(), goalRepository = get())
    }

    factory<GetRoleUseCase> {
        GetRoleUseCase(roleRepository = get())
    }

    factory<RenameRoleUseCase> {
        RenameRoleUseCase(roleRepository = get())
    }

    factory<EditGoalUseCase> {
        EditGoalUseCase(goalRepository = get())
    }

    factory<GetGoalUseCase> {
        GetGoalUseCase(goalRepository = get())
    }
}