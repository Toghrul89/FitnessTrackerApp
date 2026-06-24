package com.example.it216groupproject1.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.it216groupproject1.data.model.DashboardStats
import com.example.it216groupproject1.data.model.FitnessGoal
import com.example.it216groupproject1.data.repository.FitnessRepository

class DashboardViewModel(
    private val repository: FitnessRepository
) : ViewModel() {
    private val _goal = MutableLiveData<FitnessGoal>()
    val goal: LiveData<FitnessGoal> = _goal

    private val _stats = MutableLiveData<DashboardStats>()
    val stats: LiveData<DashboardStats> = _stats

    fun loadDashboard() {
        _goal.value = repository.getGoal()
        _stats.value = repository.getDashboardStats()
    }
}
