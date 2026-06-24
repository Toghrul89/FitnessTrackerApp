package com.example.it216groupproject1.ui.progress

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.it216groupproject1.data.model.DashboardStats
import com.example.it216groupproject1.data.model.FitnessGoal
import com.example.it216groupproject1.data.model.WorkoutType
import com.example.it216groupproject1.data.repository.FitnessRepository

class ProgressViewModel(
    private val repository: FitnessRepository
) : ViewModel() {
    private val _goal = MutableLiveData<FitnessGoal>()
    val goal: LiveData<FitnessGoal> = _goal

    private val _stats = MutableLiveData<DashboardStats>()
    val stats: LiveData<DashboardStats> = _stats

    fun loadProgress(type: WorkoutType? = null) {
        _goal.value = type?.let { repository.getGoal(it) } ?: repository.getGoal()
        _stats.value = repository.getDashboardStats(type)
    }
}
