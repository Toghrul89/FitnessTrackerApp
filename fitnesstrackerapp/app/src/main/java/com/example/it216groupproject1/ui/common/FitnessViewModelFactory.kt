package com.example.it216groupproject1.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.it216groupproject1.data.repository.FitnessRepository
import com.example.it216groupproject1.ui.dashboard.DashboardViewModel
import com.example.it216groupproject1.ui.goal.GoalViewModel
import com.example.it216groupproject1.ui.history.HistoryViewModel
import com.example.it216groupproject1.ui.progress.ProgressViewModel
import com.example.it216groupproject1.ui.workout.WorkoutViewModel

class FitnessViewModelFactory(
    private val repository: FitnessRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> DashboardViewModel(repository) as T
            modelClass.isAssignableFrom(GoalViewModel::class.java) -> GoalViewModel(repository) as T
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> HistoryViewModel(repository) as T
            modelClass.isAssignableFrom(ProgressViewModel::class.java) -> ProgressViewModel(repository) as T
            modelClass.isAssignableFrom(WorkoutViewModel::class.java) -> WorkoutViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
